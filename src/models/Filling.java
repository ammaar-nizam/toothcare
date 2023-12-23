package models;

public class Filling implements Treatment {
	
	// Overriding the methods in Treatment Interface
	@Override
	public String getTreatmentName() {
		return "This is Filling.";
	}

	// Overriding the methods in Treatment Interface
	@Override
	public double getTreatmentCost() {
		return 8000.00;
	}
}
