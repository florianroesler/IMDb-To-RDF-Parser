package edu.hpi.semweb.lod.crawl;

public class WebLink implements Comparable<WebLink>{
	private String link;
	private int depth;
	public WebLink(String link, int depth) {
		super();
		this.link = link;
		this.depth = depth;
	}
	public String getLink() {
		return link;
	}
	public int getDepth() {
		return depth;
	}
	
	@Override
	public int compareTo(WebLink o) {
		if(this.depth < o.depth) return 1;
		if(this.depth == o.depth) return 0;
		return -1;
	}
	
	
	
}
