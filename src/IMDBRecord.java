import java.util.HashMap;
import java.util.Map;


public class IMDBRecord {

	private static final String linkPrefix = "http://www.imdb.com";
	private String link;
	private int yearOfRelease;
	private Map<String,String> titles;
	
	
	public IMDBRecord(String link, int yearOfRelease) {
		super();
		this.link = link;
		this.yearOfRelease = yearOfRelease;
		this.titles = new HashMap<String,String>();
	}
	
	public String getLink() {
		return link;
	}
	public String getFullLink() {
		return linkPrefix+link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public int getYearOfRelease() {
		return yearOfRelease;
	}
	public void setYearOfRelease(int yearOfRelease) {
		this.yearOfRelease = yearOfRelease;
	}
	public Map<String, String> getTitles() {
		return titles;
	}
	public void setTitles(Map<String, String> titles) {
		this.titles = titles;
	}
	
	@Override
	public String toString(){
		return link;
	}
}
