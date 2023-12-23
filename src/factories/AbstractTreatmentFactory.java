package factories;

import models.Treatment;

public abstract class AbstractTreatmentFactory {
	
	public abstract Treatment createTreatment(String treatmentType);
    
}
