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
	private Set<String> alreadyVisitedPages = new HashSet<String>();
	private Queue<WebLink> pagesToVisit = new PriorityQueue<WebLink>();
	protected abstract String defineSeedPage();
	protected abstract boolean shouldFollowLink(String link);
	protected abstract Collection<Quad> extract(TagNode rootNode);

	private static String baseUrl;

	public WebCrawler() {

	}
	
	@Override
	protected void startCrawling() {
		String fullUrl = defineSeedPage();
		fullUrl = fullUrl.substring(fullUrl.indexOf("www"));
		baseUrl = "http://"+fullUrl.substring(0, fullUrl.indexOf("/"));

		WebLink seedLink = new WebLink(defineSeedPage(), 0);
		pagesToVisit.add(seedLink);
		while(!isStopped() && !pagesToVisit.isEmpty()){
			crawlPage(pagesToVisit.poll());
		}
	}

	private void crawlPage(WebLink webLink){

		String link = webLink.getLink();
		if(alreadyVisitedPages.contains(link)) return;

		System.out.println("Crawl "+link);
		alreadyVisitedPages.add(link);

		TagNode rootNode = null;
		try {
			rootNode = retrieveCleanHTML(link);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if(rootNode == null) return;

		Collection<Quad> triples = extract(rootNode);

		try {
			saveTriples(triples);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		try {
			collectLinks(rootNode, webLink.getDepth()+1);
		} catch (XPatherException e) {
			e.printStackTrace();
		}		

	}

	private void collectLinks(TagNode root, int depth) throws XPatherException{
		Object[] foundObjects = root.evaluateXPath("//a/[@href]");
		for(Object o:foundObjects){
			TagNode foundNode = (TagNode)o;
			String link = foundNode.getAttributeByName("href");
			if(link.length()>0 && link.startsWith("/")){
				link=baseUrl+link;
			}

			if(shouldFollowLink(link)){
				pagesToVisit.add(new WebLink(link, depth));
			}
		}
	}

	protected static TagNode retrieveCleanHTML(final String urlPath) throws IOException {
		URLConnection connection = new URL(urlPath).openConnection(Proxy.NO_PROXY);
		connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
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
