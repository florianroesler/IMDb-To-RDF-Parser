package edu.hpi.semweb.lod.crawl;

public class WebLink implements Comparable<WebLink>{
	private String link;
	private int depth;
	private String foundOn;
	public WebLink(String link, int depth, String foundOn) {
		super();
		this.link = link;
		this.depth = depth;
		this.foundOn = foundOn;
	}
	public String getFoundOn() {
		return foundOn;
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
