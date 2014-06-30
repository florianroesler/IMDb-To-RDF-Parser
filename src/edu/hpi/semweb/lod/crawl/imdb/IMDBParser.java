package edu.hpi.semweb.lod.crawl.imdb;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import edu.hpi.semweb.lod.crawl.PlainTextCrawler;


public abstract class IMDBParser extends PlainTextCrawler{

	private PrintWriter writer;

	private String pathToDump;
	private String parsedDirPath;
	private Timer timer = new Timer();
	private Date startTime;
	private String outputFilePath; 

	public abstract String defineFileName();

	protected boolean omitOutput(){
		return false;
	}

	public IMDBParser(boolean isPatchedFile){
		setPatchedFile(isPatchedFile);

	}

	public void setPatchedFile(boolean isPatchedFile){
		if(isPatchedFile){
			pathToDump = Config.PATCHEDPATH;
			parsedDirPath = Config.PATCHEDPARSEDPATH;
		}else{
			pathToDump = Config.ORIGINALPATH;
			parsedDirPath = Config.ORIGINALPARSEDPATH;
		}
		outputFilePath = parsedDirPath+defineFileName()+"_parsed";
	}

	@Override
	public void run() {
		if(!omitOutput()){
			System.out.println("Reading "+defineInputFilePath());
			System.out.println("Writing to "+outputFilePath);
			try {

				writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(outputFilePath), Charset.forName(defineEncoding())));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

		}
		startTime = new Date();
		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				long difference = new Date().getTime() - startTime.getTime();
				difference = difference / 1000;
				long minutes = difference / 60;
				long seconds = difference % 60;

				System.out.println("Running time for current Parser: " + minutes+"m "+seconds+"s");				
			}
		};
		timer.schedule(task, 30000);
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
