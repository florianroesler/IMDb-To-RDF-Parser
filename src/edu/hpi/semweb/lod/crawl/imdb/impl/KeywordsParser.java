package edu.hpi.semweb.lod.crawl.imdb.impl;

import java.util.List;

import edu.hpi.semweb.lod.crawl.imdb.CleaningHelper;
import edu.hpi.semweb.lod.crawl.imdb.IMDBMovie;
import edu.hpi.semweb.lod.crawl.imdb.IMDBParser;
import edu.hpi.semweb.lod.crawl.imdb.IMDBRDFBuilder;

public class KeywordsParser extends IMDBParser{

	public KeywordsParser(boolean isPatchedFile) {
		super(isPatchedFile);
	}

	@Override
	protected String defineFileName() {
		return "keywords.list";
	}

	@Override
	protected String defineEncoding() {
		return "Windows-1252";
	}

	@Override
	protected void onNewLine(String line) {
		if(line.length()==0) return;
		if(line.contains("{")) return;
		List<String> tiles = CleaningHelper.removeEmptyElements(line.split("\t"));

		IMDBMovie movie = new IMDBMovie(tiles.get(0));
		String keyword = tiles.get(1);
		writeRDF(IMDBRDFBuilder.hpilodMovie(movie.toString()), IMDBRDFBuilder.keyword(), IMDBRDFBuilder.string(keyword));
	}

	@Override
	protected void onFileEnd() {
		
	}

	@Override
	protected String defineRelevanceStartingLine() {
		return "====================";
	}

	@Override
	protected String defineRelevanceEndingLine() {
		return null;
	}

}
