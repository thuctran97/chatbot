package chatbox.botengine;

import chatbox.mapletools.*;
import java.util.*;

import com.maplesoft.openmaple.Algebraic;
public class Bot {
	
	private OpenMapleConnector connector;
	public OpenMapleConnector getConnector() {
		return this.connector;
	}
	
	public Bot() {
		this.connector = new OpenMapleConnector();
	}
	
	public void receiveProblem() {
		
	}
	
	public void setProblem(ProblemType proc) {
		this.connector.setProcedure(proc);
	}
	
	public List<String> getSolution(List<Object> arguments){
		Algebraic[] argu = ConnectorUtilities.generateArguments(connector, arguments);
		return this.connector.getSolution(argu);
	}
	
}
