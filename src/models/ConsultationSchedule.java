package models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ConsultationSchedule {

	// Fields
	private int scheduleId;
	private String dayOfTheWeek;
	private String openTime;
	private String closeTime;

	// These properties are set to work with the Table View binding
	private final IntegerProperty fxScheduleId = new SimpleIntegerProperty();
	private final StringProperty fxDayOfTheWeek = new SimpleStringProperty();
	private final StringProperty fxOpenTime = new SimpleStringProperty();
	private final StringProperty fxCloseTime = new SimpleStringProperty();
	
	// Constructor
	public ConsultationSchedule(int scheduleId, String dayOfTheWeek, String openTime, String closeTime) {
		super();
		this.scheduleId = scheduleId;
		this.dayOfTheWeek = dayOfTheWeek;
		this.openTime = openTime;
		this.closeTime = closeTime;
	}

	// Getters and Setters
	public int getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(int scheduleId) {
		this.scheduleId = scheduleId;
	}

	public String getDayOfTheWeek() {
		return dayOfTheWeek;
	}

	public void setDayOfTheWeek(String dayOfTheWeek) {
		this.dayOfTheWeek = dayOfTheWeek;
	}

	public String getOpenTime() {
		return openTime;
	}

	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}

	public String getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(String closeTime) {
		this.closeTime = closeTime;
	}
	
	// These methods are to bind the fields in the Appointment class to the Table View in frontend
	public IntegerProperty fxScheduleIdProperty() {
		fxScheduleId.set(scheduleId);
		return fxScheduleId;
	}

	public StringProperty fxDayOfTheWeekProperty() {
		fxDayOfTheWeek.set(dayOfTheWeek);
		return fxDayOfTheWeek;
	}
	
	public StringProperty fxOpenTimeProperty() {
		fxOpenTime.set(openTime);
		return fxOpenTime;
	}
	
	public StringProperty fxCloseTimeProperty() {
		fxCloseTime.set(closeTime);
		return fxCloseTime;
	}

	@Override
	public String toString() {
		return "ConsultationSchedule [scheduleId=" + scheduleId + ", dayOfTheWeek=" + dayOfTheWeek + ", openTime="
				+ openTime + ", closeTime=" + closeTime + "]";
	}
	
}
