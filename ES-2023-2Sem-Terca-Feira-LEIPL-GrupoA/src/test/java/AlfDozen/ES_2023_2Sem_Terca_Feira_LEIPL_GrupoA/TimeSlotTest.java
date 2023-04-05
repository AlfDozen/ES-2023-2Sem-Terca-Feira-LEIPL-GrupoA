package AlfDozen.ES_2023_2Sem_Terca_Feira_LEIPL_GrupoA;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TimeSlotTest {

	@Test
	final void testTimeSlotLocal() {
		TimeSlot timeSlot = new TimeSlot("Qui", LocalDate.of(2023, 2, 23), LocalTime.of(3,  2, 32), LocalTime.of(11, 23, 4));
		assertEquals("Qui", timeSlot.getWeekDay());
		assertTrue(timeSlot.getWasWeekDayCorrect());
		assertEquals(LocalDate.of(2023, 2, 23), timeSlot.getDate());
		assertEquals(LocalTime.of(3, 2, 32), timeSlot.getTimeBegin());
		assertEquals(LocalTime.of(11, 23, 4), timeSlot.getTimeEnd());
		
		TimeSlot timeSlotWrongWeekDay = new TimeSlot("Sex", LocalDate.of(2023, 2, 23), LocalTime.of(3,  2, 32), LocalTime.of(11, 23, 4));
		assertEquals("Qui", timeSlotWrongWeekDay.getWeekDay());
		assertFalse(timeSlotWrongWeekDay.getWasWeekDayCorrect());
		assertEquals(LocalDate.of(2023, 2, 23), timeSlotWrongWeekDay.getDate());
		assertEquals(LocalTime.of(3, 2, 32), timeSlotWrongWeekDay.getTimeBegin());
		assertEquals(LocalTime.of(11, 23, 4), timeSlotWrongWeekDay.getTimeEnd());
		
		
		TimeSlot timeSlotNull = new TimeSlot(null, (LocalDate)null, (LocalTime)null, (LocalTime)null);
		assertNull(timeSlotNull.getWeekDay());
		assertTrue(timeSlot.getWasWeekDayCorrect());
		assertNull(timeSlotNull.getDate());
		assertNull(timeSlotNull.getTimeBegin());
		assertNull(timeSlotNull.getTimeEnd());
	}

	@Test
	final void testTimeSlotString() {
		TimeSlot timeSlot = new TimeSlot("Qui", "23/02/2023", "03:02:32", "11:23:4");
		assertEquals("Qui", timeSlot.getWeekDay());
		assertTrue(timeSlot.getWasWeekDayCorrect());
		assertEquals(LocalDate.of(2023, 2, 23), timeSlot.getDate());
		assertEquals(LocalTime.of(3, 2, 32), timeSlot.getTimeBegin());
		assertEquals(LocalTime.of(11, 23, 4), timeSlot.getTimeEnd());
		
		TimeSlot timeSlotWrongWeekDay = new TimeSlot("Sex", "23/02/2023", "03:02:32", "11:23:4");
		assertEquals("Qui", timeSlotWrongWeekDay .getWeekDay());
		assertFalse(timeSlotWrongWeekDay.getWasWeekDayCorrect());
		assertEquals(LocalDate.of(2023, 2, 23), timeSlotWrongWeekDay .getDate());
		assertEquals(LocalTime.of(3, 2, 32), timeSlotWrongWeekDay .getTimeBegin());
		assertEquals(LocalTime.of(11, 23, 4), timeSlotWrongWeekDay .getTimeEnd());
		
		TimeSlot timeSlotRedux = new TimeSlot("Qui", "23/2/2023", "3:2:32", "11:23:4");
		assertEquals("Qui", timeSlotRedux.getWeekDay());
		assertEquals(LocalDate.of(2023, 2, 23), timeSlotRedux.getDate());
		assertEquals(LocalTime.of(3, 2, 32), timeSlotRedux.getTimeBegin());
		assertEquals(LocalTime.of(11, 23, 4), timeSlotRedux.getTimeEnd());
		
		TimeSlot timeSlotNull = new TimeSlot(null, (String)null, (String)null, (String)null);
		assertNull(timeSlotNull.getWeekDay());
		assertNull(timeSlotNull.getDate());
		assertNull(timeSlotNull.getTimeBegin());
		assertNull(timeSlotNull.getTimeEnd());
	}

	@Test
	final void testTimeSlotStringException() {		
		IllegalArgumentException exceptionDate = assertThrows(IllegalArgumentException.class, () -> new TimeSlot("Sex", "23.02.2023", "03:02:00", "11:23:32"));
		assertEquals("Wrong date format", exceptionDate.getMessage());
		
		IllegalArgumentException exceptionImpossibleDate = assertThrows(IllegalArgumentException.class, () -> new TimeSlot("Sex", "23/23/2023", "03:02:00", "11:23:32"));
		assertEquals("Wrong date format", exceptionImpossibleDate.getMessage());
		
		IllegalArgumentException exceptionTimeBegin = assertThrows(IllegalArgumentException.class, () -> new TimeSlot("Sex", "23/02/2023", "03:0200", "11:23:32"));
		assertEquals("Wrong begin time format", exceptionTimeBegin.getMessage());
		
		IllegalArgumentException exceptionImpossibleTimeBegin = assertThrows(IllegalArgumentException.class, () -> new TimeSlot("Sex", "23/02/2023", "03:02:99", "11:23:32"));
		assertEquals("Wrong begin time format", exceptionImpossibleTimeBegin.getMessage());
		
		IllegalArgumentException exceptionTimeEnd = assertThrows(IllegalArgumentException.class, () -> new TimeSlot("Sex", "23/02/2023", "03:02:00", "11:23.32"));
		assertEquals("Wrong end time format", exceptionTimeEnd.getMessage());
		
		IllegalArgumentException exceptionImpossibleTimeEnd = assertThrows(IllegalArgumentException.class, () -> new TimeSlot("Sex", "23/02/2023", "03:02:00", "-1:23:32"));
		assertEquals("Wrong end time format", exceptionImpossibleTimeEnd.getMessage());
	}
	
	@Test
	final void testGetWeekDay() {
		TimeSlot timeSlot = new TimeSlot("Qui", LocalDate.of(2023, 2, 23), LocalTime.of(3,  2, 32), LocalTime.of(11, 23, 4));
		assertEquals("Qui", timeSlot.getWeekDay());
	}
	
	final void testFindWeekDay() {
		assertEquals("Seg", TimeSlot.findWeekDay(LocalDate.of(2023, 2, 20)));
		assertEquals("Ter", TimeSlot.findWeekDay(LocalDate.of(2023, 2, 21)));
		assertEquals("Qua", TimeSlot.findWeekDay(LocalDate.of(2023, 2, 22)));
		assertEquals("Qui", TimeSlot.findWeekDay(LocalDate.of(2023, 2, 23)));
		assertEquals("Sex", TimeSlot.findWeekDay(LocalDate.of(2023, 2, 24)));
		assertEquals("Sáb", TimeSlot.findWeekDay(LocalDate.of(2023, 2, 25)));
		assertEquals("Dom", TimeSlot.findWeekDay(LocalDate.of(2023, 2, 26)));		
	}
	
	@Test
	final void testGetWasWeekDayCorrect() {
		TimeSlot timeSlot = new TimeSlot("Qui", LocalDate.of(2023, 2, 23), LocalTime.of(3,  2, 32), LocalTime.of(11, 23, 4));
		assertEquals("Qui", timeSlot.getWeekDay());
		assertTrue(timeSlot.getWasWeekDayCorrect());
		
		TimeSlot timeSlotWrongWeekDay = new TimeSlot("Sex", LocalDate.of(2023, 2, 23), LocalTime.of(3,  2, 32), LocalTime.of(11, 23, 4));
		assertEquals("Qui", timeSlotWrongWeekDay.getWeekDay());
		assertFalse(timeSlotWrongWeekDay.getWasWeekDayCorrect());
	}
	
	@Test
	final void testGetDate() {
		TimeSlot timeSlot = new TimeSlot("Qui", LocalDate.of(2023, 2, 23), LocalTime.of(3,  2, 32), LocalTime.of(11, 23, 4));
		assertEquals(LocalDate.of(2023, 2, 23), timeSlot.getDate());
	}

	@Test
	final void testGetDateString() {
		TimeSlot timeSlot = new TimeSlot("Qui", LocalDate.of(2023, 2, 23), LocalTime.of(3,  2, 32), LocalTime.of(11, 23, 4));
		assertEquals("23/02/2023", timeSlot.getDateString());
		
		TimeSlot timeSlot2 = new TimeSlot("Sex", LocalDate.of(2023, 11, 4), LocalTime.of(3,  2, 32), LocalTime.of(11, 23, 4));
		assertEquals("04/11/2023", timeSlot2.getDateString());
		
		TimeSlot timeSlotNull = new TimeSlot("Seg", null, LocalTime.of(3,  2, 32), LocalTime.of(11, 23, 4));
		assertEquals("Unknown", timeSlotNull.getDateString());
	}

	@Test
	final void testSetDateLocalDate() {
		TimeSlot timeSlot = new TimeSlot("Sex", LocalDate.of(2023, 2, 23), LocalTime.of(3,  2, 32), LocalTime.of(11, 23, 4));
		timeSlot.setDate(LocalDate.of(2022, 01, 30));
		assertEquals("30/01/2022", timeSlot.getDateString());
		assertEquals("Dom", timeSlot.getWeekDay());
		timeSlot.setDate((LocalDate)null);
		assertEquals(Lecture.FORNULL, timeSlot.getDateString());
		assertNull(timeSlot.getDate());
		assertEquals("Dom", timeSlot.getWeekDay());
	}

	@Test
	final void testSetDateString() {
		TimeSlot timeSlot = new TimeSlot("Sex", LocalDate.of(2023, 2, 23), LocalTime.of(3,  2, 32), LocalTime.of(11, 23, 4));
		timeSlot.setDate("15/02/2024");
		assertEquals("15/02/2024", timeSlot.getDateString());
		assertEquals("Qui", timeSlot.getWeekDay());
		timeSlot.setDate("14/2/2024");
		assertEquals("14/02/2024", timeSlot.getDateString());
		assertEquals("Qua", timeSlot.getWeekDay());
		timeSlot.setDate((String)null);
		assertNull(timeSlot.getDate());
		assertEquals(Lecture.FORNULL, timeSlot.getDateString());
		assertEquals("Qua", timeSlot.getWeekDay());
	}
	
	@Test
	final void testSetDateStringException() {
		TimeSlot timeSlot = new TimeSlot("Sex", LocalDate.of(2023, 2, 23), LocalTime.of(3,  2, 32), LocalTime.of(11, 23, 4));
		
		IllegalArgumentException exceptionDate = assertThrows(IllegalArgumentException.class, () -> timeSlot.setDate("23/02/2023/04"));
		assertEquals("Wrong date format", exceptionDate.getMessage());
		
		IllegalArgumentException exceptionDate2 = assertThrows(IllegalArgumentException.class, () -> timeSlot.setDate("23/2023/04"));
		assertEquals("Wrong date format", exceptionDate2.getMessage());
		
		IllegalArgumentException exceptionDate3 = assertThrows(IllegalArgumentException.class, () -> timeSlot.setDate("jc23/02/2023"));
		assertEquals("Wrong date format", exceptionDate3.getMessage());
		
		IllegalArgumentException exceptionImpossibleDate = assertThrows(IllegalArgumentException.class, () -> timeSlot.setDate("23/-1/2023"));
		assertEquals("Wrong date format", exceptionImpossibleDate.getMessage());
		
		IllegalArgumentException exceptionImpossibleDate2 = assertThrows(IllegalArgumentException.class, () -> timeSlot.setDate("40/02/2023"));
		assertEquals("Wrong date format", exceptionImpossibleDate2.getMessage());
	}
	

	@Test
	final void testGetTimeBegin() {
		TimeSlot timeSlot = new TimeSlot("Sex", LocalDate.of(2023, 2, 23), LocalTime.of(3,  2, 32), LocalTime.of(11, 23, 4));
		assertEquals(LocalTime.of(3, 2, 32), timeSlot.getTimeBegin());
	}

	@Test
	final void testGetTimeBeginString() {
		TimeSlot timeSlot = new TimeSlot("Sex", LocalDate.of(2023, 2, 23), LocalTime.of(3,  2, 32), LocalTime.of(11, 23, 4));
		assertEquals("03:02:32", timeSlot.getTimeBeginString());
		
		TimeSlot timeSlotNoSeconds = new TimeSlot("Sex", LocalDate.of(2023, 2, 23), LocalTime.of(3,  2, 00), LocalTime.of(11, 23, 4));
		assertEquals("03:02:00", timeSlotNoSeconds.getTimeBeginString());
		
		TimeSlot timeSlotNull = new TimeSlot("Sex", LocalDate.of(2023, 2, 23), (LocalTime)null, LocalTime.of(11, 23, 4));
		assertEquals(Lecture.FORNULL, timeSlotNull.getTimeBeginString());
	}

	@Test
	final void testSetTimeBeginLocalTime() {
		TimeSlot timeSlot = new TimeSlot("Qui", LocalDate.of(2023, 2, 23), LocalTime.of(3,  2, 32), LocalTime.of(11, 23, 4));
		timeSlot.setTimeBegin(LocalTime.of(14, 4, 23));
		assertEquals("14:04:23", timeSlot.getTimeBeginString());
		timeSlot.setTimeBegin((LocalTime)null);
		assertEquals(Lecture.FORNULL, timeSlot.getTimeBeginString());
		assertNull(timeSlot.getTimeBegin());
	}

	@Test
	final void testSetTimeBeginString() {
		TimeSlot timeSlot = new TimeSlot("Qui", LocalDate.of(2023, 2, 23), LocalTime.of(3,  2, 32), LocalTime.of(11, 23, 4));
		timeSlot.setTimeBegin("14:4:23");
		assertEquals("14:04:23", timeSlot.getTimeBeginString());
		timeSlot.setTimeBegin((String)null);
		assertEquals(Lecture.FORNULL, timeSlot.getTimeBeginString());
		assertNull(timeSlot.getTimeBegin());
	}

	@Test
	final void testSetTimeBeginStringException() {
		TimeSlot timeSlot = new TimeSlot("Sex", LocalDate.of(2023, 2, 23), LocalTime.of(3,  2, 32), LocalTime.of(11, 23, 4));
		
		IllegalArgumentException exceptionTimeBegin = assertThrows(IllegalArgumentException.class, () -> timeSlot.setTimeBegin("23:02:56:04"));
		assertEquals("Wrong begin time format", exceptionTimeBegin.getMessage());
		
		IllegalArgumentException exceptionTimeBegin2 = assertThrows(IllegalArgumentException.class, () -> timeSlot.setTimeBegin("12:ab:23"));
		assertEquals("Wrong begin time format", exceptionTimeBegin2.getMessage());
		
		IllegalArgumentException exceptionTimeBegin3 = assertThrows(IllegalArgumentException.class, () -> timeSlot.setTimeBegin("23:"));
		assertEquals("Wrong begin time format", exceptionTimeBegin3.getMessage());
		
		IllegalArgumentException exceptionImpossibleTimeBegin = assertThrows(IllegalArgumentException.class, () -> timeSlot.setTimeBegin("23:-1:23"));
		assertEquals("Wrong begin time format", exceptionImpossibleTimeBegin.getMessage());
		
		IllegalArgumentException exceptionImpossibleTimeBegin2 = assertThrows(IllegalArgumentException.class, () -> timeSlot.setTimeBegin("40:02:20"));
		assertEquals("Wrong begin time format", exceptionImpossibleTimeBegin2.getMessage());
	}
	
	@Test
	final void testGetTimeEnd() {
		TimeSlot timeSlot = new TimeSlot("Sex", LocalDate.of(2023, 2, 23), LocalTime.of(3,  2, 32), LocalTime.of(11, 23, 4));
		assertEquals(LocalTime.of(11, 23, 4), timeSlot.getTimeEnd());
	}

	@Test
	final void testGetTimeEndString() {
		TimeSlot timeSlot = new TimeSlot("Sex", LocalDate.of(2023, 2, 23), LocalTime.of(3,  2, 32), LocalTime.of(11, 23, 4));
		assertEquals("11:23:04", timeSlot.getTimeEndString());
		
		TimeSlot timeSlotNoSeconds = new TimeSlot("Sex", LocalDate.of(2023, 2, 23), LocalTime.of(3,  2, 00), LocalTime.of(11, 23, 0));
		assertEquals("11:23:00", timeSlotNoSeconds.getTimeEndString());
		
		TimeSlot timeSlotNull = new TimeSlot("Sex", LocalDate.of(2023, 2, 23), LocalTime.of(11, 23, 4), (LocalTime)null);
		assertEquals(Lecture.FORNULL, timeSlotNull.getTimeEndString());
	}
	
	@Test
	final void testSetTimeEndLocalTime() {
		TimeSlot timeSlot = new TimeSlot("Qui", LocalDate.of(2023, 2, 23), LocalTime.of(3,  2, 32), LocalTime.of(11, 23, 4));
		timeSlot.setTimeEnd(LocalTime.of(14, 4, 23));
		assertEquals("14:04:23", timeSlot.getTimeEndString());
		timeSlot.setTimeEnd((LocalTime)null);
		assertEquals(Lecture.FORNULL, timeSlot.getTimeEndString());
		assertNull(timeSlot.getTimeEnd());
	}

	@Test
	final void testSetTimeEndString() {
		TimeSlot timeSlot = new TimeSlot("Qui", LocalDate.of(2023, 2, 23), LocalTime.of(3,  2, 32), LocalTime.of(11, 23, 4));
		timeSlot.setTimeEnd("14:4:23");
		assertEquals("14:04:23", timeSlot.getTimeEndString());
		timeSlot.setTimeEnd((String)null);
		assertEquals(Lecture.FORNULL, timeSlot.getTimeEndString());
		assertNull(timeSlot.getTimeEnd());
	}
	
	@Test
	final void testSetTimeEndStringException() {
		TimeSlot timeSlot = new TimeSlot("Sex", LocalDate.of(2023, 2, 23), LocalTime.of(3,  2, 32), LocalTime.of(11, 23, 4));
		
		IllegalArgumentException exceptionTimeEnd = assertThrows(IllegalArgumentException.class, () -> timeSlot.setTimeEnd("23:02:56:04"));
		assertEquals("Wrong end time format", exceptionTimeEnd.getMessage());
		
		IllegalArgumentException exceptionTimeEnd2 = assertThrows(IllegalArgumentException.class, () -> timeSlot.setTimeEnd("12:ab:23"));
		assertEquals("Wrong end time format", exceptionTimeEnd2.getMessage());
		
		IllegalArgumentException exceptionTimeEnd3 = assertThrows(IllegalArgumentException.class, () -> timeSlot.setTimeEnd("23:"));
		assertEquals("Wrong end time format", exceptionTimeEnd3.getMessage());
		
		IllegalArgumentException exceptionImpossibleTimeEnd = assertThrows(IllegalArgumentException.class, () -> timeSlot.setTimeEnd("23:-1:23"));
		assertEquals("Wrong end time format", exceptionImpossibleTimeEnd.getMessage());
		
		IllegalArgumentException exceptionImpossibleTimeEnd2 = assertThrows(IllegalArgumentException.class, () -> timeSlot.setTimeEnd("40:02:20"));
		assertEquals("Wrong end time format", exceptionImpossibleTimeEnd2.getMessage());
	}

	@Test
	final void testIsComplete() {
		TimeSlot timeSlot = new TimeSlot("Sex", null, LocalTime.of(3,  2, 32), LocalTime.of(11, 23, 4));
		assertFalse(timeSlot.isComplete());
		timeSlot.setDate("23/04/2021");
		assertTrue(timeSlot.isComplete());
		timeSlot.setTimeBegin((String)null);
		assertFalse(timeSlot.isComplete());
		timeSlot.setTimeBegin("02:23:54");
		timeSlot.setTimeEnd((String)null);
		assertFalse(timeSlot.isComplete());
	}
	
	@Test
	final void testIsValidInterval() {
		TimeSlot timeSlot = new TimeSlot("Sex", null, LocalTime.of(3,  2, 32), LocalTime.of(11, 23, 4));
		assertTrue(timeSlot.isValidInterval());
		
		TimeSlot timeSlotSwitch = new TimeSlot("Sex", null, LocalTime.of(11, 23, 4), LocalTime.of(3,  2, 32));
		assertFalse(timeSlotSwitch.isValidInterval());
		
		TimeSlot timeSlotSame = new TimeSlot("Sex", LocalDate.of(2023, 2, 23), LocalTime.of(11, 23, 4), LocalTime.of(11,  23, 4));
		assertFalse(timeSlotSame.isValidInterval());
	}
	
	@Test
	final void testCompareTo() {
		TimeSlot timeSlotSameDate = new TimeSlot("Qui", LocalDate.of(2023, 2, 23), LocalTime.of(3,  2, 32), LocalTime.of(11, 23, 4));
		TimeSlot timeSlotSameDate2 = new TimeSlot("Qui", LocalDate.of(2023, 2, 23), LocalTime.of(4,  2, 32), LocalTime.of(11, 23, 4));
		TimeSlot timeSlotSameDate3 = new TimeSlot("Qui", LocalDate.of(2023, 2, 23), LocalTime.of(4,  2, 32), LocalTime.of(2, 23, 4));
		assertEquals(-1, timeSlotSameDate.compareTo(timeSlotSameDate2));
		assertEquals(1, timeSlotSameDate2.compareTo(timeSlotSameDate));
		assertEquals(0, timeSlotSameDate2.compareTo(timeSlotSameDate3));
		assertEquals(-1, timeSlotSameDate.compareTo(timeSlotSameDate3));
		
		TimeSlot timeSlotDiffDate = new TimeSlot("Qui", LocalDate.of(2022, 2, 23), LocalTime.of(4,  2, 32), LocalTime.of(11, 23, 4));
		TimeSlot timeSlotDiffDate2 = new TimeSlot("Qui", LocalDate.of(2023, 2, 23), LocalTime.of(4,  2, 32), LocalTime.of(11, 23, 4));
		TimeSlot timeSlotDiffDate3 = new TimeSlot("Qui", LocalDate.of(2023, 2, 23), LocalTime.of(3,  2, 32), LocalTime.of(11, 23, 4));
		assertEquals(-1, timeSlotDiffDate.compareTo(timeSlotDiffDate2));
		assertEquals(1, timeSlotDiffDate2.compareTo(timeSlotDiffDate));
		assertEquals(-1, timeSlotDiffDate.compareTo(timeSlotDiffDate3));
	}

	@Test
	final void testToString() {
		TimeSlot timeSlot = new TimeSlot("Qui", LocalDate.of(2023, 2, 23), LocalTime.of(3,  2, 32), LocalTime.of(11, 23, 4));
		assertEquals("23/02/2023 - 03:02:32-11:23:04", timeSlot.toString());
		
		TimeSlot timeSlotNull = new TimeSlot(null, (String)null, (String)null, (String)null);
		assertEquals(Lecture.FORNULL + " - " + Lecture.FORNULL + "-" + Lecture.FORNULL, timeSlotNull.toString());
		
	}

}
