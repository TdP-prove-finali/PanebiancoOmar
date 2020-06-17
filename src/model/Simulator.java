package model;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;

import model.Event.EventType;

public class Simulator {
	
	public class EventPriority implements Comparator<Event> {
		@Override
		public int compare(Event e1, Event e2) {
			Random random = new Random();
			if(random.nextDouble() < 0.5)
				return -1;
			else
				return 1;
		}
	}
	
	private Model model;
	private PriorityQueue<Event> queue;
	private Random random;
	
	private double homeShots, awayShots;
	private double homeFouls, awayFouls;
	
	double redCardMultiplier;
	double homeRedCardMultiplier;
	double awayRedCarMultiplier;
	double randomDouble;
	
	private Match simulatedMatch;
	private int homeGoals, awayGoals;
	
	public Simulator(Model model) {
		this.model = model;
		this.queue = new PriorityQueue<>(new EventPriority());
		this.random = new Random();
	}
	
	public void init(Match match) {
		queue.clear();
		match.setHomeTeamGoals(-1);
		match.setAwayTeamGoals(-1);
		System.out.println("");
		System.out.println("*****************************************************************************");
		System.out.println(match);
		
		redCardMultiplier = model.getRedCardMultiplier();
		homeRedCardMultiplier = 1;
		awayRedCarMultiplier = 1;
		
		simulatedMatch = match;
		homeGoals = 0;
		awayGoals = 0;
		
		homeShots = (match.getHomeTeam().getHomeStats().getTotalMadeShots() + match.getAwayTeam().getAwayStats().getTotalConcededShots()) / 2;
		awayShots = (match.getAwayTeam().getAwayStats().getTotalMadeShots() + match.getHomeTeam().getHomeStats().getTotalConcededShots()) / 2;
		homeFouls = (match.getHomeTeam().getHomeStats().getCommittedFouls() + match.getAwayTeam().getAwayStats().getTakenFouls()) / 2;
		awayFouls = (match.getAwayTeam().getAwayStats().getCommittedFouls() + match.getHomeTeam().getHomeStats().getTakenFouls()) / 2;
		
		for(int i = 0; i < homeShots; i ++) {
			double homeOpenPlayMadeShots = match.getHomeTeam().getHomeStats().getOpenPlayMadeShots();
			double awayOpenPlayConcededShots = match.getAwayTeam().getAwayStats().getOpenPlayConcededShots();
			double mixedOpenPlayShots = (homeOpenPlayMadeShots + awayOpenPlayConcededShots) / 2;
			double openPlayShotProbability = mixedOpenPlayShots / homeShots;
			
			double homeFreeKickMadeShots = match.getHomeTeam().getHomeStats().getFreeKickMadeShots();
			double awayFreeKickConcededShots = match.getAwayTeam().getAwayStats().getFreeKickConcededShots();
			double mixedFreeKickShots = (homeFreeKickMadeShots + awayFreeKickConcededShots) / 2;
			double openFreeKickProbability = mixedFreeKickShots / homeShots;
			
			double homePenaltyMadeShots = match.getHomeTeam().getHomeStats().getPenaltyMadeShots();
			double awayPenaltyConcededShots = match.getAwayTeam().getAwayStats().getPenaltyConcededShots();
			double mixedPenaltyShots = (homePenaltyMadeShots + awayPenaltyConcededShots) / 2;
			
			randomDouble = random.nextDouble();
			
			if(randomDouble < openPlayShotProbability) {
				double homeMadeGoalProbability = match.getHomeTeam().getHomeStats().getOpenPlayMadeGoals() / mixedOpenPlayShots;
				double awayConcededGoalProbability = match.getAwayTeam().getAwayStats().getOpenPlayConcededGoals() / mixedOpenPlayShots;
				double successRate = (homeMadeGoalProbability + awayConcededGoalProbability) / 2;
				
				queue.add(new Event(EventType.HOME_OPEN_PLAY, successRate));
				
			} else if(randomDouble < (openPlayShotProbability + openFreeKickProbability)) {
				double homeMadeGoalProbability = match.getHomeTeam().getHomeStats().getFreeKickMadeGoals() / mixedFreeKickShots;
				double awayConcededGoalProbability = match.getAwayTeam().getAwayStats().getFreeKickConcededGoals() / mixedFreeKickShots;
				double successRate = (homeMadeGoalProbability + awayConcededGoalProbability) / 2;
				
				queue.add(new Event(EventType.HOME_FREE_KICK, successRate));
				
			} else {
				double homeMadeGoalProbability = match.getHomeTeam().getHomeStats().getPenaltyMadeGoals() / mixedPenaltyShots;
				double awayConcededGoalProbability = match.getAwayTeam().getAwayStats().getPenaltyConcededGoals() / mixedPenaltyShots;
				double successRate = (homeMadeGoalProbability + awayConcededGoalProbability) / 2;
				
				queue.add(new Event(EventType.HOME_PENALTY, successRate));
			}
		}
		
		for(int i = 0; i < awayShots; i ++) {
			double awayOpenPlayMadeShots = match.getAwayTeam().getAwayStats().getOpenPlayMadeShots();
			double homeOpenPlayConcededShots = match.getHomeTeam().getHomeStats().getOpenPlayConcededShots();
			double mixedOpenPlayShots = (awayOpenPlayMadeShots + homeOpenPlayConcededShots) / 2;
			double openPlayShotProbability = mixedOpenPlayShots / awayShots;
			
			double awayFreeKickMadeShots = match.getAwayTeam().getAwayStats().getFreeKickMadeShots();
			double homeFreeKickConcededShots = match.getHomeTeam().getHomeStats().getFreeKickConcededShots();
			double mixedFreeKickShots = (awayFreeKickMadeShots + homeFreeKickConcededShots) / 2;
			double openFreeKickProbability = mixedFreeKickShots / awayShots;
			
			double awayPenaltyMadeShots = match.getAwayTeam().getAwayStats().getPenaltyMadeShots();
			double homePenaltyConcededShots = match.getHomeTeam().getHomeStats().getPenaltyConcededShots();
			double mixedPenaltyShots = (awayPenaltyMadeShots + homePenaltyConcededShots) / 2;
			
			randomDouble = random.nextDouble();
			
			if(randomDouble < openPlayShotProbability) {
				double awayMadeGoalProbability = match.getAwayTeam().getAwayStats().getOpenPlayMadeGoals() / mixedOpenPlayShots;
				double homeConcededGoalProbability = match.getHomeTeam().getHomeStats().getOpenPlayConcededGoals() / mixedOpenPlayShots;
				double successRate = (awayMadeGoalProbability + homeConcededGoalProbability) / 2;
				
				queue.add(new Event(EventType.AWAY_OPEN_PLAY, successRate));
				
			} else if(randomDouble < (openPlayShotProbability + openFreeKickProbability)) {
				double awayMadeGoalProbability = match.getAwayTeam().getAwayStats().getFreeKickMadeGoals() / mixedFreeKickShots;
				double homeConcededGoalProbability = match.getHomeTeam().getHomeStats().getFreeKickConcededGoals() / mixedFreeKickShots;
				double successRate = (awayMadeGoalProbability + homeConcededGoalProbability) / 2;
				
				queue.add(new Event(EventType.AWAY_FREE_KICK, successRate));
				
			} else {
				double awayMadeGoalProbability = match.getAwayTeam().getAwayStats().getPenaltyMadeGoals() / mixedPenaltyShots;
				double homeConcededGoalProbability = match.getHomeTeam().getHomeStats().getPenaltyConcededGoals() / mixedPenaltyShots;
				double successRate = (awayMadeGoalProbability + homeConcededGoalProbability) / 2;
				
				queue.add(new Event(EventType.AWAY_PENALTY, successRate));
			}
		}
		
		double homeConcededOwnGoalProbability = match.getHomeTeam().getHomeStats().getConcededOwnGoalProbability();
		double awayMadeOwnGoalProbability = match.getAwayTeam().getAwayStats().getMadeOwnGoalProbability();
		double homeOwnGoalsuccessRate = (homeConcededOwnGoalProbability + awayMadeOwnGoalProbability) / 2;
		
		queue.add(new Event(EventType.HOME_OWN_GOAL, homeOwnGoalsuccessRate));
		
		double awayConcededOwnGoalProbability = match.getAwayTeam().getAwayStats().getConcededOwnGoalProbability();
		double homeMadeOwnGoalProbability = match.getHomeTeam().getHomeStats().getMadeOwnGoalProbability();
		double awayOwnGoalsuccessRate = (awayConcededOwnGoalProbability + homeMadeOwnGoalProbability) / 2;
		
		queue.add(new Event(EventType.AWAY_OWN_GOAL, awayOwnGoalsuccessRate));
		
		for(int i = 0; i < homeFouls; i ++) {
			double successRate = match.getHomeTeam().getHomeStats().getRedCardProbability();
			
			queue.add(new Event(EventType.HOME_FOUL, successRate));
		}
		
		for(int i = 0; i < awayFouls; i ++) {
			double successRate = match.getAwayTeam().getAwayStats().getRedCardProbability();
			
			queue.add(new Event(EventType.AWAY_FOUL, successRate));
		}
	}
	
	public void run() {
		System.out.println("Simulation started...");
		System.out.println("");
		
		while(!queue.isEmpty()) {
			Event event = queue.poll();
			System.out.print(event + ": ");
			
			randomDouble = random.nextDouble();
			switch(event.getType()) {
				case HOME_OPEN_PLAY:
					if(randomDouble < (event.getSuccessRate() * homeRedCardMultiplier)) {
						homeGoals ++;
						System.out.println("GOAL");
					} else
						System.out.println("NO GOAL");
					
					break;
					
				case AWAY_OPEN_PLAY:
					if(randomDouble < (event.getSuccessRate() * awayRedCarMultiplier)) {
						awayGoals ++;
						System.out.println("GOAL");
					} else
						System.out.println("NO GOAL");
					
					break;
					
				case HOME_FREE_KICK:
					if(randomDouble < (event.getSuccessRate() * homeRedCardMultiplier)) {
						homeGoals ++;
						System.out.println("GOAL");
					} else
						System.out.println("NO GOAL");
					
					break;
					
				case AWAY_FREE_KICK:
					if(randomDouble < (event.getSuccessRate() * awayRedCarMultiplier)) {
						awayGoals ++;
						System.out.println("GOAL");
					} else
						System.out.println("NO GOAL");
					
					break;
					
				case HOME_PENALTY:
					if(randomDouble < (event.getSuccessRate() * homeRedCardMultiplier)) {
						homeGoals ++;
						System.out.println("GOAL");
					} else
						System.out.println("NO GOAL");
					
					break;
					
				case AWAY_PENALTY:
					if(randomDouble < (event.getSuccessRate() * awayRedCarMultiplier)) {
						awayGoals ++;
						System.out.println("GOAL");
					} else
						System.out.println("NO GOAL");
					
					break;
					
				case HOME_OWN_GOAL:
					if(randomDouble < (event.getSuccessRate() * awayRedCarMultiplier)) {
						awayGoals ++;
						System.out.println("GOAL");
					} else
						System.out.println("NO GOAL");
					
					break;
					
				case AWAY_OWN_GOAL:
					if(randomDouble < (event.getSuccessRate() * homeRedCardMultiplier)) {
						homeGoals ++;
						System.out.println("GOAL");
					} else
						System.out.println("NO GOAL");
					
					break;
					
				case HOME_FOUL:
					if(randomDouble < event.getSuccessRate()) {
						awayRedCarMultiplier += redCardMultiplier;
						homeRedCardMultiplier -= redCardMultiplier;
						System.out.println("RED CARD");
					} else
						System.out.println("NO RED CARD");
					
					break;
					
				case AWAY_FOUL:
					if(randomDouble < event.getSuccessRate()) {
						homeRedCardMultiplier += redCardMultiplier;
						awayRedCarMultiplier -= redCardMultiplier;
						System.out.println("RED CARD");
					} else
						System.out.println("NO RED CARD");
					
					break;
			}
		}
		
		simulatedMatch.setHomeTeamGoals(homeGoals);
		simulatedMatch.setAwayTeamGoals(awayGoals);
		
		System.out.println("");
		System.out.println("Simulation ended.");
		System.out.println(simulatedMatch);
	}

	public int getHomeGoals() {
		return homeGoals;
	}

	public int getAwayGoals() {
		return awayGoals;
	}

	public Match getSimulatedMatch() {
		return simulatedMatch;
	}

}
