package edu.hpi.semweb.lod.crawl.imdb;

import java.net.URI;
import java.net.URISyntaxException;


public class IMDBRDFBuilder {


	private static final String PROPERTY = "<http://www.dbpedia.org/property/";
	private static final String HPILOD = "<http://www.hpi.de/lod/";
	private static final String ONTOLOGY = "<http://www.dbpedia.org/ontology/";
	private static final String ACTOR = "<http://www.dbpedia.org/ontology/Actor>";
	private static final String PERSON = "<http://www.dbpedia.org/ontology/Person>";
	private static final String FILM = "<http://www.dbpedia.org/ontology/Film>";
	private static final String IS = "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type>";
	private static final String NAME = "<http://www.dbpedia.org/property/name>";
	private static final String LABEL = "<http://www.w3.org/2000/01/rdf-schema#label>";
	private static final String RELEASEDATE = "<http://www.dbpedia.org/property/releaseDate>";
	private static final String TRIVIA = "<http://www.hpi.de/lod/trivia>";
	private static final String SOUNDTRACK = "<http://dbpedia.org/property/soundtrack>";
	private static final String PLOT = "<http://dbpedia.org/property/plot>";
	private static final String RUNTIME = "<http://dbpedia.org/ontology/runtime>";
	private static final String KEYWORD = "<http://www.hpi.de/lod/keyword>";
	private static final String WRITER = "<http://www.dbpedia.org/ontology/writer>";
	private static final String PRODUCER = "<http://www.dbpedia.org/ontology/producer>";
	private static final String RATING = "<http://dbpedia.org/property/imdbRating>";
	private static final String RATINGCOUNT = "<http://www.schema.org/ratingCount>";
	private static final String LOCATION = "<http://dbpedia.org/property/location>";
	private static final String AKANAME = "<http://dbpedia.org/property/aka>";
	private static final String AKATITLE = "<http://dbpedia.org/property/aka>";
	private static final String ALTERNATEVERSION = "<http://dbpedia.org/property/alternateVersions>";
	private static final String ARTICLE = "<http://www.hpi.de/lod/article>";
	private static final String COVER = "<http://dbpedia.org/property/cover>";
	private static final String OTHERWORK = "<http://www.hpi.de/lod/other_work>";
	private static final String MISCCREW = "<http://www.hpi.de/lod/misc_crew>";
	private static final String TRADEMARK = "<http://www.hpi.de/lod/trademark>";
	private static final String INTERVIEW = "<http://www.hpi.de/lod/interview>";
	private static final String SPOUSE = "<http://dbpedia.org/ontology/spouse>";
	private static final String PICTORIAL = "<http://www.hpi.de/lod/pictorial>";
	private static final String QUOTE = "<http://dbpedia.org/property/quote>";
	private static final String PORTRAYAL = "<http://www.hpi.de/lod/portrayal>";
	private static final String BIOGRAPHY = "<http://www.hpi.de/lod/biography>";
	private static final String SALARY = "<http://dbpedia.org/property/grossIncome>";
	private static final String DATEBORN = "<http://dbpedia.org/ontology/birthDate>";
	private static final String DEATHDATE = "<http://dbpedia.org/ontology/deathDate>";
	private static final String NICKNAME = "<http://dbpedia.org/property/nickname>";
	private static final String HEIGHT = "<http://dbpedia.org/property/height>";
	private static final String REALNAME = "<http://dbpedia.org/property/realName>";
	private static final String BACKGROUND = "<http://dbpedia.org/property/background>";
	private static final String DATE = "<http://dbpedia.org/property/date>";
	private static final String YEAR = "<http://dbpedia.org/property/year>";
	private static final String AKATYPE = "<http://hpi.de/lod/akaType>";
	private static final String THING = "<http://www.w3.org/2002/07/owl#Thing>";
	private static final String IMDBID = "<http://dbpedia.org/ontology/imdbId>";	
	private static final String SAMEAS = "<http://www.w3.org/2002/07/owl#sameAs>";

	
	private static final String PRODUCTIONDESIGNER = "<http://dbpedia.org/property/productionDesigner>";
	private static final String EDITOR = "<http://dbpedia.org/property/editor>";
	private static final String DIRECTOR = "<http://dbpedia.org/ontology/director>";
	private static final String COSTUMEDESIGNER = "<http://dbpedia.org/property/costumeDesigner>";
	private static final String COMPOSER = "<http://dbpedia.org/ontology/composer>";
	private static final String CINEMATOGRAPHER = "<http://dbpedia.org/property/cinematographer>";



	public static final String string(String s){
		return "\""+s.replace("\\", "\\\\").replace("\"", "\\\"")+"\"";
	}

	public static final String hpilodMovie(String uniqueMovieTitle){
		uniqueMovieTitle = uniqueMovieTitle.replace("/", "_");
		try {
			return "<"+new URI("http", "www.hpi.de", "/lod/movie/"+uniqueMovieTitle, null).toASCIIString()+">";
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static final String actor(){
		return ACTOR;
	}

	public static final String hpilodActor(String uniqueActorName){
		uniqueActorName = uniqueActorName.replace("/", "_");
		try {
			return "<"+new URI("http", "www.hpi.de", "/lod/actor/"+uniqueActorName, null).toASCIIString()+">";
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
	
	public static final String onto(String specific){
		return buildRDF(ONTOLOGY, specific);
	}
	
	public static final String lod(String specific){
		return buildRDF(HPILOD, specific);
	}
	
	public static final String arbitrary(String URI,String specificObject){
		return buildRDF(HPILOD,URI+"/"+specificObject);
	}
	
	public static final String is(){
		return IS;
	}

	public static final String akaType(){
		return AKATYPE;
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
	
	public static final String thing(){
		return THING;
	}
	
	public static final String trivia(){
		return TRIVIA;
	}
	
	public static final String date(){
		return DATE;
	}
	
	public static final String year(){
		return YEAR;
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
	
	public static final String akaTitleObject(String id){
		id = id.replace("/", "_");
		try {
			return "<"+new URI("http", "www.hpi.de", "/lod/akaTitle/"+id, null).toASCIIString()+">";
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return "";
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
	public static final String imdbPerson(String uniquePersonName){
		uniquePersonName = uniquePersonName.replace("/", "_");
		try {
			return "<"+new URI("http", "www.imdb.com", "/person/"+uniquePersonName, null).toASCIIString()+">";
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static final String person(){
		return PERSON;
	}
	
	public static final String imdbId(){
		return IMDBID;
	}
	
	public static final String sameAs(){
		return SAMEAS;
	}
	
	private static String buildRDF(String genericURI, String specificPart){
		specificPart = specificPart.replaceAll("<.*?>", "");
		return new StringBuilder(genericURI).append(specificPart).append(">").toString();
	}
}
