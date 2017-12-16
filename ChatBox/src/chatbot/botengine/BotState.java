package chatbot.botengine;

public enum BotState {
	AFTER_GREETING,
	AFTER_CHOOSING_PROBLEM,
	AFTER_CHOOSING_SUBPROBLEM,
	ASKING_FOR_ARGUMENTS,
	PROVIDING_SUGGESTION;
	
	private static BotState[] vals = values();
	
	public BotState next(){
		int index = (this.ordinal() + 1) % vals.length;
		return vals[index];
	}
	
	public BotState prev(){
		int index = (this.ordinal() - 1) % vals.length;
		if (index >= 0)
			return vals[index];
		else
			return vals[0];
	}
}
