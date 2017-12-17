package chatbot.suggestionsengine;

import java.util.*;
import chatbot.knowledgebase.*;

public class Parser {
	
	public Parser() {}
	
	public static List<KnowledgeBase> parsingSolution(List<String> solutions){
		List<KnowledgeBase> base = new ArrayList<>();
		for (String sol : solutions) {
			String[] temp = sol.split(" , ", -1);
			base.add(new KnowledgeBase(temp));
		}
		return base;
	}
		
}
