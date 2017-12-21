package chatbot.knowledgebase;

	
public enum Problem{
	TIEP_TUYEN_TAI_DIEM("KSHS1[tieptuyen_taidiem]",
						"(f,x,y)",
						new String[] {"f","x","y"},
						new String[] {"","hoành độ tiếp điểm", "tung độ tiếp điểm"},
						"Bài toán tiếp tuyến tại điểm cho trước",
						ProblemType.PHUONG_TRINH_TIEP_TUYEN,
						1),
	
	TIEP_TUYEN_SONG_SONG("KSHS1[tieptuyen_songsong]",
						"(f,d)",
						new String[] {"f","d"},
						new String[] {"","phương trình đường thẳng song song  với tiếp tuyến"},						
						"Bài toán tiếp tuyến song song với đường thẳng cho trước",
						ProblemType.PHUONG_TRINH_TIEP_TUYEN,
						3),
	
	TIEP_TUYEN_VUONG_GOC("KSHS1[tieptuyen_vuonggoc]",
						"(f,d)",
						new String[] {"f","d"},
						new String[] {"","phương trình đường thẳng vuông góc với tiếp tuyến"},
						"Bài toán tiếp tuyến vuông góc với đường thẳng cho trước",
						ProblemType.PHUONG_TRINH_TIEP_TUYEN,
						4),
	
	TIEP_TUYEN_HE_SO_GOC("KSHS1[tieptuyen_hesogoc]",
						"(f,k)",
						new String[] {"f","k"},
						new String[] {""," hệ số góc k"},
						"Bài toán tiếp tuyến có hệ số góc cho trước",
						ProblemType.PHUONG_TRINH_TIEP_TUYEN,
						5),
	
	
	XET_TINH_DONG_BIEN("KSHS1[dongbien]",
					"(f,x,m,a,b)",
					new String[] {"f","x","m","a","b"},
					new String[] {"","tên biến","tên tham số","cận dưới a của khoảng đang xét","cận trên b của khoảng đang xét"},
					"Xét tính đồng biến của hàm số",
					ProblemType.DON_DIEU_HAM_SO,
					1),
	
	XET_TINH_NGHICH_BIEN("KSHS1[nghichbien]",
						"(f,x,m,a,b)",
						new String[] {"f","x","m","a","b"},
						new String[] {"","tên biến","tên tham số","cận dưới a của khoảng đang xét","cận trên b của khoảng đang xét"},
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
		this.currentArgumentIndex = 1;
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
