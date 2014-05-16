package edu.hpi.semweb.lod.crawl.imdb.diff;

import java.io.File;
import java.util.Date;

public class PatchFile implements Comparable<PatchFile>{
	private File file;
	private Date patchDate;
	public PatchFile(File file, Date patchDate) {
		super();
		this.file = file;
		this.patchDate = patchDate;
	}
	
	public File getFile() {
		return file;
	}
	public Date getPatchDate() {
		return patchDate;
	}
	
	@Override
	public int hashCode() {
		return file.hashCode()+patchDate.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof PatchFile)) return false;
		
		PatchFile patchFile = (PatchFile) obj;
		if(patchFile.getFile().equals(this.file) && patchFile.getPatchDate().equals(this.patchDate)) return true;
		
		return false;
	}

	@Override
	public int compareTo(PatchFile file) {
		int comparedFilePaths =  file.getFile().compareTo(this.file);
		if(comparedFilePaths != 0) return comparedFilePaths;
		
		return file.getPatchDate().compareTo(this.patchDate);
	}
}
