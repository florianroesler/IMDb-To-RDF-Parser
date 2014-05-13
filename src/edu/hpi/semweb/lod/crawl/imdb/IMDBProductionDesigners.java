package edu.hpi.semweb.lod.crawl.imdb;

public class IMDBProductionDesigners extends IMDBGenericPersonParser{

	@Override
	protected String defineFileName() {
		return "production-designers.list";
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
