package edu.hpi.semweb.lod.crawl.imdb.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.hpi.semweb.lod.crawl.imdb.CleaningHelper;
import edu.hpi.semweb.lod.crawl.imdb.IMDBMovie;
import edu.hpi.semweb.lod.crawl.imdb.IMDBParser;
import edu.hpi.semweb.lod.crawl.imdb.IMDBRDFBuilder;

public class MoviesParser extends IMDBParser{

	public MoviesParser(boolean isPatchedFile) {
		super(isPatchedFile);
		// TODO Auto-generated constructor stub
	}

	Map<String,Integer> map = new HashMap<String, Integer>();
	
	@Override
	protected String defineFileName() {
		return "movies.list";
	}

	@Override
	protected void onNewLine(String line) {
		if(line.length() == 0 || line.contains("{")) return;
		
		List<String> tiles = CleaningHelper.removeEmptyElements(line.split("\t"));
		
		//String titlePart = tiles.get(0);
		//String yearPart = tiles.get(1).trim();
		
		//String title = RegexHelper.findFirstOccurence(titlePart, ".+?\\s\\(").replace("(", "").replace("\"", "").trim();
		
		IMDBMovie movie = new IMDBMovie(line);
		String titleAndYear = movie.toString();
		writeRDF(movie.getTitle(), IMDBRDFBuilder.is(), IMDBRDFBuilder.movie(movie.toString()));

	}

	@Override
	protected String defineEncoding() {
		return "Windows-1252";
	}

	@Override
	protected String defineRelevanceStartingLine() {
		return "===========";
	}

	@Override
	protected String defineRelevanceEndingLine() {
		return "--------------------------------------------------------------------------------";
	}

	@Override
	protected void onFileEnd() {

	}

}
