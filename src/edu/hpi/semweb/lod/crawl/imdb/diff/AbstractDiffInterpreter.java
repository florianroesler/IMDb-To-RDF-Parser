package edu.hpi.semweb.lod.crawl.imdb.diff;

import edu.hpi.semweb.lod.crawl.PlainTextParser;

public abstract class AbstractDiffInterpreter extends PlainTextParser{

	protected abstract String defineInputFilePath();

	protected abstract void onAddLine(String line);
	protected abstract void onRemoveLine(String line);
	protected abstract void onFileEnd();

	
	@Override
	protected String defineEncoding() {
		return "Windows-1252";
	}

	@Override
	protected void onNewLine(String line) {
		
		if(line.startsWith(">")){
			onAddLine(line.replaceFirst("> ", ""));
		}
		if(line.startsWith("<")){
			onRemoveLine(line.replaceFirst("< ", ""));
		}
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
