package edu.hpi.semweb.lod.crawl.imdb.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.hpi.semweb.lod.crawl.imdb.CleaningHelper;
import edu.hpi.semweb.lod.crawl.imdb.IMDBMovie;
import edu.hpi.semweb.lod.crawl.imdb.IMDBParser;

public class BusinessParser extends IMDBParser{

	private IMDBMovie currentMovie;
	private List<String>  rentals;
	private List<String>  copyRight;
	private List<String> budgets;
	private List<String> grossBoxOffices;
	private List<String> openingWeekendBoxOffices;
	private List<String> admissions;
	private List<String> shootingDates;
	private List<String> productionDates;
	private List<String> studiosFilmed;
	private List<String> wgs;

	private Map<String, List<String>> listMappings = new HashMap<String, List<String>>();

	public BusinessParser(boolean isPatchedFile){
		super(isPatchedFile);
		
		listMappings.put("RT", rentals);
		listMappings.put("CP", copyRight);
		listMappings.put("BT", budgets);
		listMappings.put("GR", grossBoxOffices);
		listMappings.put("OW", openingWeekendBoxOffices);
		listMappings.put("AD", admissions);
		listMappings.put("SD", shootingDates);
		listMappings.put("PD", productionDates);
		listMappings.put("ST", studiosFilmed);
		listMappings.put("WG", wgs);

	}	
	
	@Override
	public String defineFileName() {
		return "business.list";

	}

	@Override
	protected String defineEncoding() {
		return "Windows-1252";
	}
	
	private void writeEntry(){
		
	}
	
	@Override
	protected void onNewLine(String line) {
		if(line.trim().length() == 0) return;
		
		if(line.startsWith("-------------------------------------------------------------------------------") || line.startsWith("=============")){

			for(String key:listMappings.keySet()){
				listMappings.put(key, new ArrayList<String>());
			}
		}else{
			List<String> tiles = CleaningHelper.removeEmptyElements(line.split(":"));
			String abbrv = tiles.get(0).trim();
			String sentence = tiles.get(1).trim();
			
			if(abbrv.equals("MV")){
				currentMovie = new IMDBMovie(sentence);			
			}else{
				listMappings.get(abbrv).add(sentence);
			}
		}
	}

	@Override
	protected void onFileEnd() {		
	}

	@Override
	protected String defineRelevanceStartingLine() {
		return "BUSINESS LIST";
	}

	@Override
	protected String defineRelevanceEndingLine() {
		return "----------------------------------------------------------------------------";
	}

}
