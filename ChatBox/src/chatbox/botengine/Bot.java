package chatbox.botengine;

import chatbox.mapletools.*;
import chatbot.knowledgerendering.*;
import java.util.*;
import com.maplesoft.openmaple.Algebraic;


public class Bot {
	
	private OpenMapleConnector connector;	
	private BotState state;
	private List<String> arguments;
	private ProblemType currentProblemType;
	private Problem currentProblem;
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
		/*if (BotUtil.checkEquation(equation)) {*/
			this.arguments.add(equation);
			StringBuilder response = new StringBuilder("Bạn muốn làm dạng toán nào với hàm số này (chọn số thứ tự)<br//>");
			for (ProblemType type : problems.keySet()) {
				response.append(type.ordinal() + 1).append(")").append(type.getDescription()).append("<br//>");
			}			
			this.state = this.state.next();
			return response.toString();
/*		}else {
			return "Cái bạn nhập ko phải hàm số, mời nhập lại";
		}*/
	}
	
	public String handlingAfterChoosingProblem(String msg) {
		if (SentenceGroups.revertSentences.contains(msg.toLowerCase())){
			this.state = this.state.prev();
			return this.handlingAfterGreeting(msg);
		}
		else if (SentenceGroups.restartSentences.contains(msg.toLowerCase())) {			
			this.reset();
			return "Mời bạn nhập một hàm số khác";
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
	
	public String getSolution(StringBuilder response) {
		List<KnowledgeBase> solution = this.parser.parsing(this.getSolution(this.arguments, this.currentProblem.getVariableNames()));
		response.append("Lời giải cho bài này là:<br//>");
		int i = 0;
		for (KnowledgeBase sol : solution) {
			response.append("Step ").append(i++).append(" ").append(sol.toString()).append("<br//>");					
		}
		return response.toString();
	}
		
	public String handlingAfterChoosingSubProblem(String msg) {
		if (SentenceGroups.revertSentences.contains(msg.toLowerCase())){
			this.state = this.state.prev();
			return this.handlingAfterChoosingProblem(msg);
		}
		else if (SentenceGroups.restartSentences.contains(msg.toLowerCase())) {			
			this.reset();
			return "Mời bạn nhập một hàm số khác";
		}
		try {
			int option = Integer.parseInt(msg);
			this.currentProblem = problems.get(this.currentProblemType).get(option);
			this.connector.setProcedure(this.currentProblem);
			StringBuilder response =  new StringBuilder("Bạn đã chọn bài toán ");
			response.append(this.currentProblem.getDescription()).append("<br/.>");
			if (this.currentProblem.getVariableNames().length == 1) {			
				return this.getSolution(response);
			}
			response.append("Mời bạn nhập ").append((currentProblem.getVariableDescription()[currentProblem.getCurrentArgumentsIndex()]));
			this.state = this.state.next();
			return response.toString();
		}catch(NumberFormatException e) {
			return "Lựa chọn của bạn không hợp lệ, bạn hãy chọn lại";
		}
	}
	
	public String handlingAskingForArguments(String value) {
		if (SentenceGroups.revertSentences.contains(value.toLowerCase())){
			this.state = this.state.prev();
			this.arguments.clear();
			return this.handlingAfterChoosingProblem(value);
		}
		if (SentenceGroups.restartSentences.contains(value.toLowerCase())) {			
			this.reset();
			return "Mời bạn nhập một hàm số khác";
		}
		StringBuilder response = new StringBuilder();
		if (this.currentProblem.getCurrentArgumentsIndex() == this.currentProblem.getVariableNames().length - 1) {					
			this.state = state.next();
			return "Bắt đầu hướng dẫn";
		}
		else {
			this.arguments.add(value);		
			this.currentProblem.increaseCurrentArgumentsIndex();			
			response.append("Mời bạn nhập ").append((currentProblem.getVariableDescription()[currentProblem.getCurrentArgumentsIndex()]));			
			return response.toString();
		}
	}
	
	public String replyMessage(String msg){
		if (this.state.equals(BotState.AFTER_GREETING)){
			return this.handlingAfterGreeting(msg);
		}		
		else if (this.state.equals(BotState.AFTER_CHOOSING_PROBLEM)) {
			return this.handlingAfterChoosingProblem(msg);
		}
		else if (this.state.equals(BotState.AFTER_CHOOSING_SUBPROBLEM)) {
			return this.handlingAfterChoosingSubProblem(msg);
		}
		else if (this.state.equals(BotState.ASKING_FOR_ARGUMENTS)) {
			return this.handlingAskingForArguments(msg);
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
	}
	
	
}
