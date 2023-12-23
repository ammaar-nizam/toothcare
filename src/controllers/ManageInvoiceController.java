package controllers;

import singletons.AppointmentManager;
import singletons.FeeManager;
import singletons.InvoiceManager;
import application.Main;
import factories.AbstractTreatmentFactory;
import factories.PreventiveTreatmentFactory;
import factories.RestorativeTreatmentFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.print.PrinterJob;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import models.Appointment;
import models.Invoice;
import models.Treatment;

public class ManageInvoiceController implements Initializable{
	
	public static final URL LOCATION = Main.class.getResource("../views/ManageInvoice.fxml");

	InvoiceManager invoiceManager = InvoiceManager.getInstance();
	AppointmentManager appointmentManager = AppointmentManager.getInstance();
	FeeManager feeManager = FeeManager.getInstance();
	
	@FXML
    private Button buttonBack;

    @FXML
    private Button buttonClose;

    @FXML
    private Button buttonCreate;

    @FXML
    private Button buttonReset;

    @FXML
    private Button buttonView;

    @FXML
    private CheckBox checkBoxCleaning;

    @FXML
    private CheckBox checkBoxFilling;

    @FXML
    private CheckBox checkBoxNerveFilling;

    @FXML
    private CheckBox checkBoxRegistrationFee;

    @FXML
    private CheckBox checkBoxRootCanalTherapy;

    @FXML
    private CheckBox checkBoxWhitening;

    @FXML
    private TableView<Invoice> tableViewInvoices;
    
    @FXML
    private TableColumn<Invoice, String> amountDue;

    @FXML
    private TableColumn<Invoice, String> appointmentId;
    
    @FXML
    private TableColumn<Invoice, String> invoiceDate;

    @FXML
    private TableColumn<Invoice, String> invoiceId;

    @FXML
    private TableColumn<Invoice, String> registrationFee;
    
    @FXML
    private TableColumn<Invoice, String> totalAmount;

    @FXML
    private TextField textFieldAmountDue;

    @FXML
    private TextField textFieldAppointmentId;

    @FXML
    private TextField textFieldPatientName;

    @FXML
    private TextField textFieldRegistrationFee;

    @FXML
    private TextField textFieldTotalFee;
    
    @FXML
    private TextArea area;
    
    private double initialFee = 0.0;
	
	public static void load() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(LOCATION));
            Main.stage.setScene(new Scene(root));
            Main.stage.setTitle("Invoice Details");
        } catch (MalformedURLException e) {
            System.out.println("MalformedURLException");
        } catch (IOException e) {
            System.out.println("IOException");
        }
    }
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		buttonView.setDisable(true);
		
		checkBoxCleaning.selectedProperty().addListener((obs, oldVal, newVal) -> updateTotalFee());
	    checkBoxWhitening.selectedProperty().addListener((obs, oldVal, newVal) -> updateTotalFee());
	    checkBoxFilling.selectedProperty().addListener((obs, oldVal, newVal) -> updateTotalFee());
	    checkBoxNerveFilling.selectedProperty().addListener((obs, oldVal, newVal) -> updateTotalFee());
	    checkBoxRootCanalTherapy.selectedProperty().addListener((obs, oldVal, newVal) -> updateTotalFee());

	}

	private void updateTotalFee() {
		
	    double totalFee = initialFee;

	    // Calculate total fee based on checkbox selection
	    if (checkBoxCleaning.isSelected()) {
	    	AbstractTreatmentFactory preventiveFactory = new PreventiveTreatmentFactory();
            Treatment cleaning = preventiveFactory.createTreatment("CLEANING");
	        totalFee = totalFee + cleaning.getTreatmentCost();
	    }
	    if (checkBoxWhitening.isSelected()) {
	    	AbstractTreatmentFactory preventiveFactory = new PreventiveTreatmentFactory();
            Treatment whitening = preventiveFactory.createTreatment("WHITENING");
            totalFee = totalFee + whitening.getTreatmentCost();
	    }
	    if (checkBoxFilling.isSelected()) {
	    	AbstractTreatmentFactory restorativeFactory = new RestorativeTreatmentFactory();
            Treatment filling = restorativeFactory.createTreatment("FILLING");
            totalFee = totalFee + filling.getTreatmentCost();
	    }
	    if (checkBoxNerveFilling.isSelected()) {
	    	AbstractTreatmentFactory restorativeFactory = new RestorativeTreatmentFactory();
            Treatment nerveFilling = restorativeFactory.createTreatment("NERVE FILLING");
            totalFee = totalFee + nerveFilling.getTreatmentCost();
	    }
	    if (checkBoxRootCanalTherapy.isSelected()) {
	    	AbstractTreatmentFactory restorativeFactory = new RestorativeTreatmentFactory();
            Treatment rootCanalTherapy = restorativeFactory.createTreatment("ROOT CANAL THERAPY");
            totalFee = totalFee + rootCanalTherapy.getTreatmentCost();
	    }

	    // Update the corresponding text field with the calculated total fee
	    textFieldTotalFee.setText(String.valueOf(totalFee));
	    textFieldAmountDue.setText(String.valueOf(totalFee - 1000.00));
	    
	}
	
	@FXML
	public void buttonBackClicked(ActionEvent event) {
		DashboardController.load();
	}
	
	@FXML
	public void buttonCloseClicked(ActionEvent event) {
		javafx.application.Platform.exit();
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
				textFieldPatientName.setText(searchedAppointment.getPatient().getTitle() + " " + 
				searchedAppointment.getPatient().getFirstName() + " " + searchedAppointment.getPatient().getLastName());
				textFieldRegistrationFee.setText(searchedAppointment.getRegistrationFee().getFeeAmount().toString());
			}
			
		} else {
			
		}	
    }

    @FXML
    public void buttonCreateClicked(ActionEvent event) {
    	
    	try {
    		
    		String cleaning = "No";
    		String whitening = "No";
    		String filling = "No";
    		String nerveFilling = "No";
    		String rootCanalTherapy = "No";
    		Double overallFee = Double.parseDouble(textFieldTotalFee.getText());
    		Double registrationFee = Double.parseDouble(textFieldRegistrationFee.getText());
    		Double amountPaid = Double.parseDouble(textFieldAmountDue.getText());
    		
    		if(checkBoxCleaning.isSelected())
    			cleaning = "Yes";
    		if(checkBoxWhitening.isSelected())
    			whitening = "Yes";
    		if(checkBoxFilling.isSelected())
    			filling = "Yes";
    		if(checkBoxNerveFilling.isSelected())
    			nerveFilling = "Yes";
    		if(checkBoxRootCanalTherapy.isSelected())
    			rootCanalTherapy = "Yes";
    		
    		invoiceManager.addInvoice(appointmentManager.searchAppointmentById(textFieldAppointmentId.getText()),
    				LocalDateTime.now(), feeManager.addOverallFee(Double.parseDouble(textFieldTotalFee.getText()), 
    						"This fee includes Registration fee", true), 
    				Double.parseDouble(textFieldAmountDue.getText()));
    		
    		showInformation("Thank you for making the payment. You paid Rs. " + textFieldAmountDue.getText());
    		Invoice invoice = invoiceManager.searchInvoiceByAppointmentId(textFieldAppointmentId.getText());
    		setInvoiceDetailsinViewArea(invoice.getInvoiceDateAndTime(), invoice.getInvoiceId(), 
    				invoice.getAppointment().getAppointmentId(), 
    				invoice.getAppointment().getPatient().getTitle() + " " + invoice.getAppointment().getPatient().getFirstName() + 
    				" " + invoice.getAppointment().getPatient().getLastName(), 
    				cleaning, whitening, filling, nerveFilling, rootCanalTherapy, 
    				overallFee, registrationFee, amountPaid);
    		buttonView.setDisable(false);
    	} catch(Exception ex) {
    		System.out.println(ex.getMessage());
    	}
    }

    @FXML
    public void buttonResetClicked(ActionEvent event) {
    	clearTextFields();
    }

    public void setInvoiceDetailsinViewArea(LocalDateTime dateTime, String invoiceId, String appointmentId,
    		String patientName, String cleaning, String whitening, String filling, String nerveFilling, 
    		String rootCanalTherapy, Double totalFee, Double registrationFee, Double amountPaid) {
    	area.setText("*****************************************************************\n");
    	area.setText(area.getText() + "	             TOOTHCARE NUGEGODA INVOICE                    \n");
    	area.setText(area.getText() + "*****************************************************************\n");

    	LocalDate date = dateTime.toLocalDate();
    	
    	area.setText(area.getText() + "Inv ID: " + invoiceId + "\t\t" + "Apt ID: " + appointmentId +"\n");
    	area.setText(area.getText() + "Inv Date: " + date + "\t" + "Patient Name: " + patientName +"\n");
    	area.setText(area.getText() + "\nTREATMENTS TAKEN: \n");
    	area.setText(area.getText() + "1. Cleaning: " + "\t\t\t\t\t" + cleaning + "\n");
    	area.setText(area.getText() + "2. Whitening: " + "\t\t\t\t\t" + whitening + "\n");
    	area.setText(area.getText() + "3. Filling: " + "\t\t\t\t\t\t" + filling + "\n");
    	area.setText(area.getText() + "4. Nerve Filling: " + "\t\t\t\t" + nerveFilling + "\n");
    	area.setText(area.getText() + "5. Root Canal Therapy: " + "\t\t\t" + rootCanalTherapy + "\n");
    	area.setText(area.getText() + "\nTotal Fee: " + "\t\t\t\t" + totalFee + "\n");
    	area.setText(area.getText() + "Registration Fee: "  + "\t\t\t" + registrationFee + "\n");
    	area.setText(area.getText() + "Amount Paid: " + "\t\t\t\t" + amountPaid + "\n");
    }
    
    @FXML
    public void buttonViewClicked(ActionEvent event) {
    	try {
    		printTextArea(area);
    	}catch(Exception ex) {
    		System.out.println(ex.getMessage());
    	}
    }
    
    public static void printTextArea(TextArea textArea) {
        PrinterJob job = PrinterJob.createPrinterJob();

        if (job != null && job.showPrintDialog(textArea.getScene().getWindow())) {
            boolean success = job.printPage(textArea);

            if (success) {
                job.endJob();
            }
        }
    }
    
    private void clearTextFields() {
		textFieldAppointmentId.clear();
		textFieldPatientName.clear();
		checkBoxCleaning.setSelected(false);
		checkBoxWhitening.setSelected(false);
		checkBoxFilling.setSelected(false);
		checkBoxNerveFilling.setSelected(false);
		checkBoxRootCanalTherapy.setSelected(false);
		textFieldTotalFee.clear();
		textFieldRegistrationFee.clear();
		textFieldAmountDue.clear();
		area.clear();
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
