package edu.hpi.semweb.lod.crawl.imdb.impl;

import java.util.ArrayList;
import java.util.List;

import edu.hpi.semweb.lod.crawl.imdb.IMDBMovie;
import edu.hpi.semweb.lod.crawl.imdb.IMDBParser;
import edu.hpi.semweb.lod.crawl.imdb.IMDBRDFBuilder;

public class PlotParser extends IMDBParser{

	private IMDBMovie currentMovie;
	private List<StringBuilder> currentPlots;
	
	public PlotParser(boolean isPatchedFile) {
		super(isPatchedFile);
	}

	@Override
	public String defineFileName() {
		return "plot.list";
	}


	@Override
	protected void onNewLine(String line) {
		if(line.length() == 0) return;
		
		if(line.startsWith("-------------------------------------------------------------------------------")){
			writePlot();
			return;
		}
		
		if(line.startsWith("MV: ")){
			currentMovie = new IMDBMovie(line.replaceFirst("MV: ", "").trim());
			currentPlots = new ArrayList<StringBuilder>();
			currentPlots.add(new StringBuilder());
		}else if(line.startsWith("PL: ")){
			currentPlots.get(currentPlots.size()-1).append(line.replaceFirst("PL: ", "")+" ");
		}else if(line.startsWith("BY: ")){
			currentPlots.add(new StringBuilder());
		}
	}

	@Override
	protected void onFileEnd() {
		writePlot();
	}
	
	private void writePlot(){
		if(currentMovie == null) return;
		
		for(StringBuilder plot: currentPlots){
			if(plot.length() == 0) continue;
			writeRDF(IMDBRDFBuilder.hpilodMovie(currentMovie.toString()), IMDBRDFBuilder.plot(), IMDBRDFBuilder.string(plot.toString().trim()));
		}
	}

	@Override
	protected String defineRelevanceStartingLine() {
		return "===================";
	}

	@Override
	protected String defineRelevanceEndingLine() {
		return null;
	}

}
