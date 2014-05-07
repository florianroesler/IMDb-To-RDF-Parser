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
}
