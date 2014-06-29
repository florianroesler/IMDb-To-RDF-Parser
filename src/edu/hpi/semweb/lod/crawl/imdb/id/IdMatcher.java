package edu.hpi.semweb.lod.crawl.imdb.id;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;

import edu.hpi.semweb.lod.crawl.imdb.Config;
import edu.hpi.semweb.lod.crawl.imdb.IMDBRDFBuilder;
import edu.hpi.semweb.lod.crawl.imdb.RegexHelper;

public class IdMatcher{

	private HashSetBuilder hashSetBuilder = new HashSetBuilder();

	private PrintWriter writer;

	public IdMatcher() throws IOException {
		System.out.println("Building Hash Index For IDs");
		hashSetBuilder.run();	
		
		writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(Config.ORIGINALPARSEDPATH+"MatchedIds"), Charset.forName("Windows-1252")));

		InputStreamReader streamReader = new InputStreamReader(new FileInputStream(new File(Config.ORIGINALPARSEDPATH+"movies.list_parsed")));
		BufferedReader reader = new BufferedReader(streamReader);
		String line =null;
		while((line = reader.readLine()) != null){
			onNewLine(line);
		}
		System.out.println("Done Matching Ids");
		writer.close();
	}


	private void onNewLine(String line) {
		if(line.contains("http://www.w3.org/2000/01/rdf-schema#label")) return;
		
		String uri = RegexHelper.findFirstOccurence(line, ".*?>");
		line = line.replaceAll("<.*>", "").replaceAll("\\.$", "").trim();
		String title = line.replaceAll("^\"", "").replaceAll("\"$", "");
		
		String id = hashSetBuilder.getIdForTitle(title);
		if(id == null){
			return;
		}
		writer.write(uri+" "+IMDBRDFBuilder.imdbId()+" "+ IMDBRDFBuilder.string(id)+"\n");
	}
}
