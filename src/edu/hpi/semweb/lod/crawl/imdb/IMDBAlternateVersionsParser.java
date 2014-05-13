package edu.hpi.semweb.lod.crawl.imdb;

import java.util.ArrayList;
import java.util.List;

public class IMDBAlternateVersionsParser extends IMDBToCSVParser{

	private String title = "";
	private String year = "";
	private List<StringBuilder> versions = new ArrayList<StringBuilder>();

	@Override
	protected String defineFileName() {
		return "alternate-versions.list";
	}

	@Override
	protected String defineEncoding() {
		return "Windows-1252";
	}

	@Override
	protected void onNewLine(String line) {

		if(line.startsWith("#") && !line.contains("{")){
			if(title.length()!=0){
				writeToFile();
			}

			List<String> titleTiles = CleaningHelper.removeEmptyElements(line.replace("# ", "").split("\\("));
			title = titleTiles.get(0).replace("\\s\\(", "").replace("\"", "").trim();
			if(titleTiles.size()>1){
				year = CleaningHelper.removeRoundBrackets(titleTiles.get(1).trim());
			}
		}else if(line.startsWith("- ") && title.length()!=0){
			String firstLine = line.replaceFirst("- ", "");
			versions.add(new StringBuilder().append(firstLine.trim()));
		}else if(line.startsWith(" ") && title.length()!=0){
			versions.get(versions.size()-1).append(line.trim());
		}
	}

	private void writeToFile(){
		for(StringBuilder b:versions){
			writeCSV(title, year, b.toString());
		}
		title = "";
		year = "";
		versions = new ArrayList<StringBuilder>();
	}

	@Override
	protected String defineRelevanceStartingLine() {
		return "ALTERNATE VERSIONS LIST";
	}

	@Override
	protected String defineRelevanceEndingLine() {
		return "----------------------------------------------------------------------------";
	}

	@Override
	protected void onFileEnd() {
		writeToFile();

	}

}
