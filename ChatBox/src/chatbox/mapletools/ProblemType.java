package chatbox.mapletools;

public enum ProblemType {
	TIEP_TUYEN_SONG_SONG(null,null),
	TIEP_TUYEN_VUONG_GOC(null,null),
	KHAO_SAT_HAM_SO("KSHS1[kshs]","(a,b,c,d)");
	
	private String proc;
	private String arguments;
	
	ProblemType(String proc, String arguments){
		this.proc = proc;
		this.arguments = arguments;
	}
	
	public String getProcedureName() {
		return this.proc;
	}
	
	public String getArguments() {
		return this.arguments;
	}
}
