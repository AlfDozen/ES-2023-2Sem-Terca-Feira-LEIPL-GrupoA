package AlfDozen.ES_2023_2Sem_Terca_Feira_LEIPL_GrupoA;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public final class TimeSlot implements Comparable<TimeSlot>{

	private static final String[] weekDayArray = {"Seg", "Ter", "Qua", "Qui", "Sex", "Sáb", "Dom"};
	private String weekDay;
	private LocalDate date;
	private LocalTime timeBegin;
	private LocalTime timeEnd;
	private Boolean wasWeekDayCorrect;
	
	TimeSlot(String weekDay, LocalDate date, LocalTime timeBegin, LocalTime timeEnd){
		this.date = date;
		this.timeBegin = timeBegin;
		this.timeEnd = timeEnd;
		wasWeekDayCorrect = true;
		if(this.date != null) {
			String newWeekDay = findWeekDay(this.date);
			this.weekDay = newWeekDay;
			if(!newWeekDay.equals(weekDay)) {
				wasWeekDayCorrect = false;
			}
		} else {
			this.weekDay = weekDay;
		}
	}
	
	TimeSlot(String weekDay, String date, String timeBegin, String timeEnd){
		wasWeekDayCorrect = true;
		if(date == null) {
			this.date = null;
			this.weekDay = weekDay;
		} else {
			try {
				DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("d/M/yyyy");
				LocalDate newDate = LocalDate.from(formatterDate.parse(date));
				this.date = newDate;
			} catch(DateTimeParseException e) {
				throw new IllegalArgumentException("Wrong date format");
			}
			String newWeekDay = findWeekDay(this.date);
			this.weekDay = newWeekDay;
			if(!newWeekDay.equals(weekDay)) {
				wasWeekDayCorrect = false;
			}
		}
		DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("H:m:s");
		if(timeBegin == null) {
			this.timeBegin = null;
		} else {
			try {			
				LocalTime newTimeBegin = LocalTime.from(formatterTime.parse(timeBegin));
				this.timeBegin = newTimeBegin;
			} catch(DateTimeParseException e) {
				throw new IllegalArgumentException("Wrong begin time format");
			}
		}
		if(timeEnd == null) {
			this.timeEnd = null;
		} else {
			try {
				LocalTime newTimeEnd = LocalTime.from(formatterTime.parse(timeEnd));
				this.timeEnd = newTimeEnd;
			} catch(DateTimeParseException e) {
				throw new IllegalArgumentException("Wrong end time format");
			}
		}
	}
	
	String getWeekDay() {
		return weekDay;
	}
	
	static String findWeekDay(LocalDate date) {
		return weekDayArray[date.getDayOfWeek().getValue() - 1];
	}
	
	Boolean getWasWeekDayCorrect() {
		return wasWeekDayCorrect;
	}
	
	LocalDate getDate() {
		return date;
	}
	
	String getDateString() {
		if(date == null) {
			return Lecture.FORNULL;
		}
		int iDay = date.getDayOfMonth();
		String day;
		if(iDay < 10) {
			day = "0" + iDay;
		} else {
			day = Integer.toString(iDay);
		}
		int iMonth = date.getMonthValue();
		String month;
		if(iMonth < 10) {
			month = "0" + iMonth;
		} else {
			month = Integer.toString(iMonth);
		}
		return day + "/" + month + "/" + date.getYear();
	}

	void setDate(LocalDate date) {
		this.date = date;
		if(date != null) {
			weekDay = findWeekDay(date);
		}
	}
	
	void setDate(String date) {
		if(date == null) {
			this.date = null;
			return;
		}
		try {
			DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("d/M/yyyy");
			LocalDate newDate = LocalDate.from(formatterDate.parse(date));
			this.date = newDate;
		} catch(DateTimeParseException e) {
			throw new IllegalArgumentException("Wrong date format");
		}
		weekDay = findWeekDay(this.date);
	}
	
	LocalTime getTimeBegin() {
		return timeBegin;
	}
	
	String getTimeBeginString() {
		if(timeBegin == null) {
			return Lecture.FORNULL;
		}
		if(timeBegin.getSecond() == 0) {
			return timeBegin.toString() + ":00";
		}
		return timeBegin.toString();
	}
	
	void setTimeBegin(LocalTime timeBegin) {
		this.timeBegin = timeBegin;
	}
	
	void setTimeBegin(String timeBegin) {
		if(timeBegin == null) {
			this.timeBegin = null;
			return;
		}
		try {
			DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("H:m:s");
			LocalTime newTimeBegin = LocalTime.from(formatterTime.parse(timeBegin));
			this.timeBegin = newTimeBegin;
		} catch(DateTimeParseException e) {
			throw new IllegalArgumentException("Wrong begin time format");
		}
	}

	LocalTime getTimeEnd() {
		return timeEnd;
	}
	
	String getTimeEndString() {
		if(timeEnd == null) {
			return Lecture.FORNULL;
		}
		if(timeEnd.getSecond() == 0) {
			return timeEnd.toString() + ":00";
		}
		return timeEnd.toString();
	}

	void setTimeEnd(LocalTime timeEnd) {
		this.timeEnd = timeEnd;
	}
	
	void setTimeEnd(String timeEnd) {
		if(timeEnd == null) {
			this.timeEnd = null;
			return;
		}
		try {
			DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("H:m:s");
			LocalTime newTimeEnd = LocalTime.from(formatterTime.parse(timeEnd));
			this.timeEnd = newTimeEnd;
		} catch(DateTimeParseException e) {
			throw new IllegalArgumentException("Wrong end time format");
		}
	}
	
	boolean isComplete() {
		return weekDay != null && date != null && timeBegin != null && timeEnd != null;
	}
	
	boolean isValidInterval() {
		return timeBegin.compareTo(timeEnd) < 0;
	}
	
	@Override
	public int compareTo(TimeSlot timeSlot) {
		int dateResult = this.getDate().compareTo(timeSlot.getDate());
		if(dateResult != 0) {
			return dateResult;
		}
		return this.getTimeBegin().compareTo(timeSlot.getTimeBegin());
	}
	
	@Override
	public String toString() {
		return getDateString() + " - " + getTimeBeginString() + "-" + getTimeEndString();
	}
}
