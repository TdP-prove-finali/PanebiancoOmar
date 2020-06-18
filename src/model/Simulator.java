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
	double pointsFactor;
	
	private Match simulatedMatch;
	private int homeGoals, awayGoals;
	
	public Simulator(Model model) {
		this.model = model;
		this.queue = new PriorityQueue<>(new EventPriority());
		this.random = new Random();
	}
	
	public void init(Match match) {
		queue.clear();
		
		redCardMultiplier = model.getRedCardMultiplier();
		homeRedCardMultiplier = 1;
		awayRedCarMultiplier = 1;
		
		if(model.isPointsFactorActivated()) {
			int homePoints = ((match.getHomeTeam().getHomeWins() + match.getHomeTeam().getAwayWins()) * 3) + (match.getHomeTeam().getHomeDraws() + match.getHomeTeam().getAwayDraws());
			int awayPoints = ((match.getAwayTeam().getHomeWins() + match.getAwayTeam().getAwayWins()) * 3) + (match.getAwayTeam().getHomeDraws() + match.getAwayTeam().getAwayDraws());
			pointsFactor = (double) (homePoints - awayPoints) / (100 / model.getPointsFactor());
		} else
			pointsFactor = 0;
		
		simulatedMatch = match;
		homeGoals = 0;
		awayGoals = 0;
		
		if(model.isHomeFactor()) {
			homeShots = (match.getHomeTeam().getHomeStats().getTotalMadeShots() + match.getAwayTeam().getAwayStats().getTotalConcededShots()) / 2;
			awayShots = (match.getAwayTeam().getAwayStats().getTotalMadeShots() + match.getHomeTeam().getHomeStats().getTotalConcededShots()) / 2;
			homeFouls = (match.getHomeTeam().getHomeStats().getCommittedFouls() + match.getAwayTeam().getAwayStats().getTakenFouls()) / 2;
			awayFouls = (match.getAwayTeam().getAwayStats().getCommittedFouls() + match.getHomeTeam().getHomeStats().getTakenFouls()) / 2;
			
			for(int i = 0; i < (homeShots * (1 + pointsFactor)); i ++) {
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
					
					queue.add(new Event(EventType.HOME_OPEN_PLAY, (successRate * (1 + pointsFactor))));
					
				} else if(randomDouble < (openPlayShotProbability + openFreeKickProbability)) {
					double homeMadeGoalProbability = match.getHomeTeam().getHomeStats().getFreeKickMadeGoals() / mixedFreeKickShots;
					double awayConcededGoalProbability = match.getAwayTeam().getAwayStats().getFreeKickConcededGoals() / mixedFreeKickShots;
					double successRate = (homeMadeGoalProbability + awayConcededGoalProbability) / 2;
					
					queue.add(new Event(EventType.HOME_FREE_KICK, (successRate * (1 + pointsFactor))));
					
				} else {
					double homeMadeGoalProbability = match.getHomeTeam().getHomeStats().getPenaltyMadeGoals() / mixedPenaltyShots;
					double awayConcededGoalProbability = match.getAwayTeam().getAwayStats().getPenaltyConcededGoals() / mixedPenaltyShots;
					double successRate = (homeMadeGoalProbability + awayConcededGoalProbability) / 2;
					
					queue.add(new Event(EventType.HOME_PENALTY, successRate));
				}
			}
			
			for(int i = 0; i < (awayShots * (1 - pointsFactor)); i ++) {
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
					
					queue.add(new Event(EventType.AWAY_OPEN_PLAY, (successRate * (1 - pointsFactor))));
					
				} else if(randomDouble < (openPlayShotProbability + openFreeKickProbability)) {
					double awayMadeGoalProbability = match.getAwayTeam().getAwayStats().getFreeKickMadeGoals() / mixedFreeKickShots;
					double homeConcededGoalProbability = match.getHomeTeam().getHomeStats().getFreeKickConcededGoals() / mixedFreeKickShots;
					double successRate = (awayMadeGoalProbability + homeConcededGoalProbability) / 2;
					
					queue.add(new Event(EventType.AWAY_FREE_KICK, (successRate * (1 - pointsFactor))));
					
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
			
			queue.add(new Event(EventType.HOME_OWN_GOAL, (homeOwnGoalsuccessRate * (1 - pointsFactor))));
			
			double awayConcededOwnGoalProbability = match.getAwayTeam().getAwayStats().getConcededOwnGoalProbability();
			double homeMadeOwnGoalProbability = match.getHomeTeam().getHomeStats().getMadeOwnGoalProbability();
			double awayOwnGoalsuccessRate = (awayConcededOwnGoalProbability + homeMadeOwnGoalProbability) / 2;
			
			queue.add(new Event(EventType.AWAY_OWN_GOAL, (awayOwnGoalsuccessRate * (1 + pointsFactor))));
			
			for(int i = 0; i < (homeFouls * (1 - pointsFactor)); i ++) {
				double successRate = match.getHomeTeam().getHomeStats().getRedCardProbability();
				
				queue.add(new Event(EventType.HOME_FOUL, (successRate * (1 - pointsFactor))));
			}
			
			for(int i = 0; i < (awayFouls * (1 + pointsFactor)); i ++) {
				double successRate = match.getAwayTeam().getAwayStats().getRedCardProbability();
				
				queue.add(new Event(EventType.AWAY_FOUL, (successRate * (1 + pointsFactor))));
			}
			
		} else {
			homeShots = (((match.getHomeTeam().getHomeStats().getTotalMadeShots() + match.getHomeTeam().getAwayStats().getTotalMadeShots()) / 2)
					+ ((match.getAwayTeam().getAwayStats().getTotalConcededShots() + match.getAwayTeam().getHomeStats().getTotalConcededShots()) / 2)) / 2;
			awayShots = (((match.getAwayTeam().getAwayStats().getTotalMadeShots() + match.getAwayTeam().getHomeStats().getTotalMadeShots()) / 2)
					+ ((match.getHomeTeam().getHomeStats().getTotalConcededShots() + match.getHomeTeam().getAwayStats().getTotalConcededShots()) / 2)) / 2;
			homeFouls = (((match.getHomeTeam().getHomeStats().getCommittedFouls() + match.getHomeTeam().getAwayStats().getCommittedFouls()) / 2)
					+ ((match.getAwayTeam().getAwayStats().getTakenFouls() + match.getAwayTeam().getHomeStats().getTakenFouls()) / 2)) / 2;
			awayFouls = (((match.getAwayTeam().getAwayStats().getCommittedFouls() + match.getAwayTeam().getHomeStats().getCommittedFouls()) / 2)
					+ ((match.getHomeTeam().getHomeStats().getTakenFouls() + match.getHomeTeam().getAwayStats().getTakenFouls()) / 2)) / 2;
			
			for(int i = 0; i < (homeShots * (1 + pointsFactor)); i ++) {
				double homeOpenPlayMadeShots = (match.getHomeTeam().getHomeStats().getOpenPlayMadeShots() 
						+ match.getHomeTeam().getAwayStats().getOpenPlayMadeShots()) / 2;
				double awayOpenPlayConcededShots = (match.getAwayTeam().getAwayStats().getOpenPlayConcededShots() 
						+ match.getAwayTeam().getHomeStats().getOpenPlayConcededShots()) / 2;
				double mixedOpenPlayShots = (homeOpenPlayMadeShots + awayOpenPlayConcededShots) / 2;
				double openPlayShotProbability = mixedOpenPlayShots / homeShots;
				
				double homeFreeKickMadeShots = (match.getHomeTeam().getHomeStats().getFreeKickMadeShots() 
						+ match.getHomeTeam().getAwayStats().getFreeKickMadeShots()) / 2;
				double awayFreeKickConcededShots = (match.getAwayTeam().getAwayStats().getFreeKickConcededShots() 
						+ match.getAwayTeam().getHomeStats().getFreeKickConcededShots()) / 2;
				double mixedFreeKickShots = (homeFreeKickMadeShots + awayFreeKickConcededShots) / 2;
				double openFreeKickProbability = mixedFreeKickShots / homeShots;
				
				double homePenaltyMadeShots = (match.getHomeTeam().getHomeStats().getPenaltyMadeShots() 
						+ match.getHomeTeam().getAwayStats().getPenaltyMadeShots()) / 2;
				double awayPenaltyConcededShots = (match.getAwayTeam().getAwayStats().getPenaltyConcededShots() 
						+ match.getAwayTeam().getHomeStats().getPenaltyConcededShots()) / 2;
				double mixedPenaltyShots = (homePenaltyMadeShots + awayPenaltyConcededShots) / 2;
				
				randomDouble = random.nextDouble();
				
				if(randomDouble < openPlayShotProbability) {
					double homeMadeGoalProbability = ((match.getHomeTeam().getHomeStats().getOpenPlayMadeGoals()
							+ match.getHomeTeam().getAwayStats().getOpenPlayMadeGoals()) / 2) / mixedOpenPlayShots;
					double awayConcededGoalProbability = ((match.getAwayTeam().getAwayStats().getOpenPlayConcededGoals()
							+ match.getAwayTeam().getHomeStats().getOpenPlayConcededGoals()) / 2) / mixedOpenPlayShots;
					double successRate = (homeMadeGoalProbability + awayConcededGoalProbability) / 2;
					
					queue.add(new Event(EventType.HOME_OPEN_PLAY, (successRate * (1 + pointsFactor))));
					
				} else if(randomDouble < (openPlayShotProbability + openFreeKickProbability)) {
					double homeMadeGoalProbability = ((match.getHomeTeam().getHomeStats().getFreeKickMadeGoals()
							+ match.getHomeTeam().getAwayStats().getFreeKickMadeGoals()) / 2) / mixedFreeKickShots;
					double awayConcededGoalProbability = ((match.getAwayTeam().getAwayStats().getFreeKickConcededGoals()
							+ match.getAwayTeam().getHomeStats().getFreeKickConcededGoals()) / 2) / mixedFreeKickShots;
					double successRate = (homeMadeGoalProbability + awayConcededGoalProbability) / 2;
					
					queue.add(new Event(EventType.HOME_FREE_KICK, (successRate * (1 + pointsFactor))));
					
				} else {
					double homeMadeGoalProbability = ((match.getHomeTeam().getHomeStats().getPenaltyMadeGoals()
							+ match.getHomeTeam().getAwayStats().getPenaltyMadeGoals()) / 2) / mixedPenaltyShots;
					double awayConcededGoalProbability = ((match.getAwayTeam().getAwayStats().getPenaltyConcededGoals()
							+ match.getAwayTeam().getAwayStats().getPenaltyConcededGoals()) / 2) / mixedPenaltyShots;
					double successRate = (homeMadeGoalProbability + awayConcededGoalProbability) / 2;
					
					queue.add(new Event(EventType.HOME_PENALTY, successRate));
				}
			}
			
			for(int i = 0; i < (awayShots * (1 - pointsFactor)); i ++) {
				double awayOpenPlayMadeShots = (match.getAwayTeam().getAwayStats().getOpenPlayMadeShots()
						+ match.getAwayTeam().getHomeStats().getOpenPlayMadeShots()) / 2;
				double homeOpenPlayConcededShots = (match.getHomeTeam().getHomeStats().getOpenPlayConcededShots()
						+ match.getHomeTeam().getAwayStats().getOpenPlayConcededShots()) / 2;
				double mixedOpenPlayShots = (awayOpenPlayMadeShots + homeOpenPlayConcededShots) / 2;
				double openPlayShotProbability = mixedOpenPlayShots / awayShots;
				
				double awayFreeKickMadeShots = (match.getAwayTeam().getAwayStats().getFreeKickMadeShots()
						+ match.getAwayTeam().getHomeStats().getFreeKickMadeShots()) / 2;
				double homeFreeKickConcededShots = (match.getHomeTeam().getHomeStats().getFreeKickConcededShots()
						+ match.getHomeTeam().getAwayStats().getFreeKickConcededShots()) / 2;
				double mixedFreeKickShots = (awayFreeKickMadeShots + homeFreeKickConcededShots) / 2;
				double openFreeKickProbability = mixedFreeKickShots / awayShots;
				
				double awayPenaltyMadeShots = (match.getAwayTeam().getAwayStats().getPenaltyMadeShots()
						+ match.getAwayTeam().getHomeStats().getPenaltyMadeShots()) / 2;
				double homePenaltyConcededShots = (match.getHomeTeam().getHomeStats().getPenaltyConcededShots()
						+ match.getHomeTeam().getAwayStats().getPenaltyConcededShots()) / 2;
				double mixedPenaltyShots = (awayPenaltyMadeShots + homePenaltyConcededShots) / 2;
				
				randomDouble = random.nextDouble();
				
				if(randomDouble < openPlayShotProbability) {
					double awayMadeGoalProbability = ((match.getAwayTeam().getAwayStats().getOpenPlayMadeGoals()
							+ match.getAwayTeam().getHomeStats().getOpenPlayMadeGoals()) / 2) / mixedOpenPlayShots;
					double homeConcededGoalProbability = ((match.getHomeTeam().getHomeStats().getOpenPlayConcededGoals()
							+ match.getHomeTeam().getAwayStats().getOpenPlayConcededGoals()) / 2) / mixedOpenPlayShots;
					double successRate = (awayMadeGoalProbability + homeConcededGoalProbability) / 2;
					
					queue.add(new Event(EventType.AWAY_OPEN_PLAY, (successRate * (1 - pointsFactor))));
					
				} else if(randomDouble < (openPlayShotProbability + openFreeKickProbability)) {
					double awayMadeGoalProbability = ((match.getAwayTeam().getAwayStats().getFreeKickMadeGoals()
							+ match.getAwayTeam().getHomeStats().getFreeKickMadeGoals()) / 2) / mixedFreeKickShots;
					double homeConcededGoalProbability = ((match.getHomeTeam().getHomeStats().getFreeKickConcededGoals()
							+ match.getHomeTeam().getAwayStats().getFreeKickConcededGoals()) / 2) / mixedFreeKickShots;
					double successRate = (awayMadeGoalProbability + homeConcededGoalProbability) / 2;
					
					queue.add(new Event(EventType.AWAY_FREE_KICK, (successRate * (1 - pointsFactor))));
					
				} else {
					double awayMadeGoalProbability = ((match.getAwayTeam().getAwayStats().getPenaltyMadeGoals()
							+ match.getAwayTeam().getHomeStats().getPenaltyMadeGoals()) / 2) / mixedPenaltyShots;
					double homeConcededGoalProbability = ((match.getHomeTeam().getHomeStats().getPenaltyConcededGoals()
							+ match.getHomeTeam().getAwayStats().getPenaltyConcededGoals()) / 2) / mixedPenaltyShots;
					double successRate = (awayMadeGoalProbability + homeConcededGoalProbability) / 2;
					
					queue.add(new Event(EventType.AWAY_PENALTY, successRate));
				}
			}
			
			double homeConcededOwnGoalProbability = (match.getHomeTeam().getHomeStats().getConcededOwnGoalProbability()
					+ match.getHomeTeam().getAwayStats().getConcededOwnGoalProbability()) / 2;
			double awayMadeOwnGoalProbability = (match.getAwayTeam().getAwayStats().getMadeOwnGoalProbability()
					+ match.getAwayTeam().getHomeStats().getMadeOwnGoalProbability()) / 2;
			double homeOwnGoalsuccessRate = (homeConcededOwnGoalProbability + awayMadeOwnGoalProbability) / 2;
			
			queue.add(new Event(EventType.HOME_OWN_GOAL, (homeOwnGoalsuccessRate * (1 - pointsFactor))));
			
			double awayConcededOwnGoalProbability = (match.getAwayTeam().getAwayStats().getConcededOwnGoalProbability()
					+ match.getAwayTeam().getHomeStats().getConcededOwnGoalProbability()) / 2;
			double homeMadeOwnGoalProbability = (match.getHomeTeam().getHomeStats().getMadeOwnGoalProbability()
					+ match.getHomeTeam().getAwayStats().getMadeOwnGoalProbability()) / 2;
			double awayOwnGoalsuccessRate = (awayConcededOwnGoalProbability + homeMadeOwnGoalProbability) / 2;
			
			queue.add(new Event(EventType.AWAY_OWN_GOAL, (awayOwnGoalsuccessRate * (1 + pointsFactor))));
			
			for(int i = 0; i < (homeFouls * (1 - pointsFactor)); i ++) {
				double successRate = (match.getHomeTeam().getHomeStats().getRedCardProbability()
						+ match.getHomeTeam().getAwayStats().getRedCardProbability()) / 2;
				
				queue.add(new Event(EventType.HOME_FOUL, (successRate * (1 - pointsFactor))));
			}
			
			for(int i = 0; i < (awayFouls * (1 + pointsFactor)); i ++) {
				double successRate = (match.getAwayTeam().getAwayStats().getRedCardProbability()
						+ match.getAwayTeam().getHomeStats().getRedCardProbability()) / 2;
				
				queue.add(new Event(EventType.AWAY_FOUL, (successRate * (1 + pointsFactor))));
			}
		}
	}
	
	public void run() {
		while(!queue.isEmpty()) {
			Event event = queue.poll();
			
			randomDouble = random.nextDouble();
			switch(event.getType()) {
				case HOME_OPEN_PLAY:
					if(randomDouble < (event.getSuccessRate() * homeRedCardMultiplier)) 
						homeGoals ++;
					
					break;
					
				case AWAY_OPEN_PLAY:
					if(randomDouble < (event.getSuccessRate() * awayRedCarMultiplier)) 
						awayGoals ++;
					
					break;
					
				case HOME_FREE_KICK:
					if(randomDouble < (event.getSuccessRate() * homeRedCardMultiplier)) 
						homeGoals ++;
					
					break;
					
				case AWAY_FREE_KICK:
					if(randomDouble < (event.getSuccessRate() * awayRedCarMultiplier)) 
						awayGoals ++;
					
					break;
					
				case HOME_PENALTY:
					if(randomDouble < (event.getSuccessRate() * homeRedCardMultiplier)) 
						homeGoals ++;
					
					break;
					
				case AWAY_PENALTY:
					if(randomDouble < (event.getSuccessRate() * awayRedCarMultiplier)) 
						awayGoals ++;
					
					break;
					
				case HOME_OWN_GOAL:
					if(randomDouble < (event.getSuccessRate() * awayRedCarMultiplier)) 
						awayGoals ++;
					
					break;
					
				case AWAY_OWN_GOAL:
					if(randomDouble < (event.getSuccessRate() * homeRedCardMultiplier)) 
						homeGoals ++;
					
					break;
					
				case HOME_FOUL:
					if(randomDouble < event.getSuccessRate()) {
						awayRedCarMultiplier += redCardMultiplier;
						homeRedCardMultiplier -= redCardMultiplier;
					}
					
					break;
					
				case AWAY_FOUL:
					if(randomDouble < event.getSuccessRate()) {
						homeRedCardMultiplier += redCardMultiplier;
						awayRedCarMultiplier -= redCardMultiplier;
					}
					
					break;
			}
		}
		
		simulatedMatch.setHomeTeamGoals(homeGoals);
		simulatedMatch.setAwayTeamGoals(awayGoals);
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