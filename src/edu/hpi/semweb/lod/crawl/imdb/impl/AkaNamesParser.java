package edu.hpi.semweb.lod.crawl.imdb.impl;

import edu.hpi.semweb.lod.crawl.imdb.CleaningHelper;
import edu.hpi.semweb.lod.crawl.imdb.IMDBParser;
import edu.hpi.semweb.lod.crawl.imdb.RegexHelper;

public class AkaNamesParser extends IMDBParser{

	private String currentActor;
	@Override
	protected String defineFileName() {
		return "aka-names.list";
	}

	@Override
	protected String defineEncoding() {
		return "Windows-1252";
	}

	@Override
	protected void onNewLine(String line) {
		if(line.startsWith(" ")){
			String name = RegexHelper.findFirstOccurence(line, "aka\\s[^)]+").replace("aka ", "");
			name = CleaningHelper.cleanActorName(name);
			writeCSV(currentActor, name);
		}else{
			currentActor = CleaningHelper.cleanActorName(line);
			return;
		}
	}

	@Override
	protected String defineRelevanceStartingLine() {
		return "==============";
	}

	@Override
	protected String defineRelevanceEndingLine() {
		return null;
	}

	@Override
	protected void onFileEnd() {
		// TODO Auto-generated method stub
		
	}

}
