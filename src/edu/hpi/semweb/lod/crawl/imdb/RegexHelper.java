package edu.hpi.semweb.lod.crawl.imdb;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexHelper {

	public static String findFirstOccurence(String target, String pattern){
		 Pattern p = Pattern.compile(pattern);

		    Matcher matcher = p.matcher(target);
		    
		    if (matcher.find()) {
		      return matcher.group();
		    }else{
		    	return "";
		    }
	}
}
