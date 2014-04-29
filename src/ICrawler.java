import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Date;


public abstract class ICrawler implements Runnable{

	protected abstract void startCrawling();
	
	private boolean stopped = true;
	
	@Override
	public void run() {
		stopped = false;
		startCrawling();		
	}

	public void stop(){
		stopped = true;
	}
	
	public boolean isStopped() {
		return stopped;
	}

	protected static void saveTriples(Collection<Quad> triples) throws FileNotFoundException{
		File file = new File("Crawler"+new Date().getTime()+"_"+String.valueOf(Math.random()).replace(".", "")+".triples");
		StringBuilder builder = new  StringBuilder();
		for(Quad triple:triples){
			builder.append(triple.toString()+"\n");
		}
		PrintWriter out = new PrintWriter(file);
		out.write(builder.toString());
		out.close();
		System.out.println("Saved Chunk to "+ file);
	}
}
