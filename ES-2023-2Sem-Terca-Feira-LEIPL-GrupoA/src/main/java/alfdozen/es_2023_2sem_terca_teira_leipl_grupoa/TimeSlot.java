package alfdozen.es_2023_2sem_terca_teira_leipl_grupoa;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * The TimeSlot class is used to define the beginning and ending time of a
 * lecture. The lecture is intended to begin and end on the same date. The class
 * accepts an ending time that is earlier than the beginning, but this can be
 * checked by using isValidInterval function. Since the information used to
 * instantiate this class will frequently come from documents, the constructor
 * accepts all arguments as Strings. If given as a String, the accepted format
 * for date is day/month/year and for time is hour:minute:second. Although the
 * day of the week is accepted as a constructor argument, it is checked against
 * the calendar using the date attribute and corrected if necessary. As such, it
 * has not setter method. Also, the getWasWeekDayCorrect function informs if the
 * day of the week was corrected by the constructor. Null arguments are accepted
 * by the constructor and setters. With the function isComplete it can be
 * checked if there are still null attributes. TimeSlot can be sorted: First by
 * date, then by beginning time and finally by ending time. Null attributes are
 * always considered to be before non-null attributes.
 *
 * @author alfdozen
 * @version 1.0.0
 */
final class TimeSlot implements Comparable<TimeSlot> {

	static final String WRONG_DATE_FORMAT = "Wrong date format";
	static final String WRONG_BEGIN_TIME_FORMAT = "Wrong begin time format";
	static final String WRONG_END_TIME_FORMAT = "Wrong end time format";
	static final String[] WEEK_DAY_ARRAY = { "Seg", "Ter", "Qua", "Qui", "Sex", "Sáb", "Dom" };
	private static final String DATE_FORMAT = "d/M/yyyy";
	private static final String TIME_FORMAT = "H:m:s";
	private static final int TWO_DIGIT_NUMBER = 10;
	private String weekDay;
	private LocalDate date;
	private LocalTime timeBegin;
	private LocalTime timeEnd;
	private Boolean wasWeekDayCorrect;

	/**
	 * Constructor creates a time slot with day of the week (as String), date (as
	 * LocalDate), beginning and ending time (both as LocalTime). If the date is not
	 * null, checks if the day of the week is correct. It is not, the day of the
	 * week is changed to the correct designation and the parameter
	 * wasWeekDayCorrect is set to true.
	 * 
	 * @param weekDay   the day of the week for the time slot.
	 * @param date      the date for the time slot.
	 * @param timeBegin the beginning time for the time slot.
	 * @param timeEnd   the ending time for the time slot.
	 */
	TimeSlot(String weekDay, LocalDate date, LocalTime timeBegin, LocalTime timeEnd) {
		this.date = date;
		this.timeBegin = timeBegin;
		this.timeEnd = timeEnd;
		wasWeekDayCorrect = true;
		if (this.date != null) {
			String newWeekDay = findWeekDay(this.date);
			this.weekDay = newWeekDay;
			if (!newWeekDay.equals(weekDay)) {
				wasWeekDayCorrect = false;
			}
		} else {
			this.weekDay = weekDay;
		}
	}

	/**
	 * Constructor creates a time slot with day of the week, date, beginning and
	 * ending time. Receives all parameters as a String and converts date to
	 * LocalDate and beginning and ending time to LocalTime. If the date is not
	 * null, checks if the day of the week is correct. It is not, the day of the
	 * week is changed to the correct designation and the parameter
	 * wasWeekDayCorrect is set to true.
	 * 
	 * @param weekDay   the day of the week for the time slot.
	 * @param date      the date for the time slot as String.
	 * @param timeBegin the beginning time for the time slot as String.
	 * @param timeEnd   the ending time for the time slot as String.
	 * @throws IllegalArgumentException if the date or the beginning or the ending
	 *                                  time cannot be converted to LocalData or
	 *                                  LocalTime, respectively.
	 */
	TimeSlot(String weekDay, String date, String timeBegin, String timeEnd) {
		wasWeekDayCorrect = true;
		if (date == null) {
			this.date = null;
			this.weekDay = weekDay;
		} else {
			try {
				DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern(DATE_FORMAT);
				LocalDate newDate = LocalDate.from(formatterDate.parse(date));
				this.date = newDate;
			} catch (DateTimeParseException e) {
				throw new IllegalArgumentException(WRONG_DATE_FORMAT);
			}
			String newWeekDay = findWeekDay(this.date);
			this.weekDay = newWeekDay;
			if (!newWeekDay.equals(weekDay)) {
				wasWeekDayCorrect = false;
			}
		}
		DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern(TIME_FORMAT);
		if (timeBegin == null) {
			this.timeBegin = null;
		} else {
			try {
				LocalTime newTimeBegin = LocalTime.from(formatterTime.parse(timeBegin));
				this.timeBegin = newTimeBegin;
			} catch (DateTimeParseException e) {
				throw new IllegalArgumentException(WRONG_BEGIN_TIME_FORMAT);
			}
		}
		if (timeEnd == null) {
			this.timeEnd = null;
		} else {
			try {
				LocalTime newTimeEnd = LocalTime.from(formatterTime.parse(timeEnd));
				this.timeEnd = newTimeEnd;
			} catch (DateTimeParseException e) {
				throw new IllegalArgumentException(WRONG_END_TIME_FORMAT);
			}
		}
	}

	/**
	 * Returns the day of the week of the time slot.
	 * 
	 * @return the day of the week of the time slot.
	 */
	String getWeekDay() {
		return weekDay;
	}

	/**
	 * Returns the day of the week associated with the provided date (as LocalDate).
	 * 
	 * @param date the date (as LocalDate).
	 * @return the day of the week associated with the provided date.
	 */
	private static String findWeekDay(LocalDate date) {
		return WEEK_DAY_ARRAY[date.getDayOfWeek().getValue() - 1];
	}

	/**
	 * Returns the day of the week of the time slot.
	 * 
	 * @return the day of the week of the time slot.
	 */
	Boolean getWasWeekDayCorrect() {
		return wasWeekDayCorrect;
	}

	/**
	 * Returns the date of the time slot.
	 * 
	 * @return the date of the time slot.
	 */
	LocalDate getDate() {
		return date;
	}

	/**
	 * Returns a string representation of the date of the time slot with the format
	 * dd/mm/year If the date is null, the string "Unknown" is returned instead.
	 * 
	 * @return a string representation of the date of the time slot.
	 */
	String getDateString() {
		if (date == null) {
			return Lecture.FOR_NULL;
		}
		int iDay = date.getDayOfMonth();
		String day;
		if (iDay < TWO_DIGIT_NUMBER) {
			day = "0" + iDay;
		} else {
			day = Integer.toString(iDay);
		}
		int iMonth = date.getMonthValue();
		String month;
		if (iMonth < TWO_DIGIT_NUMBER) {
			month = "0" + iMonth;
		} else {
			month = Integer.toString(iMonth);
		}
		return day + "/" + month + "/" + date.getYear();
	}

	/**
	 * Sets the date of the time slot. If the date is not null, it changes the day
	 * of the week to the new day.
	 * 
	 * @param date the date for the time slot.
	 */
	void setDate(LocalDate date) {
		this.date = date;
		if (date != null) {
			weekDay = findWeekDay(date);
		}
	}

	/**
	 * Sets the date of the time slot. Receives the date as a String and converts to
	 * LocalData. If the date is not null, it changes the day of the week to the new
	 * day.
	 * 
	 * @param date the date for the time slot as String.
	 * @throws IllegalArgumentException if the date cannot be converted to
	 *                                  LocalData.
	 */
	void setDate(String date) {
		if (date == null) {
			this.date = null;
			return;
		}
		try {
			DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern(DATE_FORMAT);
			LocalDate newDate = LocalDate.from(formatterDate.parse(date));
			this.date = newDate;
		} catch (DateTimeParseException e) {
			throw new IllegalArgumentException(WRONG_DATE_FORMAT);
		}
		weekDay = findWeekDay(this.date);
	}

	/**
	 * Returns the beginning time of the time slot.
	 * 
	 * @return the beginning time of the time slot.
	 */
	LocalTime getTimeBegin() {
		return timeBegin;
	}

	/**
	 * Returns a string representation of the beginning time of the time slot with
	 * the format hh:ss. If the time is null, the string "Unknown" is returned
	 * instead.
	 * 
	 * @return a string representation of the beginning time of the time slot.
	 */
	String getTimeBeginString() {
		if (timeBegin == null) {
			return Lecture.FOR_NULL;
		}
		if (timeBegin.getSecond() == 0) {
			return timeBegin.toString() + ":00";
		}
		return timeBegin.toString();
	}

	/**
	 * Sets the beginning time of the time slot.
	 * 
	 * @param timeBegin the beginning time for the time slot.
	 */
	void setTimeBegin(LocalTime timeBegin) {
		this.timeBegin = timeBegin;
	}

	/**
	 * Sets the beginning time of the time slot. Receives the time as a String and
	 * converts to LocalTime.
	 * 
	 * @param timeBegin the beginning time for the time slot as String.
	 * @throws IllegalArgumentException if the beginning time cannot be converted to
	 *                                  LocalTime.
	 */
	void setTimeBegin(String timeBegin) {
		if (timeBegin == null) {
			this.timeBegin = null;
			return;
		}
		try {
			DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern(TIME_FORMAT);
			LocalTime newTimeBegin = LocalTime.from(formatterTime.parse(timeBegin));
			this.timeBegin = newTimeBegin;
		} catch (DateTimeParseException e) {
			throw new IllegalArgumentException(WRONG_BEGIN_TIME_FORMAT);
		}
	}

	/**
	 * Returns the ending time of the time slot.
	 * 
	 * @return the ending time of the time slot.
	 */
	LocalTime getTimeEnd() {
		return timeEnd;
	}

	/**
	 * Returns a string representation of the ending time of the time slot with the
	 * format hh:ss. If the time is null, the string "Unknown" is returned instead.
	 * 
	 * @return a string representation of the ending time of the time slot.
	 */
	String getTimeEndString() {
		if (timeEnd == null) {
			return Lecture.FOR_NULL;
		}
		if (timeEnd.getSecond() == 0) {
			return timeEnd.toString() + ":00";
		}
		return timeEnd.toString();
	}

	/**
	 * Sets the ending time of the time slot.
	 * 
	 * @param timeEnd the ending time for the time slot.
	 */
	void setTimeEnd(LocalTime timeEnd) {
		this.timeEnd = timeEnd;
	}

	/**
	 * Sets the ending time of the time slot. Receives the time as a String and
	 * converts to LocalTime.
	 * 
	 * @param timeEnd the ending time for the time slot as String.
	 * @throws IllegalArgumentException if the ending time cannot be converted to
	 *                                  LocalTime.
	 */
	void setTimeEnd(String timeEnd) {
		if (timeEnd == null) {
			this.timeEnd = null;
			return;
		}
		try {
			DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern(TIME_FORMAT);
			LocalTime newTimeEnd = LocalTime.from(formatterTime.parse(timeEnd));
			this.timeEnd = newTimeEnd;
		} catch (DateTimeParseException e) {
			throw new IllegalArgumentException(WRONG_END_TIME_FORMAT);
		}
	}

	/**
	 * Checks if the day of the week, date and beginning and ending time are not
	 * null. If a null is found, the method returns false. Otherwise, it returns
	 * true.
	 * 
	 * @return true if the day of the week, date and beginning and ending time are
	 *         not null; false otherwise.
	 */
	boolean isComplete() {
		return weekDay != null && date != null && timeBegin != null && timeEnd != null;
	}

	/**
	 * Checks if the beginning time is before the ending time. Returns true if this
	 * condition is met. Otherwise, or if any of the times are null, returns false.
	 * It does not consider the hypothesis the two dates are in different dates.
	 * 
	 * @return true if the beginning time is before the ending time; false
	 *         otherwise.
	 */
	boolean isValidInterval() {
		if (timeBegin == null || timeEnd == null) {
			return false;
		}
		return timeBegin.compareTo(timeEnd) < 0;
	}

	/**
	 * Compares this time slot with the specified time slot for order. Return -1, 0,
	 * or 1 as this time slot is less than, equal to, or greater than the specified
	 * time slot. To determine which is less, this method compares, by order of
	 * priority, the date, then the beginning time and finally the ending time. Null
	 * attributes are always considered less than non null attributes.
	 * 
	 * @param timeSlot the time slot to be compared with.
	 * @return -1, 0, or 1 as this time slot is less than, equal to, or greater than
	 *         the specified lecture
	 */
	@Override
	public int compareTo(TimeSlot timeSlot) {
		LocalDate otherDate = timeSlot.getDate();
		int dateResult;
		if (date == null || otherDate == null) {
			dateResult = resolveCompareToNull(date, otherDate);
		} else {
			dateResult = this.getDate().compareTo(otherDate);
		}
		if (dateResult != 0) {
			return dateResult;
		}

		LocalTime otherTimeBegin = timeSlot.getTimeBegin();
		int timeBeginResult;
		if (timeBegin == null || otherTimeBegin == null) {
			timeBeginResult = resolveCompareToNull(timeBegin, otherTimeBegin);
		} else {
			timeBeginResult = timeBegin.compareTo(otherTimeBegin);
		}
		if (timeBeginResult != 0) {
			return timeBeginResult;
		}

		LocalTime otherTimeEnd = timeSlot.getTimeEnd();
		int timeEndResult;
		if (timeEnd == null || otherTimeEnd == null) {
			timeEndResult = resolveCompareToNull(timeEnd, otherTimeEnd);
		} else {
			timeEndResult = timeEnd.compareTo(otherTimeEnd);
		}
		return timeEndResult;
	}

	/**
	 * Auxiliary to compareTo method to order objects where at least one of the two
	 * is null. The original compareTo() method throws an exception when there is a
	 * null involved. Returns -1 if only the first object is null, 0 if both objects
	 * are null and 1 if only the second object is null.
	 * 
	 * @param o  the object to compare.
	 * @param o2 the second object to compare.
	 * @return -1 if only the first object is null, 0 if both objects are null and 1
	 *         if only the second object is null.
	 */
	static int resolveCompareToNull(Object o, Object o2) {
		if (o != null && o2 == null) {
			return 1;
		}
		if (o == null && o2 != null) {
			return -1;
		}
		return 0;
	}

	/**
	 * Return a string representation of the time slot, including its date,
	 * beginning and ending time. If any of this information is not defined, that
	 * information is instead given as "Unknown".
	 * 
	 * @return a string representation of the time slot.
	 */
	@Override
	public String toString() {
		return getDateString() + " - " + getTimeBeginString() + "-" + getTimeEndString();
	}

	/**
	 * Verify if this TimeSlot overlaps a given TimeSlot by checking if this
	 * timeslots timeBegin is before the timeEnd given TimeSlot or this TimeSlot
	 * timeEnd is after the timeBegin of the given TimeSlot
	 * 
	 * @param other The other TimeSlot to be verified.
	 * @return true is overlaps, false otherwise.
	 */
	public boolean overlaps(TimeSlot other) {
		if (other == null || other.timeBegin == null || other.timeEnd == null || other.date == null
				|| this.timeBegin == null || this.timeEnd == null || this.date == null
				|| !other.date.equals(this.date)) {
			return false;
		}
		return (this.timeBegin.compareTo(other.timeEnd) < 0) && (this.timeEnd.compareTo(other.timeBegin) > 0);
	}

}
