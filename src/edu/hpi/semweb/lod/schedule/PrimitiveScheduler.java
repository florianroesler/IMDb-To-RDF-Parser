package edu.hpi.semweb.lod.schedule;

import java.io.IOException;
import java.text.ParseException;

import edu.hpi.semweb.lod.crawl.imdb.diff.Patcher;
import edu.hpi.semweb.lod.crawl.imdb.impl.ActorsParser;
import edu.hpi.semweb.lod.crawl.imdb.impl.MoviesParser;



public class PrimitiveScheduler extends IScheduler{


	public static void main(String[] args) throws IOException, ParseException{
<<<<<<< HEAD
		new MoviesParser(false).run();;
=======
		//Patcher.diffParsedAndOriginalFolder();
		new ActorsParser(false).run();
>>>>>>> d0d9db0dc8d153e92e5a16d7dc6b2e9e77994b36
	}
}
