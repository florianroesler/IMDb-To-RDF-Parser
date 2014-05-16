package edu.hpi.semweb.lod.crawl.imdb.diff;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

import edu.hpi.semweb.lod.crawl.imdb.ConfigReader;
import edu.hpi.semweb.lod.crawl.imdb.RegexHelper;

public class Patcher {

	
	public static Set<PatchFile> getPatchDates(boolean isPatch) throws IOException, ParseException{
		String pathToDumps = ConfigReader.readPath();
		File dir = new File(pathToDumps+"/diffs/");
		if(!dir.exists()){
			throw new FileNotFoundException("The path to your lists is invalid! Please check your config. "
					+ "Currently configured path is: "+dir.getAbsolutePath());
		}
		
		List<File> diffFiles = new ArrayList<File>();
		
		for(File f: dir.listFiles()){
			if(f.isDirectory()){
				diffFiles.addAll(Arrays.asList(f.listFiles()));
			}
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
			
			//deal with cases where files have no changes etc
			String firstLine = reader.readLine();
			
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
