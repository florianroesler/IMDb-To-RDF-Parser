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
		
		if(link.contains("imdb.com/search/title") && link.contains("year=") && link.contains("sort=moviemeter,asc")){
			return true;
		}
		return false;
	}
	
	@Override
	protected String modifyLink(String link) {
		if(link.contains("year=")){
			if(link.contains("count=")){
				link = link.replaceAll("count=\\d+", "count=100");
			}else{
				link = link+"&count=100";
			}
		}
		return link;	
		
	}
}
