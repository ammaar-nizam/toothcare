package factories;

import models.Cleaning;
import models.Treatment;
import models.Whitening;

public class PreventiveTreatmentFactory extends AbstractTreatmentFactory{

	@Override
	public Treatment createTreatment(String treatmentType) {
		if(treatmentType.equalsIgnoreCase("CLEANING")){
	         return new Cleaning();         
	      }else if(treatmentType.equalsIgnoreCase("WHITENING")){
	         return new Whitening();
	      } 
	      return null;
	}

}
