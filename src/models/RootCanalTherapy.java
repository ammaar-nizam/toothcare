package models;

public class RootCanalTherapy implements Treatment {
	
	// Overriding the methods in Treatment Interface
	@Override
	public String getTreatmentName() {
		return "This is Root Canal Therapy.";
	}

	// Overriding the methods in Treatment Interface
	@Override
	public double getTreatmentCost() {
		// TODO Auto-generated method stub
		return 60000.00;
	}
}
