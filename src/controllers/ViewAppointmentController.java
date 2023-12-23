package controllers;

import singletons.*;
import application.Main;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import models.Appointment;

public class ViewAppointmentController implements Initializable{
	
	public static final URL LOCATION = Main.class.getResource("../views/ViewAppointment.fxml");
	
	AppointmentManager appointmentManager = AppointmentManager.getInstance();

	@FXML
    private Button buttonBack;
	
	@FXML
    private Button buttonClose;
	
	@FXML
    private Button buttonReset;
    
    @FXML
    private Button buttonSearch;

    @FXML
    private TextField textFieldAppointmentId;

    @FXML
	private TableView<Appointment> tableViewAppointment;
	
	@FXML
	private TableColumn<Appointment, String> appointmentId;
	
	@FXML
	private TableColumn<Appointment, String> patientName;
	 
	@FXML
	private TableColumn<Appointment, String> surgeonName;
	
	@FXML
	private TableColumn<Appointment, String> day;
	
	@FXML
	private TableColumn<Appointment, String> startTime;

	@FXML
	private TableColumn<Appointment, String> endTime;
	
	@FXML
    private DatePicker datePickerSearchDate;

	@FXML
	private DatePicker datePickerSearchDateFrom;

    @FXML
    private DatePicker datePickerSearchDateTo;
    
    @FXML
    private Label movingMessage;

    @FXML
    private TextField textFieldPatientName;
	
	public static void load() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(LOCATION));
            Main.stage.setScene(new Scene(root));
            Main.stage.setTitle("View Appointments");
        } catch (MalformedURLException e) {
            System.out.println("MalformedURLException");
        } catch (IOException e) {
            System.out.println("IOException");
        }
    }
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	
		appointmentManager.getAvailableAppointments(tableViewAppointment);
		
		buttonSearch.setOnAction(this::buttonSearchClicked);
	
	}
	
	public List<Appointment> searchAppointments(String appointmentId, String patientName, LocalDate searchDate,
			LocalDate searchDateFrom, LocalDate searchDateTo) {
		List<Appointment> searchResults = new ArrayList<>();

		for (Appointment appointment : appointmentManager.getAppointmentsMap().values()) {
			boolean matches = true;

			// Check each criterion if provided and filter accordingly
			if (appointmentId != null && !appointmentId.isEmpty()) {
				matches = matches && appointment.getAppointmentId().equals(appointmentId);
			}
			
			if (patientName != null && !patientName.isEmpty()) {
				matches = matches && (containsPartialName(appointment.getPatient().getFirstName(), patientName) ||
                        containsPartialName(appointment.getPatient().getLastName(), patientName));
			}

			if (searchDate != null) {
				matches = matches && appointment.getAppointmentDate().equals(searchDate);
			}

			if (searchDateFrom != null && searchDateTo != null) {
				matches = matches && 
						(appointment.getAppointmentDate().isAfter(searchDateFrom) || 
								appointment.getAppointmentDate().isEqual(searchDateFrom)) &&
						(appointment.getAppointmentDate().isBefore(searchDateTo) || 
								appointment.getAppointmentDate().isEqual(searchDateTo));
			}

			// If all criteria match, add to search results
			if (matches) {
				searchResults.add(appointment);
			}
		}

		return searchResults;
	}
	
	private boolean containsPartialName(String fullNamePart, String partialName) {
	    return fullNamePart.toLowerCase().contains(partialName.toLowerCase());
	}
	
	@FXML
    public void buttonSearchClicked(ActionEvent event) {
		String appointmentId = textFieldAppointmentId.getText();
		String patientName = textFieldPatientName.getText();
		LocalDate searchDate = datePickerSearchDate.getValue();
		LocalDate searchDateFrom = datePickerSearchDateFrom.getValue();
		LocalDate searchDateTo = datePickerSearchDateTo.getValue();
		
		List<Appointment> searchResults = searchAppointments(appointmentId, patientName, searchDate, 
				searchDateFrom, searchDateTo);
		
		tableViewAppointment.getItems().clear();
		ObservableList<Appointment> filteredAppointments = FXCollections.observableArrayList(searchResults);
		tableViewAppointment.setItems(filteredAppointments);
		
//		ObservableList<Appointment> filteredAppointments = FXCollections.observableArrayList();
//		if(textFieldAppointmentId.getText() != null) {
//			
//			tableViewAppointment.setItems(filteredAppointments);
//		}else if(textFieldPatientName.getText() != null) {
//			
//		}else if(datePickerSearchDate.getValue() != null) {
//			
//		}else if(datePickerSearchDateFrom.getValue() != null) {
//			
//		}else {
//			showInformation("Something is wrong.");
//		}
//		tableViewAppointment.setItems(filteredAppointments);
		
    }
	
	@FXML
	public void OnTextFieldPatientNameMouseClicked(MouseEvent event) {
//		String preSetAppointmentIdPrefix = "APPOINTMENT_";
//		ChangeListener<String> listener = (observable, oldValue, newValue) -> {
//	        if (!newValue.startsWith(preSetAppointmentIdPrefix)) {
//	            textFieldAppointmentId.setText(preSetAppointmentIdPrefix + newValue);
//	        }
//	    };
//		textFieldAppointmentId.textProperty().removeListener(listener);
//		textFieldAppointmentId.clear();

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
    public void buttonResetClicked(ActionEvent event) {
		clearTextFields();
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
		textFieldPatientName.clear();
		datePickerSearchDate.setValue(null);
		datePickerSearchDateFrom.setValue(null);
		datePickerSearchDateTo.setValue(null);
	}
	
}
