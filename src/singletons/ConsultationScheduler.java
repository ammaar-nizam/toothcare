package singletons;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import models.ConsultationSchedule;


// A singleton class 
/* There is only one instance managing all consultation across the application. */
public class ConsultationScheduler {
	
	private static ConsultationScheduler instance = new ConsultationScheduler();
	ObservableList<ConsultationSchedule> consultationSchedules = FXCollections.observableArrayList(
    		new ConsultationSchedule(1, "Monday", "6:00 PM", "9:00 PM"),
            new ConsultationSchedule(2, "Wednesday", "6:00 PM", "9:00 PM"),
            new ConsultationSchedule(3, "Saturday", "3:00 PM", "10:00 PM"),
            new ConsultationSchedule(4, "Sunday", "3:00 PM", "10:00 PM"));

	// A private constructor to avoid creation of multiple objects at multiple places
	private ConsultationScheduler() {

    }
    
	// A method to get the only running instance of the object AppointmentManager
    public static ConsultationScheduler getInstance(){
    	if (instance == null)
			instance = new ConsultationScheduler();
	      return instance;
	}

    // A method to bind all the data in the ArrayList datastructure to a table view in the frontend 
    @SuppressWarnings("unchecked")
	public void getConsultationSchedule(TableView<ConsultationSchedule> tableViewConsultationSchedule) {
    	// Clearing the TableView first
    	tableViewConsultationSchedule.getItems().clear(); 

    	// Setting columns
        TableColumn<ConsultationSchedule, String> columnDayOfTheWeek = new TableColumn<>("Day Of The Week");
        columnDayOfTheWeek.setCellValueFactory(cellData -> cellData.getValue().fxDayOfTheWeekProperty());
        
        TableColumn<ConsultationSchedule, String> columnOpenTime = new TableColumn<>("Open Time");
        columnOpenTime.setCellValueFactory(cellData -> cellData.getValue().fxOpenTimeProperty());
        
        TableColumn<ConsultationSchedule, String> columnCloseTime = new TableColumn<>("Close Time");
        columnCloseTime.setCellValueFactory(cellData -> cellData.getValue().fxCloseTimeProperty());
        
        // Clear existing columns and add new columns to the TableView
        tableViewConsultationSchedule.getColumns().clear();
        tableViewConsultationSchedule.getColumns().addAll(columnDayOfTheWeek, columnOpenTime, columnCloseTime);

        // Get available ConsultationSchedule from the arraylist and add them to the TableView
        tableViewConsultationSchedule.setItems(FXCollections.observableArrayList(consultationSchedules));
    }
}
