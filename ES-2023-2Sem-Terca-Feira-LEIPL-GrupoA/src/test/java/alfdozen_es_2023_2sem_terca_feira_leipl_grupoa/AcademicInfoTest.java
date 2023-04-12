package alfdozen_es_2023_2sem_terca_feira_leipl_grupoa;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import alfdozen_es_2023_2sem_terca_feira_leipl_grupoa.AcademicInfo;
import alfdozen_es_2023_2sem_terca_feira_leipl_grupoa.Lecture;

class AcademicInfoTest {

	@Test
	final void testAcademicInfoIntegerEnrolled() {
		AcademicInfo academicInfo = new AcademicInfo("LEI-PL", "Engenharia de Software", "T02A", "LEIPL1", 5);
		assertEquals("LEI-PL", academicInfo.getDegree());
		assertEquals("Engenharia de Software", academicInfo.getCourse());
		assertEquals("T02A", academicInfo.getShift());
		assertEquals("LEIPL1", academicInfo.getClassGroup());
		assertEquals(5, academicInfo.getStudentsEnrolled());

		AcademicInfo academicInfoNull = new AcademicInfo(null, null, null, null, (Integer) null);
		assertNull(academicInfoNull.getDegree());
		assertNull(academicInfoNull.getCourse());
		assertNull(academicInfoNull.getShift());
		assertNull(academicInfoNull.getClassGroup());
		assertNull(academicInfoNull.getStudentsEnrolled());
	}

	@Test
	final void testAcademicInfoIntegerEnrolledException() {
		IllegalArgumentException exceptionNegative = assertThrows(IllegalArgumentException.class,
				() -> new AcademicInfo("LEI-PL", "Engenharia de Software", "T02A", "LEIPL1", -1));
		assertEquals(AcademicInfo.NEGATIVE_EXCEPTION, exceptionNegative.getMessage());
	}

	@Test
	final void testAcademicInfoStringEnrolled() {
		AcademicInfo academicInfo = new AcademicInfo("LEI-PL", "Engenharia de Software", "T02A", "LEIPL1", "5");
		assertEquals("LEI-PL", academicInfo.getDegree());
		assertEquals("Engenharia de Software", academicInfo.getCourse());
		assertEquals("T02A", academicInfo.getShift());
		assertEquals("LEIPL1", academicInfo.getClassGroup());
		assertEquals(5, academicInfo.getStudentsEnrolled());

		AcademicInfo academicInfoNull = new AcademicInfo(null, null, null, null, (String) null);
		assertNull(academicInfoNull.getDegree());
		assertNull(academicInfoNull.getCourse());
		assertNull(academicInfoNull.getShift());
		assertNull(academicInfoNull.getClassGroup());
		assertNull(academicInfoNull.getStudentsEnrolled());
	}

	@Test
	final void testAcademicInfoStringEnrolledException() {
		NumberFormatException exceptionNotNumber = assertThrows(NumberFormatException.class,
				() -> new AcademicInfo("LEI-PL", "Engenharia de Software", "T02A", "LEIPL1", "a"));
		assertEquals(AcademicInfo.NOT_NUMBER_EXCEPTION, exceptionNotNumber.getMessage());

		IllegalArgumentException exceptionNegative = assertThrows(IllegalArgumentException.class,
				() -> new AcademicInfo("LEI-PL", "Engenharia de Software", "T02A", "LEIPL1", "-1"));
		assertEquals(AcademicInfo.NEGATIVE_EXCEPTION, exceptionNegative.getMessage());
	}

	@Test
	final void testGetDegree() {
		AcademicInfo academicInfo = new AcademicInfo("LEI-PL", "Engenharia de Software", "T02A", "LEIPL1", 5);
		assertEquals("LEI-PL", academicInfo.getDegree());
	}

	@Test
	final void testSetDegree() {
		AcademicInfo academicInfo = new AcademicInfo("LEI-PL", "Engenharia de Software", "T02A", "LEIPL1", 5);
		academicInfo.setDegree("IGE");
		assertEquals("IGE", academicInfo.getDegree());
		academicInfo.setDegree(null);
		assertNull(academicInfo.getDegree());
	}

	@Test
	final void testGetCourse() {
		AcademicInfo academicInfo = new AcademicInfo("LEI-PL", "Engenharia de Software", "T02A", "LEIPL1", 5);
		assertEquals("Engenharia de Software", academicInfo.getCourse());
	}

	@Test
	final void testSetCourse() {
		AcademicInfo academicInfo = new AcademicInfo("LEI-PL", "Engenharia de Software", "T02A", "LEIPL1", 5);
		academicInfo.setCourse("Cálculo I");
		assertEquals("Cálculo I", academicInfo.getCourse());
		academicInfo.setCourse(null);
		assertNull(academicInfo.getCourse());
	}

	@Test
	final void testGetCourseAbbreviation() {
		AcademicInfo academicInfo = new AcademicInfo("LEI-PL", "Engenharia de Software", "T02A", "LEIPL1", 5);
		assertEquals("ES", academicInfo.getCourseAbbreviation());
		academicInfo.setCourse("Interacção Pessoa-Máquina");
		assertEquals("IPM", academicInfo.getCourseAbbreviation());
		academicInfo.setCourse("MicroProceSsadorES I");
		assertEquals("MPSESI", academicInfo.getCourseAbbreviation());
		academicInfo.setCourse(null);
		assertNull(academicInfo.getCourseAbbreviation());
	}

	@Test
	final void testGetShift() {
		AcademicInfo academicInfo = new AcademicInfo("LEI-PL", "Engenharia de Software", "T02A", "LEIPL1", 5);
		assertEquals("T02A", academicInfo.getShift());
	}

	@Test
	final void testSetShift() {
		AcademicInfo academicInfo = new AcademicInfo("LEI-PL", "Engenharia de Software", "T02A", "LEIPL1", 5);
		academicInfo.setShift("23SD2");
		assertEquals("23SD2", academicInfo.getShift());
		academicInfo.setShift(null);
		assertNull(academicInfo.getShift());
	}

	@Test
	final void testGetClassGroup() {
		AcademicInfo academicInfo = new AcademicInfo("LEI-PL", "Engenharia de Software", "T02A", "LEIPL1", 5);
		assertEquals("LEIPL1", academicInfo.getClassGroup());
	}

	@Test
	final void testSetClassGroup() {
		AcademicInfo academicInfo = new AcademicInfo("LEI-PL", "Engenharia de Software", "T02A", "LEIPL1", 5);
		academicInfo.setClassGroup("IGE2");
		assertEquals("IGE2", academicInfo.getClassGroup());
		academicInfo.setClassGroup(null);
		assertNull(academicInfo.getClassGroup());
	}

	@Test
	final void testGetStudentsEnrolled() {
		AcademicInfo academicInfo = new AcademicInfo("LEI-PL", "Engenharia de Software", "T02A", "LEIPL1", 5);
		assertEquals(5, academicInfo.getStudentsEnrolled());
	}

	@Test
	final void testSetStudentsEnrolledInteger() {
		AcademicInfo academicInfo = new AcademicInfo("LEI-PL", "Engenharia de Software", "T02A", "LEIPL1", 5);
		academicInfo.setStudentsEnrolled(10);
		assertEquals(10, academicInfo.getStudentsEnrolled());
		academicInfo.setStudentsEnrolled((Integer) null);
		assertNull(academicInfo.getStudentsEnrolled());
	}

	@Test
	final void testSetStudentsEnrolledIntegerException() {
		AcademicInfo academicInfo = new AcademicInfo("LEI-PL", "Engenharia de Software", "T02A", "LEIPL1", 5);
		IllegalArgumentException exceptionNegative = assertThrows(IllegalArgumentException.class,
				() -> academicInfo.setStudentsEnrolled(-1));
		assertEquals(AcademicInfo.NEGATIVE_EXCEPTION, exceptionNegative.getMessage());
	}

	@Test
	final void testSetStudentsEnrolledString() {
		AcademicInfo academicInfo = new AcademicInfo("LEI-PL", "Engenharia de Software", "T02A", "LEIPL1", 5);
		academicInfo.setStudentsEnrolled("10");
		assertEquals(10, academicInfo.getStudentsEnrolled());
		academicInfo.setStudentsEnrolled((String) null);
		assertNull(academicInfo.getStudentsEnrolled());
	}

	@Test
	final void testSetStudentsEnrolledStringException() {
		AcademicInfo academicInfo = new AcademicInfo("LEI-PL", "Engenharia de Software", "T02A", "LEIPL1", 5);
		IllegalArgumentException exceptionNotNumber = assertThrows(IllegalArgumentException.class,
				() -> academicInfo.setStudentsEnrolled("abc"));
		assertEquals(AcademicInfo.NOT_NUMBER_EXCEPTION, exceptionNotNumber.getMessage());

		IllegalArgumentException exceptionNegative = assertThrows(IllegalArgumentException.class,
				() -> academicInfo.setStudentsEnrolled("-1"));
		assertEquals(AcademicInfo.NEGATIVE_EXCEPTION, exceptionNegative.getMessage());
	}

	@Test
	final void testIsComplete() {
		AcademicInfo academicInfo = new AcademicInfo("LEI-PL", "Engenharia de Software", "T02A", "LEIPL1", 5);
		assertTrue(academicInfo.isComplete());
		academicInfo.setDegree(null);
		assertFalse(academicInfo.isComplete());
		academicInfo.setDegree("AQP");
		academicInfo.setCourse(null);
		assertFalse(academicInfo.isComplete());
		academicInfo.setCourse("MicroProcessadores");
		academicInfo.setShift(null);
		assertFalse(academicInfo.isComplete());
		academicInfo.setShift("EASD");
		academicInfo.setClassGroup(null);
		assertFalse(academicInfo.isComplete());
		academicInfo.setClassGroup("AQP1");
		academicInfo.setStudentsEnrolled((String) null);
		assertFalse(academicInfo.isComplete());
	}

	@Test
	final void testToString() {
		AcademicInfo academicInfo = new AcademicInfo("LEI-PL", "Engenharia de Software", "T02A", "LEIPL1", 5);
		assertEquals("Degree LEI-PL - Course Engenharia de Software - Shift T02A - Class LEIPL1 - 5 Enrolled Students",
				academicInfo.toString());

		AcademicInfo academicInfoNull = new AcademicInfo(null, null, null, null, (Integer) null);
		assertEquals(
				"Degree " + Lecture.FOR_NULL + " - Course " + Lecture.FOR_NULL + " - Shift " + Lecture.FOR_NULL
						+ " - Class " + Lecture.FOR_NULL + " - " + Lecture.FOR_NULL + " Enrolled Students",
				academicInfoNull.toString());
	}

}
