package edu.hpi.semweb.lod.crawl.imdb;

public class IMDBDirectorsParser extends IMDBToCSVParser{

	@Override
	protected String defineFileName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String defineEncoding() {
		return "Windows-1252";
	}

	@Override
	protected void onNewLine(String line) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onFileEnd() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected String defineRelevanceStartingLine() {
		return "Name			Titles";
	}

	@Override
	protected String defineRelevanceEndingLine() {
		return "-----------------------------------------------------------------------------";
	}

}
