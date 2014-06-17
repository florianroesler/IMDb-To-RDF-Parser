package edu.hpi.semweb.lod.crawl.imdb.diff;

import edu.hpi.semweb.lod.crawl.imdb.Config;

public class RDFDiffInterpreter extends AbstractDiffInterpreter{

	@Override
	protected String defineInputFilePath() {
		return Config.RDFDIFFFILE;
	}

	@Override
	protected void onAddLine(String line) {
		System.out.println("Added: "+line);
	}

	@Override
	protected void onRemoveLine(String line) {
		System.out.println("Removed: "+line);
		
	}

}
