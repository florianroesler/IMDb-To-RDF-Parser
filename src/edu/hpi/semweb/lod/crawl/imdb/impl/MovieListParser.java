package edu.hpi.semweb.lod.crawl.imdb.impl;

import java.util.List;

import edu.hpi.semweb.lod.crawl.imdb.CleaningHelper;
import edu.hpi.semweb.lod.crawl.imdb.IMDBToCSVParser;
import edu.hpi.semweb.lod.crawl.imdb.RegexHelper;

public class MovieListParser extends IMDBToCSVParser {
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
