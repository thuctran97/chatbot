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

	public String provideTheory(KnowledgeBase current) {
		StringBuilder builder = new StringBuilder("bạn cần phải <br//>");	
		builder.append(current.getTheory());
		if (this.currentKnowledgeIter.hasNext())
			builder.append("<br//>Bạn đã biết chưa?");
		return builder.toString();
	}
	
	public String provideEquation(KnowledgeBase current) {
		StringBuilder builder = new StringBuilder();
		String rhs = current.getAllLhs(this.bot);
		builder.append("Vậy bạn hãy nhập đáp án của ").append(rhs).append(" mỗi đáp án cách nhau bởi dấu \",\"");		
		return builder.toString();
	}
	
	public String provideEquationAnswer(KnowledgeBase current) {
		StringBuilder builder = new StringBuilder();
		builder.append("Bạn cần phải tính như thế này:<br//>");
		List<Equation> equations = current.getEquations();
		for (Equation equation : equations)
		builder.append("$").append(bot.getConnector().getLatex(equation.toString())).append("$<br//>");		
		return builder.toString();		
	}
	
	public String handlingNegativeAnswer(KnowledgeBase current) {
		StringBuilder response = new StringBuilder();
		response.append(this.provideEquationAnswer(current));	
		response.append("Tiếp theo ");
		if (!currentKnowledgeIter.hasNext()) {
			this.state = SuggestionTypes.ENDING;
			response.append("<br//>Đã xong, mời bạn nhập hàm số khác");
		}
		else {
			this.currentKnowledge = this.currentKnowledgeIter.next();
			response.append(this.provideTheory(this.currentKnowledge));
		}				
		return response.toString();
	}
			
	public String provideSuggestion(String answer) {
		StringBuilder response = new StringBuilder();
		if (!currentKnowledgeIter.hasNext()) {
			this.state = SuggestionTypes.ENDING;
			return "Đã xong, mời bạn nhập hàm số khác";
		}
		else {			
			if (answer.isEmpty()) {
				response.append("Để giải bài toán này trước hết ");
				response.append(this.provideTheory(this.currentKnowledge));					
			}
			else if (this.state == SuggestionTypes.ASKING_THEORY && SentenceGroups.positiveResponse.contains(answer)) {
				this.state = SuggestionTypes.ASKING_VAR;
				response.append(this.provideEquation(this.currentKnowledge));				
			}
			else if (this.state == SuggestionTypes.ASKING_THEORY && SentenceGroups.negativeResponse.contains(answer)) {							
				response.append(this.handlingNegativeAnswer(this.currentKnowledge));
			}
			
			else if (this.state == SuggestionTypes.ASKING_VAR && SentenceGroups.negativeResponse.contains(answer)) {
				this.state = SuggestionTypes.ASKING_THEORY;
				response.append(this.provideEquationAnswer(this.currentKnowledge));	
			}
			
			else if (this.state == SuggestionTypes.ASKING_VAR && this.currentKnowledge.checkAnswer(answer)){
				response.append("Đúng rồi<br//>Tiếp theo ");
				this.state = SuggestionTypes.ASKING_THEORY;		
				this.currentKnowledge = this.currentKnowledgeIter.next();
				response.append(this.provideTheory(this.currentKnowledge));	
			}
			
			else if (this.state == SuggestionTypes.ASKING_VAR && !this.currentKnowledge.checkAnswer(answer)) {
				response.append("Sai rồi<br//>");
				this.state = SuggestionTypes.ASKING_THEORY;
				response.append(this.provideEquationAnswer(this.currentKnowledge));
				this.currentKnowledge = this.currentKnowledgeIter.next();
				response.append(this.provideTheory(this.currentKnowledge));
			}
			
			if (!this.currentKnowledgeIter.hasNext()) {				
				this.state = SuggestionTypes.ENDING;
				response.append("<br//>Đã xong, mời bạn nhập hàm số khác");
			}
			
			return response.toString();
		}				
	}
	
	public void reset() {
		this.solutions = null;
		this.currentKnowledge = null;
		this.state = SuggestionTypes.ASKING_THEORY;
	}
}
