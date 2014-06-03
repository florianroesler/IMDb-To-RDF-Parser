package edu.hpi.semweb.lod.crawl.imdb.impl;

import java.util.List;

import edu.hpi.semweb.lod.crawl.imdb.CleaningHelper;
import edu.hpi.semweb.lod.crawl.imdb.IMDBMovie;
import edu.hpi.semweb.lod.crawl.imdb.IMDBParser;
import edu.hpi.semweb.lod.crawl.imdb.IMDBRDFBuilder;

public class AkaTitlesParser extends IMDBParser{
	
	
	private IMDBMovie currentMovie;
	
	public AkaTitlesParser(boolean isPatchedFile) {
		super(isPatchedFile);
	}
	
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

			String dirtyTitle = titlePart.trim().replace("(aka ", "").replaceAll("\\)$", "");
			IMDBMovie alternativeMovie = new IMDBMovie(dirtyTitle);
			
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
			writeRDF(IMDBRDFBuilder.imdbMovie(currentMovie.toString()), IMDBRDFBuilder.akaTitle(), IMDBRDFBuilder.string(alternativeMovie.getTitle()));
		}else{
			currentMovie = new IMDBMovie(line);
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
		
	}

}
