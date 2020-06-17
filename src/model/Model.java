package model;

import java.util.ArrayList;
import java.util.List;

import db.SerieADao;

public class Model {
	
	private SerieADao dao;
	private List<Team> teamsList;
	private List<Match> matchesList;
	
	private Simulator simulator;
	private double redCardMultiplier;
	
	public Model(double redCardMultiplier) {
		this.dao = new SerieADao();
		this.teamsList = dao.getTeamsList();
		this.matchesList = dao.getMatchesList();
		this.simulator = new Simulator(this);
		this.redCardMultiplier = redCardMultiplier;
	}
	
	public Match simulateMatch(Match match) {
		simulator.init(match);
		simulator.run();
		
		return simulator.getSimulatedMatch();
	}

	public List<Team> getTeamsList() {
		return teamsList;
	}

	public List<Match> getMatchesList() {
		return matchesList;
	}

	public double getRedCardMultiplier() {
		return redCardMultiplier;
	}

	public void setRedCardMultiplier(double redCardMultiplier) {
		this.redCardMultiplier = redCardMultiplier;
	}

	public List<Match> getMatchesListByTeam(Team team) {
		List<Match> result = new ArrayList<>();
		
		for(Match match : matchesList) {
			if(match.getHomeTeam().equals(team) || match.getAwayTeam().equals(team))
				result.add(match);
		}
		
		return result;
	}

}
