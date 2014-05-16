package edu.hpi.semweb.lod.crawl.imdb.diff;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import edu.hpi.semweb.lod.crawl.imdb.Config;
import edu.hpi.semweb.lod.crawl.imdb.RegexHelper;

public class Patcher {

	public static List<PatchJob> identifyPatchJobs(){
		
		Set<PatchFile> originalFiles = findFilesEligibleForPatch();
		Set<PatchFile> availablePatches = findPatches();
		
		Map<String, PatchJob> patchJobs = new HashMap<String, PatchJob>();
		
		for(PatchFile p:originalFiles){
			patchJobs.put(p.getFile().getName(), new PatchJob(p));
		}
		
		for(PatchFile p: availablePatches){
			String fileName = p.getFile().getName();
			if(!patchJobs.containsKey(fileName)){
				continue;
			}
			List<PatchFile> patchesForFile = patchJobs.get(fileName).getPatchList();
			patchesForFile.add(p);
		}
		
		//remove all patches that are older/same age than our original file
		for(String key:patchJobs.keySet()){
			PatchJob job = patchJobs.get(key);
			PatchFile originalFile = job.getOriginalFile();
			Iterator<PatchFile> iterator = job.getPatchList().iterator();
			while(iterator.hasNext()){
				PatchFile patch = iterator.next();
				if(!patch.getPatchDate().after(originalFile.getPatchDate())){
					iterator.remove();
				}
			}
		}
		return new ArrayList<PatchJob>(patchJobs.values());
	}
	
	private static Set<PatchFile> findFilesEligibleForPatch(){
		try {
			return getPatchDates(false);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new TreeSet<PatchFile>();
	}
	
	private static Set<PatchFile> findPatches(){
		try {
			return getPatchDates(true);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new TreeSet<PatchFile>();
	}
	
	private static Set<PatchFile> getPatchDates(boolean isPatch) throws IOException, ParseException{
		String pathToFiles = "";
		if(isPatch){
			pathToFiles = Config.DIFFPATH;
		}else{
			pathToFiles = Config.ROOTPATH;
		}
		
		File dir = new File(pathToFiles);
		
		List<File> diffFiles = new ArrayList<File>();
		
		if(isPatch){
			for(File f: dir.listFiles()){
				if(f.isDirectory()){
					diffFiles.addAll(Arrays.asList(f.listFiles()));
				}
			}
		}else{
			diffFiles = Arrays.asList(dir.listFiles());
		}
			
		Set<PatchFile> dates = new TreeSet<PatchFile>();
		
		for(File f:diffFiles){
			if(!f.getName().endsWith(".list")) continue;
			InputStreamReader streamReader = new InputStreamReader(new FileInputStream(f));
			BufferedReader reader = new BufferedReader(streamReader);
			
			//patches have the date in their fourth line
			if(isPatch){
				reader.readLine();
				reader.readLine();
				reader.readLine();
			}
			
			String firstLine = reader.readLine();
			
			//deal with cases where files have no changes etc
			if(firstLine == null){
				System.out.println(f.getName()+" does not contain patch date!");
				continue;
			}
			
			String dateLine = RegexHelper.findFirstOccurence(firstLine, "Date:\\s.+");
			
			if(!dateLine.contains("Date:")){
				System.out.println(f.getName()+" does not contain patch date!");
				continue;
			}
			String dateString = dateLine.replace("Date:", "").trim();
			Date date = new SimpleDateFormat("E MMMM dd mm:HH:ss yyyy", Locale.ENGLISH).parse(dateString);
			dates.add(new PatchFile(f, date));
			reader.close();
		}
		return dates;

	}
	
}
