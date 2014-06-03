package edu.hpi.semweb.lod.crawl.imdb;


public class IMDBActor {
	
	private String name;
	public IMDBActor(String line){
		name = CleaningHelper.cleanActorName(line);
	}

	@Override
	public String toString(){
		return name;
	}
}
