package model;

public class Event {
	
	public enum EventType {
		HOME_OPEN_PLAY,
		AWAY_OPEN_PLAY,
		HOME_FREE_KICK,
		AWAY_FREE_KICK,
		HOME_PENALTY,
		AWAY_PENALTY,
		HOME_OWN_GOAL,
		AWAY_OWN_GOAL,
		HOME_FOUL,
		AWAY_FOUL
	}
	
	private EventType type;
	private double successRate;
	
	public Event(EventType type, double successRate) {
		this.type = type;
		this.successRate = successRate;
	}

	public EventType getType() {
		return type;
	}

	public double getSuccessRate() {
		return successRate;
	}

	@Override
	public String toString() {
		if(type.equals(EventType.HOME_FOUL) || type.equals(EventType.AWAY_FOUL))
			return String.format("%s, redCardRate=%.2f", type, successRate);
		else
			return String.format("%s, successRate=%.2f", type, successRate);
	}
	
}
