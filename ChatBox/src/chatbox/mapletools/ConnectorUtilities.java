package chatbox.mapletools;

import com.maplesoft.externalcall.MapleException;
import com.maplesoft.openmaple.*;

public class ConnectorUtilities {
	
	public ConnectorUtilities() {
		
	}
	
	public static Algebraic[] generateArguments(OpenMapleConnector connector, java.util.List<Object> arguments){
		try {
			Engine engine = connector.getEngine();
			Algebraic[] argu = new Algebraic[arguments.size()];		
			for (int i = 0; i < arguments.size(); i++) {
				if (arguments.get(i) instanceof Integer) {
					argu[i] = engine.newNumeric((Integer)arguments.get(i));
				}				
				else {
					argu[i] = engine.newMString((String)arguments.get(i));
				}
			}
			return argu;
		}catch(MapleException e) {
			e.printStackTrace();
			return null;
		}
	}	
}
