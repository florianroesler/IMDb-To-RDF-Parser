package edu.hpi.semweb.lod.crawl.imdb;


public class IMDBActorsParser extends IMDBToCSVParser{

	private boolean isRelevantContent = false;
	
	private String currentActor = "";
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
			if(line.startsWith("-----------------------------------------------------------------------------")){
				isRelevantContent = false;
				closeWriter();
				return;
			}

			if(isRelevantContent){
				if(line.contains("{")){
					return;
				}
				if(line.startsWith("\t")){
					//new line for movie title
					return;
				}else{
					//new actor
				}
				
				
			}else{
				if(line.startsWith("----			------")){
					isRelevantContent = true;
					return;
				}
			}

		}		
	}

}
