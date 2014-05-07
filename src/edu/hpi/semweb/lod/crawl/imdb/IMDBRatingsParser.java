package edu.hpi.semweb.lod.crawl.imdb;
import java.util.ArrayList;
import java.util.List;


public class IMDBRatingsParser extends IMDBToCSVParser{


	@Override
	protected String defineInputFilePath() {
		return "/Users/froesler/Downloads/moviedb-3.24/lists/ratings.list";
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

				String ratingDistribution = cleanedParts.get(0);
				String ratingCount = cleanedParts.get(1);

				String rating = cleanedParts.get(2);

				String title = "";
				String yearOfProduction = "";
				for(int i = 3; i<cleanedParts.size()-1;i++){
					if(cleanedParts.get(i).startsWith("(")){
						yearOfProduction = cleanedParts.get(i).replace("(", "").replace(")", "");
						break;
					}
					title+=cleanedParts.get(i)+" ";
				}
				title = title.trim();
				title = title.replaceAll("^\"|\"$", "");
				writeCSV(new String[]{title, yearOfProduction, rating, ratingCount, ratingDistribution});
			}

		}
	}

	@Override
	protected String defineEncoding() {
		return "Windows-1252";
	}



}
