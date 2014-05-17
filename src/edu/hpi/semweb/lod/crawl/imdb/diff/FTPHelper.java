package edu.hpi.semweb.lod.crawl.imdb.diff;

import java.io.IOException;
import java.net.SocketException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

public class FTPHelper {

	
	public static Map<Date,FTPFile> filterDiffs(){
		Map<Date,FTPFile> filteredFiles = new TreeMap<Date, FTPFile>();
		
		FTPFile[] files = new FTPFile[0];
		try {
			files = listFiles();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(FTPFile file : files){
			Date d = new Date(0);
			Pattern p = Pattern.compile("diffs-(\\d{6})\\.tar\\.gz");

			Matcher matcher = p.matcher(file.getName());

			if (matcher.find()) {
				try {
					d = new SimpleDateFormat("yyMMdd").parse(matcher.group(1));
					filteredFiles.put(d, file);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		return filteredFiles;
	}
	
	public static FTPFile[] listFiles() throws SocketException, IOException{
		FTPClient client = new FTPClient();
		client.connect("ftp.fu-berlin.de");
		client.login("anonymous", "");
        String reply = client.getStatus();
        System.out.println(reply);
		return client.listFiles("/pub/misc/movies/database/diffs/");
	}
}
