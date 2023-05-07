package alfdozen.es_2023_2sem_terca_teira_leipl_grupoa;


/**
 * The Lecture class is used to manage all the information regarding a lecture.
 * Null arguments are accepted by the constructor and setters. The function
 * isComplete checks if none of its attributes are null, and if none of the
 * attributes of its attributes (AcademicInfo, TimeSlot, Room) are null.
 * Lectures can be sorted by TimeSlot: first will be Lectures with null timeSlot
 * attribute, then they will be ordered by date, by beginning time and finally
 * by ending time. Null attributes are always considered to be before non null
 * attributes.
 * 
 * @author alfdozen
 * @version 1.0.0
 */
final class Lecture implements Comparable<Lecture> {

	static final String FOR_NULL = "Unknown";
	private AcademicInfo academicInfo;
	private TimeSlot timeSlot;
	private Room room;

	/**
	 * Constructor creates a lecture with academic information, time slot and room.
	 * 
	 * @param academicInfo the academic information for the lecture.
	 * @param timeSlot     the time slot for the lecture.
	 * @param room         the room for the lecture.
	 */
	Lecture(AcademicInfo academicInfo, TimeSlot timeSlot, Room room) {
		this.academicInfo = academicInfo;
		this.timeSlot = timeSlot;
		this.room = room;
	}

	/**
	 * Returns the academic information of the lecture.
	 * 
	 * @return the academic information of the lecture.
	 */
	AcademicInfo getAcademicInfo() {
		return academicInfo;
	}

	/**
	 * Sets the academic info for the lecture.
	 * 
	 * @param academicInfo the academic information for the lecture.
	 */
	void setAcademicInfo(AcademicInfo academicInfo) {
		this.academicInfo = academicInfo;
	}

	/**
	 * Returns the time slot of the lecture.
	 * 
	 * @return the time slot of the lecture.
	 */
	TimeSlot getTimeSlot() {
		return timeSlot;
	}

	/**
	 * Sets the time slot for the lecture.
	 * 
	 * @param timeSlot the time slot for the lecture.
	 */
	void setTimeSlot(TimeSlot timeSlot) {
		this.timeSlot = timeSlot;
	}

	/**
	 * Returns the room of the lecture.
	 * 
	 * @return the room of the lecture.
	 */
	Room getRoom() {
		return room;
	}

	/**
	 * Sets the room for the lecture.
	 * 
	 * @param room the room information for the lecture.
	 */
	void setRoom(Room room) {
		this.room = room;
	}

	/**
	 * Checks all the information of the lecture and returns true if it is complete
	 * (there are no null). This includes validating if all information inside the
	 * academic information, time slot and room is complete. If a null is found, the
	 * method returns false. Otherwise, it returns true.
	 * 
	 * @return true if all the lecture information is complete (not null); false
	 *         otherwise.
	 */
	boolean isComplete() {
		if (academicInfo == null || timeSlot == null || room == null) {
			return false;
		}
		return academicInfo.isComplete() && timeSlot.isComplete() && room.isComplete();
	}

	/**
	 * Verify if the enrollment of a classroom exceeds its capacity.
	 * 
	 * @return true if the enrollment exceeds the capacity, false otherwise.
	 */
	boolean isOvercrowded() {
		if (academicInfo == null || academicInfo.getStudentsEnrolled() == null || room == null
				|| room.getCapacity() == null) {
			return false;
		}
		return (academicInfo.getStudentsEnrolled() > room.getCapacity());
	}

	/**
	 * Compares this lecture with the specified lecture for order. Return -1, 0, or
	 * 1 as this lecture is less than, equal to, or greater than the specified
	 * lecture. To determine which is less, this method compares the time slots of
	 * both lectures. If both don't have a defined time slot (not null) or if the
	 * time slot are considered equal, the lectures are considered equal. Otherwise,
	 * a lecture without a time slot is considered less than a lecture with a time
	 * slot. A lecture with a lesser time slot (lesser date, followed by lesser
	 * beginning time and finally lesser ending time) is considered less than a
	 * lecture with a greater time slot. Null attributes are always considered less
	 * than non null attributes
	 * 
	 * @param lecture the lecture to be compared with.
	 * @return -1, 0, or 1 as this lecture is less than, equal to, or greater than
	 *         the specified lecture.
	 */
	@Override
	public int compareTo(Lecture lecture) {
		TimeSlot otherTS = lecture.getTimeSlot();
		if (timeSlot == null || otherTS == null) {
			return TimeSlot.resolveCompareToNull(timeSlot, otherTS);
		}
		return timeSlot.compareTo(lecture.getTimeSlot());
	}

	/**
	 * Return a string representation of the lecture, including its date, beginning
	 * and ending time, course, shift and the room name. If any of this information
	 * is not defined, that information is instead given as "Unknown".
	 * 
	 * @return a string representation of the lecture.
	 */
	@Override
	public String toString() {
		String str = "";
		if (timeSlot == null) {
			str += Lecture.FOR_NULL + " - " + Lecture.FOR_NULL + "-" + Lecture.FOR_NULL;
		} else {
			str += timeSlot;
		}
		if (academicInfo == null || academicInfo.getCourse() == null) {
			str += " - " + Lecture.FOR_NULL;
		} else {
			str += " - " + academicInfo.getCourse();
		}
		if (academicInfo == null || academicInfo.getShift() == null) {
			str += " - " + Lecture.FOR_NULL;
		} else {
			str += " - " + academicInfo.getShift();
		}
		if (room == null || room.getName() == null) {
			str += " - Room " + Lecture.FOR_NULL;
		} else {
			str += " - Room " + room.getName();
		}
		return str;
	}
}
