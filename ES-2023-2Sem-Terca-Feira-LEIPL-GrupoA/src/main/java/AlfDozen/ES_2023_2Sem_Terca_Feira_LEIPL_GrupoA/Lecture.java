package AlfDozen.ES_2023_2Sem_Terca_Feira_LEIPL_GrupoA;

/*
 * VERY OUTDATED INFO - TO CHANGE
 * Lecture class to be used by Schedule class
 * Should be instanced without arguments and set methods used for all attributes
 * However, there is the option of using all the parameters as arguments while instantiating
 * The date and time attributes can be set both using LocalDate/LocalTime objects or Strings
 * The String sintax for date is dd/mm/yyyy and for time is hh/mm/ss
*/

public final class Lecture implements Comparable<Lecture> {

	public static final String FORNULL = "Unknown";
	private AcademicInfo academicInfo;
	private TimeSlot timeSlot;
	private Room room;
	
	Lecture(AcademicInfo academicInfo, TimeSlot timeSlot, Room room){
		this.academicInfo = academicInfo;
		this.timeSlot = timeSlot;
		this.room = room;
	}
	
	Lecture(){
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
		return academicInfo.isComplete() && timeSlot.isComplete() && room.isComplete();
	}
	
	@Override
	public int compareTo(Lecture lecture) {
		return this.timeSlot.compareTo(lecture.getTimeSlot());
	}
	
	@Override
	public String toString() {
		return timeSlot + " - " + academicInfo.getCourse() + " - " + academicInfo.getShift() + " - Room " + room.getName();
	}
}
