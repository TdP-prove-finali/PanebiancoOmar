package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import db.SerieADao;

public class Model {
	
	public class ComparatorOfTeamsForLeagueTable implements Comparator<Team> {

		@Override
		public int compare(Team t1, Team t2) {
		
			int t1Points = ((t1.getHomeWins() + t1.getAwayWins()) * 3) + (t1.getHomeDraws() + t1.getAwayDraws());
			int t1MadeGoals = (t1.getHomeMadeGoals() + t1.getAwayMadeGoals());
			int t1ConcededGoals = (t1.getHomeConcededGoals() + t1.getAwayConcededGoals());
			int t1GoalDifference = t1MadeGoals - t1ConcededGoals;
			
			int t2Points = ((t2.getHomeWins() + t2.getAwayWins()) * 3) + (t2.getHomeDraws() + t2.getAwayDraws());
			int t2MadeGoals = (t2.getHomeMadeGoals() + t2.getAwayMadeGoals());
			int t2ConcededGoals = (t2.getHomeConcededGoals() + t2.getAwayConcededGoals());
			int t2GoalDifference = t2MadeGoals - t2ConcededGoals;
			
			Random random = new Random();
			
			if(t1Points == t2Points) {
				if(t1GoalDifference == t2GoalDifference) {
					if(t1MadeGoals == t2MadeGoals) {
						if(random.nextDouble() < 0.5)
							return 1;
						else 
							return -1;
					}
					else
						return t2MadeGoals - t1MadeGoals;
				} else
					return t2GoalDifference - t1GoalDifference;
			} else
				return t2Points - t1Points;
		}

	}
	
	private SerieADao dao;
	private Map<String, Team> teamsMap;
	private List<Match> matchesList;
	
	private Simulator simulator;
	private double redCardMultiplier;
	private int multipleSimulationNumber;
	private Map<Team, Integer> leagueWinsPerTeamMap;
	
	public Model(double redCardMultiplier, int multipleSimulationNumber) {
		this.dao = new SerieADao();
		this.teamsMap = dao.getTeamsMap();
		this.matchesList = dao.getMatchesList();
		this.simulator = new Simulator(this);
		this.redCardMultiplier = redCardMultiplier;
		this.multipleSimulationNumber = multipleSimulationNumber;
		this.leagueWinsPerTeamMap = new HashMap<>();
	}
	
	public void simulateAllDaysSeveralTimes() {
		leagueWinsPerTeamMap.clear();
		
		for(int i = 0; i < multipleSimulationNumber; i ++) {
			simulateAllDays();
			
			List<Team> teamsList = new ArrayList<>(teamsMap.values());
			Collections.sort(teamsList, new ComparatorOfTeamsForLeagueTable());
			
			Team winner = teamsList.get(0);
			if(leagueWinsPerTeamMap.keySet().contains(winner))
				leagueWinsPerTeamMap.replace(winner, leagueWinsPerTeamMap.get(winner) + 1);
			else
				leagueWinsPerTeamMap.put(winner, 1);
			
			this.teamsMap = dao.getTeamsMap();
			this.matchesList = dao.getMatchesList();
		}
	}
	
	public void simulateAllDays() {
		simulateDay(25);
		for(int day = 27; day <= 38; day ++)
			simulateDay(day);
	}
	
	public List<Match> simulateDay(int day) {
		List<Match> result = new ArrayList<>();
		List<Match> matches = getMatchesListByDay(day);
		for(Match match : matches) {
			Match simulatedMatch = simulateMatch(match);
			result.add(simulatedMatch);
		}
		updateTeam(result);
		return result;
	}

	public Match simulateMatch(Match match) {
		simulator.init(match);
		simulator.run();
		
		return simulator.getSimulatedMatch();
	}
	
	private void updateTeam(List<Match> simulatedDay) {
		for(Match match : simulatedDay) {
			Team homeTeam = match.getHomeTeam();
			Team awayTeam = match.getAwayTeam();
			int homeGoals = match.getHomeTeamGoals();
			int awayGoals = match.getAwayTeamGoals();
			
			teamsMap.get(homeTeam.getTeamName()).setHomeMatches(teamsMap.get(homeTeam.getTeamName()).getHomeMatches() + 1);
			teamsMap.get(awayTeam.getTeamName()).setAwayMatches(teamsMap.get(awayTeam.getTeamName()).getAwayMatches() + 1);
			
			teamsMap.get(homeTeam.getTeamName()).setHomeMadeGoals(teamsMap.get(homeTeam.getTeamName()).getHomeMadeGoals() + homeGoals);
			teamsMap.get(homeTeam.getTeamName()).setHomeConcededGoals(teamsMap.get(homeTeam.getTeamName()).getHomeConcededGoals() + awayGoals);
			teamsMap.get(awayTeam.getTeamName()).setAwayMadeGoals(teamsMap.get(awayTeam.getTeamName()).getAwayMadeGoals() + awayGoals);
			teamsMap.get(awayTeam.getTeamName()).setAwayConcededGoals(teamsMap.get(awayTeam.getTeamName()).getAwayConcededGoals() + homeGoals);

			if(homeGoals > awayGoals) {
				teamsMap.get(homeTeam.getTeamName()).setHomeWins(teamsMap.get(homeTeam.getTeamName()).getHomeWins() + 1);
				teamsMap.get(awayTeam.getTeamName()).setAwayLosses(teamsMap.get(awayTeam.getTeamName()).getAwayLosses() + 1);
			} else if(homeGoals < awayGoals) {
				teamsMap.get(homeTeam.getTeamName()).setHomeLosses(teamsMap.get(homeTeam.getTeamName()).getHomeLosses() + 1);
				teamsMap.get(awayTeam.getTeamName()).setAwayWins(teamsMap.get(awayTeam.getTeamName()).getAwayWins() + 1);
			} else {
				teamsMap.get(homeTeam.getTeamName()).setHomeDraws(teamsMap.get(homeTeam.getTeamName()).getHomeDraws() + 1);
				teamsMap.get(awayTeam.getTeamName()).setAwayDraws(teamsMap.get(awayTeam.getTeamName()).getAwayDraws() + 1);
			}
		}
	}
	
	public String printDay(List<Match> simulatedDay) {
		String result = "DAY " + simulatedDay.get(0).getDay() + "\n";
		
		for(Match match : simulatedDay) {
			result += String.format("%-30s %s", 
					match.getHomeTeam().getTeamName() + "-" + match.getAwayTeam().getTeamName(), 
					match.getHomeTeamGoals() + "-" + match.getAwayTeamGoals() + "\n");
		}
		
		return result;
	}
	
	public String printLeagueTable() {
		String result = "LEAGUE STANDINGS\n";
		result += String.format("%-3s %-20s %-5s %-3s %-3s %-3s %-3s %-3s %-3s %-3s %-3s %-3s %-3s %-3s %-3s %-3s %-3s %-3s %-3s %-3s %-3s\n",
				"POS", "TEAM", "PTS", "PM", "W", "D", "L", "GF", "GA", "HPM", "HW", "HD", "HL", "HGF", "HGA", "APM", "AW", "AD", "AL", "AGF", "AGA");
		
		List<Team> teamsList = new ArrayList<>(teamsMap.values());
		Collections.sort(teamsList, new ComparatorOfTeamsForLeagueTable());
		
		int pos = 1;
		for(Team team : teamsList) {
			int pm = team.getHomeMatches() + team.getAwayMatches();
			int w = team.getHomeWins() + team.getAwayWins();
			int d = team.getHomeDraws() + team.getAwayDraws();
			int l = team.getHomeLosses() + team.getAwayLosses();
			int gf = team.getHomeMadeGoals() + team.getAwayMadeGoals();
			int ga = team.getHomeConcededGoals() + team.getAwayConcededGoals();
			int pts = (w * 3) + d;
			
			result += String.format("%-3s %-20s %-5s %-3s %-3s %-3s %-3s %-3s %-3s %-3s %-3s %-3s %-3s %-3s %-3s %-3s %-3s %-3s %-3s %-3s %-3s\n",
					pos, team.getTeamName(), pts, pm, w, d, l, gf, ga, 
					team.getHomeMatches(), team.getHomeWins(), team.getHomeDraws(), team.getHomeLosses(), team.getHomeMadeGoals(), team.getHomeConcededGoals(), 
					team.getAwayMatches(), team.getAwayWins(), team.getAwayDraws(), team.getAwayLosses(), team.getAwayMadeGoals(), team.getAwayConcededGoals());
			pos ++;
		}
		
		return result;
	}
	
	public String printLeagueWinsChance() {
		String result = "LEAGUE WINS CHANCE\n";
		
		List<Team> teams = new ArrayList<>(leagueWinsPerTeamMap.keySet());
		List<Integer> wins = new ArrayList<>(leagueWinsPerTeamMap.values());
		
		for(int i = 0; i < leagueWinsPerTeamMap.size(); i ++) {
			result += String.format("%-20s %.1f%s\n", teams.get(i).getTeamName() + ":", (((double) wins.get(i) / multipleSimulationNumber) * 100), "%");
		}

		return result;
	}

	public List<Team> getTeamsList() {
		return new ArrayList<>(teamsMap.values());
	}

	public List<Match> getMatchesList() {
		return matchesList;
	}
	
	public List<Match> getMatchesListByTeam(Team team) {
		List<Match> result = new ArrayList<>();
		for(Match match : matchesList) {
			if(match.getHomeTeam().equals(team) || match.getAwayTeam().equals(team))
				result.add(match);
		}
		return result;
	}

	public List<Match> getMatchesListByDay(int day) {
		List<Match> result = new ArrayList<>();
		for(Match match : matchesList) {
			if(match.getDay() == day)
				result.add(match);
		}
		return result;
	}
	
	public double getRedCardMultiplier() {
		return redCardMultiplier;
	}

	public void setRedCardMultiplier(double redCardMultiplier) {
		this.redCardMultiplier = redCardMultiplier;
	}

}
