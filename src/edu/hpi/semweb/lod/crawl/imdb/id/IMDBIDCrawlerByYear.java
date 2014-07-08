package edu.hpi.semweb.lod.crawl.imdb.id;

public class IMDBIDCrawlerByYear extends IMDBIDCrawlerByGenre{

	private static final String base = "http://www.akas.imdb.com/year";

	
	@Override
	protected String defineSeedPage() {
		return base;
	}
	
	@Override
	protected boolean shouldFollowLink(String link) {
		if(link.contains("imdb.com/year")){
			return true;
		}
		
		if(link.contains("imdb.com/search/title") && link.contains("year=")){
			return true;
		}
		return false;
	}
}
