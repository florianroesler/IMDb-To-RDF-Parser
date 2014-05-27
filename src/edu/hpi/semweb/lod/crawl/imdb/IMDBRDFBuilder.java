package edu.hpi.semweb.lod.crawl.imdb;

public class IMDBRDFBuilder {
	
	private static final String ACTOR = "<http://www.imdb.com/actor/";
	private static final String MOVIE = "<http://www.imdb.com/movie/";
	private static final String is = "<rdf:type>";

	
	public static final String actor(String specific){
		return buildRDF(ACTOR, specific);
	}
	
	public static final String movie(String specific){
		return buildRDF(MOVIE, specific);
	}
	
	public static final String is(){
		return is;
	}
	
	private static String buildRDF(String genericURI, String specificPart){
		return new StringBuilder(genericURI).append(specificPart).append(">").toString();
	}
}
