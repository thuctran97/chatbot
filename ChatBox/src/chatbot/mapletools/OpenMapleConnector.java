package chatbot.mapletools;

import com.maplesoft.externalcall.MapleException;
import com.maplesoft.openmaple.*;

import java.util.*;

public class OpenMapleConnector {
	private String procedure;	
	private CallBacks callBacks;
	private Engine engine;
	
	public class CallBacks implements EngineCallBacks{
		
		private boolean limitExceeded;
		
		public java.util.List<String> solution = new ArrayList<>();
		
		
		private CallBacks(){
			this.limitExceeded = false;
		}
		public void textCallBack( Object data, int tag, String output ) throws MapleException{
	        switch ( tag ){
		        case MAPLE_TEXT_OUTPUT:		
		        	System.out.print("Text: ");
		            break;
		        case MAPLE_TEXT_DIAG:
		            System.out.print( "Diag: " );
		            break;
		        case MAPLE_TEXT_MISC:		        	
		        	solution.add(output);	
		            break;
		        case MAPLE_TEXT_HELP:
		            System.out.print( "Help: " );
		            break;
		        case MAPLE_TEXT_QUIT:
		            System.out.print( "Quit: " );
		            break;
		        case MAPLE_TEXT_WARNING:
		            System.out.print( "Warning: " );
		            break;
		        case MAPLE_TEXT_DEBUG:
		            System.out.print( "Debug: " );
		            break;
		    }		    
	        System.out.println(output);
	    }
		
	    public void errorCallBack( Object data, int offset, String msg ) throws MapleException{
		        if ( offset >= 0 ){
		        	throw new MapleException( "Error: "+msg );
		        }
		        else{
		        	throw new MapleException( "syntax error at offset " + offset + ": "+msg );
		        }
		}
	    
	    public void statusCallBack( Object data, long kbused, long kballoced, double timeused ) throws MapleException{
		        if ( timeused > 5 ){
		        	limitExceeded = true;
		        }
		        System.out.println( "Status: used = "+kbused+", alloced = "+
		        kballoced+", time = "+timeused );
		}
	    
	    public String readLineCallBack( Object data, boolean debug ) throws MapleException{
		        return "readline";
		}
	    
	    public boolean redirectCallBack( Object data, String output, boolean append ) throws MapleException{
		        System.out.println( "redirect to "+output+" append = "+append );
		        return true;
		}
	    
	    public String callBackCallBack( Object data, String output ) throws MapleException{
		        System.out.println( "callback: "+output );
		        return output+";";
		}
	    
	    public boolean queryInterrupt( Object data ) throws MapleException{
		        return limitExceeded;
		}
	    
	    public String streamCallBack( Object data, String name, String args[] ) throws MapleException{		        
		        int i;
		        System.out.print( name+"( " );
		        for ( i = 0; i < args.length; i++ ){
		        	System.out.print( args[i]+" " );
		        }
		        System.out.println( ")" );
		        return args[0]+":";
	    }
	    
	    public java.util.List<String> getSolution(){
	    	return this.solution;
	    }
	
	    public void clearSolution() {
	    	this.solution.clear();
	    }	    	    
	   
	}
	
	public OpenMapleConnector() {				
		try {
			this.callBacks = new CallBacks();
			this.procedure = "";
			String[] init = {"java"};
			this.engine = new Engine (init, this.callBacks, null, null);
		}catch(MapleException e) {
			e.printStackTrace();
			System.out.println("Could not initialize maple session");
		}
	}
	
	public String getProcedure() {
		return this.procedure;
	}


	public java.util.List<String> getSolution(String proc) {
		try {
			this.callBacks.clearSolution();									
			Algebraic alge = this.engine.evaluate(proc);
			alge.dispose();
			return this.callBacks.getSolution();
		} catch (MapleException e){		
			e.printStackTrace();
			System.out.println("Could not execute the procedure");			
			return null;
		}		
	}	
	
	
}
