package edu.hpi.semweb.lod.crawl.imdb;

public class IMDBCinematographersParser extends IMDBGenericPersonParser{

	@Override
	protected String defineFileName() {
		return "cinematographers.list";
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
