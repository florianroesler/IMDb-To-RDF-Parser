package edu.hpi.semweb.lod.crawl.imdb;

public class IMDBProducersParser extends IMDBGenericPersonParser{

	@Override
	protected String defineFileName() {
		return "producers.list";
	}

	@Override
	protected String defineRelevanceStartingLine() {
		return "----                    ------";
	}

	@Override
	protected String defineRelevanceEndingLine() {
		return "---------------------------------------------------------------------------";
	}

}
