package edu.hpi.semweb.lod.crawl.imdb;

import java.util.List;

/**
 * Simple Parser for IMDB-dumps that follow the same structure as the directors.list
 */
public abstract class IMDBGenericPersonParser extends IMDBParser{

	private IMDBActor currentPerson;

	protected abstract String definePersonRDFProperty();
	protected abstract String definePersonRDFType();
	
	public IMDBGenericPersonParser(boolean isPatchedFile) {
		super(isPatchedFile);
	}


	@Override
	protected void onNewLine(String line) {
		if(line.length()==0) return;
		List<String> tiles = CleaningHelper.removeEmptyElements(line.split("\t"));

		
		String titlePart = "";
		
		if(tiles.size() == 2){		
			//thats a new director and a trailing movie
			currentPerson = new IMDBActor(tiles.get(0));
			titlePart = tiles.get(1);
		}else{
			titlePart = tiles.get(0);
		}
		
		if(titlePart.contains("{")) return;
		
		IMDBMovie movie = new IMDBMovie(titlePart);
			
		writeRDF(IMDBRDFBuilder.hpilodMovie(movie.toString()), definePersonRDFProperty(), IMDBRDFBuilder.hpilodPerson(currentPerson.toString()));
		writeRDF(IMDBRDFBuilder.hpilodPerson(currentPerson.toString()), IMDBRDFBuilder.is(), IMDBRDFBuilder.person());
		writeRDF(IMDBRDFBuilder.hpilodPerson(currentPerson.toString()), IMDBRDFBuilder.is(), definePersonRDFType());
		
	}

	@Override
	protected void onFileEnd() {
		
	}

}
