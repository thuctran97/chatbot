package chatbot.knowledgerendering;

import java.util.*;

public class SolutionParser {
	
	public SolutionParser() {}
	
	public List<KnowledgeBase> parsing(List<String> solutions){
		List<KnowledgeBase> base = new ArrayList<>();
		for (String sol : solutions) {
			String[] temp = sol.split(" , ", -1);
			base.add(new KnowledgeBase(temp));
		}
		return base;
	}
}
