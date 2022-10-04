import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/*
 * TODO: You will have to implement memoization somewhere in this class.
 */
public class WikiScraper {
	
	private static Map<String, Set<String>> memoization = 
			new HashMap<String, Set<String>>();
			
	/*
	 * TODO: Comment this function
	 */
	public static Set<String> findWikiLinks(String link) {
		if (memoization.containsKey(link)) {
			return memoization.get(link);
		} else {
			String html = fetchHTML(link);
			Set<String> links = scrapeHTML(html);
			memoization.put(link, links);
			return links;
		}

	}
	
	/*
	 * TODO: Comment this function. What does it do at
	 * a high level. I don't expect you to read/understand
	 * the StringBuffer and while loop. But from the spec
	 * and your understanding of this assignment, what is
	 * the purpose of this function.
	 */
	private static String fetchHTML(String link) { 
		StringBuffer buffer = null; 
		try {
			URL url = new URL(getURL(link));
			InputStream is = url.openStream();
			int ptr = 0;
			buffer = new StringBuffer();
			while ((ptr = is.read()) != -1) {
			    buffer.append((char)ptr);
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		return buffer.toString();
	}
	
	/*
	 * TODO: Comment this function. What does it do.
	 */
	private static String getURL(String link) {
		return "https://en.wikipedia.org/wiki/" + link;
	}
	
	/*
	 * TODO: Fill this in with your code from the drill. Change
	 * this comment to accurately document this function.
	 */
	private static Set<String> scrapeHTML(String html) {

		Set<String> finalResult = new HashSet<String>();
		final String minimum = "<a href=\"/wiki/";
		
		while (html.contains(minimum)) {
			int indexOne = html.indexOf(minimum);
			html = html.substring(indexOne);
			int indexTwo = html.indexOf("\">");
			String candidate = html.substring(0, indexTwo + 2);
			
			String article = scrapeHTMLHelper(candidate);
			if (article != null) finalResult.add(article);
			
			html = html.substring(indexTwo + 2);
		}
			
		return finalResult;
	}
	

	private static String scrapeHTMLHelper(String rawUrl) {
		String result;
		
		int indexOne = rawUrl.indexOf("/", 10) + 1;
		int indexTwo;
		
		if (!rawUrl.contains("\" ")) {
			indexTwo = rawUrl.indexOf("\">");
		} else {
			indexTwo = rawUrl.indexOf("\" ");
		}
		
		result = rawUrl.substring(indexOne, indexTwo);
		
		if (!result.contains("#") && !result.contains(":")) {
			return result;
		}
		
		return null;
	}
	
	
}
