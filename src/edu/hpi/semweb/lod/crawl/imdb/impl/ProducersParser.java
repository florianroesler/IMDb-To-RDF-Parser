package edu.hpi.semweb.lod.crawl.imdb.impl;

import java.util.List;

import edu.hpi.semweb.lod.crawl.imdb.CleaningHelper;
import edu.hpi.semweb.lod.crawl.imdb.IMDBGenericPersonParser;
import edu.hpi.semweb.lod.crawl.imdb.RegexHelper;

public class ProducersParser extends IMDBGenericPersonParser{
	private String producer;
	private String currentFilm;
	private String producedType;
	
	@Override
	protected String defineEncoding() {
		return "Windows-1252";
	}

	@Override
	protected void onNewLine(String line) {

		/*if(line.contains("{")) return;
		
		if(line.startsWith("\t\t\t")){
			currentFilm = line;
			currentFilm = currentFilm.replace("(TV)", "").replace("(V)", "").replaceAll("\\(\\d+\\)", "").replace("(VG)", "").replace("(????)", "");
			tripleComplete = true;
			producedType = "producer";
			if(currentFilm.contains("(executive producer)")) {
				producedType = "executive producer";
				currentFilm = currentFilm.replace("(executive producer)", "");
			}
			if(currentFilm.contains("(co-producer)")) {
				producedType = "co-producer";
				currentFilm = currentFilm.replace("(co-producer)", "");
			}
			if(currentFilm.contains("(line producer)")) {
				producedType = "line producer";
				currentFilm = currentFilm.replace("(line producer)", "");
			}
			if(currentFilm.contains("(associate executive producer)")) {
				producedType = "associate executive producer";
				currentFilm = currentFilm.replace("(associate executive producer)", "");
			}
			if(currentFilm.contains("(associate producer)")) {
				producedType = "associate producer";
				currentFilm = currentFilm.replace("(associate producer)", "");
			}
			currentFilm = currentFilm.substring(0, currentFilm.length()-2).replace("\"", "");


		}else{ //TODO Name_Helper Snow,John -> John Snow
			producer = line.replace("{{SUSPENDED}}", "").replace("(TV)", "").replace("(V)", "").replaceAll("\\(\\d+\\)", "").replace("(VG)", "").replace("(????)", "").trim();
			tripleComplete = false;
		}	
		
		if (tripleComplete) writeCSV(baseFilm, producedType, currentFilm);
*/
/// Find out which types of producers exist
			if(line.startsWith("\t\t\t")){
		
			if(line.contains("(executive producer)")) {
				return;
			}
			if(line.contains("(producer)")) {
				return;
			}
			if(line.contains("(co-producer)")) {
				return;
			}
			if(line.contains("(line producer)")) {
				return;
			}
			if(line.contains("(associate executive producer)")) {
				return;
			}
			if(line.contains("(associate producer)")) {
				return;
			}
			writeCSV(line);
		}
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
