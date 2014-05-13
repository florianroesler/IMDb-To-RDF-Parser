package edu.hpi.semweb.lod.schedule;

import edu.hpi.semweb.lod.crawl.imdb.impl.CountriesParser;



public class PrimitiveScheduler extends IScheduler{

	
	public static void main(String[] args){

		CountriesParser crawler = new CountriesParser();
		crawler.run();
	
	}
}
