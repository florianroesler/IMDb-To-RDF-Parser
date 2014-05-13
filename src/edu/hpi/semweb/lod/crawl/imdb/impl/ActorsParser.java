package edu.hpi.semweb.lod.crawl.imdb.impl;

import java.util.List;

import edu.hpi.semweb.lod.crawl.imdb.CleaningHelper;
import edu.hpi.semweb.lod.crawl.imdb.IMDBToCSVParser;
import edu.hpi.semweb.lod.crawl.imdb.RegexHelper;


public class ActorsParser extends IMDBToCSVParser{

	private String currentActor = "";


	@Override
	protected String defineFileName() {
		return "actors.list";
	}

	@Override
	protected String defineEncoding() {
		return "Windows-1252";
	}

	@Override
	protected String defineRelevanceStartingLine() {
		return "----			------";
	}

	@Override
	protected String defineRelevanceEndingLine() {
		return "-----------------------------------------------------------------------------";
	}

	@Override
	protected void onNewLine(String line) {
		if(!isStopped()){

			if(line.contains("{") || line.length()==0){
				return;
			}
			
			String dirtyTitle;
			String[] tiles = line.split("\t");
			List<String> cleanedTiles = CleaningHelper.removeEmptyElements(tiles);

			if(line.startsWith("\t")){
				dirtyTitle = cleanedTiles.get(0);

			}else{

				String name = cleanedTiles.get(0);

				currentActor = CleaningHelper.cleanActorName(name);

				dirtyTitle = cleanedTiles.get(1);
			}

			String cleanLine = cleanTitleLine(dirtyTitle);

			String title = RegexHelper.findFirstOccurence(cleanLine, ".+?[(]").replace("(", "").trim();

			String year = RegexHelper.findFirstOccurence(cleanLine, "\\(\\d+\\)").replace("(", "").replace(")", "");
			String role = RegexHelper.findFirstOccurence(cleanLine, "\\[\\w+\\]").replace("[", "").replace("]", "");

			writeCSV(currentActor, title, year, role);

		}

	}		


	private String cleanTitleLine(String dirtyTitle){
		dirtyTitle = dirtyTitle.replace("(V)", "");
		dirtyTitle = dirtyTitle.replace("\"", "");
		dirtyTitle = dirtyTitle.replaceAll("<\\d*>", "");
		return dirtyTitle;
	}

	@Override
	protected void onFileEnd() {
		// TODO Auto-generated method stub
		
	}



}
