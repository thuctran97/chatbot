package chatbot.knowledgerendering;

import java.util.*;

public class KnowledgeBase{
	private String theory;
	private List<String> equations;
	
	public String getTheory() {
		return theory;
	}

	public void setTheory(String theory) {
		this.theory = theory;
	}

	public List<String> getEquations() {
		return equations;
	}

	public void setEquation(List<String> equations) {
		this.equations = equations;
	}

	public KnowledgeBase(String[] token) {
		this.theory = token[0];
		this.equations = new ArrayList<>();
		for (int i = 1; i < token.length; i++)
			equations.add(token[i]);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.theory).append(" ");//.append("$").append(this.theory).append("$: ");		
		for (String equa : equations) {
			builder.append("$").append(equa).append("$");
		}
		return builder.toString();
	}

}
