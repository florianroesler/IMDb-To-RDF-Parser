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
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.net.ftp.FTPFile;

import edu.hpi.semweb.lod.crawl.imdb.Config;
import edu.hpi.semweb.lod.crawl.imdb.IMDBParser;
import edu.hpi.semweb.lod.crawl.imdb.RegexHelper;

public class Patcher {
	
	
	private static final Set<String> allowedFiles = new HashSet<String>();
	static{
		for(IMDBParser p:Config.PARSERS){
			allowedFiles.add(p.defineFileName());
		}
	}
	
	public static void patch(){
		
		downloadMissingDiffs();
		Decompressor.decompressDiffs(true);

		List<PatchJob> patches = Patcher.identifyPatchJobs();
		for(PatchJob patchJob : patches){
			patchJob.patchToMostRecentVersion();
		}
	}
	
	private static void downloadMissingDiffs(){
		Date d = Patcher.retrieveOldestPatchDate();
		FTPConnection conn = new FTPConnection();
		Collection<FTPFile> diffFiles = conn.retrieveRelevantDiffFiles(d).values();	
		for(FTPFile f:filterAlreadyExistantFiles(diffFiles)){
			conn.downloadFileToDiffFolder(f);
			
		}
	}	
	
	private static Collection<FTPFile> filterAlreadyExistantFiles(Collection<FTPFile> diffFiles){
		Collection<FTPFile> notExistantFiles = new ArrayList<FTPFile>();
		for(FTPFile f: diffFiles){
			File localFile = new File(Config.DIFFPATH+ f.getName());
			File unpackedFolder = new File(Config.DIFFPATH+f.getName().replace(".tar.gz", ""));
			if(!localFile.exists() && !unpackedFolder.exists()){
				notExistantFiles.add(f);
			}
		}
		return notExistantFiles;
	}
	
	public static void copyPatchjobFilesToPatchedFolder(){
		File originalDir = new File(Config.ORIGINALPATH);
		for(File f: originalDir.listFiles()){
			String fileName = f.getName();
			if(f.isDirectory() || !fileName.endsWith(".list") || !allowedFiles.contains(fileName)) continue;
			System.out.println("Copying File: "+fileName);
			CommandLineHelper.execCommand("cp " + f.getAbsolutePath()+" " + Config.PATCHEDPATH+fileName);
		}
	}
	
	public static void diffParsedAndOriginalFolder(){
		diffFiles(new File(Config.ORIGINALPARSEDPATH), new File(Config.PATCHEDPARSEDPATH), new File(Config.RDFDIFFPATH+"rdf_diff.diff"));
	}
	
	public static void patchFile(PatchFile originalFile, PatchFile patch){
		CommandLineHelper.execCommand("patch " + originalFile.getFile().getAbsolutePath()+" < " + patch.getFile().getAbsolutePath());
	}
	
	public static void unPatchFile(PatchFile originalFile, PatchFile patch){
		CommandLineHelper.execCommand("patch -R " + originalFile.getFile().getAbsolutePath()+" < " + patch.getFile().getAbsolutePath());
	}
	
	public static void diffFiles(File f1, File f2, File output){
		CommandLineHelper.execCommand("diff " + f1.getAbsolutePath()+" " + f2.getAbsolutePath() + " >" + output.getAbsolutePath());
	}
	
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
	
	private static Date retrieveOldestPatchDate(){
		Date oldestDate = new Date();
		for(PatchFile p:findFilesEligibleForPatch()){
			if(p.getPatchDate().before(oldestDate)){
				oldestDate = p.getPatchDate();
			}
		}
		return oldestDate;
	}
	
	private static Set<PatchFile> findFilesEligibleForPatch(){
		try {
			return getDumpFiles(false);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new TreeSet<PatchFile>();
	}
	
	private static Set<PatchFile> findPatches(){
		try {
			return getDumpFiles(true);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new TreeSet<PatchFile>();
	}
	
	private static Set<PatchFile> getDumpFiles(boolean isPatch) throws IOException, ParseException{
		String pathToFiles = "";
		if(isPatch){
			pathToFiles = Config.DIFFPATH;
		}else{
			pathToFiles = Config.PATCHEDPATH;
		}
		
		File dir = new File(pathToFiles);
		
		List<File> diffFiles = new ArrayList<File>();
		
		if(isPatch){
			for(File f: dir.listFiles()){
				File subDir = new File(f.getAbsolutePath()+File.separatorChar+"diffs");
				if(subDir.exists() && subDir.isDirectory()){
					diffFiles.addAll(Arrays.asList(subDir.listFiles()));
				}
			}
		}else{
			diffFiles = Arrays.asList(dir.listFiles());
		}
			
		Set<PatchFile> dates = new TreeSet<PatchFile>();
		
		for(File f:diffFiles){
			if(!f.getName().endsWith(".list") || !allowedFiles.contains(f.getName())) continue;
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
				continue;
			}
			
			String dateLine = RegexHelper.findFirstOccurence(firstLine, "Date:\\s.+");
			
			if(!dateLine.contains("Date:")){
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
