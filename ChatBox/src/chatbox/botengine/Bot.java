package chatbox.botengine;

import chatbox.mapletools.*;
import java.util.*;
import com.maplesoft.openmaple.Algebraic;

public class Bot {
	
	private OpenMapleConnector connector;	
	private BotState state;
	
	private List<String> arguments;
	private ProblemType currentProblemType;
	private Problem currentProblem;
	private String equation;
	private SolutionParser parser;
	
	private static Map<ProblemType, Map<Integer, Problem>> problems = createSelectionOfSubProblem();	
	
	private static Map<ProblemType, Map<Integer, Problem>> createSelectionOfSubProblem() {
		Map<ProblemType, Map<Integer, Problem>> sub = new LinkedHashMap<>();
		List<Problem> problems = Arrays.asList(Problem.values());
		for (Problem problem : problems) {
			if (!sub.containsKey(problem.getType())) {
				sub.put(problem.getType(), new HashMap<Integer, Problem>());
			}
			sub.get(problem.getType()).put(problem.getID(), problem);
		}
		return sub;
	}
	
	public Bot() {
		this.connector = new OpenMapleConnector();
		this.state = BotState.AFTER_GREETING;
		this.arguments = new ArrayList<>();
		this.parser = new SolutionParser();
	}
	
	public OpenMapleConnector getConnector() {
		return this.connector;		
	}
	
	public String handlingAfterGreeting(String equation) {
		this.equation = equation;
		StringBuilder response = new StringBuilder("Bạn muốn làm dạng toán nào với hàm số này (chọn số thứ tự)<br//>");
		for (ProblemType type : problems.keySet()) {
			response.append(type.ordinal() + 1).append(")").append(type.getDescription()).append("<br//>");
		}			
		this.state = this.state.next();
		return response.toString();
	}
	
	public String handlingAfterChoosingProblem(String msg) {
		if (msg.compareTo("Quay lại") == 0){
			this.state = this.state.prev();
			return handlingAfterGreeting(msg);
		}
		try {
			int option = Integer.parseInt(msg);
			if ( option > 0 && option < 4 ) {
				StringBuilder response = new StringBuilder("Bạn muốn chọn bài toán nào (chọn số thứ tự)<br//>");
				for (Problem problem : problems.get(ProblemType.values()[option - 1]).values()) {
					response.append(problem.getID()).append(")").append(problem.getDescription()).append("<br//>");
				}				
				this.state = this.state.next();
				this.currentProblemType = ProblemType.values()[option - 1];
				return response.toString();
			}
			else {
				throw new NumberFormatException();
			}
		}catch (NumberFormatException e) {
			return "Lựa chọn của bạn không hợp lệ, bạn hãy chọn lại";				
		}
	}
		
	public String handlingAfterChoosingSubProblem(String msg) {
		if (msg.compareTo("Quay lại") == 0){
			this.state = this.state.prev();
			return handlingAfterChoosingProblem(msg);
		}
		try {
			int option = Integer.parseInt(msg);
			this.currentProblem = problems.get(this.currentProblemType).get(option);
			this.connector.setProcedure(this.currentProblem);
			StringBuilder response =  new StringBuilder("Bạn đã chọn bài toán ");
			response.append(this.currentProblem.getDescription()).append("<br/.>");
			response.append("Mời bạn nhập ").append((currentProblem.getVariableDescription()[currentProblem.getCurrentArgumentsIndex()]));
			this.state = this.state.next();
			return response.toString();
		}catch(NumberFormatException e) {
			return "Lựa chọn của bạn không hợp lệ, bạn hãy chọn lại";
		}
	}
	
	public String handlingAskingForArguments(String value) {
		StringBuilder response = new StringBuilder();
		this.arguments.add(value);		
		if (this.currentProblem.getCurrentArgumentsIndex() == this.currentProblem.getVariableNames().length - 1) {
			response.append("Bạn đã nhập đủ tham số");					
			List<String> solution = this.getSolution(this.arguments, this.currentProblem.getVariableNames());
			response.append("Lời giải cho bài này là : <br//>");
			int i = 0;
			for (String sol : solution) {
				response.append("Step ").append(i++).append(" ").append(sol.replace("\"", "")).append("<br//>");
			}
			return response.toString();
		}
		else {
			this.currentProblem.increaseCurrentArgumentsIndex();			
			response.append("Mời bạn nhập ").append((currentProblem.getVariableDescription()[currentProblem.getCurrentArgumentsIndex()]));			
			return response.toString();
		}
	}
	
	public String replyMessage(String msg){
		if (this.state.equals(BotState.AFTER_GREETING)){
			return handlingAfterGreeting(msg);
		}		
		else if (this.state.equals(BotState.AFTER_CHOOSING_PROBLEM)) {
			return handlingAfterChoosingProblem(msg);
		}
		else if (this.state.equals(BotState.AFTER_CHOOSING_SUBPROBLEM)) {
			return handlingAfterChoosingSubProblem(msg);
		}
		else if (this.state.equals(BotState.ASKING_FOR_ARGUMENTS)) {
			return handlingAskingForArguments(msg);
		}
		else {
			return null;
		}
	}
	
	public void setProblem(Problem proc) {
		this.connector.setProcedure(proc);
	}
	
	public List<String> getSolution(List<String> arguments, String[] variableNames){
		Algebraic[] argu = ConnectorUtilities.generateArguments(connector, arguments, variableNames);
		return this.connector.getSolution(argu);
	}
	
	public void reset() {
		this.arguments.clear();
		this.state = BotState.AFTER_GREETING;
		this.currentProblemType = null;
		this.currentProblem = null;
		this.equation = null;
	}
	
	
}
