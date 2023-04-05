package alfdozen.es_2023_2sem_terca_teira_leipl_grupoa;

/**
 * @author alfdozen
 * 
 *         The Lecture class is used to manage all the information regarding a
 *         lecture. Null arguments are accepted by the constructor and setters.
 *         The function isComplete checks if none of its attributes are null,
 *         and if none of the attributes of its attributes (AcademicInfo,
 *         TimeSlot, Room) are null. Lectures can be sorted by TimeSlot: first
 *         will be Lectures with null timeSlot attribute, then they will be
 *         ordered by date, by time of beginning and finally by time of ending.
 *         Null attributes are always considered to be before non-null
 *         attributes.
 */

public final class Lecture implements Comparable<Lecture> {

	static final String FOR_NULL = "Unknown";
	private AcademicInfo academicInfo;
	private TimeSlot timeSlot;
	private Room room;

	Lecture(AcademicInfo academicInfo, TimeSlot timeSlot, Room room) {
		this.academicInfo = academicInfo;
		this.timeSlot = timeSlot;
		this.room = room;
	}

	AcademicInfo getAcademicInfo() {
		return academicInfo;
	}

	void setAcademicInfo(AcademicInfo academicInfo) {
		this.academicInfo = academicInfo;
	}

	TimeSlot getTimeSlot() {
		return timeSlot;
	}

	void setTimeSlot(TimeSlot timeSlot) {
		this.timeSlot = timeSlot;
	}

	Room getRoom() {
		return room;
	}

	void setRoom(Room room) {
		this.room = room;
	}

	boolean isComplete() {
		if (academicInfo == null || timeSlot == null || room == null) {
			return false;
		}
		return academicInfo.isComplete() && timeSlot.isComplete() && room.isComplete();
	}

	@Override
	public int compareTo(Lecture lecture) {
		TimeSlot otherTS = lecture.getTimeSlot();
		if (timeSlot == null || otherTS == null) {
			return TimeSlot.resolveCompareToNull(timeSlot, otherTS);
		}
		return timeSlot.compareTo(lecture.getTimeSlot());
	}

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
