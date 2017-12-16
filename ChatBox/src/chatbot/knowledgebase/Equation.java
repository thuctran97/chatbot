package chatbot.knowledgebase;

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
	
	public Equation(String equa) {
		String [] temp = equa.split("(=|>|<|(>=)|(<=)|(=<)|(=>))");
		this.rhs = temp[0];		
		this.lhs = temp[temp.length -1];
		int start = this.rhs.length();
		int end = equa.length() - this.lhs.length();
		this.operator = equa.substring(start, end);
	 }
	
	@Override
	public String toString() {
		return this.rhs + " " + this.operator + " " + this.lhs;
	}
	
	public boolean matchValue(String value) {
		return this.lhs.compareTo(value) == 0;
	}
	
	
	/*public static void main(String [] args) {
		System.out.println(new Equation("x2<=156"));
	}*/
}
