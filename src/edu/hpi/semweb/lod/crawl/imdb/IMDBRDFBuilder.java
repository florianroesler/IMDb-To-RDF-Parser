package edu.hpi.semweb.lod.crawl.imdb;

import java.net.URI;
import java.net.URISyntaxException;


public class IMDBRDFBuilder {


	private static final String PROPERTY = "<http://dbpedia.org/property/";
	private static final String RESOURCE = "<http://www.imdb.com/resource/";
	private static final String ACTOR = "<http://dbpedia.org/ontology/Actor>";
	private static final String FILM = "<http://dbpedia.org/ontology/Film>";
	private static final String IS = "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type>";
	private static final String NAME = "<http://dbpedia.org/property/name>";
	private static final String LABEL = "<http://www.w3.org/2000/01/rdf-schema#label>";
	private static final String RELEASEDATE = "<http://dbpedia.org/property/releaseDate>";
	private static final String TRIVIA = "<http://www.imdb.com/trivia>";
	private static final String SOUNDTRACK = "<http://www.imdb.com/soundtrack>";
	private static final String PLOT = "<http://www.imdb.com/plot>";
	private static final String RUNTIME = "<http://www.imdb.com/runtime>";
	private static final String KEYWORD = "<http://www.imdb.com/keyword>";
	private static final String WRITER = "<http://dbpedia.org/ontology/writer>";
	private static final String PRODUCER = "<http://dbpedia.org/ontology/producer>";
	private static final String RATING = "<http://schema.org/Rating>";
	private static final String RATINGCOUNT = "<http://schema.org/ratingCount>";
	private static final String LOCATION = "<http://www.imdb.com/location>";
	private static final String AKANAME = "<http://www.imdb.com/akaname>";
	private static final String AKATITLE = "<http://www.imdb.com/akatitle>";
	private static final String ALTERNATEVERSION = "<http://www.imdb.com/alternate_version>";
	private static final String ARTICLE = "<http://www.imdb.com/article>";
	private static final String COVER = "<http://www.imdb.com/cover>";
	private static final String OTHERWORK = "<http://www.imdb.com/other_work>";
	private static final String MISCCREW = "<http://www.imdb.com/misc_crew>";
	private static final String TRADEMARK = "<http://www.imdb.com/trademark>";
	private static final String INTERVIEW = "<http://www.imdb.com/interview>";
	private static final String SPOUSE = "<http://www.imdb.com/spouse>";
	private static final String PICTORIAL = "<http://www.imdb.com/pictorial>";
	private static final String QUOTE = "<http://www.imdb.com/quote>";
	private static final String PORTRAYAL = "<http://www.imdb.com/portrayal>";
	private static final String BIOGRAPHY = "<http://www.imdb.com/biography>";
	private static final String SALARY = "<http://www.imdb.com/salary>";
	private static final String DATEBORN = "<http://www.imdb.com/date_born>";
	private static final String DEATHDATE = "<http://www.imdb.com/death_date>";
	private static final String NICKNAME = "<http://www.imdb.com/nickname>";
	private static final String HEIGHT = "<http://www.imdb.com/height>";
	private static final String REALNAME = "<http://www.imdb.com/real_name>";
	private static final String BACKGROUND = "<http://www.imdb.com/background>";
	
	private static final String PRODUCTIONDESIGNER = "<http://www.imdb.com/production_designer>";
	private static final String EDITOR = "<http://www.imdb.com/editor>";
	private static final String DIRECTOR = "<http://www.imdb.com/director>";
	private static final String COSTUMEDESIGNER = "<http://www.imdb.com/costume_designer>";
	private static final String COMPOSER = "<http://www.imdb.com/composer>";
	private static final String CINEMATOGRAPHER = "<http://www.imdb.com/cinematographer>";



	public static final String string(String s){
		return "\""+s.replace("\\", "\\\\").replace("\"", "\\\"")+"\"";
	}

	public static final String imdbMovie(String uniqueMovieTitle){
		uniqueMovieTitle = uniqueMovieTitle.replace("/", "_");
		try {
			return "<"+new URI("http", "www.imdb.com", "/movie/"+uniqueMovieTitle, null).toASCIIString()+">";
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static final String actor(){
		return ACTOR;
	}

	public static final String imdbActor(String uniqueActorName){
		uniqueActorName = uniqueActorName.replace("/", "_");
		try {
			return "<"+new URI("http", "www.imdb.com", "/actor/"+uniqueActorName, null).toASCIIString()+">";
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return "";
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
	
	public static final String producer(){
		return PRODUCER;
	}
	
	public static final String rating(){
		return RATING;
	}
	
	public static final String ratingCount(){
		return RATINGCOUNT;
	}
	
	public static final String location(){
		return LOCATION;
	}
	
	public static final String akaTitle(){
		return AKATITLE;
	}
	
	public static final String akaName(){
		return AKANAME;
	}
	
	public static final String alternateVersion(){
		return ALTERNATEVERSION;
	}
	
	
	public static final String article(){
		return ARTICLE;
	}
	public static final String cover(){
		return COVER;
	}
	public static final String otherWork(){
		return OTHERWORK;
	}
	public static final String miscCrew(){
		return MISCCREW;
	}
	public static final String trademark(){
		return TRADEMARK;
	}
	public static final String interview(){
		return INTERVIEW;
	}
	public static final String spouse(){
		return SPOUSE;
	}
	public static final String pictorial(){
		return PICTORIAL;
	}
	public static final String quote(){
		return QUOTE;
	}
	public static final String portrayal(){
		return PORTRAYAL;
	}
	public static final String biography(){
		return BIOGRAPHY;
	}
	public static final String salary(){
		return SALARY;
	}
	public static final String dateBorn(){
		return DATEBORN;
	}
	public static final String deathDate(){
		return DEATHDATE;
	}
	public static final String nickname(){
		return NICKNAME;
	}
	public static final String height(){
		return HEIGHT;
	}
	public static final String realName(){
		return REALNAME;
	}
	public static final String background(){
		return BACKGROUND;
	}
	public static final String productionDesigner(){
		return PRODUCTIONDESIGNER;
	}
	public static final String director(){
		return DIRECTOR;
	}
	public static final String editor(){
		return EDITOR;
	}
	public static final String cinematographer(){
		return CINEMATOGRAPHER;
	}
	public static final String composer(){
		return COMPOSER;
	}
	public static final String costumeDesigner(){
		return COSTUMEDESIGNER;
	}
	public static final String person(String uniquePersonName){
		uniquePersonName = uniquePersonName.replace("/", "_");
		try {
			return "<"+new URI("http", "www.imdb.com", "/person/"+uniquePersonName, null).toASCIIString()+">";
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	private static String buildRDF(String genericURI, String specificPart){
		return new StringBuilder(genericURI).append(specificPart).append(">").toString();
	}
}
