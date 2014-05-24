package edu.hpi.semweb.lod.crawl.imdb.impl;

import java.util.List;

import edu.hpi.semweb.lod.crawl.imdb.CleaningHelper;
import edu.hpi.semweb.lod.crawl.imdb.IMDBParser;
import edu.hpi.semweb.lod.crawl.imdb.RegexHelper;

public class MovieLinkParser extends IMDBParser {
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
				tripleType = "version of";
				currentFilm = currentFilm.replace("  (version of ", "");
			}
			if(currentFilm.startsWith("  (alternate language version of ")) {
				tripleType = "alternate language version of";
				currentFilm = currentFilm.replace("  (alternate language version of ", "");
			}
			currentFilm = currentFilm.substring(0, currentFilm.length()-1).replace("\"", "").trim();


		}else{
			baseFilm = line.replace("\"", "").trim();
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
