package edu.hpi.semweb.lod.crawl.imdb.impl;

import java.util.List;

import edu.hpi.semweb.lod.crawl.imdb.CleaningHelper;
import edu.hpi.semweb.lod.crawl.imdb.IMDBMovie;
import edu.hpi.semweb.lod.crawl.imdb.IMDBParser;
import edu.hpi.semweb.lod.crawl.imdb.IMDBRDFBuilder;
import edu.hpi.semweb.lod.crawl.imdb.RegexHelper;

public class RunningTimesParser extends IMDBParser{

	public RunningTimesParser(boolean isPatchedFile) {
		super(isPatchedFile);
	}

	@Override
	protected String defineFileName() {
		return "running-times.list";
	}

	@Override
	protected String defineEncoding() {
		return "Windows-1252";
	}

	@Override
	protected void onNewLine(String line) {
		List<String> tiles = CleaningHelper.removeEmptyElements(line.split("\t"));
		
		if(tiles.size()<=1) return;
		
		IMDBMovie movie = new IMDBMovie(tiles.get(0));
		String timePart = tiles.get(1);
		String country = RegexHelper.returnGroup(timePart, "(\\w+)(:)", 1);
		String runtime;
		if(country.length()!=0){
			runtime = RegexHelper.returnGroup(timePart, "(:)(\\d+)", 2);
		}else{
			runtime = timePart;
		}
		String versionName;
		if(tiles.size() >= 3){
			versionName = CleaningHelper.removeRoundBrackets(tiles.get(2));
		}
		
		writeRDF(IMDBRDFBuilder.imdbMovie(movie.toString()), IMDBRDFBuilder.runtime(), IMDBRDFBuilder.string(runtime));
	}

	@Override
	protected void onFileEnd() {
		
	}

	@Override
	protected String defineRelevanceStartingLine() {
		return "==================";
	}

	@Override
	protected String defineRelevanceEndingLine() {
		return "--------------------------------------------------------------------------------";
	}

}
