import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;


public abstract class WebCrawler extends ICrawler {
	private static HtmlCleaner cleaner = new HtmlCleaner();
	private Set<String> alreadyVisitedPages = new HashSet<String>();

	protected abstract String defineSeedPage();
	protected abstract boolean shouldFollowLink(String link);
	protected abstract Collection<Triple> extract(TagNode rootNode);

	private static String baseUrl;
	@Override
	protected void startCrawling() {
		String fullUrl = defineSeedPage();
		fullUrl = fullUrl.substring(fullUrl.indexOf("www"));
		baseUrl = "http://"+fullUrl.substring(0, fullUrl.indexOf("/"));
		
		crawlPage(defineSeedPage());
	}

	private void crawlPage(String link){
		
		if(!shouldFollowLink(link) || alreadyVisitedPages.contains(link)) return;
		
		alreadyVisitedPages.add(link);
		
		TagNode rootNode = null;
		try {
			rootNode = retrieveCleanHTML(link);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(rootNode == null) return;
		
		Collection<Triple> triples = extract(rootNode);
		
		try {
			saveTriples(triples);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		List<String> spideredLinks = null;
		try {
			spideredLinks = collectLinks(rootNode);
		} catch (XPatherException e) {
			e.printStackTrace();
		}
		
		if(spideredLinks == null) return;
		
		for(String linkToCheck : spideredLinks){
			if(linkToCheck.length()>0 && linkToCheck.startsWith("/")){
				crawlPage(baseUrl+linkToCheck);
			}else{
				crawlPage(linkToCheck);
			}
		}
		
	}
	
	private List<String> collectLinks(TagNode root) throws XPatherException{
		Object[] foundObjects = root.evaluateXPath("//a/[@href]");
		List<String> links = new LinkedList<String>();
		for(Object o:foundObjects){
			TagNode foundNode = (TagNode)o;
			String link = foundNode.getAttributeByName("href");
			links.add(link);
		}
		return links;
	}

	protected static TagNode retrieveCleanHTML(final String urlPath) throws IOException {
		URLConnection connection = new URL(urlPath).openConnection();
		connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
		connection.connect();

		BufferedReader r  = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));

		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = r.readLine()) != null) {
			sb.append(line);
		}
		String parsedHtml = sb.toString();

		TagNode root = cleaner.clean(parsedHtml);

		return root;
	}


}
