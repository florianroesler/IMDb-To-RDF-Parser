package edu.hpi.semweb.lod.crawl.imdb.impl;

import java.util.List;

import edu.hpi.semweb.lod.crawl.imdb.CleaningHelper;
import edu.hpi.semweb.lod.crawl.imdb.IMDBActor;
import edu.hpi.semweb.lod.crawl.imdb.IMDBMovie;
import edu.hpi.semweb.lod.crawl.imdb.IMDBParser;
import edu.hpi.semweb.lod.crawl.imdb.IMDBRDFBuilder;

public class WritersParser extends IMDBParser{

	public WritersParser(boolean isPatchedFile) {
		super(isPatchedFile);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String defineFileName() {
		return "writers.list";
	}
	
	private String currentPerson;
	private String currentMovie;
	

	@Override
	protected String defineEncoding() {
		return "Windows-1252";
	}

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
		
		titlePart = titlePart.replaceAll("<.+?>", "");
		
		//String year = CleaningHelper.removeRoundBrackets(RegexHelper.findFirstOccurence(titlePart, "\\(\\d+\\)"));
		
		//String role = titlePart.replace("(TV)", "").replace("(V)", "").replace("(VG)", "").replaceAll("\\(\\d{4}.*?\\)", "");
		//role = RegexHelper.findFirstOccurence(role, "\\(.+?\\)");
		//role = CleaningHelper.removeRoundBrackets(role).trim();
		
		/*if(role.length()==0 || role.equals("written by")){
			role = "writer";
		}
		titlePart = titlePart.replaceAll("\\(.+?\\)", "").replace("\"", "");
		
		String title = titlePart.trim();
		*/
		//String title = RegexHelper.findFirstOccurence(cleanLine, ".+?[(]").replace("(", "").trim();
		IMDBMovie mov = new IMDBMovie(titlePart.replaceAll("\\[[^\\[\\]]*\\]",""));
		currentMovie = mov.toString();
		IMDBActor per = new IMDBActor(currentPerson);
		currentPerson = per.toString();
		//String year = RegexHelper.findFirstOccurence(cleanLine, "\\(\\d+\\)").replace("(", "").replace(")", "");
		//String role = RegexHelper.findFirstOccurence(cleanLine, "\\[\\w+\\]").replace("[", "").replace("]", "").replace(" ", "_");

		writeRDF(IMDBRDFBuilder.imdbMovie(currentMovie), IMDBRDFBuilder.prop("writer"), IMDBRDFBuilder.person(currentPerson));
		writeRDF(IMDBRDFBuilder.person(currentPerson), IMDBRDFBuilder.is(), IMDBRDFBuilder.writer());
		//writeCSV(currentPerson, title, year, role);
	}

	@Override
	protected void onFileEnd() {
		
	}

	@Override
	protected String defineRelevanceStartingLine() {
		return "----			------";
	}

	@Override
	protected String defineRelevanceEndingLine() {
		return "---------------------------------------------------------------------";
	}
	

}
