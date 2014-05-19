package edu.hpi.semweb.lod.crawl.imdb;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

	private static final Properties properties = new Properties();
	static{
		try {
			properties.load(new FileInputStream("config/imdb.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static final String ROOTPATH = properties.getProperty("dump.path");

	public static final String DIFFPATH = ROOTPATH+"diffs/";

	public static final String PARSEDPATH = ROOTPATH+"parsed/";

	
	public static final String FTPSERVER = properties.getProperty("ftp.server");
	public static final String FTPPATH = properties.getProperty("ftp.path");
	public static final String FTPUSER = properties.getProperty("ftp.user");
	public static final String FTPPASSWORD = properties.getProperty("ftp.password");


	static{
		File rootDir = new File(ROOTPATH);
		if(ROOTPATH == null || !rootDir.isDirectory()){
			throw new IllegalArgumentException("Path to IMDB-dumps is not correctly defined. Please check the config.");
		}

		for(String path: new String[]{DIFFPATH,PARSEDPATH}){
			File subDir = new File(path);
			boolean isDir = subDir.isDirectory();
			if(!isDir){
				subDir.mkdir();
				System.out.println("Creating missing dir:" + path);
			}
		}
	}	

}
