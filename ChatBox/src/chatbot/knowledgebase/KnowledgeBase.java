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
		for (Equation equa : this.equations){
				builder.append("$").append(equa.getLhsLatex()).append("$,");
		}
		builder.deleteCharAt(builder.length() - 1);
		return builder.toString();
	}		

	public boolean checkAnswer(String answer) {
		String[] tokens = answer.split(",");
		
		if (tokens.length != this.equations.size())
			return false;
		else {
			int[] flag = new int[equations.size()];
			for (int i = 0; i < flag.length; i++)
				flag[i] = 0;
			int count = 0;
			for (int i = 0; i < tokens.length; i++) {
				for (Equation equa : this.equations)
				if (equa.matchValue(tokens[i]) && flag[i] == 0){
					count++;
					flag[i] = 1;
				}
			}			
			if (count == tokens.length)
				return true;
			return false;
		}
	}
	
}
