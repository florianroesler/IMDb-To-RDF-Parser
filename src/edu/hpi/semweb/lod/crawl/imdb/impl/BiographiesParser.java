package edu.hpi.semweb.lod.crawl.imdb.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import edu.hpi.semweb.lod.crawl.imdb.CleaningHelper;
import edu.hpi.semweb.lod.crawl.imdb.IMDBActor;
import edu.hpi.semweb.lod.crawl.imdb.IMDBParser;
import edu.hpi.semweb.lod.crawl.imdb.IMDBRDFBuilder;

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
	private Map<String, String> rdfMappings = new HashMap<String, String>();

	public BiographiesParser(boolean isPatchedFile){
		super(isPatchedFile);
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


		rdfMappings.put("AR", IMDBRDFBuilder.article());
		rdfMappings.put("CV", IMDBRDFBuilder.cover());
		rdfMappings.put("OW", IMDBRDFBuilder.otherWork());
		rdfMappings.put("BT", IMDBRDFBuilder.miscCrew());
		rdfMappings.put("TM", IMDBRDFBuilder.trademark());
		rdfMappings.put("IT", IMDBRDFBuilder.interview());
		rdfMappings.put("SP", IMDBRDFBuilder.spouse());
		rdfMappings.put("TR", IMDBRDFBuilder.trivia());
		rdfMappings.put("PT", IMDBRDFBuilder.pictorial());
		rdfMappings.put("QU", IMDBRDFBuilder.quote());
		rdfMappings.put("PI", IMDBRDFBuilder.portrayal());
		rdfMappings.put("BO", IMDBRDFBuilder.biography());
		rdfMappings.put("SA", IMDBRDFBuilder.salary());
		rdfMappings.put("DB", IMDBRDFBuilder.dateBorn());
		rdfMappings.put("DD", IMDBRDFBuilder.deathDate());
		rdfMappings.put("NK", IMDBRDFBuilder.nickname());
		rdfMappings.put("NM", IMDBRDFBuilder.name());
		rdfMappings.put("HT", IMDBRDFBuilder.height());
		rdfMappings.put("RN", IMDBRDFBuilder.realName());
		rdfMappings.put("BG", IMDBRDFBuilder.background());


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
		IMDBActor actor = new IMDBActor(stringMappings.get("NM"));

		for(String s:stringMappings.keySet()){
			String value = stringMappings.get(s);
			writeKeyValue(actor, s, value);
		}

		for(String s:listMappings.keySet()){
			List<String> list = listMappings.get(s);
			for(String value:list){
				writeKeyValue(actor, s, value);
			}
		}
	}
	
	private void writeKeyValue(IMDBActor actor,String key, String value){
		if(value == null || value.length()==0) return;
		writeRDF(IMDBRDFBuilder.imdbActor(actor.toString()), rdfMappings.get(key), IMDBRDFBuilder.string(value));
	}

	@Override
	protected void onNewLine(String line) {

		if(line.length() == 0) return;

		if(line.startsWith("-------------------------------------------------------------------------------")){

			if(stringMappings.get("NM")!=null){
				writeBiography();
			}

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
					stringMappings.put(abbrv, stringMappings.get(abbrv)+" "+sentence);
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
