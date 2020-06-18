package model;

public class TestModel {

	public static void main(String[] args) {
		
		double redCardMultiplier = 0.1;
		int multipleSimulationNumber = 1000;
		
		Model model = new Model(redCardMultiplier, multipleSimulationNumber);
		
		model.simulateAllDays();
		System.out.println("*** 1 SIMULATION ***");
		System.out.println(model.printLeagueTable());
		
		model.simulateAllDaysSeveralTimes();
		System.out.println("*** " + multipleSimulationNumber + " SIMULATIONS ***");
		System.out.println(model.printLeagueWinsChance());
		
	}

}
