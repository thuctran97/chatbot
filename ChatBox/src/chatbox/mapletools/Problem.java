package chatbox.mapletools;

	
public enum Problem{
	TIEP_TUYEN_TAI_DIEM(null,
						null,
						null,
						null,
						"Bài toán tiếp tuyến tại điểm cho trước",
						ProblemType.PHUONG_TRINH_TIEP_TUYEN,
						1),
	
	TIEP_TUYEN_SONG_SONG(null,
						null,
						null,
						null,
						"Bài toán tiếp tuyến song song với đường thẳng cho trước",
						ProblemType.PHUONG_TRINH_TIEP_TUYEN,
						3),
	
	TIEP_TUYEN_VUONG_GOC(null,
						null,
						null,
						null,
						"Bài toán tiếp tuyến vuông góc với đường thẳng cho trước",
						ProblemType.PHUONG_TRINH_TIEP_TUYEN,
						4),
	
	TIEP_TUYEN_HE_SO_GOC(null,
						null,
						null,
						null,
						"Bài toán tiếp tuyến có hệ số góc cho trước",
						ProblemType.PHUONG_TRINH_TIEP_TUYEN,
						5),
	
	TIEP_TUYEN_CAT_TAI_DIEM(null,
			null,
			null,
			null,
			"Bài toán tiếp tuyến song song cắt với đường thẳng cho trước",
			ProblemType.PHUONG_TRINH_TIEP_TUYEN,
			2),

	
	TIM_CUC_TRI(null,
			   null,
			   null,
			   null,
			   "Tìm cực trị của hàm số",
			   ProblemType.CUC_TRI_HAM_SO,
			   1),
	
	TIM_DIEU_KIEN_DAT_CUC_TRI(null,
							null,
							null,
							null,
							"Tìm điệu kiện để hàm số đạt cực trị tại x<sub>o</sub>",
							ProblemType.CUC_TRI_HAM_SO,
							2),
	
	XET_TINH_DONG_BIEN(null,
					null,
					null,
					null,
					"Xét tính đồng biến của hàm số",
					ProblemType.DON_DIEU_HAM_SO,
					1),
	
	XET_TINH_NGHICH_BIEN(null,
						null,
						null,
						null,
						"Xét tính nghịch biến của hàm số",
						ProblemType.DON_DIEU_HAM_SO,
						2),
	
	KHAO_SAT_HAM_SO("KSHS1[kshs]",
					"(f)",
					new String[] {"f"},					
					new String[] {""},
					"Khảo sát hàm số",
					ProblemType.DON_DIEU_HAM_SO,
					3);
	
	private String proc;
	private String arguments;	
	private String[] variableDescription;
	private String[] variableNames;
	private String description;
	private ProblemType type;
	private int currentArgumentIndex;
	private int id; 
	
	Problem(String proc, String arguments, String[] variableNames, String [] variableDescription, String description, ProblemType type, int id){	
		this.proc = proc;
		this.arguments = arguments;
		this.variableNames = variableNames;
		this.variableDescription = variableDescription;
		this.description = description;
		this.type = type;
		this.currentArgumentIndex = 0;
		this.id = id;
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
	
	public String[] getVariableDescription() {
		return this.variableDescription;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public ProblemType getType() {
		return this.type;
	}
	
	public int getCurrentArgumentsIndex() {
		return this.currentArgumentIndex;
	}
	
	public void increaseCurrentArgumentsIndex() {
		this.currentArgumentIndex++;
	}
	
	public int getID() {
		return this.id;
	}
	
	public void reset() {
		this.currentArgumentIndex = 0;
	}
}
