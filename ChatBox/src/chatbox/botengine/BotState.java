package chatbox.botengine;

public enum BotState {
	GREETING,
	CHOOSING_PROBLEM,
	CHOOSING_SUBPROBLEM,
	ASKING_FOR_ARGUMENT,
	GUIDING;
	
	private static BotState[] vals = values();
	
	public BotState next(){
		return vals[(this.ordinal() + 1) % vals.length];
	}
}
