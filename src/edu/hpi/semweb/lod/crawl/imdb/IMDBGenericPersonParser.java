package edu.hpi.semweb.lod.crawl.imdb;

import java.util.List;

/**
 * Simple Parser for IMDB-dumps that follow the same structure as the directors.list
 */
public abstract class IMDBGenericPersonParser extends IMDBParser{

	
	public IMDBGenericPersonParser(boolean isPatchedFile) {
		super(isPatchedFile);
	}

	private String currentPerson;
	

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
			currentPerson = CleaningHelper.cleanActorName(tiles.get(0));
			titlePart = tiles.get(1);
		}else{
			titlePart = tiles.get(0);
		}
		
		if(titlePart.contains("{")) return;
		
		String year = CleaningHelper.removeRoundBrackets(RegexHelper.findFirstOccurence(titlePart, "\\(\\d+\\)"));
		
		titlePart = titlePart.replaceAll("\\(.+?\\)", "").replace("\"", "");
		
		String title = titlePart.trim();
		
		writeCSV(currentPerson, title, year);
	}

	@Override
	protected void onFileEnd() {
		
	}

}
