package edu.hpi.semweb.lod.crawl.imdb;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
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
	private static String CONFIGPATH = "imdbImporter.properties";
	static{
		File configFile = new File(CONFIGPATH);
		if(!configFile.exists()){
			PrintWriter writer = null;
			try {
				System.out.println("Created ConfigFile at: "+configFile.getCanonicalPath());
				configFile.createNewFile();
				writer = new PrintWriter(configFile);
				writer.write("dump.path = data/\n");
				writer.write("ftp.server = ftp.fu-berlin.de\n");
				writer.write("ftp.dumppath = /pub/misc/movies/database/\n");
				writer.write("ftp.diffpath = /pub/misc/movies/database/diffs/\n");
				writer.write("ftp.user = anonymous\n");
				writer.write("ftp.password =\n");

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				if(writer != null){
					writer.close();
				}
			}
		}

		try {
			System.out.println("Reading ConfigFile at: "+configFile.getCanonicalPath());
			properties.load(new FileInputStream(CONFIGPATH));
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
	public static final String FTPDUMPPATH = properties.getProperty("ftp.dumppath");
	public static final String FTPDIFFPATH = properties.getProperty("ftp.diffpath");
	public static final String FTPUSER = properties.getProperty("ftp.user");
	public static final String FTPPASSWORD = properties.getProperty("ftp.password");
	public static final Set<IMDBParser> PARSERS = new HashSet<IMDBParser>();

	static {

		for(String path: new String[]{ROOTPATH,DIFFPATH,ORIGINALPATH,ORIGINALPARSEDPATH,PATCHEDPATH,PATCHEDPARSEDPATH, RDFDIFFPATH}){
			File subDir = new File(path);
			boolean isDir = subDir.isDirectory();
			if(!isDir){
				subDir.mkdir();
				try {
					System.out.println("Creating missing dir:" + subDir.getCanonicalPath());
				} catch (IOException e) {
					e.printStackTrace();
				}
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
