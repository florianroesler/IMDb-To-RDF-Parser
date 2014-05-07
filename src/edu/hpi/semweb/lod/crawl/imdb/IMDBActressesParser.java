package edu.hpi.semweb.lod.crawl.imdb;

public class IMDBActressesParser extends IMDBActorsParser{

	@Override
	protected String defineInputFilePath() {
		return "/Users/froesler/Downloads/moviedb-3.24/lists/actresses.list";
	}
}
