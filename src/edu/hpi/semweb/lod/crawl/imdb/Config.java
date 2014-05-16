package edu.hpi.semweb.lod.crawl.imdb;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

	public static final String ROOTPATH = readPath();
	
	public static final String DIFFPATH = ROOTPATH+"diffs/";
	
	public static final String PARSEDPATH = ROOTPATH+"parsed/";
	
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
	
	private static String readPath(){
		Properties prop = new Properties();
		InputStream input = null;
	 
		try {
	 
			input = new FileInputStream("config/imdb.properties");
	 
			prop.load(input);
	 
			// get the property value and print it out
			return prop.getProperty("dump.path");

	 
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}
