package edu.hpi.semweb.lod.crawl.imdb.id;

import edu.hpi.semweb.lod.crawl.imdb.Config;
import edu.hpi.semweb.lod.crawl.imdb.diff.CommandLineHelper;

public class IDCrawlerCommandLines {

	public static void combine(){
		String command = "cat "+Config.CRAWLRAWPATH+"* | LC_ALL='C' sort -u > "+Config.CRAWLCOMBINEDPATH+"combinedIds";
		System.out.println(command);
		CommandLineHelper.execCommand(command);
	}
	
}
