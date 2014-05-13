package edu.hpi.semweb.lod.schedule;

import edu.hpi.semweb.lod.crawl.imdb.IMDBAkaNamesParser;
import edu.hpi.semweb.lod.crawl.imdb.IMDBAkaTitlesParser;
import edu.hpi.semweb.lod.crawl.imdb.IMDBAlternateVersionsParser;
import edu.hpi.semweb.lod.crawl.imdb.IMDBBiographiesParser;
import edu.hpi.semweb.lod.crawl.imdb.IMDBBusinessParser;
import edu.hpi.semweb.lod.crawl.imdb.IMDBCinematographersParser;
import edu.hpi.semweb.lod.crawl.imdb.IMDBComposersParser;
import edu.hpi.semweb.lod.crawl.imdb.IMDBCostumeDesigners;
import edu.hpi.semweb.lod.crawl.imdb.IMDBDirectorsParser;
import edu.hpi.semweb.lod.crawl.imdb.IMDBEditorsParser;
import edu.hpi.semweb.lod.crawl.imdb.IMDBGenericPersonParser;
import edu.hpi.semweb.lod.crawl.imdb.IMDBMiscFilmographyParser;
import edu.hpi.semweb.lod.crawl.imdb.IMDBMoviesParser;
import edu.hpi.semweb.lod.crawl.imdb.IMDBProducersParser;
import edu.hpi.semweb.lod.crawl.imdb.IMDBProductionDesigners;
import edu.hpi.semweb.lod.crawl.imdb.IMDBRatingsParser;
import edu.hpi.semweb.lod.crawl.imdb.IMDBWritersParser;

public class PrimitiveScheduler extends IScheduler{

	
	public static void main(String[] args){

		IMDBWritersParser crawler = new IMDBWritersParser();
		crawler.run();
	
	}
}
