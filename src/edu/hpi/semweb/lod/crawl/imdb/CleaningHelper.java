package edu.hpi.semweb.lod.crawl.imdb;

import java.util.ArrayList;
import java.util.List;

public class CleaningHelper {

	public static String uniquifyMovie(String movie){
		boolean tvSeries = movie.contains("\"");
		boolean tvMovie = movie.contains("(TV)");
		boolean videoGame = movie.contains("(VG)");
		boolean videoMovie = movie.contains("(V)");
		boolean mini = movie.contains("(mini)");
		
		String quarter = "";
		String year = RegexHelper.findFirstOccurence(movie, "\\((\\d+|\\?{4})(/\\w+)?\\)");
		year = CleaningHelper.removeRoundBrackets(year);
		if(year.contains("/")){
			quarter = year.split("/")[1];
		}
		String title = RegexHelper.findFirstOccurence(movie, ".+?\\s\\(([^\\d].+\\s)?").replace("(", "").replace("\"", "").trim();
		
		StringBuilder builder = new  StringBuilder(title.replace(" ", "_"));
		
		if(year.length()>0){
			builder.append("_"+year);
		}
		if(quarter.length()>0){
			builder.append("_"+quarter);
		}
		
		if(tvMovie){
			builder.append("_TVMovie");
		}
		if(tvSeries){
			builder.append("_TVSeries");
		}
		if(videoGame){
			builder.append("_Videogame");
		}
		if(videoMovie){
			builder.append("_Videomovie");
		}
		
		return builder.toString();
	}
	
	public static List<String> removeEmptyElements(String[] tiles){
		List<String> cleanedParts = new ArrayList<String>();
		for(String s:tiles){
			if(s.length()>0){
				cleanedParts.add(s);
			}
		}
		return cleanedParts;
	}
	
	public static String cleanActorName(String name){
		
		if(name.contains(",")){
			String[] nameTiles = name.split(",");
			if(nameTiles.length>1){
				name = nameTiles[1]+" "+ nameTiles[0];
				name = name.trim();
			}

		}
		return name;
	}
	
	public static String removeRoundBrackets(String s){
		return s.replace("(", "").replace(")", "");
	}
	
	public static String removeSquareBrackets(String s){
		return s.replace("[", "").replace("]", "");
	}
	
	public static String removeUnderscore(String s){
		return s.replace("_", "");
	}
}
