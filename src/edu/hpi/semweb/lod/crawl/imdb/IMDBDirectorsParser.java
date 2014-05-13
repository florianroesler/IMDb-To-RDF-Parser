package edu.hpi.semweb.lod.crawl.imdb;

import java.util.List;

public class IMDBDirectorsParser extends IMDBToCSVParser{

	private String currentDirector;
	
	
	@Override
	protected String defineFileName() {
		return "directors.list";
	}

	@Override
	protected String defineEncoding() {
		return "Windows-1252";
	}

	@Override
	protected void onNewLine(String line) {
		if(line.length()==0) return;
		List<String> tiles = CleaningHelper.removeEmptyElements(line.split("\t"));

		
		String titlePart = "";
		
		if(tiles.size() == 2){		
			//thats a new director and a trailing movie
			currentDirector = CleaningHelper.cleanActorName(tiles.get(0));
			titlePart = tiles.get(1);
		}else{
			titlePart = tiles.get(0);
		}
		
		if(titlePart.contains("{")) return;
		
		titlePart = titlePart.replace("(TV)", "").replace("(V)", "").replace("(VG)", "").replace("\"", "");
		
		String title = RegexHelper.findFirstOccurence(titlePart, ".+?\\s\\(").replace("(", "").trim();
		String year = CleaningHelper.removeRoundBrackets(RegexHelper.findFirstOccurence(titlePart, "\\(\\d+\\)"));
		writeCSV(currentDirector, title, year);
	}

	@Override
	protected void onFileEnd() {
		// TODO Auto-generated method stub
		
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
