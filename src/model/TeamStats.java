package model;

public class TeamStats {
	
	private double totalMadeShots, openPlayMadeShots, freeKickMadeShots, penaltyMadeShots, openPlayMadeGoals, freeKickMadeGoals, penaltyMadeGoals;
	private double totalConcededShots, openPlayConcededShots, freeKickConcededShots, penaltyConcededShots, openPlayConcededGoals, freeKickConcededGoals, penaltyConcededGoals;
	
	public TeamStats(double totalMadeShots, double openPlayMadeShots, double freeKickMadeShots,
			double penaltyMadeShots, double openPlayMadeGoals, double freeKickMadeGoals, double penaltyMadeGoals,
			double totalConcededShots, double openPlayConcededShots, double freeKickConcededShots,
			double penaltyConcededShots, double openPlayConcededGoals, double freeKickConcededGoals,
			double penaltyConcededGoals) {
		super();
		this.totalMadeShots = totalMadeShots;
		this.openPlayMadeShots = openPlayMadeShots;
		this.freeKickMadeShots = freeKickMadeShots;
		this.penaltyMadeShots = penaltyMadeShots;
		this.openPlayMadeGoals = openPlayMadeGoals;
		this.freeKickMadeGoals = freeKickMadeGoals;
		this.penaltyMadeGoals = penaltyMadeGoals;
		this.totalConcededShots = totalConcededShots;
		this.openPlayConcededShots = openPlayConcededShots;
		this.freeKickConcededShots = freeKickConcededShots;
		this.penaltyConcededShots = penaltyConcededShots;
		this.openPlayConcededGoals = openPlayConcededGoals;
		this.freeKickConcededGoals = freeKickConcededGoals;
		this.penaltyConcededGoals = penaltyConcededGoals;
	}
	
	public double getTotalMadeShots() {
		return totalMadeShots;
	}

	public void setTotalMadeShots(double totalMadeShots) {
		this.totalMadeShots = totalMadeShots;
	}

	public double getOpenPlayMadeShots() {
		return openPlayMadeShots;
	}

	public void setOpenPlayMadeShots(double openPlayMadeShots) {
		this.openPlayMadeShots = openPlayMadeShots;
	}

	public double getFreeKickMadeShots() {
		return freeKickMadeShots;
	}

	public void setFreeKickMadeShots(double freeKickMadeShots) {
		this.freeKickMadeShots = freeKickMadeShots;
	}

	public double getPenaltyMadeShots() {
		return penaltyMadeShots;
	}

	public void setPenaltyMadeShots(double penaltyMadeShots) {
		this.penaltyMadeShots = penaltyMadeShots;
	}

	public double getOpenPlayMadeGoals() {
		return openPlayMadeGoals;
	}

	public void setOpenPlayMadeGoals(double openPlayMadeGoals) {
		this.openPlayMadeGoals = openPlayMadeGoals;
	}

	public double getFreeKickMadeGoals() {
		return freeKickMadeGoals;
	}

	public void setFreeKickMadeGoals(double freeKickMadeGoals) {
		this.freeKickMadeGoals = freeKickMadeGoals;
	}

	public double getPenaltyMadeGoals() {
		return penaltyMadeGoals;
	}

	public void setPenaltyMadeGoals(double penaltyMadeGoals) {
		this.penaltyMadeGoals = penaltyMadeGoals;
	}

	public double getTotalConcededShots() {
		return totalConcededShots;
	}

	public void setTotalConcededShots(double totalConcededShots) {
		this.totalConcededShots = totalConcededShots;
	}

	public double getOpenPlayConcededShots() {
		return openPlayConcededShots;
	}

	public void setOpenPlayConcededShots(double openPlayConcededShots) {
		this.openPlayConcededShots = openPlayConcededShots;
	}

	public double getFreeKickConcededShots() {
		return freeKickConcededShots;
	}

	public void setFreeKickConcededShots(double freeKickConcededShots) {
		this.freeKickConcededShots = freeKickConcededShots;
	}

	public double getPenaltyConcededShots() {
		return penaltyConcededShots;
	}

	public void setPenaltyConcededShots(double penaltyConcededShots) {
		this.penaltyConcededShots = penaltyConcededShots;
	}

	public double getOpenPlayConcededGoals() {
		return openPlayConcededGoals;
	}

	public void setOpenPlayConcededGoals(double openPlayConcededGoals) {
		this.openPlayConcededGoals = openPlayConcededGoals;
	}

	public double getFreeKickConcededGoals() {
		return freeKickConcededGoals;
	}

	public void setFreeKickConcededGoals(double freeKickConcededGoals) {
		this.freeKickConcededGoals = freeKickConcededGoals;
	}

	public double getPenaltyConcededGoals() {
		return penaltyConcededGoals;
	}

	public void setPenaltyConcededGoals(double penaltyConcededGoals) {
		this.penaltyConcededGoals = penaltyConcededGoals;
	}

	@Override
	public String toString() {
		return String.format(
				"[totalMadeShots=%s, openPlayMadeShots=%s, freeKickMadeShots=%s, penaltyMadeShots=%s, openPlayMadeGoals=%s, freeKickMadeGoals=%s, penaltyMadeGoals=%s, totalConcededShots=%s, openPlayConcededShots=%s, freeKickConcededShots=%s, penaltyConcededShots=%s, openPlayConcededGoals=%s, freeKickConcededGoals=%s, penaltyConcededGoals=%s]",
				totalMadeShots, openPlayMadeShots, freeKickMadeShots, penaltyMadeShots, openPlayMadeGoals,
				freeKickMadeGoals, penaltyMadeGoals, totalConcededShots, openPlayConcededShots, freeKickConcededShots,
				penaltyConcededShots, openPlayConcededGoals, freeKickConcededGoals, penaltyConcededGoals);
	}
	
}
