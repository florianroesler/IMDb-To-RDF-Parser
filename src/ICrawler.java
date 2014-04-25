import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Date;


public abstract class ICrawler implements Runnable{


	@Override
	public void run() {
		startCrawling();		
	}

	protected abstract void startCrawling();

	protected static void saveTriples(Collection<Triple> triples) throws FileNotFoundException{
		File file = new File("Crawler"+new Date().getTime()+"_"+String.valueOf(Math.random()).replace(".", "")+".txt");
		StringBuilder builder = new  StringBuilder();
		for(Triple triple:triples){
			builder.append(triple.toString()+"\n");
		}
		PrintWriter out = new PrintWriter(file);
		out.write(builder.toString());
		out.close();
		System.out.println("Saved Chunk to "+ file);
	}
}
