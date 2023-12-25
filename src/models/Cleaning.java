package models;

public class Cleaning implements Treatment{

	// Overriding the methods in Treatment Interface
	@Override
	public String getTreatmentName() {
		return "This is Cleaning.";
	}

	// Overriding the methods in Treatment Interface
	@Override
	public double getTreatmentCost() {
		return 5000.00;
	}  
}
