package edu.hpi.semweb.lod.crawl.imdb.impl;

import java.util.List;

import edu.hpi.semweb.lod.crawl.imdb.CleaningHelper;
import edu.hpi.semweb.lod.crawl.imdb.IMDBMovie;
import edu.hpi.semweb.lod.crawl.imdb.IMDBParser;
import edu.hpi.semweb.lod.crawl.imdb.IMDBRDFBuilder;
import edu.hpi.semweb.lod.crawl.imdb.RegexHelper;

public class AkaTitlesParser extends IMDBParser{
	
	
	private IMDBMovie currentMovie;
	
	public AkaTitlesParser(boolean isPatchedFile) {
		super(isPatchedFile);
	}
	
	@Override
	public String defineFileName() {
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
			String akaDate = "";
			akaDate = RegexHelper.findFirstOccurence(titlePart, "\\((\\d+|\\?{4})(/\\w+)?\\)");
			akaDate.replace("\\(", "").replace("\\)","");
			titlePart.replaceAll("\\((\\d+|\\?{4})(/\\w+)?\\)", "");
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
			String ID = alternativeMovie.getTitle()+"_"+type+"_"+country+"_"+akaDate;
			
			while (ID.contains("__")){
				ID = ID.replace("__","_");
			}
			
			writeRDF(IMDBRDFBuilder.hpilodMovie(currentMovie.toString()), IMDBRDFBuilder.akaTitle(), IMDBRDFBuilder.akaTitleObject(ID));
			writeRDF(IMDBRDFBuilder.akaTitleObject(ID), IMDBRDFBuilder.label(), IMDBRDFBuilder.string(alternativeMovie.getTitle()));
			writeRDF(IMDBRDFBuilder.akaTitleObject(ID), IMDBRDFBuilder.year(), IMDBRDFBuilder.string(akaDate));
			writeRDF(IMDBRDFBuilder.akaTitleObject(ID), IMDBRDFBuilder.akaType(), IMDBRDFBuilder.string(type));
			writeRDF(IMDBRDFBuilder.akaTitleObject(ID), IMDBRDFBuilder.is(), IMDBRDFBuilder.thing());
			
		
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
