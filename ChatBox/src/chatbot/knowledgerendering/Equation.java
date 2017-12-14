package chatbot.knowledgerendering;

import java.util.Arrays;
import java.util.List;

public class Equation {
	
	private String rhs;
	private	String lhs;
	private String operator;
	
	public String getRhs() {
		return rhs;
	}

	public void setRhs(String rhs) {
		this.rhs = rhs;
	}

	public String getLhs() {
		return lhs;
	}

	public void setLhs(String lhs) {
		this.lhs = lhs;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	
	public Equation(String rhs, String operator, String lhs) {
		this.rhs = rhs;
		this.operator = operator;
		this.lhs = lhs;
	}
	
	public Equation(String equation) {
		String [] temp = equation.split("(=|>|<|(>=)|(<=)|(=<)|(=>))");
		this.rhs = temp[0];		
		this.lhs = temp[temp.length -1];
		this.operator = equation.substring(this.rhs.length() , equation.indexOf(lhs));
	 }
	
	@Override
	public String toString() {
		return this.rhs + " " + this.operator + " " + this.lhs;
	}
	
	public static void main(String [] args) {
		System.out.println(new Equation("x2<=156"));
	}
}
