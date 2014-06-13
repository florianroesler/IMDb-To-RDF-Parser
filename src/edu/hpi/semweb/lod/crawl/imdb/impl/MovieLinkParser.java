package edu.hpi.semweb.lod.crawl.imdb.impl;

import edu.hpi.semweb.lod.crawl.imdb.IMDBMovie;
import edu.hpi.semweb.lod.crawl.imdb.IMDBParser;
import edu.hpi.semweb.lod.crawl.imdb.IMDBRDFBuilder;

public class MovieLinkParser extends IMDBParser {
	public MovieLinkParser(boolean isPatchedFile) {
		super(isPatchedFile);
		// TODO Auto-generated constructor stub
	}

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
				tripleType = "followed_by";
				currentFilm = currentFilm.replace("  (followed by ", "");
			}
			if(currentFilm.startsWith("  (version of ")) {
				tripleType = "version_of";
				currentFilm = currentFilm.replace("  (version of ", "");
			}
			if(currentFilm.startsWith("  (alternate language version of ")) {
				tripleType = "alternate_language_version_of";
				currentFilm = currentFilm.replace("  (alternate language version of ", "");
			}
			currentFilm = currentFilm.substring(0, currentFilm.length()-1).replace("\"", "").trim();


		}else{
			baseFilm = line.replace("\"", "").trim();
			tripleComplete = false;
		}	
		
		if (tripleComplete) {
			currentFilm = currentFilm.replaceAll("\\([^\\(]*producer.*\\)","").replaceAll("\\(as [^\\)]*\\)","");
			IMDBMovie mov = new IMDBMovie(currentFilm);
			currentFilm = mov.toString();
			IMDBMovie movB = new IMDBMovie(baseFilm);
			baseFilm = movB.toString();

			
			writeRDF(IMDBRDFBuilder.hpilodMovie(baseFilm), IMDBRDFBuilder.prop(tripleType), IMDBRDFBuilder.hpilodMovie(currentFilm));
			
			
		}

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
