package factories;

import models.Filling;
import models.NerveFilling;
import models.RootCanalTherapy;
import models.Treatment;

public class RestorativeTreatmentFactory extends AbstractTreatmentFactory{

	@Override
	public Treatment createTreatment(String treatmentType) {
		if(treatmentType.equalsIgnoreCase("FILLING")){
	         return new Filling();         
	      }else if(treatmentType.equalsIgnoreCase("NERVE FILLING")){
	         return new NerveFilling();
	      }else if(treatmentType.equalsIgnoreCase("ROOT CANAL THERAPY")){
		         return new RootCanalTherapy();
		      }	 
	      return null;
	}
	
}
