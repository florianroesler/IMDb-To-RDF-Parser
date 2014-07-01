package edu.hpi.semweb.lod.crawl.imdb.impl;

import edu.hpi.semweb.lod.crawl.imdb.IMDBGenericPersonParser;
import edu.hpi.semweb.lod.crawl.imdb.IMDBRDFBuilder;

public class DirectorsParser extends IMDBGenericPersonParser{

	public DirectorsParser(boolean isPatchedFile) {
		super(isPatchedFile);
	}

	@Override
	public String defineFileName() {
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

	@Override
	protected String definePersonRDFProperty() {
		return IMDBRDFBuilder.director();
	}
	
	@Override
	protected String definePersonRDFType() {
		return IMDBRDFBuilder.directorType();
	}
}
