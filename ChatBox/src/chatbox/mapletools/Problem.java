package chatbox.mapletools;

	
public enum Problem{
	TIEP_TUYEN_SONG_SONG(null,
						null,
						null,
						"Tiếp tuyến song song với đường thẳng",
						ProblemType.PHUONG_TRINH_TIEP_TUYEN),
	
	TIEP_TUYEN_VUONG_GOC(null,
						null,
						null,
						"Tiếp tuyến vuông góc với đường thẳng",
						ProblemType.PHUONG_TRINH_TIEP_TUYEN),
	
	CUC_TRI_TAI_MOT_DIEM(null,
						null,
						null,
						"Tìm cực trị tại một điểm",
						ProblemType.CUC_TRI_HAM_SO),
	
	XET_TINH_DON_DIEU(null,
					null,
					null,
					"Xét tính đơn điệu của hàm số",
					ProblemType.DON_DIEU_HAM_SO),
	
	KHAO_SAT_HAM_SO("KSHS1[kshs]",
					"(a,b,c,d)",
					new String[] {"Hệ sô 1","Hệ số 2","Hệ số 3","Hệ số 4"},
					"Khảo sát hàm số",
					ProblemType.DON_DIEU_HAM_SO);

	
	private String proc;
	private String arguments;	
	private String[] variableNames;
	private String description;
	private ProblemType type;
	
	Problem(String proc, String arguments, String [] variableNames, String description, ProblemType type){	
		this.proc = proc;
		this.arguments = arguments;
		this.variableNames = variableNames;
		this.description = description;
		this.type = type;
	}
	
	public String getProcedureName() {
		return this.proc;
	}
	
	public String getArguments() {
		return this.arguments;
	}
	
	public String[] getVariableNames() {
		return this.variableNames;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public ProblemType getType() {
		return this.type;
	}
}
