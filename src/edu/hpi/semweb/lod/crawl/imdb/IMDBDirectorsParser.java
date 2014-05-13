package edu.hpi.semweb.lod.crawl.imdb;

public class IMDBDirectorsParser extends IMDBGenericPersonParser{

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
