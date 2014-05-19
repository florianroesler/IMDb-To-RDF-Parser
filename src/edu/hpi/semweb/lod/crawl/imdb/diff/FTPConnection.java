package edu.hpi.semweb.lod.crawl.imdb.diff;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.SocketException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import edu.hpi.semweb.lod.crawl.imdb.Config;

public class FTPConnection {

	private FTPClient client;

	public FTPConnection(){
		client = new FTPClient();
		try {
			client.connect(Config.FTPSERVER);
			client.login(Config.FTPUSER, Config.FTPPASSWORD);
			String reply = client.getStatus();
			client.changeWorkingDirectory(Config.FTPPATH);
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public void downloadMissingDiffs(){
		Date d = Patcher.retrieveOldestPatchDate();
		FTPConnection conn = new FTPConnection();
		Collection<FTPFile> diffFiles = conn.retrieveRelevantDiffFiles(d).values();	
		
		for(FTPFile f:filterAlreadyExistantFiles(diffFiles)){
			conn.downloadFileToDiffFolder(f);
			
		}
	}
	
	private Collection<FTPFile> filterAlreadyExistantFiles(Collection<FTPFile> diffFiles){
		Collection<FTPFile> notExistantFiles = new ArrayList<FTPFile>();
		for(FTPFile f: diffFiles){
			File localFile = new File(Config.DIFFPATH+ f.getName());
			long localFileSize = localFile.length();
			long ftpFileSize = f.getSize();
			System.out.println(localFileSize+" "+ftpFileSize);
			if(!localFile.exists()){
				notExistantFiles.add(f);
			}
		}
		return notExistantFiles;
	}

	public void disconnect(){
		try {
			client.logout();
			client.disconnect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Map<Date,FTPFile> retrieveRelevantDiffFiles(Date oldestPatchDate){
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
					if(!d.before(oldestPatchDate)){
						filteredFiles.put(d, file);
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		return filteredFiles;
	}

	public void downloadFileToDiffFolder(FTPFile file){
		System.out.println("Downloading Diff: "+file.getName());
		OutputStream output;
		try {
			output = new FileOutputStream(Config.DIFFPATH+ file.getName());
			client.retrieveFile(file.getName(), output);
			output.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public FTPFile[] listFiles() throws SocketException, IOException{
		return client.listFiles();
	}
}
