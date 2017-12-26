package chatbot.suggestionsengine;

import chatbot.knowledgebase.*;
import chatbot.botengine.*;
import java.util.*;


public class SuggestionsProvider {
	private Bot bot;
	private List<KnowledgeBase> solutions;	
	private SuggestionTypes state;
	private Iterator<KnowledgeBase> currentKnowledgeIter;
	private KnowledgeBase currentKnowledge;
	public SuggestionsProvider(Bot bot) {
		this(bot,null);
	}
	
	public SuggestionsProvider(Bot bot, List<KnowledgeBase> solutions) {
		this.bot = bot;
		this.solutions = solutions;
		this.state = SuggestionTypes.ASKING_THEORY;
		if (this.solutions != null) {
			this.currentKnowledgeIter = this.solutions.iterator();
			this.currentKnowledge = this.currentKnowledgeIter.next();
		}
		else {
			this.currentKnowledge = null;
			this.currentKnowledge = null;
		}
	}
	
	public List<KnowledgeBase> getSolutions() {
		return solutions;
	}

	public void setSolutions(List<KnowledgeBase> solutions) {
		this.solutions = solutions;
		this.currentKnowledgeIter = this.solutions.iterator();
		this.currentKnowledge = this.currentKnowledgeIter.next();
	}

	public SuggestionTypes getState() {
		return state;
	}

	public void setState(SuggestionTypes state) {
		this.state = state;
	}

	public String provideTheory() {
		StringBuilder builder = new StringBuilder("bạn cần phải ");	
		builder.append(this.currentKnowledge.getTheory());		
		builder.append("<br//>Bạn đã biết cách làm chưa?");
		return builder.toString();
	}
	
	public String provideEquation() {
		StringBuilder builder = new StringBuilder();
		String rhs = this.currentKnowledge.getAllLhs(this.bot);
		builder.append("Vậy bạn hãy nhập đáp án của ").append(rhs);		
		return builder.toString();
	}
		
	public String provideFormula() {
		this.state = SuggestionTypes.ASKING_FORMULAR;
		StringBuilder builder = new StringBuilder();
		builder.append("Gợi ý cho bạn nhé: " );
		builder.append(this.currentKnowledge.getFormula());
		builder.append("<br//>Bạn đã hiểu chưa?");
		return builder.toString();		
	}
	
	public String provideEquationAnswer() {
		StringBuilder builder = new StringBuilder();
		builder.append("Đáp án là: ");
		List<Equation> equations = this.currentKnowledge.getEquations();
		for (Equation equation : equations)
			builder.append("$").append(equation.getLatex()).append("$ ;");
		builder.append("<br//>");
		if (currentKnowledgeIter.hasNext()) {
			builder.append("Tiếp theo ");
			this.state = SuggestionTypes.ASKING_THEORY;
			this.currentKnowledge = this.currentKnowledgeIter.next();
			builder.append(this.provideTheory());
		}else {
			this.state = SuggestionTypes.ENDING;
			builder.append("<br//>Đã xong, mời bạn nhập hàm số khác");
		}
		return builder.toString();		
	}
			
	
	
	public String provideSuggestion(String answer) {
		answer = answer.trim();
		StringBuilder response = new StringBuilder();
		if (answer.isEmpty()) {
			response.append("Để giải bài toán này trước hết ");
			response.append(this.provideTheory());					
		}
		else if ((this.state == SuggestionTypes.ASKING_THEORY && SentenceGroups.positiveResponse.contains(answer.toLowerCase())) ||
				(this.state == SuggestionTypes.ASKING_FORMULAR && SentenceGroups.positiveResponse.contains(answer.toLowerCase()))) {
			this.state = SuggestionTypes.ASKING_VAR;
			response.append(this.provideEquation());				
		}
		else if (this.state == SuggestionTypes.ASKING_THEORY && SentenceGroups.negativeResponse.contains(answer.toLowerCase())) {							
			response.append(this.provideFormula());
		}
		
		else if (this.state == SuggestionTypes.ASKING_FORMULAR && SentenceGroups.negativeResponse.contains(answer.toLowerCase())) {
			response.append("Vậy để mình cho bạn xem lời giải của bước này: <br//>");
			response.append(this.provideEquationAnswer());
		}
		
		else if (this.state == SuggestionTypes.ASKING_VAR && SentenceGroups.negativeResponse.contains(answer.toLowerCase())) {
			this.state = SuggestionTypes.ASKING_THEORY;
			response.append(this.provideEquationAnswer());	
		}
		
		else if (this.state == SuggestionTypes.ASKING_VAR && this.currentKnowledge.checkAnswer(answer)){
			response.append("Đúng rồi<br//>");
			response.append(this.provideEquationAnswer());
		}
		
		else if (this.state == SuggestionTypes.ASKING_VAR && !this.currentKnowledge.checkAnswer(answer)) {
			response.append("Sai rồi<br//>");				
			response.append(this.provideEquationAnswer());			
		}
		
		else{
			response.append("Mình chưa hiểu ý bạn lắm, bạn có thể nhập lại không?");
		}
								
		return response.toString();			
	}
	
	public void reset() {
		this.solutions = null;
		this.currentKnowledge = null;
		this.state = SuggestionTypes.ASKING_THEORY;
	}
}
