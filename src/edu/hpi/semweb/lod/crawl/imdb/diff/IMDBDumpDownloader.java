package edu.hpi.semweb.lod.crawl.imdb.diff;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.net.ftp.FTPFile;

import edu.hpi.semweb.lod.crawl.imdb.Config;


public class IMDBDumpDownloader {


	public static void downloadDump(){
		downloadMissingFiles();
	}

	private static void downloadMissingFiles(){
		FTPConnection conn = new FTPConnection();
		Collection<FTPFile> filesToDownload = conn.retrieveRelevantDumpFiles();
		filesToDownload = filterAlreadyExistantFiles(filesToDownload);
		for(FTPFile file : filesToDownload){
			conn.downloadFileToDumpFolder(file);
		}
	}	

	private static Collection<FTPFile> filterAlreadyExistantFiles(Collection<FTPFile> dumpFiles){
		Collection<FTPFile> notExistantFiles = new ArrayList<FTPFile>();
		for(FTPFile f: dumpFiles){
			File localPackedFile = new File(Config.ORIGINALPATH+ f.getName());

			File unpackedFile = new File(Config.ORIGINALPATH+f.getName().replace(".gz", ""));
			if(!localPackedFile.exists() && !unpackedFile.exists()){
				notExistantFiles.add(f);
			}
		}
		return notExistantFiles;
	}
}
