package alfdozen_es_2023_2sem_terca_feira_leipl_grupoa;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * @author alfdozen
 * 
 *         The TimeSlot class is used to define the beginning and end of a
 *         lecture. The lecture is intended to begin and end on the same date.
 *         The class accepts an ending time that is earlier than the beginning,
 *         but this can be checked by using isValidInterval function. Since the
 *         information used to instantiate this class will frequently come from
 *         documents, the constructor accepts all arguments as Strings. If given
 *         as a String, the accepted format for date is day/month/year and for
 *         time is hour:minute:second. Although the day of the week is accepted
 *         as a constructor argument, it is checked against the calendar using
 *         the date attribute and corrected if necessary. As such, it has not
 *         setter method. Also, the getWasWeekDayCorrect function informs if the
 *         day of the week was corrected by the constructor. Null arguments are
 *         accepted by the constructor and setters. With the function isComplete
 *         it can be checked if there are still null attributes. TimeSlot can be
 *         sorted: First by date, then by time of beginning and finally by time
 *         of ending. Null attributes are always considered to be before
 *         non-null attributes.
 * 
 */

final class TimeSlot implements Comparable<TimeSlot> {

	static final String WRONG_DATE_FORMAT = "Wrong date format";
	static final String WRONG_BEGIN_TIME_FORMAT = "Wrong begin time format";
	static final String WRONG_END_TIME_FORMAT = "Wrong end time format";
	static final String[] WEEK_DAY_ARRAY = { "Seg", "Ter", "Qua", "Qui", "Sex", "Sáb", "Dom" };
	private static final String DATE_FORMAT = "d/M/yyyy";
	private static final String TIME_FORMAT = "H:m:s";
	private String weekDay;
	private LocalDate date;
	private LocalTime timeBegin;
	private LocalTime timeEnd;
	private Boolean wasWeekDayCorrect;

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

	String getWeekDay() {
		return weekDay;
	}

	private static String findWeekDay(LocalDate date) {
		return WEEK_DAY_ARRAY[date.getDayOfWeek().getValue() - 1];
	}

	Boolean getWasWeekDayCorrect() {
		return wasWeekDayCorrect;
	}

	LocalDate getDate() {
		return date;
	}

	String getDateString() {
		if (date == null) {
			return Lecture.FOR_NULL;
		}
		int iDay = date.getDayOfMonth();
		String day;
		if (iDay < 10) {
			day = "0" + iDay;
		} else {
			day = Integer.toString(iDay);
		}
		int iMonth = date.getMonthValue();
		String month;
		if (iMonth < 10) {
			month = "0" + iMonth;
		} else {
			month = Integer.toString(iMonth);
		}
		return day + "/" + month + "/" + date.getYear();
	}

	void setDate(LocalDate date) {
		this.date = date;
		if (date != null) {
			weekDay = findWeekDay(date);
		}
	}

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

	LocalTime getTimeBegin() {
		return timeBegin;
	}

	String getTimeBeginString() {
		if (timeBegin == null) {
			return Lecture.FOR_NULL;
		}
		if (timeBegin.getSecond() == 0) {
			return timeBegin.toString() + ":00";
		}
		return timeBegin.toString();
	}

	void setTimeBegin(LocalTime timeBegin) {
		this.timeBegin = timeBegin;
	}

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

	LocalTime getTimeEnd() {
		return timeEnd;
	}

	String getTimeEndString() {
		if (timeEnd == null) {
			return Lecture.FOR_NULL;
		}
		if (timeEnd.getSecond() == 0) {
			return timeEnd.toString() + ":00";
		}
		return timeEnd.toString();
	}

	void setTimeEnd(LocalTime timeEnd) {
		this.timeEnd = timeEnd;
	}

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

	boolean isComplete() {
		return weekDay != null && date != null && timeBegin != null && timeEnd != null;
	}

	boolean isValidInterval() {
		return timeBegin.compareTo(timeEnd) < 0;
	}

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

	static int resolveCompareToNull(Object o, Object o2) {
		if (o != null && o2 == null) {
			return 1;
		}
		if (o == null && o2 != null) {
			return -1;
		}
		return 0;
	}

	@Override
	public String toString() {
		return getDateString() + " - " + getTimeBeginString() + "-" + getTimeEndString();
	}
}
