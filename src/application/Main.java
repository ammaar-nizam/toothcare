package application;
	
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import controllers.SignInController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.FrontOfficeOperator;
import models.OverallFee;
import models.RegistrationFee;
import models.Surgeon;
import singletons.AppointmentManager;
import singletons.InvoiceManager;
import singletons.PatientRegistry;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {
	
public static Stage stage;	
	
	PatientRegistry patientManager = PatientRegistry.getInstance();
	AppointmentManager appointmentManager = AppointmentManager.getInstance();
	InvoiceManager invoiceManager = InvoiceManager.getInstance();
	
	public static List<Surgeon> surgeonList = new ArrayList<>();
	
	// Ms Gayani
	public static FrontOfficeOperator theOperator = new FrontOfficeOperator("Ms", "Gayani", "Rathnaweera", 
			"gayani", "gayani@123");
	
	// Creating a Registration Fee object
	public static RegistrationFee registrationFee = new RegistrationFee(1000.00, "Registration Fee", true);
	
	@Override
	public void start(Stage primaryStage) {
		try {
			addDummyData();

            Parent root = FXMLLoader.load(Objects.requireNonNull(SignInController.LOCATION));
            primaryStage.setScene(new Scene(root, 600, 400));
            primaryStage.setResizable(false);
            stage = primaryStage;
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();

        } catch(NullPointerException ne) {
        	System.out.println("Null Pointer Exception occured while running the application");
        } catch(IOException ie) {
        	System.out.println("IO Exception occured while running the application");
        } catch (Exception e) {
            System.out.println("Error in running the application.");
        }
	}
	
	public void addDummyData() {
		
		// Adding two surgeons to surgeonList
		surgeonList.add(new Surgeon("Mr.", "A.D.", "Ranasinghe", 721731383, "a_d@outlook.com", "Orthodontics"));
		surgeonList.add(new Surgeon("Ms.", "F.B.", "Indrasena",  754940934, "f.b.indrasena@gmail.com", "Periodontics"));
		
		// Adding few dummy data to check the working system.		
		// Few Patients
		patientManager.addPatient("Mr", "Ammaar", "Nizam", "Slave Island", LocalDate.of(2000, 10, 11), 
				754940934, "ammaar.nizam@outlook.com");
		patientManager.addPatient("Mrs", "Hafsa", "Nizam", "Mattakuliya", LocalDate.of(1996, 9, 19), 
				750284980, "hafsa@gmail.com");
		patientManager.addPatient("Master", "Mikaeel", "Ijas", "Bambalapitiya", LocalDate.of(2020, 12, 11), 
				750284980, "hafsa@gmail.com");
		patientManager.addPatient("Mrs", "Mumthaz", "Begum", "Station Passage", LocalDate.of(1968, 05, 18), 
				750284980, "mumthaz@gmail.com");
		patientManager.addPatient("Miss", "Hanna", "Mountana", "Mt Lavinia", LocalDate.of(2019, 10, 10), 
				750284980, "hana@gmail.com");
		patientManager.addPatient("Mr", "Nizam", "Brandudeen", "Dematagoda", LocalDate.of(1963, 10, 8), 
				762069622, "nizam@rocketmail.com");

		// Few Appointments
		appointmentManager.scheduleAppointment(patientManager.searchPatientById("PATIENT_1"), surgeonList.get(0), 
				LocalDate.of(2024, 1, 1), "Monday", LocalTime.of(18, 00), LocalTime.of(18, 30),
				LocalDateTime.now(), registrationFee, theOperator);
		appointmentManager.scheduleAppointment(patientManager.searchPatientById("PATIENT_2"), surgeonList.get(0), 
				LocalDate.of(2024, 1, 1), "Monday", LocalTime.of(18, 30), LocalTime.of(19, 00),
				LocalDateTime.now(), registrationFee, theOperator);
		appointmentManager.scheduleAppointment(patientManager.searchPatientById("PATIENT_3"), surgeonList.get(1), 
				LocalDate.of(2024, 1, 8), "Monday", LocalTime.of(19, 00), LocalTime.of(19, 30),
				LocalDateTime.now(), registrationFee, theOperator);
		appointmentManager.scheduleAppointment(patientManager.searchPatientById("PATIENT_4"), surgeonList.get(0), 
				LocalDate.of(2024, 1, 6), "Saturday", LocalTime.of(18, 00), LocalTime.of(18, 30),
				LocalDateTime.now(), registrationFee, theOperator);
		appointmentManager.scheduleAppointment(patientManager.searchPatientById("PATIENT_5"), surgeonList.get(1), 
				LocalDate.of(2024, 1, 6), "Saturday", LocalTime.of(18, 30), LocalTime.of(19, 00),
				LocalDateTime.now(), registrationFee, theOperator);
		appointmentManager.scheduleAppointment(patientManager.searchPatientById("PATIENT_6"), surgeonList.get(0), 
				LocalDate.of(2024, 1, 7), "Sunday", LocalTime.of(19, 00), LocalTime.of(19, 30),
				LocalDateTime.now(), registrationFee, theOperator);
		
		//Few Invoices
		invoiceManager.addInvoice(appointmentManager.searchAppointmentById("APPOINTMENT_1"), 
				LocalDateTime.now(), new OverallFee(15000.00, "Overall Fee", true), 14000.00);
		invoiceManager.addInvoice(appointmentManager.searchAppointmentById("APPOINTMENT_2"), 
				LocalDateTime.now(), new OverallFee(10000.00, "Overall Fee", true), 9000.00);
		invoiceManager.addInvoice(appointmentManager.searchAppointmentById("APPOINTMENT_3"), 
				LocalDateTime.now(), new OverallFee(6000.00, "Overall Fee", true), 5000.00);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
