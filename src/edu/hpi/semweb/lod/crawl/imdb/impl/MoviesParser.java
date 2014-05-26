package edu.hpi.semweb.lod.crawl.imdb.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.hpi.semweb.lod.crawl.imdb.CleaningHelper;
import edu.hpi.semweb.lod.crawl.imdb.IMDBParser;
import edu.hpi.semweb.lod.crawl.imdb.RegexHelper;

public class MoviesParser extends IMDBParser{

	public MoviesParser(boolean isPatchedFile) {
		super(isPatchedFile);
		// TODO Auto-generated constructor stub
	}

	Map<String,Integer> map = new HashMap<String, Integer>();
	
	@Override
	protected String defineFileName() {
		return "movies.list";
	}

	@Override
	protected void onNewLine(String line) {
		if(line.length() == 0 || line.contains("{")) return;
		
		List<String> tiles = CleaningHelper.removeEmptyElements(line.split("\t"));
		
		//String titlePart = tiles.get(0);
		//String yearPart = tiles.get(1).trim();
		
		//String title = RegexHelper.findFirstOccurence(titlePart, ".+?\\s\\(").replace("(", "").replace("\"", "").trim();
		
		String titleAndYear = CleaningHelper.uniquifyMovie(line);
		writeCSV(titleAndYear);
		if(!map.containsKey(titleAndYear)){
			map.put(titleAndYear, 1);
		}else{
			map.put(titleAndYear, map.get(titleAndYear)+1);
		}
	}

	@Override
	protected String defineEncoding() {
		return "Windows-1252";
	}

	@Override
	protected String defineRelevanceStartingLine() {
		return "===========";
	}

	@Override
	protected String defineRelevanceEndingLine() {
		return "--------------------------------------------------------------------------------";
	}

	@Override
	protected void onFileEnd() {

		for(String s:map.keySet()){
			if(map.get(s)>1){
				System.out.println(s+": "+map.get(s));
			}
		}
	}

}
