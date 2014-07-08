package edu.hpi.semweb.lod.crawl.imdb.impl;

import java.util.List;

import edu.hpi.semweb.lod.crawl.imdb.CleaningHelper;
import edu.hpi.semweb.lod.crawl.imdb.IMDBMovie;
import edu.hpi.semweb.lod.crawl.imdb.IMDBParser;
import edu.hpi.semweb.lod.crawl.imdb.IMDBRDFBuilder;
import edu.hpi.semweb.lod.crawl.imdb.RegexHelper;

public class ReleaseDatesParser extends IMDBParser{

	public ReleaseDatesParser(boolean isPatchedFile) {
		super(isPatchedFile);
	}

	@Override
	public String defineFileName() {
		return "release-dates.list";
	}


	@Override
	protected void onNewLine(String line) {
		if(line.contains("{")) return;
		List<String> tiles = CleaningHelper.removeEmptyElements(line.split("\t"));
		IMDBMovie movie = new IMDBMovie(tiles.get(0));
		String releaseInfo = tiles.get(1);
		String regex = "(\\w+)(:)(.*)";
		String country = RegexHelper.returnGroup(releaseInfo, regex, 1);
		String date = RegexHelper.returnGroup(releaseInfo, regex, 3);
		
		writeRDF(IMDBRDFBuilder.hpilodMovie(movie.toString()), IMDBRDFBuilder.releaseDate(), IMDBRDFBuilder.string(date+" ("+country+")"));

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
