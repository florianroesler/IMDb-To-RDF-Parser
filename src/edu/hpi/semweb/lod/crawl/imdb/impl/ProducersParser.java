package edu.hpi.semweb.lod.crawl.imdb.impl;

import edu.hpi.semweb.lod.crawl.imdb.IMDBGenericPersonParser;
import edu.hpi.semweb.lod.crawl.imdb.RegexHelper;

public class ProducersParser extends IMDBGenericPersonParser{
	private String type;
	private String localType;
	private String currentFilm;
	private String producer;
	private boolean tripleComplete;
	
	@Override
	protected void onNewLine(String line) {
	
		if(line.contains("{")) return;
		
		if(line.startsWith("\t\t\t")){
			currentFilm = line;
			currentFilm = currentFilm.replace("{{SUSPENDED}}", "").replace("(TV)", "").replace("(V)", "").replaceAll("\\(\\d+\\)", "").replace("(VG)", "").replace("(????)", "");
			if ((localType = RegexHelper.returnGroup(line, "\\((.*?producer.*?)\\)", 1)) != "") {
				type = localType;
			}
			else {
				type = "producer";
			}
			tripleComplete = true;
		
		}else{
			producer = line.replace("{{SUSPENDED}}", "").replace("(TV)", "").replace("(V)", "").replaceAll("\\(\\d+\\)", "").replace("(VG)", "").replace("(????)", "").trim();
			tripleComplete = false;
		}	
	
	if (tripleComplete) writeCSV(producer, type, currentFilm);
		
	}
	
	@Override
	protected String defineFileName() {
		return "producers.list";
	}

	@Override
	protected String defineRelevanceStartingLine() {
		return "----                    ------";
	}

	@Override
	protected String defineRelevanceEndingLine() {
		return "---------------------------------------------------------------------------";
	}

}
