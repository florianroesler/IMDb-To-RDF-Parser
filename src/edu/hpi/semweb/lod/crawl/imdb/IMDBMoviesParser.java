package edu.hpi.semweb.lod.crawl.imdb;

import edu.hpi.semweb.lod.crawl.PlainTextCrawler;

public class IMDBMoviesParser extends PlainTextCrawler{

	@Override
	protected String defineInputFilePath() {
		return "/Users/froesler/Downloads/moviedb-3.24/lists/movies.list";
	}

	@Override
	protected void onNewLine(String line) {
		System.out.println(line);
	}

	@Override
	protected String defineEncoding() {
		return "Windows-1252";
	}

	@Override
	protected String defineRelevanceStartingLine() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String defineRelevanceEndingLine() {
		// TODO Auto-generated method stub
		return null;
	}

}
