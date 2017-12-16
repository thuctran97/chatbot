package chatbot.knowledgebase;

public enum ProblemType {
	
	PHUONG_TRINH_TIEP_TUYEN("Dạng toán tìm phương trình tiếp tuyến"),
	CUC_TRI_HAM_SO("Dạng toán tìm cực trị hàm số"),
	DON_DIEU_HAM_SO("Dạng toán đơn điệu hàm số");
	
	private String description;
	
	ProblemType(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return this.description;
	}
}
