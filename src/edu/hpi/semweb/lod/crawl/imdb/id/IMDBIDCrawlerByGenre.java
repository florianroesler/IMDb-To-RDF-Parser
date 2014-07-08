package edu.hpi.semweb.lod.crawl.imdb.id;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import edu.hpi.semweb.lod.crawl.WebCrawler;
import edu.hpi.semweb.lod.crawl.WebLink;
import edu.hpi.semweb.lod.crawl.imdb.RegexHelper;
import edu.hpi.semweb.lod.data.Quad;


public class IMDBIDCrawlerByGenre extends WebCrawler{

	private static final String base = "http://www.akas.imdb.com/genre/?ref_=nv_ch_gr_5";


	public IMDBIDCrawlerByGenre(){

	}

	@Override
	protected String defineSeedPage() {
		return base;
	}

	@Override
	protected boolean shouldFollowLink(String link) {
		if(link.contains("imdb.com/genre")){
			return true;
		}
		
		if(link.contains("search/title") && link.contains("genres=")){
			return true;
		}
		return false;
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

	@Override
	protected String modifyLink(String link) {
		// TODO Auto-generated method stub
		return null;
	}


}
