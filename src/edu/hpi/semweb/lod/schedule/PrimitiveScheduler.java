package edu.hpi.semweb.lod.schedule;

import java.io.IOException;
import java.text.ParseException;

import org.apache.commons.net.ftp.FTPFile;

import edu.hpi.semweb.lod.crawl.imdb.diff.FTPHelper;
import edu.hpi.semweb.lod.crawl.imdb.diff.PatchJob;
import edu.hpi.semweb.lod.crawl.imdb.diff.Patcher;



public class PrimitiveScheduler extends IScheduler{


	public static void main(String[] args) throws IOException, ParseException{
		System.out.println(Patcher.retrieveOldestPatchDate());
		for(FTPFile f:FTPHelper.filterDiffs().values()){
			System.out.println(f.getName());
		}
	}
}
