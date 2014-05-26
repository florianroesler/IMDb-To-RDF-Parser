package edu.hpi.semweb.lod.crawl.imdb.impl;

import edu.hpi.semweb.lod.crawl.imdb.IMDBGenericPersonParser;

public class DirectorsParser extends IMDBGenericPersonParser{

	public DirectorsParser(boolean isPatchedFile) {
		super(isPatchedFile);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String defineFileName() {
		return "directors.list";
	}

	@Override
	protected String defineRelevanceStartingLine() {
		return "----			------";
	}

	@Override
	protected String defineRelevanceEndingLine() {
		return "-----------------------------------------------------------------------------";
	}

}
