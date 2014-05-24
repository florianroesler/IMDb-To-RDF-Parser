package edu.hpi.semweb.lod.crawl.imdb.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import edu.hpi.semweb.lod.crawl.imdb.CleaningHelper;
import edu.hpi.semweb.lod.crawl.imdb.IMDBParser;

public class BiographiesParser extends IMDBParser{
	
	// all these attributes are not really neccessary but shall 
	// give insight into the mapping of the found abbreviations to their respective meaning
	private List<String> articles;
	private List<String> covers;
	private List<String> otherWorks;
	private List<String> miscCrew;
	private List<String> tradeMarks;
	private List<String> interviews;
	private List<String> spouses;
	private List<String> trivias;
	private List<String> pictorials;
	private List<String> quotes;
	private List<String> portrayals;
	private List<String> biographies;
	private List<String> salaries;
	
	private String dateBorn;
	private String deathDate;
	private String nickname;
	private String name;
	private String height;
	private String realName;
	private String backgrounds;


	private Map<String, List<String>> listMappings = new HashMap<String, List<String>>();
	private Map<String, String> stringMappings = new HashMap<String, String>();

	private Set<String> sortedStringKeys = new TreeSet<String>();
	
	public BiographiesParser(){
		listMappings.put("AR", articles);
		listMappings.put("CV", covers);
		listMappings.put("OW", otherWorks);
		listMappings.put("BT", miscCrew);
		listMappings.put("TM", tradeMarks);
		listMappings.put("IT", interviews);
		listMappings.put("SP", spouses);
		listMappings.put("TR", trivias);
		listMappings.put("PT", pictorials);
		listMappings.put("QU", quotes);
		listMappings.put("PI", portrayals);
		listMappings.put("BO", biographies);
		listMappings.put("SA", salaries);

		stringMappings.put("DB", dateBorn);
		stringMappings.put("DD", deathDate);
		stringMappings.put("NK", nickname);
		stringMappings.put("NM", name);
		stringMappings.put("HT", height);
		stringMappings.put("RN", realName);
		stringMappings.put("BG", backgrounds);



		for(String s:stringMappings.keySet()){
			sortedStringKeys.add(s);
		}
	}
	
	@Override
	protected String defineFileName() {
		return "biographies.list";
	}

	@Override
	protected String defineEncoding() {
		return "Windows-1252";
	}

	private void writeBiography(){
		List<String> output = new ArrayList<String>();
		for(String s:sortedStringKeys){
			output.add(stringMappings.get(s));
		}
		writeCSV(output.toArray(new String[output.size()]));
	}
	
	@Override
	protected void onNewLine(String line) {
		
		if(line.length() == 0) return;
		
		if(line.startsWith("-------------------------------------------------------------------------------")){
			
			writeBiography();
			
			for(String key:listMappings.keySet()){
				listMappings.put(key, new ArrayList<String>());
			}
			
			for(String key:stringMappings.keySet()){
				stringMappings.put(key, "");
			}
			
			
		}else{
			List<String> tiles = CleaningHelper.removeEmptyElements(line.split(":"));
			String abbrv = tiles.get(0).trim();
			String sentence = tiles.get(1).trim().replace("_", "");
			
			if(listMappings.containsKey(abbrv)){
				
				if(sentence.startsWith("*")){
					sentence = sentence.replaceFirst("\\*", "").trim();
					listMappings.get(abbrv).add(sentence);
				}else{
					List<String> targetedList = listMappings.get(abbrv);
					
					String lastString = "";
					try{
						lastString = targetedList.get(targetedList.size()-1);

					}catch(Exception ex){
						System.out.println(line);
					}
					targetedList.set(targetedList.size()-1, lastString+sentence);
				}
				
			}else{
				if(abbrv.equals("BG")){
					stringMappings.put(abbrv, stringMappings.get(abbrv)+sentence);
				}else{
					stringMappings.put(abbrv, sentence);
				}
			}
		}
	}

	@Override
	protected void onFileEnd() {
		writeBiography();
	}

	@Override
	protected String defineRelevanceStartingLine() {
		return "==============";
	}

	@Override
	protected String defineRelevanceEndingLine() {
		return null;
	}

}
