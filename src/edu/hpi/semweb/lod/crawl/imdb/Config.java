package edu.hpi.semweb.lod.crawl.imdb;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import edu.hpi.semweb.lod.crawl.imdb.impl.ActorsParser;
import edu.hpi.semweb.lod.crawl.imdb.impl.ActressesParser;
import edu.hpi.semweb.lod.crawl.imdb.impl.AkaNamesParser;
import edu.hpi.semweb.lod.crawl.imdb.impl.AkaTitlesParser;
import edu.hpi.semweb.lod.crawl.imdb.impl.AlternateVersionsParser;
import edu.hpi.semweb.lod.crawl.imdb.impl.BiographiesParser;
import edu.hpi.semweb.lod.crawl.imdb.impl.BusinessParser;
import edu.hpi.semweb.lod.crawl.imdb.impl.CinematographersParser;
import edu.hpi.semweb.lod.crawl.imdb.impl.ComposersParser;
import edu.hpi.semweb.lod.crawl.imdb.impl.CostumeDesigners;
import edu.hpi.semweb.lod.crawl.imdb.impl.CountriesParser;
import edu.hpi.semweb.lod.crawl.imdb.impl.DirectorsParser;
import edu.hpi.semweb.lod.crawl.imdb.impl.EditorsParser;
import edu.hpi.semweb.lod.crawl.imdb.impl.KeywordsParser;
import edu.hpi.semweb.lod.crawl.imdb.impl.MiscFilmographyParser;
import edu.hpi.semweb.lod.crawl.imdb.impl.MovieLinkParser;
import edu.hpi.semweb.lod.crawl.imdb.impl.MoviesParser;
import edu.hpi.semweb.lod.crawl.imdb.impl.PlotParser;
import edu.hpi.semweb.lod.crawl.imdb.impl.ProducersParser;
import edu.hpi.semweb.lod.crawl.imdb.impl.ProductionDesigners;
import edu.hpi.semweb.lod.crawl.imdb.impl.RatingsParser;
import edu.hpi.semweb.lod.crawl.imdb.impl.ReleaseDatesParser;
import edu.hpi.semweb.lod.crawl.imdb.impl.RunningTimesParser;
import edu.hpi.semweb.lod.crawl.imdb.impl.SoundtracksParser;
import edu.hpi.semweb.lod.crawl.imdb.impl.TriviaParser;
import edu.hpi.semweb.lod.crawl.imdb.impl.WritersParser;

public class Config {

	private static final Properties properties = new Properties();
	static{
		try {
			properties.load(new FileInputStream("config/imdb.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static final String ROOTPATH = properties.getProperty("dump.path");

	public static final String DIFFPATH = ROOTPATH+"diffs/";


	public static final String ORIGINALPATH = ROOTPATH+"original/";
	public static final String ORIGINALPARSEDPATH = ORIGINALPATH+"parsed/";
	
	public static final String PATCHEDPATH = ROOTPATH+"patched/";
	public static final String PATCHEDPARSEDPATH = PATCHEDPATH+"parsed/";
	
	public static final String RDFDIFFPATH = ROOTPATH+"RDF_Diff/";

	public static final String FTPSERVER = properties.getProperty("ftp.server");
	public static final String FTPPATH = properties.getProperty("ftp.path");
	public static final String FTPUSER = properties.getProperty("ftp.user");
	public static final String FTPPASSWORD = properties.getProperty("ftp.password");
	public static final Set<IMDBParser> PARSERS = new HashSet<IMDBParser>();

	static {
		File rootDir = new File(ROOTPATH);
		if(ROOTPATH == null || !rootDir.isDirectory()){
			throw new IllegalArgumentException("Path to IMDB-dumps is not correctly defined. Please check the config.");
		}

		for(String path: new String[]{DIFFPATH,ORIGINALPATH,ORIGINALPARSEDPATH,PATCHEDPATH,PATCHEDPARSEDPATH, RDFDIFFPATH}){
			File subDir = new File(path);
			boolean isDir = subDir.isDirectory();
			if(!isDir){
				subDir.mkdir();
				System.out.println("Creating missing dir:" + path);
			}
		}
	}	
	
	static{
		PARSERS.add(new ActorsParser(false));
		PARSERS.add(new ActressesParser(false));
		PARSERS.add(new AkaNamesParser(false));
		PARSERS.add(new AkaTitlesParser(false));
		PARSERS.add(new AlternateVersionsParser(false));
		PARSERS.add(new BiographiesParser(false));
		PARSERS.add(new BusinessParser(false));
		PARSERS.add(new CinematographersParser(false));
		PARSERS.add(new ComposersParser(false));
		PARSERS.add(new CostumeDesigners(false));
		PARSERS.add(new CountriesParser(false));
		PARSERS.add(new DirectorsParser(false));
		PARSERS.add(new EditorsParser(false));
		PARSERS.add(new KeywordsParser(false));
		PARSERS.add(new MiscFilmographyParser(false));
		PARSERS.add(new MovieLinkParser(false));
		PARSERS.add(new MoviesParser(false));
		PARSERS.add(new PlotParser(false));
		PARSERS.add(new ProducersParser(false));
		PARSERS.add(new ProductionDesigners(false));
		PARSERS.add(new RatingsParser(false));
		PARSERS.add(new ReleaseDatesParser(false));
		PARSERS.add(new RunningTimesParser(false));
		PARSERS.add(new SoundtracksParser(false));
		PARSERS.add(new TriviaParser(false));
		PARSERS.add(new WritersParser(false));
	}

}
