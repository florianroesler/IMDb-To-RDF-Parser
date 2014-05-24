package edu.hpi.semweb.lod.crawl.imdb.impl;

import java.util.List;

import edu.hpi.semweb.lod.crawl.imdb.CleaningHelper;
import edu.hpi.semweb.lod.crawl.imdb.IMDBParser;
import edu.hpi.semweb.lod.crawl.imdb.RegexHelper;

public class AkaTitlesParser extends IMDBParser{
	private String currentTitle;
	private String currentYear;
	
	@Override
	protected String defineFileName() {
		return "aka-titles.list";
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
			currentTitle = RegexHelper.findFirstOccurence(line, "\".+?\"").replace("\"", "");
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
