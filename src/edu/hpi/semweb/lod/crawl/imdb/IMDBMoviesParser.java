package edu.hpi.semweb.lod.crawl.imdb;

import java.util.List;

import edu.hpi.semweb.lod.crawl.PlainTextCrawler;

public class IMDBMoviesParser extends IMDBToCSVParser{

	@Override
	protected String defineFileName() {
		return "movies.list";
	}

	@Override
	protected void onNewLine(String line) {
		if(line.length() == 0 || line.contains("{")) return;
		
		List<String> tiles = CleaningHelper.removeEmptyElements(line.split("\t"));
		
		String titlePart = tiles.get(0);
		String yearPart = tiles.get(1).trim();
		
		String title = RegexHelper.findFirstOccurence(titlePart, ".+\\s\\(").replace("(", "").replace("\"", "").trim();
		
		writeCSV(title, yearPart);

	}

	@Override
	protected String defineEncoding() {
		return "Windows-1252";
	}

	@Override
	protected String defineRelevanceStartingLine() {
		return "===========";
	}

	@Override
	protected String defineRelevanceEndingLine() {
		return "--------------------------------------------------------------------------------";
	}

	@Override
	protected void onFileEnd() {
		// TODO Auto-generated method stub
		
	}

}
