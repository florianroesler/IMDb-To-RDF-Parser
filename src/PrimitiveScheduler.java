
public class PrimitiveScheduler extends IScheduler{

	
	public static void main(String[] args){
		//IMDBMoviesParser crawler = new IMDBMoviesParser();
		//LDSpiderCrawler crawler = new LDSpiderCrawler();
		IMDBRatingsParser crawler = new IMDBRatingsParser();
		crawler.run();
	
	}
}
