package edu.hpi.semweb.lod.crawl.imdb.impl;

public class ActressesParser extends ActorsParser{

	public ActressesParser(boolean isPatchedFile) {
		super(isPatchedFile);
	}

	@Override
	protected String defineFileName() {
		return "actresses.list";
	}
}
