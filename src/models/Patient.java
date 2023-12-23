package models;

import java.time.LocalDate;

public class Patient {
	
	// Fields
	private static int counter = 0;
	private String patientId;
	private String title;
	private String firstName;
	private String lastName;
	private String address;
	private LocalDate dateOfBirth;
	private int mobileNumber;
	private String email;
	
	// Constructor
	public Patient(String title, String firstName, String lastName, String address, LocalDate dateOfBirth, int mobileNumber,
			String email) {
		super();
		this.patientId = generatePatientId();
		this.title = title;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.address = address;
		this.mobileNumber = mobileNumber;
		this.email = email;
	}
	
	// A method to create Patient IDs
	private String generatePatientId() {
        String prefix = "PATIENT_";
        return prefix + ++counter;
    }
	
	// Getters and Setters
	public String getPatientId() {
		return patientId;
	}

	public String getTitle() {
		return title;
	}

	void setTitle(String title) {
		this.title = title;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(int mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	@Override
	public String toString() {
		return "Patient [patientId=" + patientId + ", title=" + title + ", firstName=" + firstName + ", lastName="
				+ lastName + ", address=" + address + ", dateOfBirth=" + dateOfBirth + ", mobileNumber=" + mobileNumber
				+ ", email=" + email + "]";
	}

}
