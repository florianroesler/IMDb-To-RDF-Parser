package edu.hpi.semweb.lod.crawl.imdb.cmd;

import java.io.IOException;
import java.util.Set;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import edu.hpi.semweb.lod.crawl.imdb.Config;
import edu.hpi.semweb.lod.crawl.imdb.IMDBParser;
import edu.hpi.semweb.lod.crawl.imdb.diff.IMDBDumpDownloader;
import edu.hpi.semweb.lod.crawl.imdb.diff.Patcher;
import edu.hpi.semweb.lod.crawl.imdb.diff.RDFDiffInterpreter;
import edu.hpi.semweb.lod.crawl.imdb.id.IMDBIDCrawler;
import edu.hpi.semweb.lod.crawl.imdb.id.IdMatcher;
import edu.hpi.semweb.lod.crawl.imdb.impl.MoviesParser;

public class CmdRunner {

	private static final String download = "download";
	private static final String patch = "patch";
	private static final String parseOriginals = "parseOriginals";
	private static final String parsePatches = "parsePatches";
	private static final String createDiff = "createDiff";
	private static final String help = "help";
	private static final String interpretDiff = "interpretDiff";
	private static final String init = "init";
	private static final String crawlIds = "crawlIds";
	private static final String matchIds = "matchIds";

	public static void main(String[] args) throws ParseException {
		Options options = new Options();
		
		options.addOption(download, false, "Downloads the initial IMDB-dump via FTP");
		options.addOption(patch, false, "Updates the IMDB-dump via FTP");
		options.addOption(parseOriginals, false, "Creates a parsed dump of the original folder");
		options.addOption(parsePatches, false, "Creates a parsed dump of the patched folder");
		options.addOption(createDiff, false, "Creates a diff between the original and patched folder");
		options.addOption(help, false, "Outputs the commandline options");
		options.addOption(interpretDiff, false, "Interprets the created diff and patches the database accordingly");
		options.addOption(init, false, "Initializes Required Folders and Init Files");
		options.addOption(crawlIds, false, "Crawls IMDB for the IMDB-Ids");
		options.addOption(matchIds, false, "Matches Crawled IDs with the parsed movies list");

		
		CommandLineParser parser = new BasicParser();
		CommandLine cmd = parser.parse( options, args);
		
		if(cmd.hasOption(help) || cmd.getOptions().length == 0){
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("*.jar", options );
		}
		
		if(cmd.hasOption(init)){
			Config c = new Config();
			c.hashCode();			
		}
		
		if(cmd.hasOption(download)){
			IMDBDumpDownloader.downloadDump();
		}
		
		if(cmd.hasOption(parseOriginals)){
			Set<IMDBParser> parsers = Config.PARSERS;
			for(IMDBParser p:parsers){
				p.setPatchedFile(false);
				p.run();
			}
		}
		
		if(cmd.hasOption(parsePatches)){
			Set<IMDBParser> parsers = Config.PARSERS;
			for(IMDBParser p:parsers){
				p.setPatchedFile(true);
				p.run();
			}
		}
		
		if(cmd.hasOption(interpretDiff)){
			new RDFDiffInterpreter().run();
		}
		
		if(cmd.hasOption(patch)){
			Patcher.patch();
		}
		
		if(cmd.hasOption(crawlIds)){
			IMDBIDCrawler crawler = new IMDBIDCrawler();
			crawler.run();
		}
		
		if(cmd.hasOption(matchIds)){
				MoviesParser moviesParser = new MoviesParser(false);
				moviesParser.setOnlyMatchIds(true);
				moviesParser.run();
		}

	}

}
