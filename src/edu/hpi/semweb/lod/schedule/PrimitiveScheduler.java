package edu.hpi.semweb.lod.schedule;

import edu.hpi.semweb.lod.crawl.imdb.IMDBAkaNamesParser;
import edu.hpi.semweb.lod.crawl.imdb.IMDBAkaTitlesParser;
import edu.hpi.semweb.lod.crawl.imdb.IMDBAlternateVersionsParser;
import edu.hpi.semweb.lod.crawl.imdb.IMDBBiographiesParser;
import edu.hpi.semweb.lod.crawl.imdb.IMDBBusinessParser;
import edu.hpi.semweb.lod.crawl.imdb.IMDBRatingsParser;

public class PrimitiveScheduler extends IScheduler{

	
	public static void main(String[] args){
		//IMDBMoviesParser crawler = new IMDBMoviesParser();
		//LDSpiderCrawler crawler = new LDSpiderCrawler();
		//IMDBRatingsParser crawler = new IMDBRatingsParser();
		//IMDBActorsParser crawler = new IMDBActorsParser();
		//IMDBActressesParser crawler = new IMDBActressesParser();
		//IMDBAkaNamesParser crawler = new IMDBAkaNamesParser();
		//IMDBAkaTitlesParser crawler = new IMDBAkaTitlesParser();
		//IMDBAlternateVersionsParser crawler = new IMDBAlternateVersionsParser();
		//IMDBBiographiesParser crawler = new IMDBBiographiesParser();
		IMDBBusinessParser crawler = new IMDBBusinessParser();
		crawler.run();
	
	}
}
