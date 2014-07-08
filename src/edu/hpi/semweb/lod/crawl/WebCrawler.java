package edu.hpi.semweb.lod.crawl;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;


public abstract class WebCrawler extends ICrawler {
	private static HtmlCleaner cleaner = new HtmlCleaner();
	protected Set<String> alreadyVisitedPages = new HashSet<String>();
	private Queue<WebLink> pagesToVisit = new PriorityQueue<WebLink>();
	
	protected abstract String defineSeedPage();
	protected abstract boolean shouldFollowLink(String link);
	protected abstract Collection<String> extract(TagNode rootNode);

	protected abstract String modifyLink(String link);
	
	private static String baseUrl;

	public WebCrawler() {

	}
	
	@Override
	protected void startCrawling() {
		String fullUrl = defineSeedPage();
		fullUrl = fullUrl.substring(fullUrl.indexOf("www")).replace("www.", "");
		baseUrl = "http://"+fullUrl.substring(0, fullUrl.indexOf("/"));

		WebLink seedLink = new WebLink(defineSeedPage().replace("www.", ""), 0, "http://imdb.com");
		pagesToVisit.add(seedLink);
		while(!isStopped() && !pagesToVisit.isEmpty()){
			crawlPage(pagesToVisit.poll());
		}
	}

	private void crawlPage(WebLink webLink){

		String link = webLink.getLink();
		
		String modifiedLink = modifyLink(link);
		if(modifiedLink != null){
			link = modifiedLink;
		}
		
		link = link.replaceAll("count=\\d+", "count=100");
		link = link.replaceAll("&num_votes=\\d+,", "");
		
		String linkWithoutToken = link.replaceAll("&tok=.*", "");
		webLink.setLink(link);
		if(alreadyVisitedPages.contains(link) || alreadyVisitedPages.contains(linkWithoutToken)) return;

		System.out.println("Crawl "+link);
		alreadyVisitedPages.add(link);
		alreadyVisitedPages.add(linkWithoutToken);
		

		TagNode rootNode = null;
		try {
			rootNode = retrieveCleanHTML(webLink);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if(rootNode == null) return;

		Collection<String> triples = extract(rootNode);

		try {
			if(triples.size()>0){
				saveTriples(triples);
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		try {
			collectLinks(rootNode, webLink.getDepth()+1, webLink.getLink());
		} catch (XPatherException e) {
			e.printStackTrace();
		}		

	}

	private void collectLinks(TagNode root, int depth, String foundOn) throws XPatherException{
		Object[] foundObjects = root.evaluateXPath("//a/[@href]");
		for(Object o:foundObjects){
			TagNode foundNode = (TagNode)o;
			String link = foundNode.getAttributeByName("href");
			if(link.length()>0 && link.startsWith("/")){
				link=baseUrl+link;
			}

			if(shouldFollowLink(link)){
				pagesToVisit.add(new WebLink(link, depth, foundOn));
			}
		}
	}

	protected static TagNode retrieveCleanHTML(WebLink link) throws IOException {
		URLConnection connection = new URL(link.getLink()).openConnection(Proxy.NO_PROXY);
		connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
		connection.setRequestProperty("Connection", "keep-alive");
		if(link.getFoundOn()!=null){
            connection.setRequestProperty("Referer", link.getFoundOn());
		}
		
		connection.connect();


		BufferedReader r  = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));

		StringBuilder sb = new StringBuilder();
		String line;

		while ((line = r.readLine()) != null) {
			sb.append(line);
		}

		r.close();

		String parsedHtml = sb.toString();

		TagNode root = cleaner.clean(parsedHtml);

		return root;
	}


}
