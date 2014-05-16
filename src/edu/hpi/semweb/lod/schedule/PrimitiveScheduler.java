package edu.hpi.semweb.lod.schedule;

import java.io.IOException;
import java.text.ParseException;

import edu.hpi.semweb.lod.crawl.imdb.diff.PatchJob;
import edu.hpi.semweb.lod.crawl.imdb.diff.Patcher;



public class PrimitiveScheduler extends IScheduler{


	public static void main(String[] args) throws IOException, ParseException{
		for(PatchJob d:Patcher.identifyPatchJobs()){
			System.out.println(d.getOriginalFile().getFile().getName() + ": "+ d.getPatchList().size());
		}
	}
}
