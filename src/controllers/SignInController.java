package controllers;

import application.Main;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

public class SignInController implements Initializable {

	public static final URL LOCATION = Main.class.getResource("../views/SignIn.fxml");

    @FXML
    TextField fieldUsername;

    @FXML
    PasswordField fieldPassword;
    
    @FXML
    private Button buttonClear;

    @FXML
    private Button buttonSignIn;
    
    @FXML
    Label labelMessage;
    
    public static void load() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(LOCATION));
            Main.stage.setScene(new javafx.scene.Scene(root));
            Main.stage.setTitle("Sign In");
        } catch (MalformedURLException e) {
            System.out.println("MalformedURLException");
        } catch (IOException e) {
            System.out.println("IOException");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    
    }   

    @FXML
    public void handleSignIn() {
    	String username = fieldUsername.getText().trim();
        String password = fieldPassword.getText().trim();
        
        if (username.equalsIgnoreCase("GAYANI") && password.equals("gayani@123")) {
        	clearFields();
        	showInformation("User login Successful.");
        	DashboardController.load();
        }else {
        	showAlert("Incorrect Username or Password.");
        	clearFields();
        }
        
    }

    @FXML
    void handleClearFields() {
    	clearFields();
    }
    
    public void clearFields() {
        fieldUsername.clear();
        fieldPassword.clear();
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
