package edu.hpi.semweb.lod.crawl.imdb.id;

import java.util.HashMap;
import java.util.Map;

import edu.hpi.semweb.lod.crawl.PlainTextParser;
import edu.hpi.semweb.lod.crawl.imdb.Config;

public class HashSetBuilder extends PlainTextParser{

	private Map<String, String> titleToIdMap = new HashMap<String, String>();
	
	public String getIdForTitle(String title){
		return titleToIdMap.get(title);
	}
	
	@Override
	protected String defineInputFilePath() {
		return Config.CRAWLCOMBINEDPATH+"combinedIds";
	}

	@Override
	protected String defineEncoding() {
		return null;
	}

	@Override
	protected void onNewLine(String line) {
		String[] parts = line.split("!_!");
		if(parts.length<3) return;
		String lookup = parts[1]+"_"+parts[2];
		titleToIdMap.put(lookup, parts[0]);
	}

	@Override
	protected void onFileEnd() {	
		System.out.println("Done building HashMap");
	}

	@Override
	protected String defineRelevanceStartingLine() {
		return null;
	}

	@Override
	protected String defineRelevanceEndingLine() {
		return null;
	}

	
	
}
