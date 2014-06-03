package edu.hpi.semweb.lod.crawl.imdb;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;


public class IMDBRDFBuilder {


	private static final String IMDBACTOR = "<http://www.imdb.com/actor/";
	private static final String PROPERTY = "<http://www.imdb.com/property/";
	private static final String RESOURCE = "<http://www.imdb.com/resource/";
	private static final String PERSON = "<http://dbpedia.org/ontology/person/";
	private static final String ACTOR = "<http://dbpedia.org/ontology/Actor>";
	private static final String FILM = "<http://dbpedia.org/ontology/Film>";
	private static final String IS = "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type>";
	private static final String NAME = "<http://dbpedia.org/property/name>";
	private static final String LABEL = "<http://www.w3.org/2000/01/rdf-schema#label>";
	private static final String RELEASEDATE = "<http://dbpedia.org/property/releaseDate>";
	private static final String IMDBMOVIE = "<http://www.imdb.com/movie/";
	private static final String TRIVIA = "<http://www.imdb.com/trivia/>";
	private static final String SOUNDTRACK = "<http://www.imdb.com/soundtrack/>";
	private static final String PLOT = "<http://www.imdb.com/plot/>";
	private static final String RUNTIME = "<http://www.imdb.com/runtime/>";
	private static final String KEYWORD = "<http://www.imdb.com/keyword/>";
	private static final String WRITER = "<http://dbpedia.org/ontology/writer/>";



	public static final String string(String s){
		return "\""+s.replace("\\", "\\\\")+"\"";
	}

	public static final String imdbMovie(String uniqueMovieTitle){
		uniqueMovieTitle = uniqueMovieTitle.replace("/", "_");
		try {
			return "<"+new URI("http", "www.imdb.com", "/movie/"+uniqueMovieTitle, null).toASCIIString()+">";
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
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
	
	public static final String trivia(){
		return TRIVIA;
	}
	
	public static final String soundtack(){
		return SOUNDTRACK;
	}
	
	public static final String plot(){
		return PLOT;
	}
	
	public static final String runtime(){
		return RUNTIME;
	}
	
	public static final String keyword(){
		return KEYWORD;
	}
	
	public static final String writer(){
		return WRITER;
	}
	
	
	public static final String person(String specific){
		return buildRDF(PERSON, specific);
	}
	
	private static String buildRDF(String genericURI, String specificPart){
		return new StringBuilder(genericURI).append(specificPart).append(">").toString();
	}
}
