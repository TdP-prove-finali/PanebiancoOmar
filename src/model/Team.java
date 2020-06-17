package model;

public class Team {
	
	private int teamId;
	private String teamName;
	private int homeMatches, homeWins, homeDraws, homeLosses, homeMadeGoals, homeConcededGoals;
	private int awayMatches, awayWins, awayDraws, awayLosses, awayMadeGoals, awayConcededGoals;
	
	public Team(int teamId, String teamName, int homeMatches, int homeWins, int homeDraws, int homeLosses,
			int homeMadeGoals, int homeConcededGoals, int awayMatches, int awayWins, int awayDraws, int awayLosses,
			int awayMadeGoals, int awayConcededGoals) {
		super();
		this.teamId = teamId;
		this.teamName = teamName;
		this.homeMatches = homeMatches;
		this.homeWins = homeWins;
		this.homeDraws = homeDraws;
		this.homeLosses = homeLosses;
		this.homeMadeGoals = homeMadeGoals;
		this.homeConcededGoals = homeConcededGoals;
		this.awayMatches = awayMatches;
		this.awayWins = awayWins;
		this.awayDraws = awayDraws;
		this.awayLosses = awayLosses;
		this.awayMadeGoals = awayMadeGoals;
		this.awayConcededGoals = awayConcededGoals;
	}

	public int getTeamId() {
		return teamId;
	}

	public String getTeamName() {
		return teamName;
	}

	public int getHomeMatches() {
		return homeMatches;
	}

	public void setHomeMatches(int homeMatches) {
		this.homeMatches = homeMatches;
	}

	public int getHomeWins() {
		return homeWins;
	}

	public void setHomeWins(int homeWins) {
		this.homeWins = homeWins;
	}

	public int getHomeDraws() {
		return homeDraws;
	}

	public void setHomeDraws(int homeDraws) {
		this.homeDraws = homeDraws;
	}

	public int getHomeLosses() {
		return homeLosses;
	}

	public void setHomeLosses(int homeLosses) {
		this.homeLosses = homeLosses;
	}

	public int getHomeMadeGoals() {
		return homeMadeGoals;
	}

	public void setHomeMadeGoals(int homeMadeGoals) {
		this.homeMadeGoals = homeMadeGoals;
	}

	public int getHomeConcededGoals() {
		return homeConcededGoals;
	}

	public void setHomeConcededGoals(int homeConcededGoals) {
		this.homeConcededGoals = homeConcededGoals;
	}

	public int getAwayMatches() {
		return awayMatches;
	}

	public void setAwayMatches(int awayMatches) {
		this.awayMatches = awayMatches;
	}

	public int getAwayWins() {
		return awayWins;
	}

	public void setAwayWins(int awayWins) {
		this.awayWins = awayWins;
	}

	public int getAwayDraws() {
		return awayDraws;
	}

	public void setAwayDraws(int awayDraws) {
		this.awayDraws = awayDraws;
	}

	public int getAwayLosses() {
		return awayLosses;
	}

	public void setAwayLosses(int awayLosses) {
		this.awayLosses = awayLosses;
	}

	public int getAwayMadeGoals() {
		return awayMadeGoals;
	}

	public void setAwayMadeGoals(int awayMadeGoals) {
		this.awayMadeGoals = awayMadeGoals;
	}

	public int getAwayConcededGoals() {
		return awayConcededGoals;
	}

	public void setAwayConcededGoals(int awayConcededGoals) {
		this.awayConcededGoals = awayConcededGoals;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + teamId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Team other = (Team) obj;
		if (teamId != other.teamId)
			return false;
		return true;
	}
	
}
