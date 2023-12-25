package models;

import java.time.LocalDateTime;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Invoice {
	
	// Fields
	private static int counter = 0;
	private String invoiceId;
	private Appointment appointment;
    private LocalDateTime invoiceDateAndTime;
    private OverallFee overallFee;
    private Double amountDue;
    
    // Constructor
	public Invoice(Appointment appointment, LocalDateTime invoiceDateAndTime, OverallFee overallFee,
			Double amountDue) {
		super();
		this.invoiceId = generateInvoiceId();
		this.appointment = appointment;
		this.invoiceDateAndTime = invoiceDateAndTime;
		this.overallFee = overallFee;
		this.amountDue = amountDue;
	}
	
	// These properties are set to work with the Table View binding
	 	private final StringProperty fxInvoiceId = new SimpleStringProperty();
	    private final StringProperty fxAppointmentId = new SimpleStringProperty();
	    private final ObjectProperty<LocalDateTime> fxInvoiceDateAndTime = new SimpleObjectProperty<LocalDateTime>();
	    private final DoubleProperty fxTotalAmount = new SimpleDoubleProperty();
	    private final DoubleProperty fxAmountDue = new SimpleDoubleProperty();
	    private final BooleanProperty fxIsInvoicePaid = new SimpleBooleanProperty();

	// A method to create Invoice IDs
	private String generateInvoiceId() {
        String prefix = "INVOICE_";
        return prefix + ++counter;
    }
	
	// Getters and Setters
	public String getInvoiceId() {
		return invoiceId;
	}

	public Appointment getAppointment() {
		return appointment;
	}

	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}

	public LocalDateTime getInvoiceDateAndTime() {
		return invoiceDateAndTime;
	}

	public void setInvoiceDateAndTime(LocalDateTime invoiceDateAndTime) {
		this.invoiceDateAndTime = invoiceDateAndTime;
	}

	public OverallFee getOverallFee() {
		return overallFee;
	}

	public void setOverallFee(OverallFee overallFee) {
		this.overallFee = overallFee;
	}

	public Double getAmountDue() {
		return amountDue;
	}

	public void setAmountDue(Double amountDue) {
		this.amountDue = amountDue;
	}

	// These methods are to bind the fields in the Invoice class to the Table View in frontend
	public StringProperty fxInvoiceIdProperty() {
		fxInvoiceId.set(invoiceId);
		return fxInvoiceId;
	}

	public StringProperty fxAppointmentIdProperty() {
		fxAppointmentId.set(appointment.getAppointmentId());
		return fxAppointmentId;
	}

	public ObjectProperty<LocalDateTime> fxInvoiceDateProperty() {
		fxInvoiceDateAndTime.set(invoiceDateAndTime);
		return fxInvoiceDateAndTime;
	}
	
	public DoubleProperty fxTotalAmountProperty() {
		fxTotalAmount.set(overallFee.getFeeAmount());
		return fxTotalAmount;
	}

	public DoubleProperty fxAmountDueProperty() {
		fxAmountDue.set(amountDue);
		return fxAmountDue;
	}
	
	public BooleanProperty fxIsInvoicePaidProperty() {
		fxIsInvoicePaid.set(overallFee.getIsPaid());
		return fxIsInvoicePaid;
	}

	@Override
	public String toString() {
		return "Invoice [invoiceId=" + invoiceId + ", appointment=" + appointment + ", invoiceDateAndTime="
				+ invoiceDateAndTime + ", overallFee=" + overallFee + ", amountDue=" + amountDue + ", fxInvoiceId="
				+ fxInvoiceId + ", fxAppointmentId=" + fxAppointmentId + ", fxInvoiceDateAndTime="
				+ fxInvoiceDateAndTime + ", fxTotalAmount=" + fxTotalAmount + ", fxAmountDue=" + fxAmountDue
				+ ", fxIsInvoicePaid=" + fxIsInvoicePaid + "]";
	}

}
