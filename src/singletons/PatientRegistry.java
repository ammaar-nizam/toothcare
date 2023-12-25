package singletons;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import models.Patient;

//A singleton class 
/* There is only one instance managing all patients across the application. */
public class PatientRegistry {
	
	private static PatientRegistry instance = new PatientRegistry();
	List<Patient> patientList = new ArrayList<>();
	
	private PatientRegistry() {
		patientList = new ArrayList<Patient>();
	}
	
	public static PatientRegistry getInstance(){
		if (instance == null)
			instance = new PatientRegistry();
	    return instance;
	}
	
	public void addPatient(String title, String firstName, String lastName, String address, LocalDate dateOfBirth, 
			int mobile, String email) {
        // Checking if a patient with the same first and last name already exists
        if (searchOnePatientByName(firstName, lastName) == null) {
            // Creating a new Patient object
            Patient newPatient = new Patient(title, firstName, lastName, address, dateOfBirth, mobile, email);

            // Adding the new patient to the ArrayList
            patientList.add(newPatient);
        }      
    }
	
	// A method to search for one patient by name
    public Patient searchOnePatientByName(String firstName, String lastName) {
    	for (Patient patient : patientList) {
            if (patient.getFirstName().equalsIgnoreCase(firstName) && patient.getLastName().equalsIgnoreCase(lastName)) {
                return patient;
            }
        }
        return null;
    }

	// Method to search for a patient by Id
    public Patient searchPatientById(String id) {
        for (Patient patient : patientList) {
            if (patient.getPatientId().equalsIgnoreCase(id)) {
                return patient;
            }
        }
        return null;
    }
    
    // Method to search for patients by firstName
    public List<Patient> searchPatientByFirstName(String firstName) {
        List<Patient> foundPatients = new ArrayList<>();
        for (Patient patient : patientList) {
            if (patient.getFirstName().equalsIgnoreCase(firstName)) {
                foundPatients.add(patient);
            }
        }
        return foundPatients;
    }

    // Method to search for patients by lastName
    public List<Patient> searchPatientByLastName(String lastName) {
        List<Patient> foundPatients = new ArrayList<>();
        for (Patient patient : patientList) {
            if (patient.getLastName().equalsIgnoreCase(lastName)) {
                foundPatients.add(patient);
            }
        }
        return foundPatients;
    }
	

	public void displayPatientsDetails() {
		System.out.println("\nAll Patient Details:");
		for (Patient patient : patientList) {
            displayPatientDetails(patient);
        }
	}

	public void displayPatientDetails(Patient patient) {
		System.out.println("Patient ID: " + patient.getPatientId());
        System.out.println("First Name: " + patient.getFirstName());
        System.out.println("Last Name: " + patient.getLastName());
        System.out.println("Address: " + patient.getAddress());
        System.out.println("Mobile: " + patient.getMobileNumber());
        System.out.println("Email: " + patient.getEmail());
        System.out.println("-------------------------");
	}
}
