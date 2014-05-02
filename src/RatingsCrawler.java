import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


public class RatingsCrawler extends PlainTextCrawler{

	private boolean isRelevantContent = false;
	@Override
	protected String defineInputFilePath() {
		return "/Users/froesler/Downloads/moviedb-3.24/lists/ratings.list";
	}

	@Override
	protected void onNewLine(String line) {
		if(!isStopped()){
			if(line.startsWith("-")){
				isRelevantContent = false;
				return;
			}
			
			if(isRelevantContent && line.startsWith(" ")){

				if(line.contains("{")) return;
				
				String[] parts  = line.replace("\\s+", " ").split(" ");
				List<String> cleanedParts = new ArrayList<String>();
				for(String s:parts){
					if(s.length()>0){
						cleanedParts.add(s);
					}
				}
					String rating = cleanedParts.get(2);
					
					String title = "";
					String yearOfProduction = "";
					for(int i = 3; i<cleanedParts.size()-1;i++){
						if(cleanedParts.get(i).startsWith("(")){
							yearOfProduction = cleanedParts.get(i).replace("(", "").replace(")", "");
							break;
						}
						title+=cleanedParts.get(i)+" ";
					}
					title = title.trim();
					title=title.replaceAll("^\"|\"$", "");
					
					String encodedTitle = new String(Charset.forName("UTF8").encode(title).array());
					System.out.println(title+" "+yearOfProduction+" "+rating);

			}else{
				if(line.startsWith("New")){
					isRelevantContent = true;
				}
			}

		}
	}


}
