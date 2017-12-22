package chatbot.mapletools;


import chatbot.knowledgebase.*;


public class ConnectorUtilities {
	
	public ConnectorUtilities() {
		
	}
	public static String generateProcedure(Problem prob, java.util.List<String> arguments){
		StringBuilder builder = new StringBuilder(prob.getProcedureName());
		builder.append("(");
		for (String argument : arguments) {
			builder.append(argument).append(",");
		}
		builder.deleteCharAt(builder.length() - 1);
		builder.append("):");
		return builder.toString();
	}	
}
