package edu.hpi.semweb.lod.schedule;

import edu.hpi.semweb.lod.crawl.imdb.IMDBCountriesParser;



public class PrimitiveScheduler extends IScheduler{

	
	public static void main(String[] args){

		IMDBCountriesParser crawler = new IMDBCountriesParser();
		crawler.run();
	
	}
}
