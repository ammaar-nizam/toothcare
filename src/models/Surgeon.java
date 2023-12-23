package models;

public class Surgeon {
	
	// Fields
	private static int counter = 0;
	private String surgeonId;
	private String title;
	private String firstName;
	private String lastName;
	private int mobileNumber;
	private String email;
	private String specialization;
	
	// Constructor
	public Surgeon(String title, String firstName, String lastName, int mobileNumber,
			String email, String specialization) {
		super();
		this.surgeonId = generateSurgeonId();
		this.title = title;
		this.firstName = firstName;
		this.lastName = lastName;
		this.mobileNumber = mobileNumber;
		this.email = email;
		this.specialization = specialization;
	}
	
	// A method to create Surgeon IDs
		private String generateSurgeonId() {
			String prefix = "SURGEON_";
			return prefix + ++counter;
		}

	// Getters and Setters
	public String getSurgeonId() {
		return surgeonId;
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

	void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public int getMobileNumber() {
		return mobileNumber;
	}

	void setMobileNumber(int mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmail() {
		return email;
	}

	void setEmail(String email) {
		this.email = email;
	}

	public String getSpecialization() {
		return specialization;
	}

	void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	@Override
	public String toString() {
		return "Surgeon [surgeonId=" + surgeonId + ", title=" + title + ", firstName=" + firstName + ", lastName="
				+ lastName + ", mobileNumber=" + mobileNumber + ", email=" + email
				+ ", specialization=" + specialization + "]";
	}
}
