package edu.hpi.semweb.lod.crawl.imdb.impl;

import edu.hpi.semweb.lod.crawl.imdb.IMDBGenericPersonParser;
import edu.hpi.semweb.lod.crawl.imdb.IMDBRDFBuilder;

public class CostumeDesigners extends IMDBGenericPersonParser{

	public CostumeDesigners(boolean isPatchedFile) {
		super(isPatchedFile);
	}

	@Override
	public String defineFileName() {
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
	
	@Override
	protected String definePersonRDFProperty() {
		return IMDBRDFBuilder.costumeDesigner();
	}
	
	@Override
	protected String definePersonRDFType() {
		return IMDBRDFBuilder.costumeDesignerType();
	}

}
