package controllers;

import singletons.*;
import application.Main;

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
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;
import models.Appointment;
import models.Surgeon;

public class UpdateAppointmentController implements Initializable{
	
	public static final URL LOCATION = Main.class.getResource("../views/UpdateAppointment.fxml");
	
	AppointmentManager appointmentManager = AppointmentManager.getInstance();

	@FXML
    private Button buttonBack;

    @FXML
    private Button buttonClose;

    @FXML
    private Button buttonReset;

    @FXML
    private Button buttonUpdate;

    @FXML
    private ComboBox<String> comboBoxAppointmentSlot;

    @FXML
    private ComboBox<Surgeon> comboBoxSurgeon;

    @FXML
    private DatePicker datePickerAppointmentDate;

    @FXML
    private TextField textFieldAddress;

    @FXML
    private TextField textFieldAppointmentId;

    @FXML
    private TextField textFieldDOB;

    @FXML
    private TextField textFieldEmail;

    @FXML
    private TextField textFieldMobile;

    @FXML
    private TextField textFieldPatientId;

    @FXML
    private TextField textFieldPatientName;
    
    private LocalDate previousValue;
    private String previousValueComboBox;
	
	public static void load() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(LOCATION));
            Main.stage.setScene(new Scene(root));
            Main.stage.setTitle("Update Appointments");
        } catch (MalformedURLException e) {
            System.out.println("MalformedURLException");
        } catch (IOException e) {
            System.out.println("IOException");
        }
    }
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		datePickerAppointmentDate.setOnAction(event -> {
			LocalDate selectedDate = datePickerAppointmentDate.getValue();
			if(selectedDate != null) {
				loadAppointmentSlots(selectedDate, previousValue, previousValueComboBox);
			}
		});		
		
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
	}
	
	@FXML
	public void OnMouseClicked(MouseEvent event) {
		String preSetAppointmentIdPrefix = "APPOINTMENT_";
		textFieldAppointmentId.setText(preSetAppointmentIdPrefix);
		textFieldAppointmentId.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.startsWith(preSetAppointmentIdPrefix)) {
            	textFieldAppointmentId.setText(preSetAppointmentIdPrefix + newValue);
            }
        });
		textFieldAppointmentId.positionCaret(preSetAppointmentIdPrefix.length());
	}
	
	@FXML
    public void onEnter(ActionEvent event) {
		Appointment searchedAppointment = appointmentManager.searchAppointmentById(textFieldAppointmentId.getText());
		if(searchedAppointment != null) {
			if(searchedAppointment.getAppointmentId() == null) {
				showAlert("Appointment not found.");
			}
			else {
				previousValue = searchedAppointment.getAppointmentDate();
				datePickerAppointmentDate.setValue(previousValue);
				
				previousValueComboBox = searchedAppointment.getDayOfTheWeek() + " - " + 
						searchedAppointment.getStartTime() + " - " + searchedAppointment.getEndTime();
				comboBoxAppointmentSlot.setValue(previousValueComboBox);
				
				comboBoxSurgeon.setValue(searchedAppointment.getSurgeon());
				
				textFieldPatientId.setText(String.valueOf(searchedAppointment.getPatient().getPatientId()));
				textFieldPatientName.setText(searchedAppointment.getPatient().getTitle() + " " + 
				searchedAppointment.getPatient().getFirstName() + " " + searchedAppointment.getPatient().getLastName());
				textFieldAddress.setText(searchedAppointment.getPatient().getAddress());
				textFieldMobile.setText(String.valueOf(searchedAppointment.getPatient().getMobileNumber()));
				textFieldEmail.setText(searchedAppointment.getPatient().getEmail());
				textFieldDOB.setText(searchedAppointment.getPatient().getDateOfBirth().toString());
			}
			
		} else {
			
		}	
    }
	
	@FXML
    public void buttonResetClicked(ActionEvent event) {
		clearTextFields();
    }

    @FXML
    public void buttonUpdateClicked(ActionEvent event) {
    	Appointment appointmentToUpdate = appointmentManager.searchAppointmentById(textFieldAppointmentId.getText());
    	if (appointmentToUpdate != null) {
    		try {
    			LocalDate newAppointmentDate = datePickerAppointmentDate.getValue();
    			String newAppointmentSlot = comboBoxAppointmentSlot.getValue();
    			String newDayName = getDayName(datePickerAppointmentDate.getValue());
    			Surgeon newSelectedSurgeon = comboBoxSurgeon.getSelectionModel().getSelectedItem();
    			
    			// Conversion of String Object to LocalTime Object has taken place here
    			String[] strings = newAppointmentSlot.split(" - ");
    			String[] parts = strings[1].split(":");
    			int hour = Integer.parseInt(parts[0]);
    			int minute = Integer.parseInt(parts[1].split("\\s+")[0]); // Extract minutes
    			if (strings[1].contains("PM") && hour < 12) {
    			    hour += 12;
    			}
    			LocalTime newAppointmentStartTime = LocalTime.of(hour, minute);
    			
    			String[] endParts = strings[2].split(":");
				int endHour = Integer.parseInt(endParts[0]);
				int endMinute = Integer.parseInt(endParts[1].split("\\s+")[0]); // Extract minutes
				if (strings[1].contains("PM") && endHour < 12) {
					endHour += 12;
				}
				LocalTime newAppointmentEndTime = LocalTime.of(endHour, endMinute);
    			
    			if(appointmentManager.isAppointmentSlotAvailable(newAppointmentDate, newAppointmentStartTime, 
    					newSelectedSurgeon)) {
    				appointmentToUpdate.setAppointmentDate(newAppointmentDate);
    				appointmentToUpdate.setDayOfTheWeek(newDayName);
    				appointmentToUpdate.setStartTime(newAppointmentStartTime);
    				appointmentToUpdate.setEndTime(newAppointmentEndTime);
    				appointmentToUpdate.setSurgeon(newSelectedSurgeon);
    				appointmentToUpdate.setDateAndTimeAppointmentMade(LocalDateTime.now());
					showInformation("Appointment updated.");
					clearTextFields();
				}
				else {
					showInformation("Appointment slot already booked. Please select an available slot.");
					comboBoxAppointmentSlot.setValue(appointmentToUpdate.getDayOfTheWeek() + " - " + 
							appointmentToUpdate.getStartTime() + " - " + appointmentToUpdate.getEndTime());
					//comboBoxAppointmentSlot.getSelectionModel().clearSelection();
				}
        		
    		} catch(Exception ex) {
    			System.out.println(ex.getMessage());
    		}
    		
        } else {
            System.out.println("Appointment not found.");
        }
    }
	
	@FXML
	public void buttonBackClicked(ActionEvent event) {
		DashboardController.load();
	}
	
	@FXML
	public void buttonCloseClicked(ActionEvent event) {
		javafx.application.Platform.exit();
	}
	
	private void clearTextFields() {
		textFieldAppointmentId.clear();
		datePickerAppointmentDate.setValue(null);
		comboBoxAppointmentSlot.setValue(null);
		comboBoxSurgeon.setValue(null);
		textFieldPatientId.clear();
		textFieldPatientName.clear();
		textFieldAddress.clear();
		textFieldMobile.clear();
		textFieldEmail.clear();
		textFieldDOB.clear();
	}
	
	private String getDayName(LocalDate date) {
		DayOfWeek dayOfWeek = date.getDayOfWeek();
        String dayName = dayOfWeek.toString();
        return dayName;
    }
	
	public void loadAppointmentSlots(LocalDate selectedDate, LocalDate previousValue, String previousValueComboBox) {
		String dayName = getDayName(selectedDate);
		if(dayName.equalsIgnoreCase("MONDAY")) {
			ObservableList<String> AppointmentSlotList = FXCollections.observableArrayList(
					"Monday - 6:00 PM - 6:30 PM", "Monday - 6:30 PM - 7:00 PM", "Monday - 7:00 PM - 7:30 PM", 
					"Monday - 7:30 PM - 8:00 PM", "Monday - 8:00 PM - 8:30 PM", "Monday - 8:30 PM - 9:00 PM");
			comboBoxAppointmentSlot.setValue(null);
			comboBoxAppointmentSlot.setItems(AppointmentSlotList);
		} else if(dayName.equalsIgnoreCase("WEDNESDAY")) {
			ObservableList<String> AppointmentSlotList = FXCollections.observableArrayList(
					"Wednesday - 6:00 PM - 6:30 PM", "Wednesday - 6:30 PM - 7:00 PM", "Wednesday - 7:00 PM - 7:30 PM", 
					"Wednesday - 7:30 PM - 8:00 PM", "Wednesday - 8:00 PM - 8:30 PM", "Wednesday - 8:30 PM - 9:00 PM");
			comboBoxAppointmentSlot.setValue(null);
			comboBoxAppointmentSlot.setItems(AppointmentSlotList);
		} else if(dayName.equalsIgnoreCase("SATURDAY")) {
			ObservableList<String> AppointmentSlotList = FXCollections.observableArrayList(
					"Saturday - 3:00 PM - 3:30 PM", "Saturday - 3:30 PM - 4:00 PM", "Saturday - 4:30 PM - 5:00 PM", 
					"Saturday - 5:00 PM - 5:30 PM", "Saturday - 5:30 PM - 6:00 PM", "Saturday - 6:00 PM - 6:30 PM",
					"Saturday - 6:30 PM - 7:00 PM", "Saturday - 7:00 PM - 7:30 PM", "Saturday - 7:30 PM - 8:00 PM", 
					"Saturday - 8:00 PM - 8:30 PM", "Saturday - 8:30 PM - 9:00 PM", "Saturday - 9:00 PM - 9:30 PM",
					"Saturday - 9:30 PM - 10:00 PM");
			comboBoxAppointmentSlot.setValue(null);
			comboBoxAppointmentSlot.setItems(AppointmentSlotList);
		} else if(dayName.equalsIgnoreCase("SUNDAY")) {
			ObservableList<String> AppointmentSlotList = FXCollections.observableArrayList(
					"Sunday - 3:00 PM - 3:30 PM", "Sunday - 3:30 PM - 4:00 PM", "Sunday - 4:30 PM - 5:00 PM", 
					"Sunday - 5:00 PM - 5:30 PM", "Sunday - 5:30 PM - 6:00 PM", "Sunday - 6:00 PM - 6:30 PM",
					"Sunday - 6:30 PM - 7:00 PM", "Sunday - 7:00 PM - 7:30 PM", "Sunday - 7:30 PM - 8:00 PM", 
					"Sunday - 8:00 PM - 8:30 PM", "Sunday - 8:30 PM - 9:00 PM", "Sunday - 9:00 PM - 9:30 PM",
					"Sunday - 9:30 PM - 10:00 PM");
			comboBoxAppointmentSlot.setValue(null);
			comboBoxAppointmentSlot.setItems(AppointmentSlotList);
		} else {
			showInformation("Please select a following day of the week: Monday, Wednesday, Saturday or Sunday.");
			comboBoxAppointmentSlot.setValue(previousValueComboBox);
			datePickerAppointmentDate.setValue(previousValue);
		}
		
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

}
