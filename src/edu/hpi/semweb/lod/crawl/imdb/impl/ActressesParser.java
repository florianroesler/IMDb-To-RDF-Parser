package edu.hpi.semweb.lod.crawl.imdb.impl;

public class ActressesParser extends ActorsParser{

	public ActressesParser(boolean isPatchedFile) {
		super(isPatchedFile);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String defineInputFilePath() {
		return "/Users/froesler/Downloads/moviedb-3.24/lists/actresses.list";
	}
}
