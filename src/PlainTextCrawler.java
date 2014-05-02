import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public abstract class PlainTextCrawler extends ICrawler{

	protected abstract String defineInputFilePath();
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
		BufferedReader reader = new BufferedReader(new FileReader(file));
		
		String line = null;
		while((line = reader.readLine()) != null){
			onNewLine(line);
		}
		
		reader.close();
	}


}
