package chatbot.knowledgebase;

import java.util.*;

public class KnowledgeBase{
	private String theory;
	private String formula;
	private List<Equation> equations;
	
	public String getTheory() {
		return theory;
	}

	public List<Equation> getEquations() {
		return equations;
	}
	
	public String getFormula() {
		return this.formula;
	}
	

	public KnowledgeBase(String[] tokens) {
		this.theory = tokens[0];
		this.equations = new ArrayList<>();
		for (int i = 1; i < tokens.length; i++)
			equations.add(new Equation(tokens[i]));
	}
	
	@Override
	public String toString() {		
		return "";
	}
		
	public String getAllRhs() {
		StringBuilder builder = new StringBuilder();
		for (Equation equa : this.equations)
			builder.append("$").append(equa.getRhs()).append("$,");
		return builder.toString();
	}		

}
