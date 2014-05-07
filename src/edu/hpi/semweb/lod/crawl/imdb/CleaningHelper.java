package edu.hpi.semweb.lod.crawl.imdb;

import java.util.ArrayList;
import java.util.List;

public class CleaningHelper {

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
}
