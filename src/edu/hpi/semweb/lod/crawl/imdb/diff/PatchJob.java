package edu.hpi.semweb.lod.crawl.imdb.diff;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PatchJob {

	private PatchFile originalFile;
	
	private List<PatchFile> patchList;

	public PatchJob(PatchFile originalFile, List<PatchFile> patchList) {
		super();
		this.originalFile = originalFile;
		this.patchList = patchList;
	}

	public PatchJob(PatchFile originalFile) {
		super();
		this.originalFile = originalFile;
		this.patchList = new ArrayList<PatchFile>();
	}

	public PatchFile getOriginalFile() {
		return originalFile;
	}

	public List<PatchFile> getPatchList() {
		return patchList;
	}
	
	public void patchToMostRecentVersion(){
		Collections.sort(patchList);
		Collections.reverse(patchList);
		for(PatchFile patch:patchList){
			Patcher.patchFile(originalFile, patch);
		}
	}
	
	
	
}
