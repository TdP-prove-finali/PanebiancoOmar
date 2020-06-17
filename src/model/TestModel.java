package model;

import java.util.List;

public class TestModel {

	public static void main(String[] args) {
		
		double redCardMultiplier = 0.1;
		
		Model model = new Model(redCardMultiplier);
		List<Team> teamList = model.getTeamsList();
		Team team = teamList.get(6);
		List<Match> matchesList = model.getMatchesListByTeam(team);
		
		int wins = 0;
		int draws = 0;
		int losses = 0;
		for(Match match : matchesList) {
			Match simulatedMatch = model.simulateMatch(match);
			
			if(simulatedMatch.getHomeTeam().equals(team)) {
				if(simulatedMatch.getHomeTeamGoals() > simulatedMatch.getAwayTeamGoals())
					wins ++;
				else if (simulatedMatch.getHomeTeamGoals() < simulatedMatch.getAwayTeamGoals())
					losses ++;
				else
					draws ++;
				
			} else {
				if(simulatedMatch.getHomeTeamGoals() < simulatedMatch.getAwayTeamGoals())
					wins ++;
				else if (simulatedMatch.getHomeTeamGoals() > simulatedMatch.getAwayTeamGoals())
					losses ++;
				else
					draws ++;
			}
		}
		
		System.out.println("");
		System.out.println("*****************************************************************************");
		System.out.println("WINS: " + wins);
		System.out.println("DRAWS: " + draws);
		System.out.println("LOSSES: " + losses);
		
	}

}
