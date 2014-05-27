package edu.hpi.semweb.lod.crawl.imdb;

public class IMDBRDFBuilder {
	
	private static final String ACTOR = "<http://www.imdb.com/actor/";
	private static final String MOVIE = "<http://www.imdb.com/movie/";
	private static final String PROPERTY = "<http://www.imdb.com/property/";
	private static final String RESOURCE = "<http://www.imdb.com/resource/";
	private static final String is = "<rdf:type>";

	
	public static final String actor(String specific){
		return buildRDF(ACTOR, specific);
	}
	
	public static final String movie(String specific){
		return buildRDF(MOVIE, specific);
	}
	
	public static final String prop(String specific){
		return buildRDF(PROPERTY, specific);
	}
	
	public static final String res(String specific){
		return buildRDF(RESOURCE, specific);
	}
	
	public static final String arbitrary(String URI,String specificObject){
		return buildRDF(RESOURCE,URI+"/"+specificObject);
	}
	
	public static final String is(){
		return is;
	}
	
	private static String buildRDF(String genericURI, String specificPart){
		return new StringBuilder(genericURI).append(specificPart).append(">").toString();
	}
}
