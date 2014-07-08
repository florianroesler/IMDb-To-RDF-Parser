package edu.hpi.semweb.lod.crawl.imdb.impl;

import java.util.List;

import edu.hpi.semweb.lod.crawl.imdb.CleaningHelper;
import edu.hpi.semweb.lod.crawl.imdb.IMDBMovie;
import edu.hpi.semweb.lod.crawl.imdb.IMDBParser;
import edu.hpi.semweb.lod.crawl.imdb.IMDBRDFBuilder;
import edu.hpi.semweb.lod.crawl.imdb.RegexHelper;

public class MiscFilmographyParser extends IMDBParser{

	public MiscFilmographyParser(boolean isPatchedFile) {
		super(isPatchedFile);
	}

	@Override
	public String defineFileName() {
		return "miscellaneous.list";
	}
	
	private String currentPerson;
	


	@Override
	protected void onNewLine(String line) {
		if(line.length()==0) return;
		List<String> tiles = CleaningHelper.removeEmptyElements(line.split("\t"));

		
		String titlePart = "";
		
		if(tiles.size() == 2){		
			currentPerson = CleaningHelper.cleanActorName(tiles.get(0));
			titlePart = tiles.get(1);
		}else{
			titlePart = tiles.get(0);
		}
		
		if(titlePart.contains("{")) return;
		
		String year = CleaningHelper.removeRoundBrackets(RegexHelper.findFirstOccurence(titlePart, "\\(\\d+\\)"));
		
		String role = titlePart.replace("(TV)", "").replace("(V)", "").replace("(VG)", "").replaceAll("\\(\\d{4}.*?\\)", "");
		role = RegexHelper.findFirstOccurence(role, "\\(.+\\)");
		role = CleaningHelper.removeRoundBrackets(role);
		
		titlePart = titlePart.replaceAll("\\(.+?\\)", "").replace("\"", "");
		
		IMDBMovie mov = new IMDBMovie(titlePart+" ("+year+")");
		titlePart = mov.toString();
		role = role.replace(" ", "_");
		
		writeRDF(IMDBRDFBuilder.hpilodMovie(titlePart), IMDBRDFBuilder.prop(role), IMDBRDFBuilder.imdbPerson(currentPerson));
		writeRDF(IMDBRDFBuilder.imdbPerson(currentPerson), IMDBRDFBuilder.is(), IMDBRDFBuilder.person());
		
		//String title = titlePart.trim();
		
		//writeCSV(currentPerson, titlePart, year, role);
	}

	@Override
	protected void onFileEnd() {
		
	}

	@Override
	protected String defineRelevanceStartingLine() {
		return "----                    ------";
	}

	@Override
	protected String defineRelevanceEndingLine() {
		return "-----------------------------------------------------------------------------";
	}
	

}
