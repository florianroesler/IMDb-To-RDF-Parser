package edu.hpi.semweb.lod.schedule;

import edu.hpi.semweb.lod.crawl.imdb.impl.CountriesParser;
import edu.hpi.semweb.lod.crawl.imdb.impl.MovieListParser;



public class PrimitiveScheduler extends IScheduler{

	
	public static void main(String[] args){

		//CountriesParser crawler = new CountriesParser();
		MovieListParser crawler = new MovieListParser();
		crawler.run();
	
	}
}
