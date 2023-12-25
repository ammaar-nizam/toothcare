package controllers;

import singletons.AppointmentManager;
import singletons.ConsultationScheduler;
import application.Main;
import singletons.PatientRegistry;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import models.ConsultationSchedule;
import models.Surgeon;

public class AppointmentSchedulerController implements Initializable {

	public static final URL LOCATION = Main.class.getResource("../views/AppointmentScheduler.fxml");
	
	ConsultationScheduler consultationScheduler = ConsultationScheduler.getInstance();
	AppointmentManager appointmentManager = AppointmentManager.getInstance();
	PatientRegistry patientManager = PatientRegistry.getInstance();
	
	@FXML
    private Button buttonBack;
	
	@FXML
    private Button buttonClose;
	
    @FXML
    private Button buttonCancel;

    @FXML
    private Button buttonConfirm;

    @FXML
    private Button buttonSave;

    @FXML
    private CheckBox checkBoxRegistrationFeePaid;
	
	@FXML
    private ComboBox<String> comboBoxAppointmentSlot;

    @FXML
    private ComboBox<String> comboBoxTitle;
    
    @FXML
    private ComboBox<Surgeon> comboBoxSurgeon;

    @FXML
    private TableView<ConsultationSchedule> tableViewConsultationSchedule;
    
    @FXML
    private TableColumn<ConsultationSchedule, String> day;
    
    @FXML
    private TableColumn<ConsultationSchedule, String> startTime;
    
    @FXML
    private TableColumn<ConsultationSchedule, String> endTime;
    
    @FXML
    private TextField textFieldEmail;

    @FXML
    private TextField textFieldFirstName;

    @FXML
    private TextField textFieldLastName;

    @FXML
    private TextField textFieldMobile;

    @FXML
    private TextField textFieldAddress;
    
    @FXML
    private TextField textFieldSurgeon;
    
    @FXML
    private DatePicker dobDatePicker;
    
    @FXML
    private DatePicker appointmentDatePicker;

	public static void load() {
        try {
        	Parent root = FXMLLoader.load(Objects.requireNonNull(LOCATION));
            Main.stage.setScene(new Scene(root));
            Main.stage.setTitle("Appointment Scheduler");
        } catch (MalformedURLException e) {
            System.out.println("MalformedURLException");
        } catch (IOException e) {
            System.out.println("IOException");
        }
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		ObservableList<Surgeon> surgeon = FXCollections.observableArrayList(Main.surgeonList);
		comboBoxSurgeon.setItems(surgeon);
		
		// Set a custom cell factory if you want to display a specific property of Surgeon in the ComboBox
		comboBoxSurgeon.setCellFactory(param -> new ListCell<Surgeon>() {
            @Override
            protected void updateItem(Surgeon surgeon, boolean empty) {
                super.updateItem(surgeon, empty);
                if (empty || surgeon == null || surgeon.getFirstName() == null || surgeon.getLastName() == null) {
                    setText(null);
                } else {
                    setText(surgeon.getFirstName() + surgeon.getLastName());
                }
            }
        });

        // Optionally, set a listener to handle selection changes
		comboBoxSurgeon.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                
            }
        });
		
		// Create a StringConverter for Person objects
		StringConverter<Surgeon> surgeonConverter = new StringConverter<Surgeon>() {
		    @Override
		    public String toString(Surgeon surgeon) {
		        return surgeon != null ? surgeon.getFirstName() + " " + surgeon.getLastName() : null;
		    }

		    @Override
		    public Surgeon fromString(String surgeon) {
		        // This method is not used for ComboBox display
		        return null;
		    }
		};
		
		comboBoxSurgeon.setConverter(surgeonConverter);
		
		ObservableList<String> titleList = FXCollections.observableArrayList("Mr", "Master", "Mrs", "Miss", 
				"Ms", "Dr", "Reverend", "Dame", "Lord", "Lady");
		appointmentDatePicker.setOnAction(event -> {
			LocalDate selectedDate = appointmentDatePicker.getValue();
			if(selectedDate != null) {
				loadAppointmentSlots(selectedDate);
			}
		});
		
		comboBoxTitle.setItems(titleList);
		comboBoxAppointmentSlot.setItems(FXCollections.emptyObservableList());
        
		day.setCellValueFactory(new PropertyValueFactory<>("day"));
		startTime.setCellValueFactory(new PropertyValueFactory<>("startTime"));
		endTime.setCellValueFactory(new PropertyValueFactory<>("endTime"));
		
		consultationScheduler.getConsultationSchedule(tableViewConsultationSchedule);
		
		// The confirm button will be disable at the time of initialization
		buttonConfirm.setDisable(true);
		
	}
	
	public void loadAppointmentSlots(LocalDate selectedDate) {
		String dayName = getDayName(selectedDate);
		if(dayName.equalsIgnoreCase("MONDAY")) {
			ObservableList<String> AppointmentSlotList = FXCollections.observableArrayList(
					"Monday - 6:00 PM - 6:30 PM", "Monday - 6:30 PM - 7:00 PM", "Monday - 7:00 PM - 7:30 PM", 
					"Monday - 7:30 PM - 8:00 PM", "Monday - 8:00 PM - 8:30 PM", "Monday - 8:30 PM - 9:00 PM");
			comboBoxAppointmentSlot.setItems(AppointmentSlotList);
		} else if(dayName.equalsIgnoreCase("WEDNESDAY")) {
			ObservableList<String> AppointmentSlotList = FXCollections.observableArrayList(
					"Wednesday - 6:00 PM - 6:30 PM", "Wednesday - 6:30 PM - 7:00 PM", "Wednesday - 7:00 PM - 7:30 PM", 
					"Wednesday - 7:30 PM - 8:00 PM", "Wednesday - 8:00 PM - 8:30 PM", "Wednesday - 8:30 PM - 9:00 PM");
			comboBoxAppointmentSlot.setItems(AppointmentSlotList);
		} else if(dayName.equalsIgnoreCase("SATURDAY")) {
			ObservableList<String> AppointmentSlotList = FXCollections.observableArrayList(
					"Saturday - 3:00 PM - 3:30 PM", "Saturday - 3:30 PM - 4:00 PM", "Saturday - 4:30 PM - 5:00 PM", 
					"Saturday - 5:00 PM - 5:30 PM", "Saturday - 5:30 PM - 6:00 PM", "Saturday - 6:00 PM - 6:30 PM",
					"Saturday - 6:30 PM - 7:00 PM", "Saturday - 7:00 PM - 7:30 PM", "Saturday - 7:30 PM - 8:00 PM", 
					"Saturday - 8:00 PM - 8:30 PM", "Saturday - 8:30 PM - 9:00 PM", "Saturday - 9:00 PM - 9:30 PM",
					"Saturday - 9:30 PM - 10:00 PM");
			comboBoxAppointmentSlot.setItems(AppointmentSlotList);
		} else if(dayName.equalsIgnoreCase("SUNDAY")) {
			ObservableList<String> AppointmentSlotList = FXCollections.observableArrayList(
					"Sunday - 3:00 PM - 3:30 PM", "Sunday - 3:30 PM - 4:00 PM", "Sunday - 4:30 PM - 5:00 PM", 
					"Sunday - 5:00 PM - 5:30 PM", "Sunday - 5:30 PM - 6:00 PM", "Sunday - 6:00 PM - 6:30 PM",
					"Sunday - 6:30 PM - 7:00 PM", "Sunday - 7:00 PM - 7:30 PM", "Sunday - 7:30 PM - 8:00 PM", 
					"Sunday - 8:00 PM - 8:30 PM", "Sunday - 8:30 PM - 9:00 PM", "Sunday - 9:00 PM - 9:30 PM",
					"Sunday - 9:30 PM - 10:00 PM");
			comboBoxAppointmentSlot.setItems(AppointmentSlotList);
		} else {
			showInformation("Please select a following day of the week: Monday, Wednesday, Saturday or Sunday.");
			comboBoxAppointmentSlot.setValue(null);
			comboBoxAppointmentSlot.getSelectionModel().clearSelection();
			appointmentDatePicker.setValue(null);
		}
	}
	
	private String getDayName(LocalDate date) {
		DayOfWeek dayOfWeek = date.getDayOfWeek();
        String dayName = dayOfWeek.toString();
        return dayName;
    }
	
	@FXML
	public void buttonSaveClicked(ActionEvent event) {
		try {
			String title = comboBoxTitle.getValue();
			String fname = textFieldFirstName.getText();
			String lname = textFieldLastName.getText();
			String address = textFieldAddress.getText();
			String mobile = textFieldMobile.getText();
			String email = textFieldEmail.getText();
			LocalDate appointmentDate = appointmentDatePicker.getValue();
			String appointmentSlot = comboBoxAppointmentSlot.getValue();
			Surgeon selectedSurgeon = comboBoxSurgeon.getSelectionModel().getSelectedItem();
			
//			if(title == null || fname.isEmpty() || lname.isEmpty() || address.isEmpty() || mobile.toString().isEmpty()
//                    || email.isEmpty() || appointmentDate == null || appointmentSlot == null || selectedSurgeon == null) {
//				showInformation("Please fill patient and appointment details.");				
//			}
			
			if(validateFields(title, fname, lname, address, String.valueOf(mobile), email, appointmentSlot, selectedSurgeon)) {
				
				// Conversion of String Object to LocalTime Object has taken place here
				String[] strings = appointmentSlot.split(" - ");
				String[] parts = strings[1].split(":");
				int hour = Integer.parseInt(parts[0]);
				int minute = Integer.parseInt(parts[1].split("\\s+")[0]); // Extract minutes
				if (strings[1].contains("PM") && hour < 12) {
				    hour += 12;
				}
				LocalTime appointmentStartTime = LocalTime.of(hour, minute);
				
				if(appointmentManager.isAppointmentSlotAvailable(appointmentDate, appointmentStartTime, selectedSurgeon)) {
					showInformation("Appointment available. Now please pay the Registration Fee.");
					checkBoxRegistrationFeePaid.requestFocus();
					buttonSave.setDisable(true);
					buttonConfirm.setDisable(false);
					disableTextFields();
				}
				else {
					showInformation("Appointment slot already booked. Please select an available slot.");
					comboBoxAppointmentSlot.setValue(null);
					comboBoxAppointmentSlot.getSelectionModel().clearSelection();
				}
			}
			
		} catch(Exception e) {
			
		}
		
	}
	
	@FXML
	public void buttonCancelClicked(ActionEvent event) {
		buttonSave.setDisable(false);
		clearTextFields();
		enableTextFields();
	}
	
	@FXML
	public void buttonConfirmClicked(ActionEvent event) {
		try {
			String title = comboBoxTitle.getValue();
			String fname = textFieldFirstName.getText();
			String lname = textFieldLastName.getText();
			String address = textFieldAddress.getText();
			LocalDate dob = dobDatePicker.getValue();
			int mobile = Integer.parseInt(textFieldMobile.getText());
			String email = textFieldEmail.getText();
			LocalDate appointmentDate = appointmentDatePicker.getValue();
			String dayName = getDayName(appointmentDatePicker.getValue());
			String appointmentSlot = comboBoxAppointmentSlot.getValue();
			Surgeon selectedSurgeon = comboBoxSurgeon.getSelectionModel().getSelectedItem();
			
			if(checkBoxRegistrationFeePaid.isSelected()) {
				
				// Conversion of String Object to LocalTime Object has taken place here
				String[] strings = appointmentSlot.split(" - ");
				String[] startParts = strings[1].split(":");
				int startHour = Integer.parseInt(startParts[0]);
				int startMinute = Integer.parseInt(startParts[1].split("\\s+")[0]); // Extract minutes
				if (strings[1].contains("PM") && startHour < 12) {
					startHour += 12;
				}
				LocalTime appointmentStartTime = LocalTime.of(startHour, startMinute);
				
				String[] endParts = strings[2].split(":");
				int endHour = Integer.parseInt(endParts[0]);
				int endMinute = Integer.parseInt(endParts[1].split("\\s+")[0]); // Extract minutes
				if (strings[1].contains("PM") && endHour < 12) {
					endHour += 12;
				}
				LocalTime appointmentEndTime = LocalTime.of(endHour, endMinute);
				
				patientManager.addPatient(title, fname, lname, address, dob, mobile, email);
				appointmentManager.scheduleAppointment(patientManager.searchOnePatientByName(fname, lname), 
						selectedSurgeon, appointmentDate, dayName, appointmentStartTime, appointmentEndTime, 
						LocalDateTime.now(), Main.registrationFee, Main.theOperator);
				
				clearTextFields();
				buttonConfirm.setDisable(true);
				showInformation("Appointment successfully booked.");
				
			}
			
			else {
				showInformation("Please pay the registration fee.");
			}
			
			
		} catch(Exception e) {
			
		}
	}
	
	@FXML
	public void buttonBackClicked() {
		DashboardController.load();
	}
	
	@FXML
	public void buttonCloseClicked() {
		javafx.application.Platform.exit();
	}
	
	private static boolean validateFields(String title, String fname, String lname, String address, String mobileText,
            String email, String appointmentSlot, Surgeon selectedSurgeon) {
        // Validate Title (assuming a non-null and non-empty value is valid)
        if (title == null || title.isEmpty()) {
            showAlert("Title is required.");
            return false;
        }
        // Validate First Name
        if (fname == null || fname.isEmpty()) {
            showAlert("First Name is required.");
            return false;
        }
        
        if(!fname.matches("^[a-zA-Z\\\\s]+$")) {
            showAlert("First Name cannot contain digits or special characters.");
            return false;
        }

        // Validate Last Name
        if (lname == null || lname.isEmpty()) {
            showAlert("Last Name is required.");
            return false;
        }
        
        if(!lname.matches("^[a-zA-Z\\\\s]+$")) {
            showAlert("Last Name cannot contain digits or special characters.");
            return false;
        }

        // Validate Address (assuming a simple format check)
        if (address == null || address.isEmpty()) {
            showAlert("Address is required.");
            return false;
        }

     // Validate Mobile Number
        if (mobileText == null || mobileText.isEmpty()) {
            showAlert("Mobile Number is required.");
            return false;
        }

        // Check if the mobile number has exactly 9 digits
        if (!mobileText.matches("\\d{10}")) {
            showAlert("Invalid Mobile Number. It should have exactly 10 digits.");
            return false;
        }

        // Validate Email (assuming a simple format check)
        if (!email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            showAlert("Invalid Email format.");
            return false;
        }

        // Validate Appointment Slot (assuming a non-null and non-empty value is valid)
        if (appointmentSlot == null || appointmentSlot.isEmpty()) {
            showAlert("Appointment Slot is required.");
            return false;
        }
        
     // Validate Surgeon Selection (assuming a non-null and non-empty value is valid)
        if (selectedSurgeon == null || selectedSurgeon.getSurgeonId() == null) {
            showAlert("Select the Surgeon.");
            return false;
        }
        
        return true;

    }
	
	private static void showInformation(String message) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
	}
	
	private static void showAlert(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Input Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
	
	private void clearTextFields() {
		comboBoxTitle.setValue(null);
		comboBoxTitle.getSelectionModel().clearSelection();
		textFieldFirstName.clear();
		textFieldLastName.clear();
		textFieldAddress.clear();
		textFieldMobile.clear();
		textFieldEmail.clear();
		comboBoxAppointmentSlot.setValue(null);
		comboBoxAppointmentSlot.getSelectionModel().clearSelection();
		comboBoxSurgeon.setValue(null);
		comboBoxSurgeon.getSelectionModel().clearSelection();
		checkBoxRegistrationFeePaid.setSelected(false);
		dobDatePicker.setValue(null);
		appointmentDatePicker.setValue(null);
	}
	
	private void enableTextFields() {
		comboBoxTitle.setDisable(false);
		comboBoxTitle.setDisable(false);
		textFieldFirstName.setDisable(false);
		textFieldLastName.setDisable(false);
		textFieldAddress.setDisable(false);
		textFieldMobile.setDisable(false);
		textFieldEmail.setDisable(false);
		comboBoxAppointmentSlot.setDisable(false);
		comboBoxSurgeon.setDisable(false);
		dobDatePicker.setDisable(false);
		appointmentDatePicker.setDisable(false);
	}
	
	private void disableTextFields() {
		comboBoxTitle.setDisable(true);
		comboBoxTitle.setDisable(true);
		textFieldFirstName.setDisable(true);
		textFieldLastName.setDisable(true);
		textFieldAddress.setDisable(true);
		textFieldMobile.setDisable(true);
		textFieldEmail.setDisable(true);
		comboBoxAppointmentSlot.setDisable(true);
		comboBoxSurgeon.setDisable(true);
		dobDatePicker.setDisable(true);
		appointmentDatePicker.setDisable(true);
	}
	
}
