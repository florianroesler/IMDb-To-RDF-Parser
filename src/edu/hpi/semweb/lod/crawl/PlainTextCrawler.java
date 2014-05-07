package edu.hpi.semweb.lod.crawl;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;


public abstract class PlainTextCrawler extends ICrawler{

	protected abstract String defineInputFilePath();
	protected abstract String defineEncoding();

	protected abstract void onNewLine(String line);
	
	@Override
	protected void startCrawling() {
		try {
			readFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	private void readFile() throws IOException{
		File file = new File(defineInputFilePath());
		InputStreamReader streamReader = null;
		
		if(defineEncoding()==null){
			streamReader = new InputStreamReader(new FileInputStream(file));
		}else{
			streamReader = new InputStreamReader(new FileInputStream(file), Charset.forName(defineEncoding()));
		}
		BufferedReader reader = new BufferedReader(streamReader);
		
		String line = null;
		while((line = reader.readLine()) != null){
			onNewLine(line);
		}
		
		reader.close();
	}


}
