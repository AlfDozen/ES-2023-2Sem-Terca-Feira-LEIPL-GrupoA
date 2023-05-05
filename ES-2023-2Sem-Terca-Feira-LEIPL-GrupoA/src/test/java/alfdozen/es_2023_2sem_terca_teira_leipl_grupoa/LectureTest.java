package alfdozen.es_2023_2sem_terca_teira_leipl_grupoa;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;

class LectureTest {

	@Test
	final void testLecture() {
		AcademicInfo academicInfo = new AcademicInfo("LEI-PL", "Engenharia de Software", "T02A", "LEIPL1", 5);
		TimeSlot timeSlot = new TimeSlot("Qui", LocalDate.of(2023, 2, 23), LocalTime.of(3, 2, 32),
				LocalTime.of(11, 23, 4));
		Room room = new Room("ES23", 20);
		Lecture lecture = new Lecture(academicInfo, timeSlot, room);
		assertEquals(academicInfo, lecture.getAcademicInfo());
		assertEquals(timeSlot, lecture.getTimeSlot());
		assertEquals(room, lecture.getRoom());

		Lecture lectureNull = new Lecture(null, null, null);
		assertNull(lectureNull.getAcademicInfo());
		assertNull(lectureNull.getTimeSlot());
		assertNull(lectureNull.getRoom());
	}

	@Test
	final void testGetAcademicInfo() {
		AcademicInfo academicInfo = new AcademicInfo("LEI-PL", "Engenharia de Software", "T02A", "LEIPL1", 5);
		Lecture lecture = new Lecture(academicInfo, null, null);
		assertEquals(academicInfo, lecture.getAcademicInfo());
	}

	@Test
	final void testSetAcademicInfo() {
		AcademicInfo academicInfo = new AcademicInfo("LEI-PL", "Engenharia de Software", "T02A", "LEIPL1", 5);
		Lecture lecture = new Lecture(academicInfo, null, null);
		AcademicInfo academicInfo2 = new AcademicInfo("IGE", "MicroProcessadores", "T32B", "IGE3", 12);
		lecture.setAcademicInfo(academicInfo2);
		assertEquals(academicInfo2, lecture.getAcademicInfo());
	}

	@Test
	final void testGetTimeSlot() {
		TimeSlot timeSlot = new TimeSlot("Qui", LocalDate.of(2023, 2, 23), LocalTime.of(3, 2, 32),
				LocalTime.of(11, 23, 4));
		Lecture lecture = new Lecture(null, timeSlot, null);
		assertEquals(timeSlot, lecture.getTimeSlot());
	}

	@Test
	final void testSetTimeSlot() {
		TimeSlot timeSlot = new TimeSlot("Qui", LocalDate.of(2023, 2, 23), LocalTime.of(3, 2, 32),
				LocalTime.of(11, 23, 4));
		Lecture lecture = new Lecture(null, timeSlot, null);
		TimeSlot timeSlot2 = new TimeSlot("Sáb", LocalDate.of(2023, 2, 25), LocalTime.of(9, 4, 0),
				LocalTime.of(17, 45, 28));
		lecture.setTimeSlot(timeSlot2);
		assertEquals(timeSlot2, lecture.getTimeSlot());
	}

	@Test
	final void testGetRoom() {
		Room room = new Room("ES23", 20);
		Lecture lecture = new Lecture(null, null, room);
		assertEquals(room, lecture.getRoom());
	}

	@Test
	final void testSetRoom() {
		Room room = new Room("ES23", 20);
		Lecture lecture = new Lecture(null, null, room);
		Room room2 = new Room("B.401", 100);
		lecture.setRoom(room2);
		assertEquals(room2, lecture.getRoom());
	}

	@Test
	final void testIsComplete() {
		AcademicInfo academicInfo = new AcademicInfo("LEI-PL", "Engenharia de Software", "T02A", "LEIPL1", 5);
		TimeSlot timeSlot = new TimeSlot("Qui", LocalDate.of(2023, 2, 23), LocalTime.of(3, 2, 32),
				LocalTime.of(11, 23, 4));
		Room room = new Room("ES23", 20);
		AcademicInfo academicInfoNull = new AcademicInfo(null, null, null, null, (Integer) null);
		TimeSlot timeSlotNull = new TimeSlot(null, (LocalDate) null, (LocalTime) null, (LocalTime) null);
		Room roomNull = new Room(null, (Integer) null);
		Lecture lecture = new Lecture(academicInfo, timeSlot, room);
		assertTrue(lecture.isComplete());
		lecture.setAcademicInfo(null);
		assertFalse(lecture.isComplete());
		lecture.setAcademicInfo(academicInfoNull);
		assertFalse(lecture.isComplete());
		lecture.setAcademicInfo(academicInfo);
		lecture.setTimeSlot(null);
		assertFalse(lecture.isComplete());
		lecture.setTimeSlot(timeSlotNull);
		assertFalse(lecture.isComplete());
		lecture.setTimeSlot(timeSlot);
		lecture.setRoom(null);
		assertFalse(lecture.isComplete());
		lecture.setRoom(roomNull);
		assertFalse(lecture.isComplete());
	}


	@Test
	final void testCompareTo() {
		TimeSlot timeSlotSameBeginTime = new TimeSlot("Qui", LocalDate.of(2023, 2, 23), LocalTime.of(3, 2, 32),
				LocalTime.of(11, 23, 4));
		TimeSlot timeSlotSameBeginTime2 = new TimeSlot("Qui", LocalDate.of(2023, 2, 23), LocalTime.of(3, 2, 32),
				LocalTime.of(12, 33, 5));
		TimeSlot timeSlotSameBeginTime3 = new TimeSlot("Qui", LocalDate.of(2023, 2, 23), LocalTime.of(3, 2, 32),
				LocalTime.of(11, 23, 4));
		TimeSlot timeSlotSameBeginTimeNull = new TimeSlot("Qui", LocalDate.of(2023, 2, 23), LocalTime.of(3, 2, 32),
				(LocalTime) null);
		TimeSlot timeSlotSameBeginTimeNull2 = new TimeSlot("Qui", LocalDate.of(2023, 2, 23), LocalTime.of(3, 2, 32),
				(LocalTime) null);
		Lecture lectureSameBeginTime = new Lecture(null, timeSlotSameBeginTime, null);
		Lecture lectureSameBeginTime2 = new Lecture(null, timeSlotSameBeginTime2, null);
		Lecture lectureSameBeginTime3 = new Lecture(null, timeSlotSameBeginTime3, null);
		Lecture lectureSameBeginTimeNull = new Lecture(null, timeSlotSameBeginTimeNull, null);
		Lecture lectureSameBeginTimeNull2 = new Lecture(null, timeSlotSameBeginTimeNull2, null);
		assertEquals(-1, lectureSameBeginTime.compareTo(lectureSameBeginTime2));
		assertEquals(1, lectureSameBeginTime2.compareTo(lectureSameBeginTime));
		assertEquals(0, lectureSameBeginTime.compareTo(lectureSameBeginTime3));
		assertEquals(0, lectureSameBeginTimeNull.compareTo(lectureSameBeginTimeNull2));
		assertEquals(-1, lectureSameBeginTimeNull.compareTo(lectureSameBeginTime));
		assertEquals(1, lectureSameBeginTime.compareTo(lectureSameBeginTimeNull));

		TimeSlot timeSlotSameDate = new TimeSlot("Qui", LocalDate.of(2023, 2, 23), LocalTime.of(3, 2, 32),
				LocalTime.of(11, 23, 4));
		TimeSlot timeSlotSameDate2 = new TimeSlot("Qui", LocalDate.of(2023, 2, 23), LocalTime.of(4, 1, 10),
				LocalTime.of(11, 23, 4));
		TimeSlot timeSlotSameDateNull = new TimeSlot("Qui", LocalDate.of(2023, 2, 23), (LocalTime) null,
				LocalTime.of(11, 23, 4));
		TimeSlot timeSlotSameDateNull2 = new TimeSlot("Qui", LocalDate.of(2023, 2, 23), (LocalTime) null,
				LocalTime.of(11, 23, 4));
		Lecture lectureSameDate = new Lecture(null, timeSlotSameDate, null);
		Lecture lectureSameDate2 = new Lecture(null, timeSlotSameDate2, null);
		Lecture lectureSameDateNull = new Lecture(null, timeSlotSameDateNull, null);
		Lecture lectureSameDateNull2 = new Lecture(null, timeSlotSameDateNull2, null);
		assertEquals(-1, lectureSameDate.compareTo(lectureSameDate2));
		assertEquals(1, lectureSameDate2.compareTo(lectureSameDate));
		assertEquals(0, lectureSameDateNull.compareTo(lectureSameDateNull2));
		assertEquals(-1, lectureSameDateNull.compareTo(lectureSameDate));
		assertEquals(1, lectureSameDate.compareTo(lectureSameDateNull));

		TimeSlot timeSlotDiffDate = new TimeSlot("Qui", LocalDate.of(2022, 2, 23), LocalTime.of(4, 2, 32),
				LocalTime.of(11, 23, 4));
		TimeSlot timeSlotDiffDate2 = new TimeSlot("Qui", LocalDate.of(2023, 2, 23), LocalTime.of(4, 2, 32),
				LocalTime.of(11, 23, 4));
		TimeSlot timeSlotDiffDateNull = new TimeSlot("Qui", (LocalDate) null, LocalTime.of(4, 2, 32),
				LocalTime.of(11, 23, 4));
		TimeSlot timeSlotDiffDateNull2 = new TimeSlot("Qui", (LocalDate) null, LocalTime.of(4, 2, 32),
				LocalTime.of(11, 23, 4));
		Lecture lectureDiffDate = new Lecture(null, timeSlotDiffDate, null);
		Lecture lectureDiffDate2 = new Lecture(null, timeSlotDiffDate2, null);
		Lecture lectureDiffDateNull = new Lecture(null, timeSlotDiffDateNull, null);
		Lecture lectureDiffDateNull2 = new Lecture(null, timeSlotDiffDateNull2, null);
		assertEquals(-1, lectureDiffDate.compareTo(lectureDiffDate2));
		assertEquals(1, lectureDiffDate2.compareTo(lectureDiffDate));
		assertEquals(0, lectureDiffDateNull.compareTo(lectureDiffDateNull2));
		assertEquals(-1, lectureDiffDateNull.compareTo(lectureDiffDate));
		assertEquals(1, lectureDiffDate.compareTo(lectureDiffDateNull));

		TimeSlot timeSlot = new TimeSlot("Qui", LocalDate.of(2022, 2, 23), LocalTime.of(4, 2, 32),
				LocalTime.of(11, 23, 4));
		Lecture lecture = new Lecture(null, timeSlot, null);
		Lecture lectureNull = new Lecture(null, null, null);
		Lecture lectureNull2 = new Lecture(null, null, null);
		assertEquals(-1, lectureNull.compareTo(lecture));
		assertEquals(1, lecture.compareTo(lectureNull));
		assertEquals(0, lectureNull.compareTo(lectureNull2));
	}

	@Test
	final void testToString() {
		AcademicInfo academicInfo = new AcademicInfo("LEI-PL", "Engenharia de Software", "T02A", "LEIPL1", 5);
		TimeSlot timeSlot = new TimeSlot("Qui", LocalDate.of(2023, 2, 23), LocalTime.of(3, 2, 32),
				LocalTime.of(11, 23, 4));
		Room room = new Room("ES23", 20);
		Lecture lecture = new Lecture(academicInfo, timeSlot, room);
		assertEquals("23/02/2023 - 03:02:32-11:23:04 - Engenharia de Software - T02A - Room ES23", lecture.toString());

		Lecture lectureNull = new Lecture(null, null, null);
		assertEquals("Unknown - Unknown-Unknown - Unknown - Unknown - Room Unknown", lectureNull.toString());

		AcademicInfo academicInfoNull = new AcademicInfo(null, null, null, null, (Integer) null);
		TimeSlot timeSlotNull = new TimeSlot(null, (String) null, (String) null, (String) null);
		Room roomNull = new Room(null, (Integer) null);
		Lecture lectureHalfNull = new Lecture(academicInfoNull, timeSlotNull, roomNull);
		assertEquals("Unknown - Unknown-Unknown - Unknown - Unknown - Room Unknown", lectureHalfNull.toString());
	}
	
	@Test
	final void testIsOvercrowded() {
		AcademicInfo academicInfo1 = new AcademicInfo("LEI-PL", "Engenharia de Software", "T02A", "LEIPL1", 5);
		TimeSlot timeSlot1 = new TimeSlot("Qui", LocalDate.of(2023, 2, 23), LocalTime.of(3, 2, 32),
				LocalTime.of(11, 23, 4));
		Room room1 = new Room("ES23", 20);
		Lecture lecture1 = new Lecture(academicInfo1, timeSlot1, room1);
		
		AcademicInfo academicInfo2 = new AcademicInfo("LEI-PL", "Engenharia de Software", "T02A", "LEIPL1", 25);
		TimeSlot timeSlot2 = new TimeSlot("Qui", LocalDate.of(2023, 2, 23), LocalTime.of(3, 2, 32),
				LocalTime.of(11, 23, 4));
		Room room2 = new Room("ES23", 20);
		Lecture lecture2 = new Lecture(academicInfo2, timeSlot2, room2);
		
		AcademicInfo academicInfo3 = new AcademicInfo(null, null, null, null, 25);
		TimeSlot timeSlot3 = null;
		Room room3 = new Room(null, 20);
		Lecture lecture3 = new Lecture(academicInfo3, null, room3);
		
		Lecture lecture4 = new Lecture(null, null, null);
		
		assertFalse(lecture1.isOvercrowded());		
		assertFalse(lecture4.isOvercrowded());		
		assertTrue(lecture2.isOvercrowded());
		assertTrue(lecture3.isOvercrowded());
		
	}
}
