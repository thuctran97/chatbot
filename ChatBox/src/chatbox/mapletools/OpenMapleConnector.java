package chatbox.mapletools;

import com.maplesoft.externalcall.MapleException;
import com.maplesoft.openmaple.*;
import com.maplesoft.util.encoder.*;

import java.util.*;

public class OpenMapleConnector {
	private  Engine engine;
	private  Procedure procedure;	
	
	static private class CallBacks implements EngineCallBacks{
		
		private boolean limitExceeded;
		
		public static java.util.List<String> solution = new ArrayList<>();
		
		private CallBacks(){
			this.limitExceeded = false;
		}
		public void textCallBack( Object data, int tag, String output ) throws MapleException{
	        switch ( tag ){
		        case MAPLE_TEXT_OUTPUT:		        		            
		            break;
		        case MAPLE_TEXT_DIAG:
		            System.out.print( "Diag: " );
		            break;
		        case MAPLE_TEXT_MISC:
		        	solution.add(UTF8Encoder.convertUnicodeToUtf8(output));		            
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
	    
	    public static java.util.List<String> getSolution(){
	    	return solution;
	    }
	
	    public static void clearSolution() {
	    	solution.clear();
	    }
	}
	
	public OpenMapleConnector() {
		try {
			String[] args = {"java"};
			this.engine = new Engine(args, new CallBacks(), null, null);			
		}catch (MapleException e) {
			System.out.println("Cannot intialize maple's engine");
		}
	}
	
	public Engine getEngine() {
		return engine;
	}
	
	public Procedure getProcedure() {
		return this.procedure;
	}

	public void setProcedure(Problem proc) {
		try {
			String eval = "proc" + proc.getArguments() + proc.getProcedureName() + proc.getArguments() +";end proc:";
			this.procedure = (Procedure)this.engine.evaluate(eval);
		}catch (MapleException e) {
			System.out.println("Could not intialize the procedure");
		}
	}

	public java.util.List<String> getSolution(Algebraic[] arguments) {
		if (this.procedure != null)
			try {
				CallBacks.clearSolution();
				this.procedure.execute(arguments);
				return CallBacks.getSolution();
			} catch (MapleException e)
		{				
				System.out.println("Could not execute the procedure");
			}
		return null;
	}

	
}
