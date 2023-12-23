package models;

public class RegistrationFee extends FeeStructure{

	// Constructor from Super Class
	public RegistrationFee(Double feeAmount, String feeDescription, boolean isPaid) {
		super(feeAmount, feeDescription, isPaid);
		// TODO Auto-generated constructor stub
	}
	
	// Override the calculateTotalFee method in FeeStructure class
	@Override
	public double calculateTotalFee() {
	    return 1000.00;
	} 

}
