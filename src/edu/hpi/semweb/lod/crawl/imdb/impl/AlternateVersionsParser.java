package edu.hpi.semweb.lod.crawl.imdb.impl;

import java.util.ArrayList;
import java.util.List;

import edu.hpi.semweb.lod.crawl.imdb.IMDBMovie;
import edu.hpi.semweb.lod.crawl.imdb.IMDBParser;
import edu.hpi.semweb.lod.crawl.imdb.IMDBRDFBuilder;

public class AlternateVersionsParser extends IMDBParser{

	private List<StringBuilder> versions = new ArrayList<StringBuilder>();
	private IMDBMovie currentMovie;
	
	public AlternateVersionsParser(boolean isPatchedFile) {
		super(isPatchedFile);
	}

	@Override
	public String defineFileName() {
		return "alternate-versions.list";
	}


	@Override
	protected void onNewLine(String line) {

		if(line.startsWith("#") && !line.contains("{")){
			if(currentMovie!=null){
				writeToFile();
			}
			currentMovie = new IMDBMovie(line.replace("# ", "").trim());

		}else if(line.startsWith("- ") && currentMovie!=null){
			String firstLine = line.replaceFirst("- ", "");
			versions.add(new StringBuilder().append(firstLine.trim()));
		}else if(line.startsWith(" ") && currentMovie!=null){
			versions.get(versions.size()-1).append(line.trim());
		}
	}

	private void writeToFile(){
		for(StringBuilder b:versions){
			writeRDF(IMDBRDFBuilder.hpilodMovie(currentMovie.toString()), IMDBRDFBuilder.alternateVersion(), IMDBRDFBuilder.string(b.toString()));
		}
		currentMovie = null;
		versions = new ArrayList<StringBuilder>();
	}

	@Override
	protected String defineRelevanceStartingLine() {
		return "ALTERNATE VERSIONS LIST";
	}

	@Override
	protected String defineRelevanceEndingLine() {
		return "----------------------------------------------------------------------------";
	}

	@Override
	protected void onFileEnd() {
		writeToFile();

	}

}
