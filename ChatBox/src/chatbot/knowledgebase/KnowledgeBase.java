package chatbot.knowledgebase;

import java.util.*;
import chatbot.botengine.*;

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
		String [] tokens = this.formula.split(" : ",-1);
		if (tokens.length != 1)
			return tokens[0] + " $" + tokens[1] + "$";
		else
			return tokens[0];
	}
	

	public KnowledgeBase(String[] tokens) {
		this.theory = tokens[0];
		this.formula = tokens[1];
		this.equations = new ArrayList<>();
		for (int i = 2; i < tokens.length; i+= 2)
			equations.add(new Equation(tokens[i], tokens[i+1]));
	}
	
	@Override
	public String toString() {		
		return "";
	}
		
	public String getAllLhs(Bot bot) {
		StringBuilder builder = new StringBuilder();
		for (Equation equa : this.equations)
			builder.append("$").append(bot.getConnector().getLatex(equa.getLhs())).append("$,");
		builder.deleteCharAt(builder.length() - 1);
		return builder.toString();
	}		

	public boolean checkAnswer(String answer) {
		String[] tokens = answer.split(",");
		if (tokens.length != this.equations.size())
			return false;
		else {
			for (int i = 0; i < tokens.length; i++) {
				if (!(this.equations.get(i).matchValue(tokens[i]))){
					return false;
				}
			}			
			return true;
		}
	}
	
}
