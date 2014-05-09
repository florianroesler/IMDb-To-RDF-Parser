package edu.hpi.semweb.lod.crawl.imdb;

import java.util.List;

public class IMDBAkaTitlesParser extends IMDBToCSVParser{
	private String currentTitle;
	private String currentYear;
	@Override
	protected String defineInputFilePath() {
		return "/Users/froesler/Downloads/moviedb-3.24/lists/aka-titles.list";
	}

	@Override
	protected String defineEncoding() {
		return "Windows-1252";
	}

	@Override
	protected void onNewLine(String line) {

		if(line.contains("{")) return;

		if(line.startsWith(" ")){
			List<String> tiles = CleaningHelper.removeEmptyElements(line.trim().split("\t"));
			String titlePart = tiles.get(0);

			List<String> titleTiles = CleaningHelper.removeEmptyElements(titlePart.trim().replace("(aka ", "").split("\\("));
			String alternativeTitle = titleTiles.get(0).trim().replace("\"", "");

			String alternativeYear = CleaningHelper.removeRoundBrackets(titleTiles.get(1).trim());
			
			String country = "";
			String type = "";

			if(tiles.size()>1){
				String typeTile = tiles.get(1).trim();

				String[] splitTypeTile = typeTile.split("\\) \\(");
				country = CleaningHelper.removeRoundBrackets(splitTypeTile[0]);

				if(splitTypeTile.length>1){
					type = CleaningHelper.removeRoundBrackets(splitTypeTile[1].replace(")", ""));
				}
			}
			writeCSV(currentTitle, currentYear, alternativeTitle, alternativeYear, country, type);

		}else{
			currentTitle = RegexHelper.findFirstOccurence(line, "\".+\"").replace("\"", "");
			currentYear = RegexHelper.findFirstOccurence(line, "\\(\\d+\\)").replace("(", "").replace(")", "");
		}		
	}

	@Override
	protected String defineRelevanceStartingLine() {
		return "===============";
	}

	@Override
	protected String defineRelevanceEndingLine() {
		return "-----------------------------------------------------------------------------";
	}

	@Override
	protected void onFileEnd() {
		// TODO Auto-generated method stub
		
	}

}
