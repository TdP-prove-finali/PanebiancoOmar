package model;

public class Match {
	
	private int matchId;
	private Team homeTeam, awayTeam;
	private int homeTeamGoals, awayTeamGoals;
	
	public Match(int matchId, Team homeTeam, Team awayTeam, int homeTeamGoals, int awayTeamGoals) {
		super();
		this.matchId = matchId;
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
		this.homeTeamGoals = homeTeamGoals;
		this.awayTeamGoals = awayTeamGoals;
	}

	public int getMatchId() {
		return matchId;
	}

	public Team getHomeTeam() {
		return homeTeam;
	}

	public Team getAwayTeam() {
		return awayTeam;
	}

	public int getHomeTeamGoals() {
		return homeTeamGoals;
	}

	public void setHomeTeamGoals(int homeTeamGoals) {
		this.homeTeamGoals = homeTeamGoals;
	}

	public int getAwayTeamGoals() {
		return awayTeamGoals;
	}

	public void setAwayTeamGoals(int awayTeamGoals) {
		this.awayTeamGoals = awayTeamGoals;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + matchId;
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
		Match other = (Match) obj;
		if (matchId != other.matchId)
			return false;
		return true;
	}

}
