package edu.hpi.semweb.lod.crawl.imdb;

public class IMDBAkaNamesParser extends IMDBToCSVParser{

	private String currentActor;
	@Override
	protected String defineInputFilePath() {
		return "/Users/froesler/Downloads/moviedb-3.24/lists/aka-names.list";
	}

	@Override
	protected String defineEncoding() {
		return "Windows-1252";
	}

	@Override
	protected void onNewLine(String line) {
		if(line.startsWith(" ")){
			String name = RegexHelper.findFirstOccurence(line, "aka\\s[^)]+").replace("aka ", "");
			name = CleaningHelper.cleanActorName(name);
			writeCSV(currentActor, name);
		}else{
			currentActor = CleaningHelper.cleanActorName(line);
			return;
		}
	}

	@Override
	protected String defineRelevanceStartingLine() {
		return "==============";
	}

	@Override
	protected String defineRelevanceEndingLine() {
		return null;
	}

	@Override
	protected void onFileEnd() {
		// TODO Auto-generated method stub
		
	}

}
