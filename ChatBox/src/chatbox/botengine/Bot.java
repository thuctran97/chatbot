package chatbox.botengine;

import chatbox.mapletools.*;
import java.util.*;
import com.maplesoft.openmaple.Algebraic;

public class Bot {
	
	private OpenMapleConnector connector;	
	private BotState state;
	
	
	private static Map<ProblemType, List<Problem>> problems = createSelectionOfSubProblem();	
	
	private static Map<ProblemType, List<Problem>> createSelectionOfSubProblem() {
		Map<ProblemType, List<Problem>> sub = new LinkedHashMap<>();
		List<Problem> problems = Arrays.asList(Problem.values());
		for (Problem problem : problems) {
			if (!sub.containsKey(problem.getType())) {
				sub.put(problem.getType(), new ArrayList<Problem>());
			}
			sub.get(problem.getType()).add(problem);
		}
		return sub;
	}
	
	
	public Bot() {
		this.connector = new OpenMapleConnector();
		this.state = BotState.GREETING;
	}
	
	public OpenMapleConnector getConnector() {
		return this.connector;		
	}
	
	public String replyMessage(String msg){
		if (this.state.equals(BotState.GREETING)){
			StringBuilder response = new StringBuilder("Bạn muốn làm dạng toán nào với hàm số này (chọn số thứ tự)<br//>");
			for (ProblemType type : problems.keySet()) {
				response.append(type.ordinal() + 1).append(")").append(type.getDescription()).append("<br//>");
			}			
			this.state = this.state.next();
			return response.toString();
		}		
		else if (this.state.equals(BotState.CHOOSING_PROBLEM)) {
			try {
				int option = Integer.parseInt(msg);
				StringBuilder response = new StringBuilder("Bạn muốn chọn bài toán nào (chọn số thứ tự)<br//>");
				for (Problem problem : problems.get(ProblemType.values()[option - 1])) {
					response.append(problem.ordinal() + 1).append(")").append(problem.getDescription()).append("<br//>");
				}
				this.state = this.state.next();
				return response.toString();
			}catch (NumberFormatException e) {
				return "Lựa chọn của bạn không hợp lệ bạn hãy chọn lại";				
			}
		}
		else if ()
	}
	
	public void setProblem(Problem proc) {
		this.connector.setProcedure(proc);
	}
	
	public List<String> getSolution(List<Object> arguments){
		Algebraic[] argu = ConnectorUtilities.generateArguments(connector, arguments);
		return this.connector.getSolution(argu);
	}
	
	/*public static void main(String [] args) {
		Bot bot = new Bot();
		bot.setProblem(ProblemType.KHAO_SAT_HAM_SO);
		
		List<Object> arguments = new ArrayList<>();
		arguments.add(-1);
		arguments.add(2);
		arguments.add(1);
		arguments.add(1);
		
		List<String> sol = bot.getSolution(arguments);
		System.out.println("Output from maple : ");
		for (String token : sol) {
			System.out.println(token);
		}
	}*/
}
