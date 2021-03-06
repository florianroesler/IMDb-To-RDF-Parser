package edu.hpi.semweb.lod.crawl.imdb.impl;

import java.util.List;

import edu.hpi.semweb.lod.crawl.imdb.CleaningHelper;
import edu.hpi.semweb.lod.crawl.imdb.IMDBActor;
import edu.hpi.semweb.lod.crawl.imdb.IMDBMovie;
import edu.hpi.semweb.lod.crawl.imdb.IMDBParser;
import edu.hpi.semweb.lod.crawl.imdb.IMDBRDFBuilder;
import edu.hpi.semweb.lod.crawl.imdb.RegexHelper;


public class ActorsParser extends IMDBParser{

	private IMDBActor currentActor;

	public ActorsParser(boolean isPatchedFile) {
		super(isPatchedFile);
	}

	@Override
	public String defineFileName() {
		return "actors.list";
	}

	@Override
	protected String defineRelevanceStartingLine() {
		return "----			------";
	}

	@Override
	protected String defineRelevanceEndingLine() {
		return "-----------------------------------------------------------------------------";
	}

	@Override
	protected void onNewLine(String line) {
		if(!isStopped()){

			if(line.contains("{") || line.length()==0){
				return;
			}

			String dirtyTitle;
			String[] tiles = line.split("\t");
			List<String> cleanedTiles = CleaningHelper.removeEmptyElements(tiles);

			if(line.startsWith("\t")){
				dirtyTitle = cleanedTiles.get(0);

			}else{

				String name = cleanedTiles.get(0);

				currentActor = new IMDBActor(name);
				
				dirtyTitle = cleanedTiles.get(1);
				
			}



			
			IMDBMovie mov = new IMDBMovie(dirtyTitle.replaceAll("<\\d*>", "").replaceAll("\\[[^\\[\\]]*\\]","").replace("(uncredited)", ""));
			String title = mov.toString();

			String role = RegexHelper.findFirstOccurence(dirtyTitle, "\\[\\w+\\]").replace("[", "").replace("]", "").replace(" ", "_");

			writeRDF(IMDBRDFBuilder.hpilodMovie(title), IMDBRDFBuilder.onto("starring"), IMDBRDFBuilder.hpilodActor(currentActor.toString()));
			writeRDF(IMDBRDFBuilder.hpilodActor(currentActor.toString()), IMDBRDFBuilder.is(), IMDBRDFBuilder.actor());
			writeRDF(IMDBRDFBuilder.hpilodMovie(title), IMDBRDFBuilder.is(), IMDBRDFBuilder.film());
			if ((role.length() > 0)&&!(role.contains("Himself"))&&!(role.contains("Themselves"))&&!(role.contains("Herself"))){
				writeRDF(IMDBRDFBuilder.hpilodActor(currentActor.toString()), IMDBRDFBuilder.prop("character"), IMDBRDFBuilder.arbitrary("fictionalCharacter", role));
				writeRDF(IMDBRDFBuilder.arbitrary("fictionalCharacter", role), IMDBRDFBuilder.lod("appearsIn"), IMDBRDFBuilder.hpilodMovie(title));
				writeRDF(IMDBRDFBuilder.arbitrary("fictionalCharacter", role), IMDBRDFBuilder.onto("portrayer"), IMDBRDFBuilder.hpilodActor(currentActor.toString()));
				writeRDF(IMDBRDFBuilder.arbitrary("fictionalCharacter", role), IMDBRDFBuilder.is(), IMDBRDFBuilder.onto("FictionalCharacter"));
			} 
		}
		// ontology/starring (Movie -> Actor)
		// ontology/portrayer (Character -> Actor)
		// http://www.hpi.de/lod/appearsIn (Character -> Movie)
	}		
	
	@Override
	protected void onFileEnd() {

	}



}
