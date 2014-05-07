package edu.hpi.semweb.lod.schedule;

import edu.hpi.semweb.lod.crawl.imdb.IMDBAkaNamesParser;
import edu.hpi.semweb.lod.crawl.imdb.IMDBRatingsParser;

public class PrimitiveScheduler extends IScheduler{

	
	public static void main(String[] args){
		//IMDBMoviesParser crawler = new IMDBMoviesParser();
		//LDSpiderCrawler crawler = new LDSpiderCrawler();
		//IMDBRatingsParser crawler = new IMDBRatingsParser();
		//IMDBActorsParser crawler = new IMDBActorsParser();
		//IMDBActressesParser crawler = new IMDBActressesParser();
		IMDBAkaNamesParser crawler = new IMDBAkaNamesParser();
		crawler.run();
	
	}
}
