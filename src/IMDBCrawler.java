import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;


public class IMDBCrawler extends WebCrawler{

	private static final String base = "http://www.imdb.com/search/title?at=0&count=100&sort=moviemeter,asc&start=1&title_type=feature";
	
	@Override
	protected String defineSeedPage() {
		return base;
	}

	@Override
	protected boolean shouldFollowLink(String link) {
		return link.contains("search/title?at=0&count=100");
	}

	@Override
	protected Collection<Triple> extract(TagNode rootNode) {
		List<Triple> triples = new LinkedList<Triple>();
		try {
			triples = retrieveIMDBRecords(rootNode);
		} catch (XPatherException e) {
			e.printStackTrace();
		}
		return triples;
	}
	
	private static List<Triple> retrieveIMDBRecords(TagNode node) throws XPatherException{
		List<Triple> triples = new LinkedList<Triple>();
		Object[] foundObjects = node.evaluateXPath(".//*[@id='main']/table/tbody/tr/td[@class='image']/a");
		for(Object o:foundObjects){
			TagNode foundNode = (TagNode)o;
			String link = foundNode.getAttributeByName("href");

			triples.add(new Triple(link, "a", "link"));
		}
		return triples;

	}

}
