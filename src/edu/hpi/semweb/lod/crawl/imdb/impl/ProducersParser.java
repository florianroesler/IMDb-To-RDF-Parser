package edu.hpi.semweb.lod.crawl.imdb.impl;

import java.util.List;

import edu.hpi.semweb.lod.crawl.imdb.CleaningHelper;
import edu.hpi.semweb.lod.crawl.imdb.IMDBActor;
import edu.hpi.semweb.lod.crawl.imdb.IMDBGenericPersonParser;
import edu.hpi.semweb.lod.crawl.imdb.IMDBMovie;
import edu.hpi.semweb.lod.crawl.imdb.IMDBRDFBuilder;
import edu.hpi.semweb.lod.crawl.imdb.RegexHelper;

public class ProducersParser extends IMDBGenericPersonParser{
	public ProducersParser(boolean isPatchedFile) {
		super(isPatchedFile);
		// TODO Auto-generated constructor stub
	}

	private String typeString;
	private String localType;
	private String currentFilm;
	private String producer;
	
	@Override
	protected void onNewLine(String line) {
		line.replaceAll("\\{\\(\\d+-\\d+-\\d+\\)\\}","");
		if(line.contains("{")) return;
		
		if(line.startsWith("\t")){
			currentFilm = line;
			currentFilm = currentFilm.replace("{{SUSPENDED}}", "").trim();
			if ((localType = RegexHelper.returnGroup(currentFilm, "\\(([^\\(]*producer[^\\)]*)\\)", 1)) != "") {
				typeString = localType;
			}
			else {
				typeString = "producer";
			}
		
		}else{
			List<String> tiles = CleaningHelper.removeEmptyElements(line.split("\t"));
			if(tiles.size() == 2){		
				//thats a new producer and a trailing movie
				producer = CleaningHelper.cleanActorName(tiles.get(0));
				currentFilm = tiles.get(1);
				producer = producer.replace("(TV)", "").replace("(V)", "").replaceAll("\\(\\d+\\)", "").replace("(VG)", "").replace("(????)", "").replaceAll("\\(.*?\\)","").trim();
			}
			if ((localType = RegexHelper.returnGroup(currentFilm, "\\(([^\\(]*producer[^\\)]*)\\)", 1)) != "") {
				typeString = localType;
			}
			else {
				typeString = "producer";
			}
			currentFilm = currentFilm.trim();
		}	
	currentFilm = currentFilm.replaceAll("\\([^\\(]*producer.*\\)","").replaceAll("\\(as [^\\)]*\\)","");
	IMDBMovie mov = new IMDBMovie(currentFilm);
	currentFilm = mov.toString();
	IMDBActor per = new IMDBActor(producer);
	producer = per.toString();
	//typeString = "producer"; // Producer-subproperties are ignored to fit Ontology
	typeString = typeString.replace(" ","_");
	
	writeRDF(IMDBRDFBuilder.imdbMovie(currentFilm), IMDBRDFBuilder.prop(typeString), IMDBRDFBuilder.person(producer));
	writeRDF(IMDBRDFBuilder.person(producer), IMDBRDFBuilder.is(), IMDBRDFBuilder.producer());
		
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
