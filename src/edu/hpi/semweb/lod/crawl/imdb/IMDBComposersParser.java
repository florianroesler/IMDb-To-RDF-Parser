package edu.hpi.semweb.lod.crawl.imdb;

public class IMDBComposersParser extends IMDBGenericPersonParser{

	@Override
	protected String defineFileName() {
		return "composers.list";
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
