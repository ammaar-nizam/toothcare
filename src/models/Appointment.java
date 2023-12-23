package models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Appointment {

	// Fields
	private static int counter = 0;
	private String appointmentId;
	private Patient patient;
	private Surgeon surgeon;
	private LocalDate appointmentDate;
	private String dayOfTheWeek;
	private LocalTime startTime;
	private LocalTime endTime;
	private LocalDateTime dateAndTimeAppointmentMade;
	private RegistrationFee registrationFee;
	private FrontOfficeOperator frontOfficeOperator;
	
	// These properties are set to work with the Table View binding
	private final StringProperty fxAppointmentId = new SimpleStringProperty();
    private final StringProperty fxPatientName = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> fxAppointmentDate = new SimpleObjectProperty<LocalDate>();
    private final StringProperty fxDayOfTheWeek = new SimpleStringProperty();
    private final ObjectProperty<LocalTime> fxStartTime = new SimpleObjectProperty<LocalTime>();
    private final ObjectProperty<LocalTime> fxEndTime = new SimpleObjectProperty<LocalTime>();
    private final StringProperty fxSurgeonName = new SimpleStringProperty();
    private final StringProperty fxFrontOfficeOperatorName = new SimpleStringProperty();
	
	// Constructor
	public Appointment(Patient patient, Surgeon surgeon, LocalDate appointmentDate, String dayOfTheWeek,
			LocalTime startTime, LocalTime endTime, LocalDateTime dateAndTimeAppointmentMade,
			RegistrationFee registrationFee, FrontOfficeOperator frontOfficeOperator) {
		super();
		this.appointmentId = generateAppointmentId();
		this.patient = patient;
		this.surgeon = surgeon;
		this.appointmentDate = appointmentDate;
		this.dayOfTheWeek = dayOfTheWeek;
		this.startTime = startTime;
		this.endTime = endTime;
		this.dateAndTimeAppointmentMade = dateAndTimeAppointmentMade;
		this.registrationFee = registrationFee;
		this.frontOfficeOperator = frontOfficeOperator;
	}

	// A method to create Appointment IDs
	private String generateAppointmentId() {
        String prefix = "APPOINTMENT_";
        return prefix + ++counter;
    }
	
	// Getters and Setters for Appointment
	public String getAppointmentId() {
		return appointmentId;
	}
	
	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Surgeon getSurgeon() {
		return surgeon;
	}

	public void setSurgeon(Surgeon surgeon) {
		this.surgeon = surgeon;
	}

	public LocalDate getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(LocalDate appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

	public String getDayOfTheWeek() {
		return dayOfTheWeek;
	}

	public void setDayOfTheWeek(String dayOfTheWeek) {
		this.dayOfTheWeek = dayOfTheWeek;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	public LocalDateTime getDateAndTimeAppointmentMade() {
		return dateAndTimeAppointmentMade;
	}

	public void setDateAndTimeAppointmentMade(LocalDateTime dateAndTimeAppointmentMade) {
		this.dateAndTimeAppointmentMade = dateAndTimeAppointmentMade;
	}

	public RegistrationFee getRegistrationFee() {
		return registrationFee;
	}

	public void setRegistrationFee(RegistrationFee registrationFee) {
		this.registrationFee = registrationFee;
	}

	public FrontOfficeOperator getFrontOfficeOperator() {
		return frontOfficeOperator;
	}

	public void setFrontOfficeOperator(FrontOfficeOperator frontOfficeOperator) {
		this.frontOfficeOperator = frontOfficeOperator;
	}

	// These methods are to bind the fields in the Appointment class to the Table View in frontend
	public StringProperty fxAppointmentIdProperty() {
        fxAppointmentId.set(appointmentId);
        return fxAppointmentId;
    }

    public StringProperty fxPatientNameProperty() {
        fxPatientName.set(patient.getTitle() + " " + patient.getFirstName() + " " + patient.getLastName());
        return fxPatientName;
    }

    public ObjectProperty<LocalDate> fxAppointmentDateProperty() {
        fxAppointmentDate.set(appointmentDate);
        return fxAppointmentDate;
    }

    public StringProperty fxDayOfTheWeekProperty() {
    	fxDayOfTheWeek.set(dayOfTheWeek);
        return fxDayOfTheWeek;
    }
    
    public ObjectProperty<LocalTime> fxStartTimeProperty() {
        fxStartTime.set(startTime);
        return fxStartTime;
    }

    public ObjectProperty<LocalTime> fxEndTimeProperty() {
        fxEndTime.set(endTime);
        return fxEndTime;
    }
    
    public StringProperty fxSurgeonNameProperty() {
        fxSurgeonName.set(surgeon.getTitle() + " " + surgeon.getFirstName() + " " + surgeon.getLastName());
        return fxSurgeonName;
    }
    
    public StringProperty fxFrontOfficeOperatorNameProperty() {
    	fxFrontOfficeOperatorName.set(frontOfficeOperator.getTitle() + " " + frontOfficeOperator.getFirstName() + " " 
    + frontOfficeOperator.getLastName());
        return fxFrontOfficeOperatorName;
    }

	@Override
	public String toString() {
		return "Appointment [appointmentId=" + appointmentId + ", patient=" + patient + ", surgeon=" + surgeon
				+ ", appointmentDate=" + appointmentDate + ", dayOfTheWeek=" + dayOfTheWeek + ", startTime=" + startTime
				+ ", endTime=" + endTime + ", dateAndTimeAppointmentMade=" + dateAndTimeAppointmentMade
				+ ", registrationFee=" + registrationFee + ", frontOfficeOperator=" + frontOfficeOperator
				+ ", fxAppointmentId=" + fxAppointmentId + ", fxPatientName=" + fxPatientName + ", fxAppointmentDate="
				+ fxAppointmentDate + ", fxDayOfTheWeek=" + fxDayOfTheWeek + ", fxStartTime=" + fxStartTime
				+ ", fxEndTime=" + fxEndTime + ", fxSurgeonName=" + fxSurgeonName + ", fxFrontOfficeOperatorName="
				+ fxFrontOfficeOperatorName + "]";
	}

}
