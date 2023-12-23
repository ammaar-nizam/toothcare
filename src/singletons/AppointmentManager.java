package singletons;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import models.Appointment;
import models.FrontOfficeOperator;
import models.Patient;
import models.RegistrationFee;
import models.Surgeon;

// A singleton class 
/* There is only one instance managing all appointments across the application. */
public class AppointmentManager {
	
	private static AppointmentManager instance = new AppointmentManager();
	private Map<String, Appointment> appointmentMap;

	// A private constructor to avoid creation of multiple objects at multiple places
	private AppointmentManager() {
        appointmentMap = new HashMap<>();
    }
    
	// A method to get the only running instance of the object AppointmentManager
    public static AppointmentManager getInstance(){
    	if (instance == null)
			instance = new AppointmentManager();
	      return instance;
	}
    
    // A method to check if the requested appointment slot is available before scheduling an appointment
    public boolean isAppointmentSlotAvailable(LocalDate requestedDate, LocalTime requestedTime, Surgeon surgeon) {
    	for (Appointment appointment : appointmentMap.values()) {
            if (appointment.getSurgeon().equals(surgeon) && appointment.getAppointmentDate().equals(requestedDate) && 
            		appointment.getStartTime().equals(requestedTime)) {
                return false;
            }
        }
    	return true;
    }
    
    // A method to schedule appointments and put them in an HashMap
    public void scheduleAppointment(Patient patient, Surgeon surgeon, LocalDate appointmentDate, String dayOfTheWeek,
			LocalTime startTime, LocalTime endTime, LocalDateTime dateAndTimeAppointmentMade,
			RegistrationFee registrationFee, FrontOfficeOperator frontOfficeOperator) {
        // Checking if an appointment with the same day and time already exists
        if (isAppointmentSlotAvailable(appointmentDate, startTime, surgeon)) {
            // Creating a new Appointment object
            Appointment newAppointment = new Appointment(patient, surgeon, appointmentDate, dayOfTheWeek,
        			startTime, endTime, dateAndTimeAppointmentMade, registrationFee, frontOfficeOperator);

            // Adding the new appointment to the HashMap
            appointmentMap.put(newAppointment.getAppointmentId(), newAppointment);
        }
    }
    
    // A method to return an unmodifiable view of the appointmentMap
    public Map<String, Appointment> getAppointmentsMap() {
        return Collections.unmodifiableMap(appointmentMap);
    }
    
    // Method to display details of a single appointment
    public void displayAppointmentDetails(Appointment appointment) {
        System.out.println("ID: " + appointment.getAppointmentId());
        System.out.println("Patient's First Name: " + appointment.getPatient().getFirstName());
        System.out.println("Patient's Last Name: " + appointment.getPatient().getLastName());
        System.out.println("Contact Number: " + appointment.getPatient().getMobileNumber());
        System.out.println("Email Address: " + appointment.getPatient().getEmail());
        System.out.println("Address: " + appointment.getPatient().getAddress());
        System.out.println("Appointment Date: " + appointment.getAppointmentDate());
        System.out.println("Day of the Week: " + appointment.getDayOfTheWeek());
        System.out.println("Start Time: " + appointment.getStartTime());
        System.out.println("End Time: " + appointment.getEndTime());
        System.out.println("Surgeon Name: " + appointment.getSurgeon().getTitle() + " " + appointment.getSurgeon().getFirstName() + " " + appointment.getSurgeon().getLastName());
        System.out.println("-------------------------");
    }
    
    // A method to display all appointment details
    public void displayAllAppointments() {
        System.out.println("\nAll Appointment Details:");
        for (Appointment appointment : appointmentMap.values()) {
            displayAppointmentDetails(appointment);
        }
    }
    
    // A method to search for an appointment by ID
    public Appointment searchAppointmentById(String id) {
        return appointmentMap.get(id);
    }
    
    // A method to search for an appointment by patient's first name and last name
    public Appointment searchAppointmentByPatientName(Patient patient, String patientFirstName, String patientLastName) {
        for (Appointment appointment : appointmentMap.values()) {
            if (patient.getFirstName().equalsIgnoreCase(patientFirstName) &&
            		patient.getLastName().equalsIgnoreCase(patientLastName)) {
                return appointment;
            }
        }
        return null;
    }
    
    // A method to bind all the data in HashMap datastructure to a table view in the frontend 
    @SuppressWarnings("unchecked")
	public void getAvailableAppointments(TableView<Appointment> tableViewAppointment) {
    	// Clearing the TableView first
    	tableViewAppointment.getItems().clear(); 

    	// Setting columns
        TableColumn<Appointment, String> columnAppointmentId = new TableColumn<>("Appointment ID");
        columnAppointmentId.setCellValueFactory(cellData -> cellData.getValue().fxAppointmentIdProperty());
        
        TableColumn<Appointment, String> columnPatientName = new TableColumn<>("Patient Name");
        columnPatientName.setCellValueFactory(cellData -> cellData.getValue().fxPatientNameProperty());
        
        TableColumn<Appointment, String> columnSurgeonName = new TableColumn<>("Surgeon");
        columnSurgeonName.setCellValueFactory(cellData -> cellData.getValue().fxSurgeonNameProperty());
        
        TableColumn<Appointment, LocalDate> columnAppointmentDate = new TableColumn<>("Appointment Date");
        columnAppointmentDate.setCellValueFactory(cellData -> cellData.getValue().fxAppointmentDateProperty());
        
        TableColumn<Appointment, String> columnDayOfTheWeek = new TableColumn<>("Day of the Week");
        columnDayOfTheWeek.setCellValueFactory(cellData -> cellData.getValue().fxDayOfTheWeekProperty());
        
        TableColumn<Appointment, LocalTime> columnStartTime = new TableColumn<>("Start Time");
        columnStartTime.setCellValueFactory(cellData -> cellData.getValue().fxStartTimeProperty());
        
        TableColumn<Appointment, LocalTime> columnEndTime = new TableColumn<>("End Time");
        columnEndTime.setCellValueFactory(cellData -> cellData.getValue().fxEndTimeProperty());

        // Clear existing columns and add new columns to the TableView
        tableViewAppointment.getColumns().clear();
        tableViewAppointment.getColumns().addAll(columnAppointmentId, columnPatientName, columnSurgeonName,
        		columnAppointmentDate, columnDayOfTheWeek, columnStartTime, columnEndTime);

        // Get available appointments from the appointmentsMap and add them to the TableView
        appointmentMap.values().stream()
                .forEach(tableViewAppointment.getItems()::add);
    }
}
