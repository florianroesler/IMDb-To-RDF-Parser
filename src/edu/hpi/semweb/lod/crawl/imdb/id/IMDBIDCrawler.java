package edu.hpi.semweb.lod.crawl.imdb.id;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import edu.hpi.semweb.lod.crawl.WebCrawler;
import edu.hpi.semweb.lod.crawl.imdb.RegexHelper;
import edu.hpi.semweb.lod.data.Quad;


public class IMDBIDCrawler extends WebCrawler{

	private static final String base = "http://www.akas.imdb.com/search/title?at=0&count=100&sort=moviemeter,asc&start=1&title_type=feature,tv_series";
	
	@Override
	protected String defineSeedPage() {
		return base;
	}

	@Override
	protected boolean shouldFollowLink(String link) {
		return link.contains("search/title?at=0&count=100");
	}


	@Override
	protected Collection<String> extract(TagNode rootNode) {
		List<String> triples = new LinkedList<String>();
		try {
			triples = retrieveIMDBRecords(rootNode);
		} catch (XPatherException e) {
			e.printStackTrace();
		}
		return triples;
	}
	
	private static List<String> retrieveIMDBRecords(TagNode node) throws XPatherException{
		List<String> triples = new LinkedList<String>();
		Object[] foundTitles = node.evaluateXPath("//td[@class='title']");
		
		for(Object o:foundTitles){
			TagNode foundNode = (TagNode)o;
			String imdbId = foundNode.getElementsByName("a", false)[0].getAttributeByName("href");
			String title = foundNode.getElementsByName("a", false)[0].getText().toString();
			title = StringEscapeUtils.unescapeHtml4(title);
			
			TagNode yearNode = (TagNode)foundNode.evaluateXPath("//span[@class='year_type']")[0];
			String year = yearNode.getText().toString();
			
			year = RegexHelper.findFirstOccurence(year, "\\d{4}");
			
			
			triples.add(imdbId + "!_!"+title+"!_!"+year);

		}

		return triples;

	}


}
