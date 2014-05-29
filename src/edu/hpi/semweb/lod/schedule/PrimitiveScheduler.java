package edu.hpi.semweb.lod.schedule;

import java.io.IOException;
import java.text.ParseException;

import edu.hpi.semweb.lod.crawl.imdb.diff.Patcher;
import edu.hpi.semweb.lod.crawl.imdb.impl.ActorsParser;
import edu.hpi.semweb.lod.crawl.imdb.impl.MoviesParser;
import edu.hpi.semweb.lod.crawl.imdb.impl.SoundtracksParser;
import edu.hpi.semweb.lod.crawl.imdb.impl.TriviaParser;



public class PrimitiveScheduler extends IScheduler{


	public static void main(String[] args) throws IOException, ParseException{
		new SoundtracksParser(false).run();;

	}
}
