package chatbot.mapletools;

import com.maplesoft.externalcall.MapleException;
import com.maplesoft.openmaple.*;

public class ConnectorUtilities {
	
	public ConnectorUtilities() {
		
	}
	public static Algebraic[] generateArguments(OpenMapleConnector connector, java.util.List<String> arguments, String[] names){
		try {
			Engine engine = connector.getEngine();
			Algebraic[] argu = new Algebraic[arguments.size()];		
			for (int i = 0; i < arguments.size(); i++) {
				Name name = (Name)engine.evaluate(names[i] + ":");			
				name.assign(engine.evaluate(arguments.get(i) + ":"));
				argu[i] = name;
			}		
			return argu;
		}catch(MapleException e) {
			e.printStackTrace();
			return null;
		}
	}	
}
