package edu.hpi.semweb.lod.crawl.imdb;

import java.util.List;

public class IMDBCountriesParser extends IMDBToCSVParser {
	private String currentFilm;
	private String country;

	@Override
	protected String defineFileName() {
		return "countries.list";
	}

	@Override
	protected String defineEncoding() {
		
		return "Windows-1252";
	}

	@Override
	protected void onNewLine(String line) {

		if (RegexHelper.findFirstOccurence(line,"\\{*#*\\}").length() != 0) {
			return;
		}
		List<String> tiles = CleaningHelper.removeEmptyElements(line.split("\t"));
		currentFilm = tiles.get(0);
		currentFilm = currentFilm.replace("{{SUSPENDED}}", "").replace("(TV)", "").replace("(V)", "").replaceAll("\\(\\d+\\)", "").replace("(VG)", "").replace("(????)", "").trim();
		
		country = tiles.get(1);
		
		writeCSV(currentFilm, country);
		
	}

	@Override
	protected void onFileEnd() {
		
	}

	@Override
	protected String defineRelevanceStartingLine() {
		// TODO Auto-generated method stub
		return "==============";
	}

	@Override
	protected String defineRelevanceEndingLine() {
		return "--------------------------------------------------------------------------------";
	}
	
}
