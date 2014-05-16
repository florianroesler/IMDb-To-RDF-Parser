package edu.hpi.semweb.lod.crawl.imdb.diff;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandLineHelper {

	public static Process execCommand(String command){
		try {
			Process p = Runtime.getRuntime().exec(new String[]{"bash","-c",command});
			String line;
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			while ((line = input.readLine()) != null) {
				System.out.println(line);
			}
			input.close();
			return p;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
}
