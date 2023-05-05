package alfdozen.es_2023_2sem_terca_teira_leipl_grupoa;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;

class TimeSlotTest {

	@Test
	final void testTimeSlotLocal() {
		TimeSlot timeSlot = new TimeSlot("Qui", LocalDate.of(2023, 2, 23), LocalTime.of(3, 2, 32),
				LocalTime.of(11, 23, 4));
		assertEquals("Qui", timeSlot.getWeekDay());
		assertTrue(timeSlot.getWasWeekDayCorrect());
		assertEquals(LocalDate.of(2023, 2, 23), timeSlot.getDate());
		assertEquals(LocalTime.of(3, 2, 32), timeSlot.getTimeBegin());
		assertEquals(LocalTime.of(11, 23, 4), timeSlot.getTimeEnd());

		TimeSlot timeSlotWrongWeekDay = new TimeSlot("Sex", LocalDate.of(2023, 2, 23), LocalTime.of(3, 2, 32),
				LocalTime.of(11, 23, 4));
		assertEquals("Qui", timeSlotWrongWeekDay.getWeekDay());
		assertFalse(timeSlotWrongWeekDay.getWasWeekDayCorrect());
		assertEquals(LocalDate.of(2023, 2, 23), timeSlotWrongWeekDay.getDate());
		assertEquals(LocalTime.of(3, 2, 32), timeSlotWrongWeekDay.getTimeBegin());
		assertEquals(LocalTime.of(11, 23, 4), timeSlotWrongWeekDay.getTimeEnd());

		TimeSlot timeSlotNull = new TimeSlot(null, (LocalDate) null, (LocalTime) null, (LocalTime) null);
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
		assertEquals("Qui", timeSlotWrongWeekDay.getWeekDay());
		assertFalse(timeSlotWrongWeekDay.getWasWeekDayCorrect());
		assertEquals(LocalDate.of(2023, 2, 23), timeSlotWrongWeekDay.getDate());
		assertEquals(LocalTime.of(3, 2, 32), timeSlotWrongWeekDay.getTimeBegin());
		assertEquals(LocalTime.of(11, 23, 4), timeSlotWrongWeekDay.getTimeEnd());

		TimeSlot timeSlotRedux = new TimeSlot("Qui", "23/2/2023", "3:2:32", "11:23:4");
		assertEquals("Qui", timeSlotRedux.getWeekDay());
		assertEquals(LocalDate.of(2023, 2, 23), timeSlotRedux.getDate());
		assertEquals(LocalTime.of(3, 2, 32), timeSlotRedux.getTimeBegin());
		assertEquals(LocalTime.of(11, 23, 4), timeSlotRedux.getTimeEnd());

		TimeSlot timeSlotNull = new TimeSlot(null, (String) null, (String) null, (String) null);
		assertNull(timeSlotNull.getWeekDay());
		assertNull(timeSlotNull.getDate());
		assertNull(timeSlotNull.getTimeBegin());
		assertNull(timeSlotNull.getTimeEnd());
	}

	@Test
	final void testTimeSlotStringException() {
		IllegalArgumentException exceptionDate = assertThrows(IllegalArgumentException.class,
				() -> new TimeSlot("Sex", "23.02.2023", "03:02:00", "11:23:32"));
		assertEquals(TimeSlot.WRONG_DATE_FORMAT, exceptionDate.getMessage());

		IllegalArgumentException exceptionImpossibleDate = assertThrows(IllegalArgumentException.class,
				() -> new TimeSlot("Sex", "23/23/2023", "03:02:00", "11:23:32"));
		assertEquals(TimeSlot.WRONG_DATE_FORMAT, exceptionImpossibleDate.getMessage());

		IllegalArgumentException exceptionTimeBegin = assertThrows(IllegalArgumentException.class,
				() -> new TimeSlot("Sex", "23/02/2023", "03:0200", "11:23:32"));
		assertEquals(TimeSlot.WRONG_BEGIN_TIME_FORMAT, exceptionTimeBegin.getMessage());

		IllegalArgumentException exceptionImpossibleTimeBegin = assertThrows(IllegalArgumentException.class,
				() -> new TimeSlot("Sex", "23/02/2023", "03:02:99", "11:23:32"));
		assertEquals(TimeSlot.WRONG_BEGIN_TIME_FORMAT, exceptionImpossibleTimeBegin.getMessage());

		IllegalArgumentException exceptionTimeEnd = assertThrows(IllegalArgumentException.class,
				() -> new TimeSlot("Sex", "23/02/2023", "03:02:00", "11:23.32"));
		assertEquals(TimeSlot.WRONG_END_TIME_FORMAT, exceptionTimeEnd.getMessage());

		IllegalArgumentException exceptionImpossibleTimeEnd = assertThrows(IllegalArgumentException.class,
				() -> new TimeSlot("Sex", "23/02/2023", "03:02:00", "-1:23:32"));
		assertEquals(TimeSlot.WRONG_END_TIME_FORMAT, exceptionImpossibleTimeEnd.getMessage());
	}

	@Test
	final void testGetWeekDay() {
		TimeSlot timeSlot = new TimeSlot("Qui", LocalDate.of(2023, 2, 23), LocalTime.of(3, 2, 32),
				LocalTime.of(11, 23, 4));
		assertEquals("Qui", timeSlot.getWeekDay());
	}

	final void testFindWeekDay() {
		TimeSlot timeSlot = new TimeSlot("Qui", LocalDate.of(2023, 2, 20), LocalTime.of(3, 2, 32),
				LocalTime.of(11, 23, 4));
		assertEquals("Seg", timeSlot.getWeekDay());
		timeSlot = new TimeSlot("Qui", LocalDate.of(2023, 2, 21), LocalTime.of(3, 2, 32), LocalTime.of(11, 23, 4));
		assertEquals("Seg", timeSlot.getWeekDay());
		timeSlot = new TimeSlot("Qui", LocalDate.of(2023, 2, 22), LocalTime.of(3, 2, 32), LocalTime.of(11, 23, 4));
		assertEquals("Seg", timeSlot.getWeekDay());
		timeSlot = new TimeSlot("Qui", LocalDate.of(2023, 2, 23), LocalTime.of(3, 2, 32), LocalTime.of(11, 23, 4));
		assertEquals("Seg", timeSlot.getWeekDay());
		timeSlot = new TimeSlot("Qui", LocalDate.of(2023, 2, 24), LocalTime.of(3, 2, 32), LocalTime.of(11, 23, 4));
		assertEquals("Seg", timeSlot.getWeekDay());
		timeSlot = new TimeSlot("Qui", LocalDate.of(2023, 2, 25), LocalTime.of(3, 2, 32), LocalTime.of(11, 23, 4));
		assertEquals("Seg", timeSlot.getWeekDay());
		timeSlot = new TimeSlot("Qui", LocalDate.of(2023, 2, 26), LocalTime.of(3, 2, 32), LocalTime.of(11, 23, 4));
		assertEquals("Seg", timeSlot.getWeekDay());
	}

	@Test
	final void testGetWasWeekDayCorrect() {
		TimeSlot timeSlot = new TimeSlot("Qui", LocalDate.of(2023, 2, 23), LocalTime.of(3, 2, 32),
				LocalTime.of(11, 23, 4));
		assertEquals("Qui", timeSlot.getWeekDay());
		assertTrue(timeSlot.getWasWeekDayCorrect());

		TimeSlot timeSlotWrongWeekDay = new TimeSlot("Sex", LocalDate.of(2023, 2, 23), LocalTime.of(3, 2, 32),
				LocalTime.of(11, 23, 4));
		assertEquals("Qui", timeSlotWrongWeekDay.getWeekDay());
		assertFalse(timeSlotWrongWeekDay.getWasWeekDayCorrect());
	}

	@Test
	final void testGetDate() {
		TimeSlot timeSlot = new TimeSlot("Qui", LocalDate.of(2023, 2, 23), LocalTime.of(3, 2, 32),
				LocalTime.of(11, 23, 4));
		assertEquals(LocalDate.of(2023, 2, 23), timeSlot.getDate());
	}

	@Test
	final void testGetDateString() {
		TimeSlot timeSlot = new TimeSlot("Qui", LocalDate.of(2023, 2, 23), LocalTime.of(3, 2, 32),
				LocalTime.of(11, 23, 4));
		assertEquals("23/02/2023", timeSlot.getDateString());

		TimeSlot timeSlot2 = new TimeSlot("Sex", LocalDate.of(2023, 11, 4), LocalTime.of(3, 2, 32),
				LocalTime.of(11, 23, 4));
		assertEquals("04/11/2023", timeSlot2.getDateString());

		TimeSlot timeSlotNull = new TimeSlot("Seg", null, LocalTime.of(3, 2, 32), LocalTime.of(11, 23, 4));
		assertEquals("Unknown", timeSlotNull.getDateString());
	}

	@Test
	final void testSetDateLocalDate() {
		TimeSlot timeSlot = new TimeSlot("Sex", LocalDate.of(2023, 2, 23), LocalTime.of(3, 2, 32),
				LocalTime.of(11, 23, 4));
		timeSlot.setDate(LocalDate.of(2022, 01, 30));
		assertEquals("30/01/2022", timeSlot.getDateString());
		assertEquals("Dom", timeSlot.getWeekDay());
		timeSlot.setDate((LocalDate) null);
		assertEquals(Lecture.FOR_NULL, timeSlot.getDateString());
		assertNull(timeSlot.getDate());
		assertEquals("Dom", timeSlot.getWeekDay());
	}

	@Test
	final void testSetDateString() {
		TimeSlot timeSlot = new TimeSlot("Sex", LocalDate.of(2023, 2, 23), LocalTime.of(3, 2, 32),
				LocalTime.of(11, 23, 4));
		timeSlot.setDate("15/02/2024");
		assertEquals("15/02/2024", timeSlot.getDateString());
		assertEquals("Qui", timeSlot.getWeekDay());
		timeSlot.setDate("14/2/2024");
		assertEquals("14/02/2024", timeSlot.getDateString());
		assertEquals("Qua", timeSlot.getWeekDay());
		timeSlot.setDate((String) null);
		assertNull(timeSlot.getDate());
		assertEquals(Lecture.FOR_NULL, timeSlot.getDateString());
		assertEquals("Qua", timeSlot.getWeekDay());
	}

	@Test
	final void testSetDateStringException() {
		TimeSlot timeSlot = new TimeSlot("Sex", LocalDate.of(2023, 2, 23), LocalTime.of(3, 2, 32),
				LocalTime.of(11, 23, 4));

		IllegalArgumentException exceptionDate = assertThrows(IllegalArgumentException.class,
				() -> timeSlot.setDate("23/02/2023/04"));
		assertEquals(TimeSlot.WRONG_DATE_FORMAT, exceptionDate.getMessage());

		IllegalArgumentException exceptionDate2 = assertThrows(IllegalArgumentException.class,
				() -> timeSlot.setDate("23/2023/04"));
		assertEquals(TimeSlot.WRONG_DATE_FORMAT, exceptionDate2.getMessage());

		IllegalArgumentException exceptionDate3 = assertThrows(IllegalArgumentException.class,
				() -> timeSlot.setDate("jc23/02/2023"));
		assertEquals(TimeSlot.WRONG_DATE_FORMAT, exceptionDate3.getMessage());

		IllegalArgumentException exceptionImpossibleDate = assertThrows(IllegalArgumentException.class,
				() -> timeSlot.setDate("23/-1/2023"));
		assertEquals(TimeSlot.WRONG_DATE_FORMAT, exceptionImpossibleDate.getMessage());

		IllegalArgumentException exceptionImpossibleDate2 = assertThrows(IllegalArgumentException.class,
				() -> timeSlot.setDate("40/02/2023"));
		assertEquals(TimeSlot.WRONG_DATE_FORMAT, exceptionImpossibleDate2.getMessage());
	}

	@Test
	final void testGetTimeBegin() {
		TimeSlot timeSlot = new TimeSlot("Sex", LocalDate.of(2023, 2, 23), LocalTime.of(3, 2, 32),
				LocalTime.of(11, 23, 4));
		assertEquals(LocalTime.of(3, 2, 32), timeSlot.getTimeBegin());
	}

	@Test
	final void testGetTimeBeginString() {
		TimeSlot timeSlot = new TimeSlot("Sex", LocalDate.of(2023, 2, 23), LocalTime.of(3, 2, 32),
				LocalTime.of(11, 23, 4));
		assertEquals("03:02:32", timeSlot.getTimeBeginString());

		TimeSlot timeSlotNoSeconds = new TimeSlot("Sex", LocalDate.of(2023, 2, 23), LocalTime.of(3, 2, 00),
				LocalTime.of(11, 23, 4));
		assertEquals("03:02:00", timeSlotNoSeconds.getTimeBeginString());

		TimeSlot timeSlotNull = new TimeSlot("Sex", LocalDate.of(2023, 2, 23), (LocalTime) null,
				LocalTime.of(11, 23, 4));
		assertEquals(Lecture.FOR_NULL, timeSlotNull.getTimeBeginString());
	}

	@Test
	final void testSetTimeBeginLocalTime() {
		TimeSlot timeSlot = new TimeSlot("Qui", LocalDate.of(2023, 2, 23), LocalTime.of(3, 2, 32),
				LocalTime.of(11, 23, 4));
		timeSlot.setTimeBegin(LocalTime.of(14, 4, 23));
		assertEquals("14:04:23", timeSlot.getTimeBeginString());
		timeSlot.setTimeBegin((LocalTime) null);
		assertEquals(Lecture.FOR_NULL, timeSlot.getTimeBeginString());
		assertNull(timeSlot.getTimeBegin());
	}

	@Test
	final void testSetTimeBeginString() {
		TimeSlot timeSlot = new TimeSlot("Qui", LocalDate.of(2023, 2, 23), LocalTime.of(3, 2, 32),
				LocalTime.of(11, 23, 4));
		timeSlot.setTimeBegin("14:4:23");
		assertEquals("14:04:23", timeSlot.getTimeBeginString());
		timeSlot.setTimeBegin((String) null);
		assertEquals(Lecture.FOR_NULL, timeSlot.getTimeBeginString());
		assertNull(timeSlot.getTimeBegin());
	}

	@Test
	final void testSetTimeBeginStringException() {
		TimeSlot timeSlot = new TimeSlot("Sex", LocalDate.of(2023, 2, 23), LocalTime.of(3, 2, 32),
				LocalTime.of(11, 23, 4));

		IllegalArgumentException exceptionTimeBegin = assertThrows(IllegalArgumentException.class,
				() -> timeSlot.setTimeBegin("23:02:56:04"));
		assertEquals(TimeSlot.WRONG_BEGIN_TIME_FORMAT, exceptionTimeBegin.getMessage());

		IllegalArgumentException exceptionTimeBegin2 = assertThrows(IllegalArgumentException.class,
				() -> timeSlot.setTimeBegin("12:ab:23"));
		assertEquals(TimeSlot.WRONG_BEGIN_TIME_FORMAT, exceptionTimeBegin2.getMessage());

		IllegalArgumentException exceptionTimeBegin3 = assertThrows(IllegalArgumentException.class,
				() -> timeSlot.setTimeBegin("23:"));
		assertEquals(TimeSlot.WRONG_BEGIN_TIME_FORMAT, exceptionTimeBegin3.getMessage());

		IllegalArgumentException exceptionImpossibleTimeBegin = assertThrows(IllegalArgumentException.class,
				() -> timeSlot.setTimeBegin("23:-1:23"));
		assertEquals(TimeSlot.WRONG_BEGIN_TIME_FORMAT, exceptionImpossibleTimeBegin.getMessage());

		IllegalArgumentException exceptionImpossibleTimeBegin2 = assertThrows(IllegalArgumentException.class,
				() -> timeSlot.setTimeBegin("40:02:20"));
		assertEquals(TimeSlot.WRONG_BEGIN_TIME_FORMAT, exceptionImpossibleTimeBegin2.getMessage());
	}

	@Test
	final void testGetTimeEnd() {
		TimeSlot timeSlot = new TimeSlot("Sex", LocalDate.of(2023, 2, 23), LocalTime.of(3, 2, 32),
				LocalTime.of(11, 23, 4));
		assertEquals(LocalTime.of(11, 23, 4), timeSlot.getTimeEnd());
	}

	@Test
	final void testGetTimeEndString() {
		TimeSlot timeSlot = new TimeSlot("Sex", LocalDate.of(2023, 2, 23), LocalTime.of(3, 2, 32),
				LocalTime.of(11, 23, 4));
		assertEquals("11:23:04", timeSlot.getTimeEndString());

		TimeSlot timeSlotNoSeconds = new TimeSlot("Sex", LocalDate.of(2023, 2, 23), LocalTime.of(3, 2, 00),
				LocalTime.of(11, 23, 0));
		assertEquals("11:23:00", timeSlotNoSeconds.getTimeEndString());

		TimeSlot timeSlotNull = new TimeSlot("Sex", LocalDate.of(2023, 2, 23), LocalTime.of(11, 23, 4),
				(LocalTime) null);
		assertEquals(Lecture.FOR_NULL, timeSlotNull.getTimeEndString());
	}

	@Test
	final void testSetTimeEndLocalTime() {
		TimeSlot timeSlot = new TimeSlot("Qui", LocalDate.of(2023, 2, 23), LocalTime.of(3, 2, 32),
				LocalTime.of(11, 23, 4));
		timeSlot.setTimeEnd(LocalTime.of(14, 4, 23));
		assertEquals("14:04:23", timeSlot.getTimeEndString());
		timeSlot.setTimeEnd((LocalTime) null);
		assertEquals(Lecture.FOR_NULL, timeSlot.getTimeEndString());
		assertNull(timeSlot.getTimeEnd());
	}

	@Test
	final void testSetTimeEndString() {
		TimeSlot timeSlot = new TimeSlot("Qui", LocalDate.of(2023, 2, 23), LocalTime.of(3, 2, 32),
				LocalTime.of(11, 23, 4));
		timeSlot.setTimeEnd("14:4:23");
		assertEquals("14:04:23", timeSlot.getTimeEndString());
		timeSlot.setTimeEnd((String) null);
		assertEquals(Lecture.FOR_NULL, timeSlot.getTimeEndString());
		assertNull(timeSlot.getTimeEnd());
	}

	@Test
	final void testSetTimeEndStringException() {
		TimeSlot timeSlot = new TimeSlot("Sex", LocalDate.of(2023, 2, 23), LocalTime.of(3, 2, 32),
				LocalTime.of(11, 23, 4));

		IllegalArgumentException exceptionTimeEnd = assertThrows(IllegalArgumentException.class,
				() -> timeSlot.setTimeEnd("23:02:56:04"));
		assertEquals(TimeSlot.WRONG_END_TIME_FORMAT, exceptionTimeEnd.getMessage());

		IllegalArgumentException exceptionTimeEnd2 = assertThrows(IllegalArgumentException.class,
				() -> timeSlot.setTimeEnd("12:ab:23"));
		assertEquals(TimeSlot.WRONG_END_TIME_FORMAT, exceptionTimeEnd2.getMessage());

		IllegalArgumentException exceptionTimeEnd3 = assertThrows(IllegalArgumentException.class,
				() -> timeSlot.setTimeEnd("23:"));
		assertEquals(TimeSlot.WRONG_END_TIME_FORMAT, exceptionTimeEnd3.getMessage());

		IllegalArgumentException exceptionImpossibleTimeEnd = assertThrows(IllegalArgumentException.class,
				() -> timeSlot.setTimeEnd("23:-1:23"));
		assertEquals(TimeSlot.WRONG_END_TIME_FORMAT, exceptionImpossibleTimeEnd.getMessage());

		IllegalArgumentException exceptionImpossibleTimeEnd2 = assertThrows(IllegalArgumentException.class,
				() -> timeSlot.setTimeEnd("40:02:20"));
		assertEquals(TimeSlot.WRONG_END_TIME_FORMAT, exceptionImpossibleTimeEnd2.getMessage());
	}

	@Test
	final void testIsComplete() {
		TimeSlot timeSlot = new TimeSlot("Sex", null, LocalTime.of(3, 2, 32), LocalTime.of(11, 23, 4));
		assertFalse(timeSlot.isComplete());
		timeSlot.setDate("23/04/2021");
		assertTrue(timeSlot.isComplete());
		timeSlot.setTimeBegin((String) null);
		assertFalse(timeSlot.isComplete());
		timeSlot.setTimeBegin("02:23:54");
		timeSlot.setTimeEnd((String) null);
		assertFalse(timeSlot.isComplete());

		TimeSlot timeSlotNull = new TimeSlot(null, (LocalDate) null, (LocalTime) null, (LocalTime) null);
		assertFalse(timeSlotNull.isComplete());
	}

	@Test
	final void testIsValidInterval() {
		TimeSlot timeSlot = new TimeSlot("Sex", null, LocalTime.of(3, 2, 32), LocalTime.of(11, 23, 4));
		assertTrue(timeSlot.isValidInterval());

		TimeSlot timeSlotSwitch = new TimeSlot("Sex", null, LocalTime.of(11, 23, 4), LocalTime.of(3, 2, 32));
		assertFalse(timeSlotSwitch.isValidInterval());

		TimeSlot timeSlotSame = new TimeSlot("Sex", LocalDate.of(2023, 2, 23), LocalTime.of(11, 23, 4),
				LocalTime.of(11, 23, 4));
		assertFalse(timeSlotSame.isValidInterval());

		TimeSlot timeSlotNull = new TimeSlot("Sex", null, null, LocalTime.of(11, 23, 4));
		assertFalse(timeSlotNull.isValidInterval());

		TimeSlot timeSlotNull2 = new TimeSlot("Sex", null, LocalTime.of(3, 2, 32), null);
		assertFalse(timeSlotNull2.isValidInterval());
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
		assertEquals(-1, timeSlotSameBeginTime.compareTo(timeSlotSameBeginTime2));
		assertEquals(1, timeSlotSameBeginTime2.compareTo(timeSlotSameBeginTime));
		assertEquals(0, timeSlotSameBeginTime.compareTo(timeSlotSameBeginTime3));
		assertEquals(0, timeSlotSameBeginTimeNull.compareTo(timeSlotSameBeginTimeNull2));
		assertEquals(-1, timeSlotSameBeginTimeNull.compareTo(timeSlotSameBeginTime));
		assertEquals(1, timeSlotSameBeginTime.compareTo(timeSlotSameBeginTimeNull));

		TimeSlot timeSlotSameDate = new TimeSlot("Qui", LocalDate.of(2023, 2, 23), LocalTime.of(3, 2, 32),
				LocalTime.of(11, 23, 4));
		TimeSlot timeSlotSameDate2 = new TimeSlot("Qui", LocalDate.of(2023, 2, 23), LocalTime.of(4, 1, 10),
				LocalTime.of(11, 23, 4));
		TimeSlot timeSlotSameDateNull = new TimeSlot("Qui", LocalDate.of(2023, 2, 23), (LocalTime) null,
				LocalTime.of(11, 23, 4));
		TimeSlot timeSlotSameDateNull2 = new TimeSlot("Qui", LocalDate.of(2023, 2, 23), (LocalTime) null,
				LocalTime.of(11, 23, 4));
		assertEquals(-1, timeSlotSameDate.compareTo(timeSlotSameDate2));
		assertEquals(1, timeSlotSameDate2.compareTo(timeSlotSameDate));
		assertEquals(0, timeSlotSameDateNull.compareTo(timeSlotSameDateNull2));
		assertEquals(-1, timeSlotSameDateNull.compareTo(timeSlotSameDate));
		assertEquals(1, timeSlotSameDate.compareTo(timeSlotSameDateNull));

		TimeSlot timeSlotDiffDate = new TimeSlot("Qui", LocalDate.of(2022, 2, 23), LocalTime.of(4, 2, 32),
				LocalTime.of(11, 23, 4));
		TimeSlot timeSlotDiffDate2 = new TimeSlot("Qui", LocalDate.of(2023, 2, 23), LocalTime.of(4, 2, 32),
				LocalTime.of(11, 23, 4));
		TimeSlot timeSlotDiffDateNull = new TimeSlot("Qui", (LocalDate) null, LocalTime.of(4, 2, 32),
				LocalTime.of(11, 23, 4));
		TimeSlot timeSlotDiffDateNull2 = new TimeSlot("Qui", (LocalDate) null, LocalTime.of(4, 2, 32),
				LocalTime.of(11, 23, 4));
		assertEquals(-1, timeSlotDiffDate.compareTo(timeSlotDiffDate2));
		assertEquals(1, timeSlotDiffDate2.compareTo(timeSlotDiffDate));
		assertEquals(0, timeSlotDiffDateNull.compareTo(timeSlotDiffDateNull2));
		assertEquals(-1, timeSlotDiffDateNull.compareTo(timeSlotDiffDate));
		assertEquals(1, timeSlotDiffDate.compareTo(timeSlotDiffDateNull));
	}

	@Test
	final void testResolveCompareToNull() {
		assertEquals(0, TimeSlot.resolveCompareToNull(new Object(), new Object()));
		assertEquals(-1, TimeSlot.resolveCompareToNull((Object) null, new Object()));
		assertEquals(1, TimeSlot.resolveCompareToNull(new Object(), (Object) null));

		assertEquals(0, TimeSlot.resolveCompareToNull(LocalDate.of(2022, 2, 23), LocalDate.of(2022, 2, 23)));
		assertEquals(-1, TimeSlot.resolveCompareToNull((LocalDate) null, LocalDate.of(2022, 2, 23)));
		assertEquals(1, TimeSlot.resolveCompareToNull(LocalDate.of(2022, 2, 23), (LocalDate) null));

		assertEquals(0, TimeSlot.resolveCompareToNull(LocalTime.of(4, 2, 32), LocalTime.of(4, 2, 32)));
		assertEquals(-1, TimeSlot.resolveCompareToNull((LocalTime) null, LocalTime.of(4, 2, 32)));
		assertEquals(1, TimeSlot.resolveCompareToNull(LocalTime.of(4, 2, 32), (LocalTime) null));

		assertEquals(0, TimeSlot.resolveCompareToNull(new TimeSlot(null, (String) null, (String) null, (String) null),
				new TimeSlot(null, (String) null, (String) null, (String) null)));
		assertEquals(-1, TimeSlot.resolveCompareToNull((TimeSlot) null,
				new TimeSlot(null, (String) null, (String) null, (String) null)));
		assertEquals(1, TimeSlot.resolveCompareToNull(new TimeSlot(null, (String) null, (String) null, (String) null),
				(TimeSlot) null));
	}

	@Test
	final void testToString() {
		TimeSlot timeSlot = new TimeSlot("Qui", LocalDate.of(2023, 2, 23), LocalTime.of(3, 2, 32),
				LocalTime.of(11, 23, 4));
		assertEquals("23/02/2023 - 03:02:32-11:23:04", timeSlot.toString());

		TimeSlot timeSlotNull = new TimeSlot(null, (String) null, (String) null, (String) null);
		assertEquals(Lecture.FOR_NULL + " - " + Lecture.FOR_NULL + "-" + Lecture.FOR_NULL, timeSlotNull.toString());
	}
	
	@Test
    final void testOverlaps() {
		TimeSlot timeSlot1 = new TimeSlot("Seg","01/05/2023", "10:00:00", "11:00:00");
		TimeSlot timeSlot2 = new TimeSlot("Seg","01/05/2023","09:00:00", "10:30:00");
		TimeSlot timeSlot3 = new TimeSlot("Seg","01/05/2023", "11:00:01", "12:00:00");
		TimeSlot timeSlot4= new TimeSlot("Seg","01/05/2023", null, "12:00:00");
		TimeSlot timeSlot5 = new TimeSlot("Ter","02/05/2023", "11:00:01", "12:00:00");
		TimeSlot timeSlot6 = new TimeSlot("Seg","01/05/2023", "10:59:59", "12:00:00");
		TimeSlot timeSlot7 = new TimeSlot(null,"01/05/2023",null, null);
		TimeSlot timeSlot8 = new TimeSlot("Seg",null, null, "12:00:00");
		

        assertTrue(timeSlot1.overlaps(timeSlot2));
        assertTrue(timeSlot1.overlaps(timeSlot6));

        assertFalse(timeSlot1.overlaps(timeSlot3));
        assertFalse(timeSlot1.overlaps(timeSlot4));
        assertFalse(timeSlot4.overlaps(timeSlot1));
        assertFalse(timeSlot5.overlaps(timeSlot3));
        assertFalse(timeSlot7.overlaps(timeSlot8));
	}

}
