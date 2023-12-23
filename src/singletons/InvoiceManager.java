package singletons;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import models.Appointment;
import models.Invoice;
import models.OverallFee;

//A singleton class 
/* There is only one instance managing all invoices across the application. */
public class InvoiceManager {
	
	private static InvoiceManager instance = new InvoiceManager();
	List<Invoice> invoiceList = new ArrayList<>();
	
	private InvoiceManager() {
		invoiceList = new ArrayList<Invoice>();
	}
	
	public static InvoiceManager getInstance(){
		if (instance == null)
			instance = new InvoiceManager();
	    return instance;
	}
	
	public void addInvoice(Appointment appointment, LocalDateTime invoiceDateAndTime, OverallFee overallFee,
			Double amountDue) {
        // Checking if an invoice with the same appointment ID already exists
        if (searchInvoiceByAppointmentId(appointment.getAppointmentId()) == null) {
            // Creating a new Invoice object
        	Invoice newInvoice = new Invoice(appointment, invoiceDateAndTime, overallFee, amountDue);

            // Adding the new invoice to the ArrayList
            invoiceList.add(newInvoice);
        }
    }
	
	// A method to search for an invoice with given appointment ID
	public Invoice searchInvoiceByAppointmentId(String appointmentId) {
		for (Invoice invoice : invoiceList) {
			if (invoice.getAppointment().getAppointmentId().equalsIgnoreCase(appointmentId)) {
				return invoice;
	        }
	    }
	    return null;
	}
	
	// A method to bind all the data in HashMap datastructure to a table view in the frontend 
    @SuppressWarnings("unchecked")
	public void getAvailableInvoices(TableView<Invoice> tableViewInvoices) {
    	// Clearing the TableView first
    	tableViewInvoices.getItems().clear(); 

    	// Setting columns
    	TableColumn<Invoice, String> columnInvoiceId = new TableColumn<>("Invoice ID");
    	columnInvoiceId.setCellValueFactory(cellData -> cellData.getValue().fxInvoiceIdProperty());
    	
        TableColumn<Invoice, String> columnAppointmentId = new TableColumn<>("Appointment ID");
        columnAppointmentId.setCellValueFactory(cellData -> cellData.getValue().fxAppointmentIdProperty());
        
        TableColumn<Invoice, Number> columnTotalAmount = new TableColumn<>("Total Amount");
        columnTotalAmount.setCellValueFactory(cellData -> cellData.getValue().fxTotalAmountProperty());
        
        TableColumn<Invoice, Number> columnAmountDue = new TableColumn<>("Amount Due");
        columnAmountDue.setCellValueFactory(cellData -> cellData.getValue().fxAmountDueProperty());
        
        TableColumn<Invoice, Boolean> columnIsInvoicePaid = new TableColumn<>("Payment Status");
        columnIsInvoicePaid.setCellValueFactory(cellData -> cellData.getValue().fxIsInvoicePaidProperty());
        
        TableColumn<Invoice, LocalDateTime> columnInvoiceDateAndTime = new TableColumn<>("Invoice Date and Time");
        columnInvoiceDateAndTime.setCellValueFactory(cellData -> cellData.getValue().fxInvoiceDateProperty());

        // Clear existing columns and add new columns to the TableView
        tableViewInvoices.getColumns().clear();
        tableViewInvoices.getColumns().addAll(columnInvoiceId, columnAppointmentId,
        		columnTotalAmount, columnAmountDue, columnIsInvoicePaid, columnInvoiceDateAndTime);

        // Get available invoices from the invoiceList and add them to the TableView
        tableViewInvoices.setItems(FXCollections.observableArrayList(invoiceList));
    }
}
