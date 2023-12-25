package singletons;

import java.util.ArrayList;
import java.util.List;

import models.OverallFee;

//A singleton class 
/* There is only one instance managing all fee across the application. */
public class FeeManager {
	
	private static FeeManager instance = new FeeManager();
	List<OverallFee> feeList = new ArrayList<>();

	private FeeManager() {
		feeList = new ArrayList<OverallFee>();
	}
	
	public static FeeManager getInstance(){
		if (instance == null)
			instance = new FeeManager();
	    return instance;
	}
	
	public OverallFee addOverallFee(Double feeAmount, String feeDescription, boolean isPaid) {

            // Creating a new OverallFee object
        	OverallFee newOverallFee = new OverallFee(feeAmount, feeDescription, isPaid);

            // Adding the new fee to the ArrayList
        	feeList.add(newOverallFee);
        	return newOverallFee;
    }
	
}
