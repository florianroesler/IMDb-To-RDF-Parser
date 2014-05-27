package edu.hpi.semweb.lod.crawl.imdb;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class IMDBRDFBuilder {

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

	public static final String imdbMovie(String movieURI){
		URI uri;
		try {
			uri = new URI(
					"http", 
					"imdb.com",
					"/movie/"+movieURI,
					null
					);
			return "<"+uri.toASCIIString()+">";

		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static final String actor(){
		return ACTOR;
	}

	public static final String film(){
		return FILM;
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
