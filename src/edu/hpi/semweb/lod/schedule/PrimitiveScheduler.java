package edu.hpi.semweb.lod.schedule;

import java.io.IOException;
import java.text.ParseException;

import edu.hpi.semweb.lod.crawl.imdb.diff.Patcher;
import edu.hpi.semweb.lod.crawl.imdb.impl.ActorsParser;
import edu.hpi.semweb.lod.crawl.imdb.impl.ActressesParser;
import edu.hpi.semweb.lod.crawl.imdb.impl.AkaTitlesParser;
import edu.hpi.semweb.lod.crawl.imdb.impl.BiographiesParser;
import edu.hpi.semweb.lod.crawl.imdb.impl.KeywordsParser;
import edu.hpi.semweb.lod.crawl.imdb.impl.MoviesParser;
import edu.hpi.semweb.lod.crawl.imdb.impl.PlotParser;
import edu.hpi.semweb.lod.crawl.imdb.impl.ReleaseDatesParser;
import edu.hpi.semweb.lod.crawl.imdb.impl.RunningTimesParser;
import edu.hpi.semweb.lod.crawl.imdb.impl.SoundtracksParser;
import edu.hpi.semweb.lod.crawl.imdb.impl.TriviaParser;



public class PrimitiveScheduler extends IScheduler{


	public static void main(String[] args) throws IOException, ParseException{
		new BiographiesParser(false).run();
	}
}
