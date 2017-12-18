package chatbot.knowledgebase;

public class Equation {
	
	private String rhs;
	private	String lhs;
	private String operator;
	private String latex;
	
	String[] comparisionOperator = new String[]{">=","<=",">","<","="};
	
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
	
	public String getLatex() {
		return this.latex;
	}

	
	public Equation(String rhs, String operator, String lhs) {
		this.rhs = rhs;
		this.operator = operator;
		this.lhs = lhs;
	}
	
	public Equation(String equa, String latex) {		
		int pos;
		for (int i = 0; i < comparisionOperator.length; i++) {
			if ((pos = equa.lastIndexOf(comparisionOperator[i])) != - 1) {				
				this.operator = equa.substring(pos, pos + comparisionOperator[i].length());
				this.lhs = equa.substring(0, pos);
				this.rhs = equa.substring(pos + comparisionOperator[i].length(), equa.length());
				break;
			}
		}
		this.latex = latex;
	}
		
	
	@Override
	public String toString() {
		return this.lhs + this.operator + this.rhs;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (this == o)
			return true;
		if (!(o instanceof Equation))
			return false;
		Equation equa = (Equation)o;
		
		return (((this.lhs.compareTo(equa.getLhs()) == 0) &&
				(this.rhs.compareTo(equa.getRhs()) == 0) &&
				(this.operator.compareTo(equa.getOperator()) == 0)) ||
				(this.operator.compareTo(equa.getOperator()) == 0 &&
				(this.rhs.compareTo(equa.getRhs()) == 0)));
	}
	
	public boolean matchValue(String value) {
		return this.rhs.compareTo(value) == 0;
	}

}
