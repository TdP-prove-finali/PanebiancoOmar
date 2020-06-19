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
	
	private boolean homeFactor;
	private boolean pointsFactorActivated;
	private double pointsFactor;
	private double redCardMultiplier;
	
	private boolean standardSimulation;
	private boolean quickSimulation;
	private boolean multipleQuickSimulation;
	private int multipleSimulationNumber;
	
	private int firstDayToSimulate;
	private int nextDayToSimulate;
	private int lastDaytoSimulate;
	
	private Map<Team, Integer> leagueWinsPerTeamMap;
	private Map<Team, Integer> championsLeagueQualificationsPerTeamMap;
	private Map<Team, Integer> europaLeagueQualificationsPerTeamMap;
	private Map<Team, Integer> relegationsPerTeamMap;
	
	private SerieADao dao;
	private Map<String, Team> teamsMap;
	private List<Match> matchesList;
	private Simulator simulator;
	
	public Model(boolean homeFactor, boolean pointsFactorActivated, double pointsFactor, double redCardMultiplier, 
			boolean standardSimulation, boolean quickSimulation, boolean multipleQuickSimulation, int multipleSimulationNumber) {
		this.homeFactor = homeFactor;
		this.pointsFactorActivated = pointsFactorActivated;
		this.pointsFactor = pointsFactor;
		this.redCardMultiplier = redCardMultiplier;
		
		this.standardSimulation = standardSimulation;
		this.quickSimulation = quickSimulation;
		this.multipleQuickSimulation = multipleQuickSimulation;
		this.multipleSimulationNumber = multipleSimulationNumber;
		
		this.firstDayToSimulate = 25;
		this.nextDayToSimulate = 27;
		this.lastDaytoSimulate = 38;
		
		this.leagueWinsPerTeamMap = new HashMap<>();
		this.championsLeagueQualificationsPerTeamMap = new HashMap<>();
		this.europaLeagueQualificationsPerTeamMap = new HashMap<>();
		this.relegationsPerTeamMap = new HashMap<>();
		
		this.dao = new SerieADao();
		this.teamsMap = dao.getTeamsMap();
		this.matchesList = dao.getMatchesList();
		this.simulator = new Simulator(this);
	}
	
	public Map<Integer, List<Match>> startSimulation() {
		Map<Integer, List<Match>> result = new HashMap<>();
		List<Match> simulatedDay = new ArrayList<>();
		
		if(standardSimulation) {
			simulatedDay = simulateDay(firstDayToSimulate);
			result.put(firstDayToSimulate, simulatedDay);
			
		} else if(quickSimulation) {
			simulatedDay = simulateDay(firstDayToSimulate);
			result.put(firstDayToSimulate, simulatedDay);
			for(int day = nextDayToSimulate; day <= lastDaytoSimulate; day ++) {
				simulatedDay = simulateDay(day);
				result.put(day, simulatedDay);
			}
			
		} else {
			for(int i = 0; i < multipleSimulationNumber; i ++) {
				simulateDay(firstDayToSimulate);
				for(int day = nextDayToSimulate; day <= lastDaytoSimulate; day ++) 
					simulateDay(day);
				
				/* LEAGUE STATS */
				List<Team> teamsList = new ArrayList<>(teamsMap.values());
				Collections.sort(teamsList, new ComparatorOfTeamsForLeagueTable());
				
				// LEAGUE WIN
				Team firstPosition = teamsList.get(0);
				if(leagueWinsPerTeamMap.keySet().contains(firstPosition))
					leagueWinsPerTeamMap.replace(firstPosition, leagueWinsPerTeamMap.get(firstPosition) + 1);
				else
					leagueWinsPerTeamMap.put(firstPosition, 1);
				
				// CHAMPIONS LEAGUE QUALIFICATION
				if(championsLeagueQualificationsPerTeamMap.keySet().contains(firstPosition))
					championsLeagueQualificationsPerTeamMap.replace(firstPosition, championsLeagueQualificationsPerTeamMap.get(firstPosition) + 1);
				else
					championsLeagueQualificationsPerTeamMap.put(firstPosition, 1);
				
				Team secondPosition = teamsList.get(1);
				if(championsLeagueQualificationsPerTeamMap.keySet().contains(secondPosition))
					championsLeagueQualificationsPerTeamMap.replace(secondPosition, championsLeagueQualificationsPerTeamMap.get(secondPosition) + 1);
				else
					championsLeagueQualificationsPerTeamMap.put(secondPosition, 1);
				
				Team thirdPosition = teamsList.get(2);
				if(championsLeagueQualificationsPerTeamMap.keySet().contains(thirdPosition))
					championsLeagueQualificationsPerTeamMap.replace(thirdPosition, championsLeagueQualificationsPerTeamMap.get(thirdPosition) + 1);
				else
					championsLeagueQualificationsPerTeamMap.put(thirdPosition, 1);
				
				Team fourthPosition = teamsList.get(3);
				if(championsLeagueQualificationsPerTeamMap.keySet().contains(fourthPosition))
					championsLeagueQualificationsPerTeamMap.replace(fourthPosition, championsLeagueQualificationsPerTeamMap.get(fourthPosition) + 1);
				else
					championsLeagueQualificationsPerTeamMap.put(fourthPosition, 1);
				
				// EUROPA LEAGUE QUALIFICATION
				Team fifthPosition = teamsList.get(4);
				if(europaLeagueQualificationsPerTeamMap.keySet().contains(fifthPosition))
					europaLeagueQualificationsPerTeamMap.replace(fifthPosition, europaLeagueQualificationsPerTeamMap.get(fifthPosition) + 1);
				else
					europaLeagueQualificationsPerTeamMap.put(fifthPosition, 1);
				
				Team sixthPosition = teamsList.get(5);
				if(europaLeagueQualificationsPerTeamMap.keySet().contains(sixthPosition))
					europaLeagueQualificationsPerTeamMap.replace(sixthPosition, europaLeagueQualificationsPerTeamMap.get(sixthPosition) + 1);
				else
					europaLeagueQualificationsPerTeamMap.put(sixthPosition, 1);
				
				// RELEGATION
				Team eighteenthPosition = teamsList.get(17);
				if(relegationsPerTeamMap.keySet().contains(eighteenthPosition))
					relegationsPerTeamMap.replace(eighteenthPosition, relegationsPerTeamMap.get(eighteenthPosition) + 1);
				else
					relegationsPerTeamMap.put(eighteenthPosition, 1);
				
				Team nineteenthPosition = teamsList.get(18);
				if(relegationsPerTeamMap.keySet().contains(nineteenthPosition))
					relegationsPerTeamMap.replace(nineteenthPosition, relegationsPerTeamMap.get(nineteenthPosition) + 1);
				else
					relegationsPerTeamMap.put(nineteenthPosition, 1);
				
				Team tweniethPosition = teamsList.get(19);
				if(relegationsPerTeamMap.keySet().contains(tweniethPosition))
					relegationsPerTeamMap.replace(tweniethPosition, relegationsPerTeamMap.get(tweniethPosition) + 1);
				else
					relegationsPerTeamMap.put(tweniethPosition, 1);
				
				/* RESET */
				this.teamsMap = dao.getTeamsMap();
				this.matchesList = dao.getMatchesList();
			}
		}
		
		return result;
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
	
	public String printLeagueWinChance() {
		String result = "LEAGUE WIN CHANCE\n";
		
		List<Team> teams = new ArrayList<>(leagueWinsPerTeamMap.keySet());
		List<Integer> wins = new ArrayList<>(leagueWinsPerTeamMap.values());
		
		for(int i = 0; i < leagueWinsPerTeamMap.size(); i ++) {
			result += String.format("%-20s %.1f%s\n", teams.get(i).getTeamName() + ":", (((double) wins.get(i) / multipleSimulationNumber) * 100), "%");
		}

		return result;
	}
	
	public String printChampionsLeagueQualificationChance() {
		String result = "CHAMPIONS LEAGUE QUALIFICATION CHANCE\n";
		
		List<Team> teams = new ArrayList<>(championsLeagueQualificationsPerTeamMap.keySet());
		List<Integer> wins = new ArrayList<>(championsLeagueQualificationsPerTeamMap.values());
		
		for(int i = 0; i < championsLeagueQualificationsPerTeamMap.size(); i ++) {
			result += String.format("%-20s %.1f%s\n", teams.get(i).getTeamName() + ":", (((double) wins.get(i) / multipleSimulationNumber) * 100), "%");
		}

		return result;
	}
	
	public String printEuropaLeagueQualificationChance() {
		String result = "EUROPA LEAGUE QUALIFICATION CHANCE\n";
		
		List<Team> teams = new ArrayList<>(europaLeagueQualificationsPerTeamMap.keySet());
		List<Integer> wins = new ArrayList<>(europaLeagueQualificationsPerTeamMap.values());
		
		for(int i = 0; i < europaLeagueQualificationsPerTeamMap.size(); i ++) {
			result += String.format("%-20s %.1f%s\n", teams.get(i).getTeamName() + ":", (((double) wins.get(i) / multipleSimulationNumber) * 100), "%");
		}

		return result;
	}
	
	public String printRelegationChance() {
		String result = "RELEGATION CHANCE\n";
		
		List<Team> teams = new ArrayList<>(relegationsPerTeamMap.keySet());
		List<Integer> wins = new ArrayList<>(relegationsPerTeamMap.values());
		
		for(int i = 0; i < relegationsPerTeamMap.size(); i ++) {
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
	
	public boolean isHomeFactor() {
		return homeFactor;
	}

	public void setHomeFactor(boolean homeFactor) {
		this.homeFactor = homeFactor;
	}

	public boolean isPointsFactorActivated() {
		return pointsFactorActivated;
	}

	public void setPointsFactorActivated(boolean pointsFactorActivated) {
		this.pointsFactorActivated = pointsFactorActivated;
	}

	public double getPointsFactor() {
		return pointsFactor;
	}

	public void setPointsFactor(double pointsFactor) {
		this.pointsFactor = pointsFactor;
	}

	public double getRedCardMultiplier() {
		return redCardMultiplier;
	}

	public void setRedCardMultiplier(double redCardMultiplier) {
		this.redCardMultiplier = redCardMultiplier;
	}

	public int getFirstDayToSimulate() {
		return firstDayToSimulate;
	}

	public void setFirstDayToSimulate(int firstDayToSimulate) {
		this.firstDayToSimulate = firstDayToSimulate;
	}

	public int getNextDayToSimulate() {
		return nextDayToSimulate;
	}

	public void setNextDayToSimulate(int nextDayToSimulate) {
		this.nextDayToSimulate = nextDayToSimulate;
	}

	public int getLastDaytoSimulate() {
		return lastDaytoSimulate;
	}

	public void setLastDaytoSimulate(int lastDaytoSimulate) {
		this.lastDaytoSimulate = lastDaytoSimulate;
	}

	public boolean isStandardSimulation() {
		return standardSimulation;
	}

	public void setStandardSimulation(boolean standardSimulation) {
		this.standardSimulation = standardSimulation;
	}

	public boolean isQuickSimulation() {
		return quickSimulation;
	}

	public void setQuickSimulation(boolean quickSimulation) {
		this.quickSimulation = quickSimulation;
	}

	public boolean isMultipleQuickSimulation() {
		return multipleQuickSimulation;
	}

	public void setMultipleQuickSimulation(boolean multipleQuickSimulation) {
		this.multipleQuickSimulation = multipleQuickSimulation;
	}

	public int getMultipleSimulationNumber() {
		return multipleSimulationNumber;
	}

	public void setMultipleSimulationNumber(int multipleSimulationNumber) {
		this.multipleSimulationNumber = multipleSimulationNumber;
	}

	public List<Team> getOrderedTeamsList() {
		List<Team> orderedTeamsList = new ArrayList<>(this.teamsMap.values());
		Collections.sort(orderedTeamsList, new ComparatorOfTeamsForLeagueTable());
		return orderedTeamsList;
	}

}
