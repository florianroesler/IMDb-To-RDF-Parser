package edu.hpi.semweb.lod.schedule;

import java.io.IOException;
import java.text.ParseException;

import edu.hpi.semweb.lod.crawl.imdb.impl.MoviesParser;



public class PrimitiveScheduler extends IScheduler{


	public static void main(String[] args) throws IOException, ParseException{

		new MoviesParser().run();
	}
}
