package edu.hpi.semweb.lod.crawl.imdb;

public class IMDBMovie{
	
	private String title;
	private String year;
	private String quarter;
	private boolean tvSeries;
	private boolean tvMovie;
	private boolean videoGame;
	private boolean videoMovie;
	private boolean mini;

	public IMDBMovie(String movie){
		tvSeries = movie.contains("\"");
		movie = movie.replace("\"", "");

		tvMovie = movie.contains("(TV)");
		movie = movie.replace("(TV)", "");

		videoGame = movie.contains("(VG)");
		movie = movie.replace("(VG)", "");

		videoMovie = movie.contains("(V)");
		movie = movie.replace("(V)", "");

		mini = movie.contains("(mini)");
		movie = movie.replace("(mini)", "");


		quarter = "";
		String yearRegex = "\\((\\d+|\\?{4})(/\\w+)?\\)";
		year = RegexHelper.findFirstOccurence(movie, yearRegex);
		year = CleaningHelper.removeRoundBrackets(year);
		if(year.contains("/")){
			quarter = year.split("/")[1];
		}
		String movieWithoutYear = movie.replaceAll(yearRegex, "");

		//title = RegexHelper.findFirstOccurence(movie, ".+?\\s\\(([^\\d].+\\s)?").replace("(", "").replace("\"", "").trim();
		title = movieWithoutYear.split("\t")[0].replace("\"", "").trim();
	}

	public String getTitle() {
		return title;
	}

	public String getYear() {
		return year;
	}

	public String getQuarter() {
		return quarter;
	}

	public boolean isTvSeries() {
		return tvSeries;
	}

	public boolean isTvMovie() {
		return tvMovie;
	}

	public boolean isVideoGame() {
		return videoGame;
	}

	public boolean isVideoMovie() {
		return videoMovie;
	}

	public boolean isMini() {
		return mini;
	}

	@Override
	public String toString(){
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

}
