package edu.hpi.semweb.lod.misc;
import java.util.Date;


public class StopWatch {

	private boolean running = false;
	private long start;
	private long end;
	public StopWatch(){	
	}
	
	public void start(){
		if(running){
			throw new IllegalStateException("Stopwatch already running.");
		}
		start = new Date().getTime();
		running = true;
	}
	
	public long stop(){
		if(!running){
			throw new IllegalStateException("Stopwatch already stopped.");
		}
		end = new Date().getTime();
		running = false;
		return end - start;
	}
	
}
