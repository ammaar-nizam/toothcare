package models;

public class FeeStructure {
	
	// Fields
	protected Double feeAmount;
	protected String feeDescription;
	protected boolean isPaid = false; 
	
	// Constructor
	public FeeStructure(Double feeAmount, String feeDescription, boolean isPaid) {
		super();
		this.feeAmount = feeAmount;
		this.feeDescription = feeDescription;
		this.isPaid = isPaid;
	}
		
	// These methods will be inherited and then overriden in RegistrationFee and OverallFee classes
	public void updateFeeDetails() {
		// To be implemented
	}
		
	// These methods will be inherited and then overriden in RegistrationFee and OverallFee classes
	public double calculateTotalFee() {
		// Calculation to be implemented
	    return feeAmount;
	}
		
	// Getters and Setters
	public Double getFeeAmount() {
		return feeAmount;
	}

	public void setFeeAmount(Double feeAmount) {
		this.feeAmount = feeAmount;
	}

	public String getFeeDescription() {
		return feeDescription;
	}

	public void setFeeDescription(String feeDescription) {
		this.feeDescription = feeDescription;
	}
	
	public boolean getIsPaid() {
		return isPaid;
	}

	public void setIsPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}

	@Override
	public String toString() {
		return "FeeStructure [feeAmount=" + feeAmount + ", feeDescription=" + feeDescription + "]";
	}
	
}
