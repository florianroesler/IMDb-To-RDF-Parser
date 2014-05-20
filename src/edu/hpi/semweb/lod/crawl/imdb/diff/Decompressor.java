package edu.hpi.semweb.lod.crawl.imdb.diff;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;

import edu.hpi.semweb.lod.crawl.imdb.Config;

public class Decompressor {

	public static void decompressDiffs(boolean deleteArchiveAfterDecompress){
		File diffDir = new File(Config.DIFFPATH);
		for(File f:diffDir.listFiles()){
			if(f.isDirectory() || !f.getName().endsWith(".tar.gz")) continue;
			try {
				uncompressTarGZ(f, new File(Config.DIFFPATH+f.getName().replace(".tar.gz", "")));
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(deleteArchiveAfterDecompress){
				f.delete();
			}
		}
	}
	
	public static void uncompressTarGZ(File tarFile, File dest) throws IOException {
        System.out.println("untaring: " + tarFile.getCanonicalPath());
	    dest.mkdir();
	    TarArchiveInputStream tarIn = null;

	    tarIn = new TarArchiveInputStream(
	                new GzipCompressorInputStream(
	                    new BufferedInputStream(
	                        new FileInputStream(
	                            tarFile
	                        )
	                    )
	                )
	            );

	    TarArchiveEntry tarEntry = tarIn.getNextTarEntry();
	    // tarIn is a TarArchiveInputStream
	    while (tarEntry != null) {// create a file with the same name as the tarEntry
	        File destPath = new File(dest, tarEntry.getName());
	        if (tarEntry.isDirectory()) {
	            destPath.mkdirs();
	        } else {
	            destPath.createNewFile();
	            //byte [] btoRead = new byte[(int)tarEntry.getSize()];
	            byte [] btoRead = new byte[1024];
	            //FileInputStream fin 
	            //  = new FileInputStream(destPath.getCanonicalPath());
	            BufferedOutputStream bout = 
	                new BufferedOutputStream(new FileOutputStream(destPath));
	            int len = 0;

	            while((len = tarIn.read(btoRead)) != -1)
	            {
	                bout.write(btoRead,0,len);
	            }

	            bout.close();
	            btoRead = null;

	        }
	        tarEntry = tarIn.getNextTarEntry();
	    }
	    tarIn.close();
	} 


	
}
