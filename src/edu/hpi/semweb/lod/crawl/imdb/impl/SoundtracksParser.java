package edu.hpi.semweb.lod.crawl.imdb.impl;

import java.util.ArrayList;
import java.util.List;

import edu.hpi.semweb.lod.crawl.imdb.IMDBMovie;
import edu.hpi.semweb.lod.crawl.imdb.IMDBParser;
import edu.hpi.semweb.lod.crawl.imdb.IMDBRDFBuilder;

public class SoundtracksParser extends IMDBParser{

	private IMDBMovie currentMovie;
	private List<StringBuilder> currentSoundtrack;
	
	public SoundtracksParser(boolean isPatchedFile) {
		super(isPatchedFile);
	}

	@Override
	protected String defineFileName() {
		return "soundtracks.list";
	}

	@Override
	protected String defineEncoding() {
		return "Windows-1252";
	}

	@Override
	protected void onNewLine(String line) {
		if(line.length()==0) return;
		
		if(line.startsWith("#")){
			writeSoundtrack();

			line = line.replaceFirst("# ", "");
			currentMovie = new IMDBMovie(line);
			currentSoundtrack = new ArrayList<StringBuilder>();
			return;
		}
		if(line.startsWith("- ")){
			line = line.replaceFirst("- ", "").trim();
			currentSoundtrack.add(new StringBuilder(line));
		}else if(line.startsWith("  ")){
			currentSoundtrack.get(currentSoundtrack.size()-1).append(" "+line.trim());
		}
	}
	
	@Override
	protected void onFileEnd() {
		writeSoundtrack();		
	}
	
	private void writeSoundtrack(){
		if(currentMovie==null) return;
		for(StringBuilder soundtrack: currentSoundtrack){
			writeRDF(IMDBRDFBuilder.imdbMovie(currentMovie.toString()), IMDBRDFBuilder.soundtack(), IMDBRDFBuilder.string(soundtrack.toString().replace(" (qv)", "")));
		}
	}
	
	@Override
	protected String defineRelevanceStartingLine() {
		return "=============";
	}

	@Override
	protected String defineRelevanceEndingLine() {
		return "-------------------------------------------------------------------------------";
	}

	
	
}
