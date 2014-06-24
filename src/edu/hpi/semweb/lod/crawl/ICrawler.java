package edu.hpi.semweb.lod.crawl;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Date;

import edu.hpi.semweb.lod.data.Quad;


public abstract class ICrawler implements Runnable{

	protected abstract void startCrawling();
	
	private boolean stopped = true;
	
	@Override
	public void run() {
		stopped = false;
		startCrawling();		
	}

	public void stop(){
		stopped = true;
	}
	
	public boolean isStopped() {
		return stopped;
	}

	protected static void saveTriples(Collection<String> triples) throws FileNotFoundException{
		File file = new File("./Crawl/Crawler"+new Date().getTime()+"_"+String.valueOf(Math.random()).replace(".", "")+".triples");
		StringBuilder builder = new  StringBuilder();
		for(String triple:triples){
			builder.append(triple.toString()+"\n");
		}
		PrintWriter out;
		try {
			out = new PrintWriter(file, "Windows-1252");
			out.write(builder.toString());
			out.close();
			System.out.println("Saved Chunk to "+ file);

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
}
