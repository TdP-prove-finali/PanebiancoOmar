package model;

public class TestModel {

	public static void main(String[] args) {
		
		boolean homeFactor = true;
		boolean pointsFactorActivated = true;
		double pointsFactor = 0.5;
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
		
		model.startSimulation();
		
		if(model.getSimulatedDays().size() == 1) {
			/* STANDARD SIMULATION */
			System.out.println(model.printDay(model.getSimulatedDays().get(25)));
			System.out.println(model.printLeagueTable());
			
		} else if(model.getSimulatedDays().size() > 1) {
			/* QUICK SIMULATION */
			System.out.println(model.printDay(model.getSimulatedDays().get(25)));
			for(int i = 27; i <= 38; i ++)
				System.out.println(model.printDay(model.getSimulatedDays().get(i)));
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
