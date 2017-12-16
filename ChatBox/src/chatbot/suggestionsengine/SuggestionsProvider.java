package chatbot.suggestionsengine;

import chatbot.knowledgebase.*;
import java.util.*;


public class SuggestionsProvider {
	
	private List<KnowledgeBase> solutions;	
	private SuggestionTypes state;
	private Iterator<KnowledgeBase> currentKnowledge;
	
	public SuggestionsProvider() {
		this(null);
	}
	
	public SuggestionsProvider(List<KnowledgeBase> solutions) {
		this.solutions = solutions;
		this.state = SuggestionTypes.PROVIDING_THEORY;
		if (this.solutions != null) {
			this.currentKnowledge = this.solutions.iterator();
		}
		else {
			this.currentKnowledge = null;
		}
	}
	
	public List<KnowledgeBase> getSolutions() {
		return solutions;
	}

	public void setSolutions(List<KnowledgeBase> solutions) {
		this.solutions = solutions;
		this.currentKnowledge = this.solutions.iterator(); 
	}

	public SuggestionTypes getState() {
		return state;
	}

	public void setState(SuggestionTypes state) {
		this.state = state;
	}

	public String provideTheory(KnowledgeBase current) {
		StringBuilder builder = new StringBuilder("Bạn cần phải <br//>");	
		builder.append(current.getTheory());
		return builder.toString();
	}
	
	public String provideEquation(KnowledgeBase current) {
		StringBuilder builder = new StringBuilder();
		String rhs = current.getAllRhs();
		builder.append("Bạn hãy nhập giá trị của ").append(rhs).append("<br//>mỗi giá trị cách nhau bởi dấu ,");
		builder.append("<br//>Bạn đã biết chưa?");			
		return builder.toString();
	}
	
	public String provideEquationAnswer(KnowledgeBase current) {
		StringBuilder builder = new StringBuilder();
		builder.append("Bạn cần phải tính như thế này:<br//>");
		List<Equation> equations = current.getEquations();
		for (Equation equation : equations)
		builder.append("$").append(equation.toString()).append("$<br//>");		
		return builder.toString();		
	}
			
	public String provideSuggestion(String answer) {
		StringBuilder response = new StringBuilder();
		if (!currentKnowledge.hasNext()) {
			this.state = SuggestionTypes.ENDING;
			return "Đã xong, bạn có muốn bắt đầu lại?";
		}
		else {
			KnowledgeBase current = this.currentKnowledge.next(); 
			if (answer.isEmpty()) {
				response.append("Để giải bài toán này trước hết ");
				response.append(this.provideTheory(current));					
			}
			else if (SentenceGroups.positiveResponse.contains(answer) && this.state == SuggestionTypes.PROVIDING_THEORY) {
				this.state = SuggestionTypes.PROVIDING_VALUE;
				response.append(this.provideEquation(current));				
			}
			else if (SentenceGroups.negativeResponse.contains(answer) && this.state == SuggestionTypes.PROVIDING_THEORY) {							
				response.append(this.provideEquationAnswer(current));	
				response.append("Tiếp theo ");
				if (!currentKnowledge.hasNext()) {
					this.state = SuggestionTypes.ENDING;
					response.append("Đã xong, bạn có muốn bắt đầu lại?");
				}
				else {
					current = this.currentKnowledge.next();
					response.append(this.provideTheory(current));
				}				
			}
			return response.toString();
		}				
	}
}
