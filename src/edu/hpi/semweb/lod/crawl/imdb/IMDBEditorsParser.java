package edu.hpi.semweb.lod.crawl.imdb;

public class IMDBEditorsParser extends IMDBGenericPersonParser{

	@Override
	protected String defineFileName() {
		return "editors.list";
	}

	@Override
	protected String defineRelevanceStartingLine() {
		return "----                    ------";
	}

	@Override
	protected String defineRelevanceEndingLine() {
		return "-----------------------------------------------------------------------------";
	}

}
