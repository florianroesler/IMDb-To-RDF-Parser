package edu.hpi.semweb.lod.crawl.imdb.diff;

import edu.hpi.semweb.lod.crawl.PlainTextCrawler;

public abstract class AbstractDiffInterpreter extends PlainTextCrawler{

	protected abstract String defineInputFilePath();

	protected abstract void onAddLine(String line);
	protected abstract void onRemoveLine(String line);

	@Override
	protected String defineEncoding() {
		return "Windows-1252";
	}

	@Override
	protected void onNewLine(String line) {
		
		if(line.startsWith(">")){
			onAddLine(line);
		}
		if(line.startsWith("<")){
			onRemoveLine(line);
		}
	}

	@Override
	protected void onFileEnd() {		
	}

	@Override
	protected String defineRelevanceStartingLine() {
		return null;
	}

	@Override
	protected String defineRelevanceEndingLine() {
		return null;
	}
	
	
	
}
