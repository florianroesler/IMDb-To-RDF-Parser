package edu.hpi.semweb.lod.crawl.imdb.diff;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import edu.hpi.semweb.lod.crawl.imdb.Config;

public class RDFDiffInterpreter extends AbstractDiffInterpreter{

	private final File newTriplesFile = new File(Config.RDFINCREMENTALNEW);
	private final File deletedTriplesFile = new File(Config.RDFINCREMENTALDELETE);
	private PrintWriter newTriplesWriter;
	private PrintWriter deletedTriplesWriter;

	public RDFDiffInterpreter(){
		super();
		try {
			newTriplesWriter = new PrintWriter(newTriplesFile);
			deletedTriplesWriter = new PrintWriter(newTriplesFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected String defineInputFilePath() {
		return Config.RDFDIFFFILE;
	}

	@Override
	protected void onAddLine(String line) {
		newTriplesWriter.write(line);
	}

	@Override
	protected void onRemoveLine(String line) {
		deletedTriplesWriter.write(line);
	}

	@Override
	protected void onFileEnd() {
		newTriplesWriter.close();	
		deletedTriplesWriter.close();
	}

}
