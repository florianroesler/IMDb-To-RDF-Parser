package edu.hpi.semweb.lod.crawl.imdb.impl;

import java.util.List;

import edu.hpi.semweb.lod.crawl.imdb.CleaningHelper;
import edu.hpi.semweb.lod.crawl.imdb.IMDBToCSVParser;
import edu.hpi.semweb.lod.crawl.imdb.RegexHelper;

public class MovieLinkParser extends IMDBToCSVParser {
	private String currentFilm;
	private String baseFilm;
	private boolean tripleComplete;
	private String tripleType;
	@Override
	protected String defineFileName() {
		return "movie-links.list";
	}

	@Override
	protected String defineEncoding() {
		return "Windows-1252";
	}

	@Override
	protected void onNewLine(String line) {

		if(line.contains("{")) return;
		
		if(line.startsWith(" ")){
			//List<String> tiles = CleaningHelper.removeEmptyElements(line.trim().split("\t"));
			//String titlePart = tiles.get(0);

			//List<String> titleTiles = CleaningHelper.removeEmptyElements(titlePart.trim().replace("(aka ", "").split("\\("));
			//String alternativeTitle = titleTiles.get(0).trim().replace("\"", "");
			currentFilm = line;
			currentFilm = currentFilm.replace("{{SUSPENDED}}", "").replace("(TV)", "").replace("(V)", "").replaceAll("\\(\\d+\\)", "").replace("(VG)", "").replace("(????)", "");
			tripleComplete = true;
			if(currentFilm.startsWith("  (follows ")) {
				tripleType = "follows";
				currentFilm = currentFilm.replace("  (follows ", "");
			}
			if(currentFilm.startsWith("  (followed by ")) {
				tripleType = "followed by";
				currentFilm = currentFilm.replace("  (followed by ", "");
			}
			if(currentFilm.startsWith("  (version of ")) {
				tripleType = "relatedTo/remake";
				currentFilm = currentFilm.replace("  (version of ", "");
			}
			if(currentFilm.startsWith("  (alternate language version of ")) {
				tripleType = "different_language";
				currentFilm = currentFilm.replace("  (alternate language version of ", "");
			}
			currentFilm = currentFilm.substring(0, currentFilm.length()-2).replace("\"", "");


		}else{
			baseFilm = line.replace("{{SUSPENDED}}", "").replace("(TV)", "").replace("(V)", "").replaceAll("\\(\\d+\\)", "").replace("(VG)", "").replace("(????)", "").trim();
			tripleComplete = false;
		}	
		
		if (tripleComplete) writeCSV(baseFilm, tripleType, currentFilm);


		/*	if(line.startsWith(" ")){
		
			if(line.startsWith("  (follows")) {
				return;
			}
			if(line.startsWith("  (followed by")) {
				return;
			}
			if(line.startsWith("  (version of")) {
				return;
			}
			if(line.startsWith("  (alternate language version of")) {
				return;
			}
			writeCSV(line);
		}*/
	}

	@Override
	protected void onFileEnd() {

	}

	@Override
	protected String defineRelevanceStartingLine() {
		return "================";
	}

	@Override
	protected String defineRelevanceEndingLine() {
		return "-------------------------------------------------------------------------------";
	}

}
