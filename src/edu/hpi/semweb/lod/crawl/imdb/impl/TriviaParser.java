package edu.hpi.semweb.lod.crawl.imdb.impl;

import java.util.ArrayList;
import java.util.List;

import edu.hpi.semweb.lod.crawl.imdb.IMDBMovie;
import edu.hpi.semweb.lod.crawl.imdb.IMDBParser;
import edu.hpi.semweb.lod.crawl.imdb.IMDBRDFBuilder;

public class TriviaParser extends IMDBParser{

	private IMDBMovie currentMovie;
	private List<StringBuilder> currentTrivias;
	
	public TriviaParser(boolean isPatchedFile) {
		super(isPatchedFile);
	}

	@Override
	public String defineFileName() {
		return "trivia.list";
	}

	@Override
	protected void onNewLine(String line) {
		if(line.length()==0) return;
		
		if(line.startsWith("#")){
			writeTrivia();

			line = line.replaceFirst("# ", "");
			currentMovie = new IMDBMovie(line);
			currentTrivias = new ArrayList<StringBuilder>();
			return;
		}
		if(line.startsWith("- ")){
			line = line.replaceFirst("- ", "").trim();
			currentTrivias.add(new StringBuilder(line));
		}else if(line.startsWith("  ")){
			currentTrivias.get(currentTrivias.size()-1).append(" "+line.trim());
		}
	}

	@Override
	protected void onFileEnd() {
		writeTrivia();
	}

	private void writeTrivia(){
		if(currentMovie==null) return;
		for(StringBuilder trivia: currentTrivias){
			writeRDF(IMDBRDFBuilder.hpilodMovie(currentMovie.toString()), IMDBRDFBuilder.trivia(), IMDBRDFBuilder.string(trivia.toString()));
		}
	}
	@Override
	protected String defineRelevanceStartingLine() {
		return "===========";
	}

	@Override
	protected String defineRelevanceEndingLine() {
		return null;
	}

}
