package edu.hpi.semweb.lod.crawl.imdb;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;

import edu.hpi.semweb.lod.crawl.PlainTextCrawler;


public abstract class IMDBToCSVParser extends PlainTextCrawler{

	private PrintWriter writer;
	
	private String pathToDump;
	
	protected abstract String defineFileName();
	
	
	
	public IMDBToCSVParser(){
		
		pathToDump = Config.ROOTPATH;
		
		try {
			writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(Config.PARSEDPATH+defineFileName()+"_parsed"), Charset.forName(defineEncoding())));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		super.run();
		closeWriter();
	};
	
	@Override
	protected String defineInputFilePath() {
		return pathToDump+defineFileName();
	};
	
	protected void writeCSV(String... tiles){
		if(tiles.length == 0) return;
		StringBuilder builder = new StringBuilder();
		for(String s:tiles){
			builder.append(s);
			builder.append(",");
		}
		builder.deleteCharAt(builder.length()-1);
		builder.append("\n");
		writer.write(builder.toString());
	}
	
	protected void writeRDF(String s, String p, String o, String sType, String pType, String oType){
		String sT = "movie";
		String pT = "resource";
		String oT = "movie";
		
		if(s.length() == 0 || p.length() == 0 || o.length() == 0) return;
		StringBuilder builder = new StringBuilder();

		if(sType == "person") sT = "actor";
		if(oType == "person") oT = "actor";
		builder.append("<http://www.imdb.com/"+sT+"/");
		builder.append(s+">,");
		builder.append("<http://www.imdb.com/"+pT+"/");
		builder.append(p+">,");
		builder.append("<http://www.imdb.com/"+oT+"/");
		builder.append(o+">,");
		
		builder.deleteCharAt(builder.length()-1);
		builder.append("\n");
		writer.write(builder.toString());
	}
	
	private void closeWriter(){
		writer.close();
	}


}
