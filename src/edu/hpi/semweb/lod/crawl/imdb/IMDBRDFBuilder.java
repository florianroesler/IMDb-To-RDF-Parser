package edu.hpi.semweb.lod.crawl.imdb;


public class IMDBRDFBuilder {


	private static final String IMDBACTOR = "<http://www.imdb.com/actor/";
	private static final String PROPERTY = "<http://www.imdb.com/property/";
	private static final String RESOURCE = "<http://www.imdb.com/resource/";
	private static final String ACTOR = "<http://dbpedia.org/ontology/Actor>";
	private static final String FILM = "<http://dbpedia.org/ontology/Film>";
	private static final String IS = "<rdf:type>";
	private static final String NAME = "<http://dbpedia.org/property/name>";
	private static final String LABEL = "<http://www.w3.org/2000/01/rdf-schema#label>";
	private static final String RELEASEDATE = "<http://dbpedia.org/property/releaseDate>";
	private static final String IMDBMOVIE = "<http://www.imdb.com/movie/";


	public static final String string(String s){
		return "\""+s+"\"";
	}

	public static final String imdbMovie(String uniqueMovieTitle){
		return buildRDF(IMDBMOVIE, uniqueMovieTitle);
	}

	public static final String actor(){
		return ACTOR;
	}

	public static final String imdbActor(String uniqueActorName){
		return buildRDF(IMDBACTOR, uniqueActorName);
	}
	
	public static final String film(){
		return FILM;
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
		return IS;
	}

	public static final String name(){
		return NAME;
	}

	public static final String releaseDate(){
		return RELEASEDATE;
	}

	public static final String label(){
		return LABEL;
	}

	private static String buildRDF(String genericURI, String specificPart){
		return new StringBuilder(genericURI).append(specificPart).append(">").toString();
	}
}
