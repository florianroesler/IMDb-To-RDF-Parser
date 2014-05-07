package edu.hpi.semweb.lod.crawl.imdb;

import java.util.List;


public class IMDBActorsParser extends IMDBToCSVParser{

	private boolean isRelevantContent = false;

	private String currentActor = "";
	@Override
	public void run() {
		super.run();
		closeWriter();
	};
	
	@Override
	protected String defineInputFilePath() {
		return "/Users/froesler/Downloads/moviedb-3.24/lists/actors.list";
	}

	@Override
	protected String defineEncoding() {
		return "Windows-1252";
	}

	@Override
	protected void onNewLine(String line) {
		if(!isStopped()){


			if(isRelevantContent){

				if(line.startsWith("-----------------------------------------------------------------------------")){
					isRelevantContent = false;
					return;
				}

				if(line.contains("{") || line.length()==0){
					return;
				}
				String dirtyTitle;
				String[] tiles = line.split("\t");
				List<String> cleanedTiles = CleaningHelper.removeEmptyElements(tiles);

				if(line.startsWith("\t")){
					dirtyTitle = cleanedTiles.get(0);

				}else{

					String name = cleanedTiles.get(0);
					if(name.contains(",")){
						String[] nameTiles = name.split(",");
						if(nameTiles.length>1){
							name = nameTiles[1]+" "+ nameTiles[0];
							name = name.trim();
						}

					}
					currentActor = name;

					dirtyTitle = cleanedTiles.get(1);
				}

				String cleanLine = cleanTitleLine(dirtyTitle);

				String title = RegexHelper.findFirstOccurence(cleanLine, ".+?[(]").replace("(", "").trim();

				String year = RegexHelper.findFirstOccurence(cleanLine, "\\(\\d+\\)").replace("(", "").replace(")", "");
				String role = RegexHelper.findFirstOccurence(cleanLine, "\\[\\w+\\]").replace("[", "").replace("]", "");

				writeCSV(new String[]{currentActor, title, year, role});

			}else{
				if(line.startsWith("----			------")){
					isRelevantContent = true;
					return;
				}
			}

		}		
	}

	private String cleanTitleLine(String dirtyTitle){
		dirtyTitle = dirtyTitle.replace("(V)", "");
		dirtyTitle = dirtyTitle.replace("\"", "");
		dirtyTitle = dirtyTitle.replaceAll("<\\d*>", "");
		return dirtyTitle;
	}

}
