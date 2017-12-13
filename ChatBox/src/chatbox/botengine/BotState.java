package chatbox.botengine;

public enum BotState {
	GREETING,
	AFTER_GREETING,
	AFTER_CHOOSING_PROBLEM,
	AFTER_CHOOSING_SUBPROBLEM,
	ASKING_FOR_ARGUMENTS,
	GUIDING;
	
	private static BotState[] vals = values();
	
	public BotState next(){
		return vals[(this.ordinal() + 1) % vals.length];
	}
	
	public BotState prev(){
		return vals[(this.ordinal() - 1) % vals.length];
	}
}
