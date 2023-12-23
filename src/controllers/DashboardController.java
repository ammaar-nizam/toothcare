package controllers;

import application.Main;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;

public class DashboardController implements Initializable{
	
	public static final URL LOCATION = Main.class.getResource("../views/Dashboard.fxml");
	
	@FXML
    private Button buttonAppointmentScheduler;

    @FXML
    private Button buttonClose;

    @FXML
    private Button buttonCreateInvoices;

    @FXML
    private Button buttonUpdateAppointments;

    @FXML
    private Button buttonViewAppointments;

	public static void load() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(LOCATION));
            Main.stage.setScene(new Scene(root));
            Main.stage.setTitle("Dashboard");
        } catch (MalformedURLException e) {
            System.out.println("MalformedURLException");
        } catch (IOException e) {
            System.out.println("IOException");
        }
    }
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
	@FXML
	public void buttonAppointmentSchedulerClicked(ActionEvent event) {
		AppointmentSchedulerController.load();
    }

    @FXML
    public void buttonCloseClicked(ActionEvent event) {
    	closeApplication();
    }

    @FXML
    public void buttonCreateInvoicesClicked(ActionEvent event) {
    	ManageInvoiceController.load();
    }

    @FXML
    public void buttonUpdateAppointmentsClicked(ActionEvent event) {
    	UpdateAppointmentController.load();
    }

    @FXML
    public void buttonViewAppointmentsClicked(ActionEvent event) {
    	ViewAppointmentController.load();
    }
	
	private void closeApplication() {
        javafx.application.Platform.exit();
    }
}
