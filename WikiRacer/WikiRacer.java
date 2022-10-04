import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class WikiRacer {
	
	/*
	 * Do not edit this main function
	 */
	public static void main(String[] args) {
		List<String> ladder = findWikiLadder(args[0], args[1]);
		System.out.println(ladder);
	}

	/*
	 * Do not edit the method signature/header of this function
	 * TODO: Fill this function in.
	 */
	private static List<String> findWikiLadder(String start, String end) {
		MaxPQ maxpq = new MaxPQ();
		int numLinksInCommon = findNumLinksInCommon(start, end);
		
		List<String> initialLadder = new ArrayList<>();
		initialLadder.add(start);
		maxpq.enqueue(initialLadder, numLinksInCommon);
		
		while (!maxpq.isEmpty()) {
			List<String> partialLadder = maxpq.dequeue();
			int endIndex = partialLadder.size() - 1;
			String curPage = partialLadder.get(endIndex);
			Set<String> curPageLinkSet = WikiScraper.findWikiLinks(curPage);
			
			if (curPageLinkSet.contains(end)) {
				partialLadder.add(end);
				return partialLadder;
			} else {
				curPageLinkSet.parallelStream().forEach(link -> {WikiScraper.findWikiLinks(link);});
				for (String neighborPage : curPageLinkSet) {
					List<String> newLadder = new ArrayList<String>(partialLadder);
					newLadder.add(neighborPage);
					numLinksInCommon = findNumLinksInCommon(neighborPage, end);
					maxpq.enqueue(newLadder, numLinksInCommon);
				}
			}
		}
		return new ArrayList<String>();
	}
	
	private static int findNumLinksInCommon(String page1, String page2) {
		Set<String> page1Links = WikiScraper.findWikiLinks(page1);
		Set<String> page2Links = WikiScraper.findWikiLinks(page2);
		
		page1Links.retainAll(page2Links);
		return page1Links.size();
	}

}
