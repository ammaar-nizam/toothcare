package models;

public class NerveFilling implements Treatment {
	
	// Overriding the methods in Treatment Interface
	@Override
	public String getTreatmentName() {
		return "This is Nerve Filling.";
	}

	// Overriding the methods in Treatment Interface
	@Override
	public double getTreatmentCost() {
		return 30000.00;
	}
}