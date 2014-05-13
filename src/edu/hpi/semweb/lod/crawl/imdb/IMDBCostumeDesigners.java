package edu.hpi.semweb.lod.crawl.imdb;

public class IMDBCostumeDesigners extends IMDBGenericPersonParser{

	@Override
	protected String defineFileName() {
		return "costume-designers.list";
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
