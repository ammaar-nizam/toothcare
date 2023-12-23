package models;

public class Staff {

	// Fields
	private static int counter = 0;
	protected String staffId;
	protected String title;
	protected String firstName;
	protected String lastName;
	protected String username;
	protected String password;

	// Constructor
	public Staff(String title, String firstName, String lastName, String username, String password) {
		super();
		this.staffId = generateStaffId();
		this.title = title;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
	}

	// A method to create Staff IDs
	private String generateStaffId() {
		String prefix = "STAFF_";
		return prefix + ++counter;
	}

	// Getters and Setters
	public String getStaffId() {
		return staffId;
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
	
	public String getUsername() {
		return username;
	}
	void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Staff [staffId=" + staffId + ", title=" + title + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", username=" + username + ", password=" + password + "]";
	}
}
