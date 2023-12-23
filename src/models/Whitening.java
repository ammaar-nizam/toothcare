package models;

public class Whitening implements Treatment {
	
	// Overriding the methods in Treatment Interface
	@Override
	public String getTreatmentName() {
		return "This is Whitening.";
	}

	// Overriding the methods in Treatment Interface
	@Override
	public double getTreatmentCost() {
		// TODO Auto-generated method stub
		return 50000.00;
	}
}
