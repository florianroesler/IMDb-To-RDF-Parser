package edu.hpi.semweb.lod.crawl.imdb.impl;
import java.util.ArrayList;
import java.util.List;

import edu.hpi.semweb.lod.crawl.imdb.IMDBMovie;
import edu.hpi.semweb.lod.crawl.imdb.IMDBParser;
import edu.hpi.semweb.lod.crawl.imdb.IMDBRDFBuilder;


public class RatingsParser extends IMDBParser{

	private String currentMovie;
	
	public RatingsParser(boolean isPatchedFile) {
		super(isPatchedFile);
		// TODO Auto-generated constructor stub
	}


	@Override
	protected String defineFileName() {
		return "ratings.list";
	}
	

	@Override
	protected String defineRelevanceStartingLine() {
		return "MOVIE RATINGS REPORT";
	}

	@Override
	protected String defineRelevanceEndingLine() {
		return "------------------------------------------------------------------------------";
	}

	@Override
	protected void onNewLine(String line) {
		if(!isStopped()){

			if(line.startsWith(" ")){

				if(line.contains("{")) return;

				String[] parts  = line.replace("\\s+", " ").split(" ");
				List<String> cleanedParts = new ArrayList<String>();
				for(String s:parts){
					if(s.length()>0){
						cleanedParts.add(s);
					}
				}

				//String ratingDistribution = cleanedParts.get(0);
				String ratingCount = cleanedParts.get(1);

				String rating = cleanedParts.get(2);

				String title = "";
				for(int i = 3; i<cleanedParts.size()-1;i++){
					if(cleanedParts.get(i).startsWith("(")){
						break;
					}
					title+=cleanedParts.get(i)+" ";
				}
				//title = title.trim();
				//title = title.replaceAll("^\"|\"$", "");
				IMDBMovie mov = new IMDBMovie(title.replaceAll("\\[[^\\[\\]]*\\]",""));
				currentMovie = mov.toString();
				
				writeRDF(IMDBRDFBuilder.hpilodMovie(currentMovie), IMDBRDFBuilder.rating(),("\""+rating+"\"^^xsd:float"));
				writeRDF(IMDBRDFBuilder.hpilodMovie(currentMovie), IMDBRDFBuilder.ratingCount(),("\""+ratingCount+"\"^^xsd:integer"));
			}

		}
	}

	@Override
	protected String defineEncoding() {
		return "Windows-1252";
	}


	@Override
	protected void onFileEnd() {
		// TODO Auto-generated method stub
		
	}



}
