package edu.hpi.semweb.lod.crawl.imdb.cmd;

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

public class CmdRunner {

	private static final String download = "download";
	private static final String patch = "patch";
	private static final String parseOriginals = "parseOriginals";
	private static final String parsePatches = "parsePatches";
	private static final String createDiff = "createDiff";
	private static final String help = "help";

	public static void main(String[] args) throws ParseException {
		Options options = new Options();
		
		options.addOption(download, false, "Downloads the initial IMDB-dump via FTP");
		options.addOption(patch, false, "Updates the IMDB-dump via FTP");
		options.addOption(parseOriginals, false, "Creates a parsed dump of the original folder");
		options.addOption(parsePatches, false, "Creates a parsed dump of the patched folder");
		options.addOption(createDiff, false, "Creates a diff between the original and patched folder");
		options.addOption(help, false, "Outputs the commandline options");

		
		CommandLineParser parser = new BasicParser();
		CommandLine cmd = parser.parse( options, args);
		
		if(cmd.hasOption(help) || cmd.getOptions().length == 0){
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("*.jar", options );
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

	}

}
