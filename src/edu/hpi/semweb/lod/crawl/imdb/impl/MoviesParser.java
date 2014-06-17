package edu.hpi.semweb.lod.crawl.imdb.impl;

import java.util.HashMap;
import java.util.Map;

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
	public String defineFileName() {
		return "movies.list";
	}

	@Override
	protected void onNewLine(String line) {
		if(line.length() == 0 || line.contains("{")) return;
	
		IMDBMovie movie = new IMDBMovie(line);
		writeRDF(IMDBRDFBuilder.hpilodMovie(movie.toString()), IMDBRDFBuilder.is(), IMDBRDFBuilder.film());
		writeRDF(IMDBRDFBuilder.hpilodMovie(movie.toString()), IMDBRDFBuilder.label(), IMDBRDFBuilder.string(movie.getTitle()));
		writeRDF(IMDBRDFBuilder.hpilodMovie(movie.toString()), IMDBRDFBuilder.name(), IMDBRDFBuilder.string(movie.getTitle()));
		//writeRDF(IMDBRDFBuilder.imdbMovie(movie.toString()), IMDBRDFBuilder.releaseDate(), IMDBRDFBuilder.string(movie.getYear()));

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
