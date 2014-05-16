package edu.hpi.semweb.lod.crawl;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.nio.charset.Charset;


public abstract class PlainTextCrawler extends ICrawler{

	private int lineCount = 0;
	private boolean isInRelevantPart = false;


	protected abstract String defineInputFilePath();
	protected abstract String defineEncoding();
	protected abstract void onNewLine(String line);
	protected abstract void onFileEnd();
	protected abstract String defineRelevanceStartingLine();
	protected abstract String defineRelevanceEndingLine();

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

		lineCount = countLineNumbers(file);


		InputStreamReader streamReader = null;

		if(defineEncoding()==null){
			streamReader = new InputStreamReader(new FileInputStream(file));
		}else{
			streamReader = new InputStreamReader(new FileInputStream(file), Charset.forName(defineEncoding()));
		}		

		BufferedReader reader = new BufferedReader(streamReader);

		String line = null;
		int count = 0;
		int recentProgress = 0;
		while((line = reader.readLine()) != null){
			count++;
			int progress = 100*count/lineCount;
			if(progress>recentProgress){
				recentProgress = progress;
				System.out.print(recentProgress+"%\r");
			}

			if(isInRelevantPart){
				if(defineRelevanceEndingLine()!=null && line.equals(defineRelevanceEndingLine())){
					isInRelevantPart = false;
					continue;
				}
				onNewLine(line);
			}else{
				if(line.equals(defineRelevanceStartingLine())){
					isInRelevantPart = true;
				}
			}
		}
		reader.close();
		onFileEnd();
		System.out.println("DONE\n");

	}

	private int countLineNumbers(File f) throws IOException{

		LineNumberReader  lnr = new LineNumberReader(new FileReader(f));
		lnr.skip(Long.MAX_VALUE);
		int count = lnr.getLineNumber();
		lnr.close();
		return count;
	}



}
