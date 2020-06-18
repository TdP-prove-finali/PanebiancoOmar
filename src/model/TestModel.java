package model;

import java.util.List;
import java.util.Map;

public class TestModel {

	public static void main(String[] args) {
		
		boolean homeFactor = true;
		boolean pointsFactorActivated = true;
		double pointsFactor = 1.0;
		double redCardMultiplier = 0.1;
		
		boolean standardSimulation = false;
		boolean quickSimulation = false;
		boolean multipleQuickSimulation = true;
		int multipleSimulationNumber = 100;
		
		Model model = new Model(homeFactor, pointsFactorActivated, pointsFactor, redCardMultiplier, 
				standardSimulation, quickSimulation, multipleQuickSimulation, multipleSimulationNumber);
		
		System.out.println("");
		System.out.println("*** STARTING SIMULATION... ***");
		System.out.println("");
		
		Map<Integer, List<Match>> result = model.startSimulation();
		
		if(result.size() == 1) {
			/* STANDARD SIMULATION */
			System.out.println(model.printDay(result.get(25)));
			System.out.println(model.printLeagueTable());
			
		} else if(result.size() > 1) {
			/* QUICK SIMULATION */
			System.out.println(model.printDay(result.get(25)));
			for(int i = 27; i <= 38; i ++)
				System.out.println(model.printDay(result.get(i)));
			System.out.println(model.printLeagueTable());
			
		} else {
			/* MULTIPLE QUICK SIMULATION */
			System.out.println("NUMBER OF SIMULATIONS: " + multipleSimulationNumber);
			System.out.println("");
			System.out.println(model.printLeagueWinChance());
			System.out.println(model.printChampionsLeagueQualificationChance());
			System.out.println(model.printEuropaLeagueQualificationChance());
			System.out.println(model.printRelegationChance());
		}
		
		System.out.println("");
		System.out.println("*** SIMULATION ENDED ***");
		System.out.println("");
		
	}

}
