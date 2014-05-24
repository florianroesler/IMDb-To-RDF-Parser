package edu.hpi.semweb.lod.crawl.imdb;

public class IMDBRDFBuilder {
	
	private final String ACTOR = "<http://www.imdb.com/actor/";
	private final String MOVIE = "<http://www.imdb.com/movie/";

	
	public final String actor(String specific){
		return buildRDF(ACTOR, specific);
	}
	
	public final String movie(String specific){
		return buildRDF(MOVIE, specific);
	}
	
	private String buildRDF(String genericURI, String specificPart){
		return new StringBuilder(genericURI).append(specificPart).append(">").toString();
	}
}
