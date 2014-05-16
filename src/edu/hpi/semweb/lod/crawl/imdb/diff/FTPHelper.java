package edu.hpi.semweb.lod.crawl.imdb.diff;

import java.io.File;
import java.io.IOException;
import java.net.SocketException;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

public class FTPHelper {

	
	public static FTPFile[] listFiles() throws SocketException, IOException{
		FTPClient client = new FTPClient();
		client.connect("ftp.fu-berlin.de");
		client.login("anonymous", "");
        String reply = client.getStatus();
        System.out.println(reply);
		return client.listFiles("/pub/misc/movies/database/diffs/");
	}
}
