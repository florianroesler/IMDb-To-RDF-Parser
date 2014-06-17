package edu.hpi.semweb.lod.crawl.imdb;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;

import edu.hpi.semweb.lod.crawl.PlainTextCrawler;


public abstract class IMDBParser extends PlainTextCrawler{

	private PrintWriter writer;
	
	private String pathToDump;
	private String parsedDirPath;

	public abstract String defineFileName();
	
	
	
	public IMDBParser(boolean isPatchedFile){
		setPatchedFile(isPatchedFile);
		
		try {
			
			writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(parsedDirPath+defineFileName()+"_parsed"), Charset.forName(defineEncoding())));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void setPatchedFile(boolean isPatchedFile){
		if(isPatchedFile){
			pathToDump = Config.PATCHEDPATH;
			parsedDirPath = Config.PATCHEDPARSEDPATH;
		}else{
			pathToDump = Config.ORIGINALPATH;
			parsedDirPath = Config.ORIGINALPARSEDPATH;
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
	

	
	protected void writeRDF(String s, String p, String o){	
		
		if(s==null || p == null || o == null) return;
		if(s.length() == 0 || p.length() == 0 || o.length() == 0) return;
		
		StringBuilder builder = new StringBuilder();
		builder.append(s+" ");
		builder.append(p+" ");
		builder.append(o);

		builder.append(" .\n");
		writer.write(builder.toString());
	}
	
	private void closeWriter(){
		writer.close();
	}


}
