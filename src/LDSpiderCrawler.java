import java.awt.List;
import java.io.File;
import java.io.FileNotFoundException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;


import com.ontologycentral.ldspider.Crawler;
import com.ontologycentral.ldspider.frontier.BasicFrontier;
import com.ontologycentral.ldspider.frontier.Frontier;
import com.ontologycentral.ldspider.hooks.content.ContentHandler;
import com.ontologycentral.ldspider.hooks.content.ContentHandlerAny23;
import com.ontologycentral.ldspider.hooks.content.ContentHandlerNx;
import com.ontologycentral.ldspider.hooks.content.ContentHandlerRdfXml;
import com.ontologycentral.ldspider.hooks.content.ContentHandlers;
import com.ontologycentral.ldspider.hooks.links.LinkFilter;
import com.ontologycentral.ldspider.hooks.links.LinkFilterDomain;
import com.ontologycentral.ldspider.hooks.sink.Sink;
import com.ontologycentral.ldspider.hooks.sink.SinkCallback;


public class LDSpiderCrawler extends ICrawler{
	private int numberOfThreads = 1;
	private String seedUri = "http://www.imdb.com/title/tt0242423/?ref_=nm_knf_t1";
	private String filterUri = "http://www.imdb.com/";
	private Frontier frontier;
	private Crawler crawler;
	private LinkFilter linkFilter;
	
	
	private String directory;

	public LinkedList<Quad> result = new LinkedList<Quad>();
	
    LDSpiderCrawler(){

		System.out.println("Start Semantic Crawler");
		System.out.println("1");
		// Directory to store index and result logs
		this.directory = "/home/peter/workspace/SemanticSearch/data/" +this.getClass().getSimpleName().toString();
		new File(directory).mkdirs();
		//initialize Crawler
		this.crawler = new Crawler(this.numberOfThreads);
		this.frontier = new BasicFrontier();
		//this.seen = new HashSetSeen(); 
		//this.frontier.setBlacklist(CrawlerConstants.BLACKLIST);
		//The border where to stop crawling / the region where to crawl
		System.out.println("2");
		try {
			this.frontier.add(new URI(this.seedUri));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		//Which links to follow
		this.linkFilter = new LinkFilterDomain(frontier);  
		((LinkFilterDomain) this.linkFilter).addHost(filterUri);  
		this.crawler.setLinkFilter(this.linkFilter);
		System.out.println("Init Any 23");

		ContentHandlerAny23 any = new ContentHandlerAny23(URI.create("http://any23.org"));
		
		ContentHandler contentHandler = new ContentHandlers(new ContentHandlerRdfXml(), new ContentHandlerNx(), any);
		this.crawler.setContentHandler(contentHandler);
		//Define where to save the data
	

		Sink sink = new SinkCallback(new LDSpiderCallback(result));
		this.crawler.setOutputCallback(sink);
		
		
		java.util.logging.Logger.getLogger("com.ontologycentral.ldspider").setLevel(java.util.logging.Level.WARNING);
	}
	@Override
	protected void startCrawling() {
		/*while(!isStopped()){
			crawlPage(pagesToVisit.poll());
		}*/
		System.out.println("Crawl");
		int maxURIs = 100;
		this.crawler.evaluateLoadBalanced(this.frontier, maxURIs);
		//this.stop();
		try {
			ICrawler.saveTriples(this.result);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}

}
