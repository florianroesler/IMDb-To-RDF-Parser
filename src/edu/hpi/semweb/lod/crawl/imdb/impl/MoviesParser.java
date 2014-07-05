package edu.hpi.semweb.lod.crawl.imdb.impl;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;

import edu.hpi.semweb.lod.crawl.imdb.Config;
import edu.hpi.semweb.lod.crawl.imdb.IMDBMovie;
import edu.hpi.semweb.lod.crawl.imdb.IMDBParser;
import edu.hpi.semweb.lod.crawl.imdb.IMDBRDFBuilder;
import edu.hpi.semweb.lod.crawl.imdb.id.HashSetBuilder;

public class MoviesParser extends IMDBParser{

	private HashSetBuilder idLookup = new HashSetBuilder();

	private PrintWriter writer;

	private boolean onlyMatchIds = false;


	public MoviesParser(boolean isPatchedFile) {
		super(isPatchedFile);

	}

	@Override
	protected boolean omitOutput() {
		return onlyMatchIds;
	}

	@Override
	public void run() {
		if(onlyMatchIds){
			idLookup.run();
			try {
				writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(Config.ORIGINALPARSEDPATH+"MatchedIds.list_parsed"), Charset.forName("Windows-1252")));
				System.out.println("Writing matched IDs to "+Config.ORIGINALPARSEDPATH+"MatchedIds.list_parsed");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		super.run();

	}

	public void setOnlyMatchIds(boolean onlyMatchIds) {
		this.onlyMatchIds = onlyMatchIds;
	}

	@Override
	public String defineFileName() {
		return "movies.list";
	}

	@Override
	protected void onNewLine(String line) {
		if(line.length() == 0 || line.contains("{")) return;

		IMDBMovie movie = new IMDBMovie(line);
		if(onlyMatchIds){
			String lookUp = movie.getTitle()+"_"+movie.getYear();
			String imdbId = idLookup.getIdForTitle(lookUp);
			final String imdbUrl = "<http://www.imdb.com";
			if(imdbId != null && !movie.isVideoGame()){
				writer.write(IMDBRDFBuilder.hpilodMovie(movie.toString()) +" "+ IMDBRDFBuilder.sameAs() +" "+ imdbUrl+imdbId+">"+" .\n");

				String cleanId = imdbId.replace("/title/", "").replace("/", "");
				writer.write(IMDBRDFBuilder.hpilodMovie(movie.toString()) +" "+ IMDBRDFBuilder.imdbId() +" "+ IMDBRDFBuilder.string(cleanId)+" .\n");
			}
			return;
		}

		writeRDF(IMDBRDFBuilder.hpilodMovie(movie.toString()), IMDBRDFBuilder.is(), IMDBRDFBuilder.film());
		writeRDF(IMDBRDFBuilder.hpilodMovie(movie.toString()), IMDBRDFBuilder.label(), IMDBRDFBuilder.string(movie.getTitle()));
		writeRDF(IMDBRDFBuilder.hpilodMovie(movie.toString()), IMDBRDFBuilder.name(), IMDBRDFBuilder.string(movie.getTitle()));
		//writeRDF(IMDBRDFBuilder.imdbMovie(movie.toString()), IMDBRDFBuilder.releaseDate(), IMDBRDFBuilder.string(movie.getYear()));

	}

	@Override
	protected String defineEncoding() {
		return "Windows-1252";
	}

	@Override
	protected String defineRelevanceStartingLine() {
		return "===========";
	}

	@Override
	protected String defineRelevanceEndingLine() {
		return "--------------------------------------------------------------------------------";
	}

	@Override
	protected void onFileEnd() {
		if(onlyMatchIds){
			writer.close();
		}
	}

}
