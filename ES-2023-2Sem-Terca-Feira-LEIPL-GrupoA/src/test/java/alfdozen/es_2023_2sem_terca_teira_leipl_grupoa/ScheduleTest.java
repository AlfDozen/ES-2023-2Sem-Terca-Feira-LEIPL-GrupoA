package alfdozen.es_2023_2sem_terca_teira_leipl_grupoa;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import alfdozen.es_2023_2sem_terca_teira_leipl_grupoa.AcademicInfo;
import alfdozen.es_2023_2sem_terca_teira_leipl_grupoa.Lecture;
import alfdozen.es_2023_2sem_terca_teira_leipl_grupoa.Room;
import alfdozen.es_2023_2sem_terca_teira_leipl_grupoa.Schedule;
import alfdozen.es_2023_2sem_terca_teira_leipl_grupoa.TimeSlot;

class ScheduleTest {
	@Test
	void testScheduleNull() {
		Schedule schedule = new Schedule(null, null, (Integer) null);
		assertNull(schedule.getStudentName());
		assertNull(schedule.getStudentNumber());
		assertEquals(new ArrayList<Lecture>(), schedule.getLectures());
	}

	@Test
	void testScheduleLecturesStudentNull() {
		Schedule schedule = new Schedule(null, null, (String) null);
		assertNull(schedule.getStudentName());
		assertNull(schedule.getStudentNumber());
		assertEquals(new ArrayList<Lecture>(), schedule.getLectures());
	}

	@Test
	void testScheduleLecturesNull() {
		Schedule schedule = new Schedule(null);
		assertNull(schedule.getStudentName());
		assertNull(schedule.getStudentNumber());
		assertEquals(new ArrayList<Lecture>(), schedule.getLectures());
	}

	@Test
	void testScheduleAllNull() {
		Schedule schedule = new Schedule(null, (Integer) null);
		assertNull(schedule.getStudentName());
		assertNull(schedule.getStudentNumber());
		assertEquals(new ArrayList<Lecture>(), schedule.getLectures());
	}

	@Test
	void testScheduleLecturesStudentNull2() {
		Schedule schedule = new Schedule(null, (String) null);
		assertNull(schedule.getStudentName());
		assertNull(schedule.getStudentNumber());
		assertEquals(new ArrayList<Lecture>(), schedule.getLectures());
	}

	@Test
	final void testScheduleEmptyConstructor() {
		Schedule schedule = new Schedule();
		assertNotNull(schedule.getLectures());
		assertNull(schedule.getStudentName());
		assertNull(schedule.getStudentNumber());
	}

	@Test
	final void testScheduleLecturesConstructor() {
		Lecture lecture = new Lecture(new AcademicInfo("LEI-PL", "Engenharia de Software", "T02A", "LEIPL1", 5),
				new TimeSlot("Qui", LocalDate.of(2023, 2, 23), LocalTime.of(3, 2, 32), LocalTime.of(11, 23, 4)),
				new Room("ES23", 20));
		List<Lecture> lectures = new ArrayList<>();
		lectures.add(lecture);
		Schedule schedule = new Schedule(lectures);
		assertEquals(lectures, schedule.getLectures());
		assertNull(schedule.getStudentName());
		assertNull(schedule.getStudentNumber());
	}

	@Test
	final void testScheduleFullConstructor() {
		Lecture lecture = new Lecture(new AcademicInfo("LEI-PL", "Engenharia de Software", "T02A", "LEIPL1", 5),
				new TimeSlot("Qui", LocalDate.of(2023, 2, 23), LocalTime.of(3, 2, 32), LocalTime.of(11, 23, 4)),
				new Room("ES23", 20));
		List<Lecture> lectures = new ArrayList<>();
		lectures.add(lecture);
		String studentName = "John Doe";
		Integer studentNumber = 12345;
		Schedule schedule = new Schedule(lectures, studentName, studentNumber);
		assertEquals(lectures, schedule.getLectures());
		assertEquals(studentName, schedule.getStudentName());
		assertEquals(studentNumber, schedule.getStudentNumber());
	}

	@Test
	final void testScheduleFullConstructorString() {
		Lecture lecture = new Lecture(new AcademicInfo("LEI-PL", "Engenharia de Software", "T02A", "LEIPL1", 5),
				new TimeSlot("Qui", LocalDate.of(2023, 2, 23), LocalTime.of(3, 2, 32), LocalTime.of(11, 23, 4)),
				new Room("ES23", 20));
		List<Lecture> lectures = new ArrayList<>();
		lectures.add(lecture);
		String studentName = "John Doe";
		String studentNumber = "12345";
		Schedule schedule = new Schedule(lectures, studentName, studentNumber);
		assertEquals(lectures, schedule.getLectures());
		assertEquals(studentName, schedule.getStudentName());
		assertEquals(Integer.valueOf(studentNumber), schedule.getStudentNumber());
	}

	@Test
	final void testScheduleNameNumberConstructor() {
		String studentName = "John Doe";
		Integer studentNumber = 12345;
		Schedule schedule = new Schedule(studentName, studentNumber);
		assertNotNull(schedule.getLectures());
		assertEquals(studentName, schedule.getStudentName());
		assertEquals(studentNumber, schedule.getStudentNumber());
	}

	@Test
	final void testScheduleNameStringNumberConstructor() {
		String studentName = "John Doe";
		String studentNumber = "12345";
		Schedule schedule = new Schedule(studentName, studentNumber);
		assertNotNull(schedule.getLectures());
		assertEquals(studentName, schedule.getStudentName());
		assertEquals(Integer.valueOf(studentNumber), schedule.getStudentNumber());
	}
	
	@Test
	final void testScheduleConstructorNegativeStudentNumber() {
	    assertThrows(IllegalArgumentException.class, () -> new Schedule("John Doe", -123));
	}

	@Test
	final void testScheduleConstructorInvalidStringStudentNumber() {
	    assertThrows(NumberFormatException.class, () -> new Schedule("John Doe", "abc"));
	}
	@Test
	final void testaddSortingLectures() {
		Schedule schedule = new Schedule();
		Lecture lecture1 = new Lecture(new AcademicInfo("LEI-PL", "Engenharia de Software", "T02A", "LEIPL1", 5),
				new TimeSlot("Qui", LocalDate.of(2023, 2, 23), LocalTime.of(3, 2, 32), LocalTime.of(11, 23, 4)),
				new Room("ES23", 20));
		Lecture lecture2 = new Lecture(new AcademicInfo("LP2", "Linguagens de Programação 2", "T02A", "LP21", 5),
				new TimeSlot("Qui", LocalDate.of(2023, 2, 23), LocalTime.of(12, 0, 0), LocalTime.of(13, 0, 0)),
				new Room("ES23", 20));
		Lecture lecture3 = new Lecture(new AcademicInfo("IA", "Inteligência Artificial", "T02A", "IA1", 5),
				new TimeSlot("Qui", LocalDate.of(2023, 2, 23), LocalTime.of(14, 0, 0), LocalTime.of(15, 0, 0)),
				new Room("ES23", 20));
		schedule.addLecture(lecture2);
		schedule.addLecture(lecture3);
		schedule.addLecture(lecture1);
		assertEquals(lecture1, schedule.getLectures().get(0));
		assertEquals(lecture2, schedule.getLectures().get(1));
		assertEquals(lecture3, schedule.getLectures().get(2));
	}
	@Test
	final void testaddSortingLectures2() {
		Schedule schedule = new Schedule();
		Lecture lecture1 = new Lecture(new AcademicInfo("LEI-PL", "Engenharia de Software", "T02A", "LEIPL1", 5),
				new TimeSlot("Qui", LocalDate.of(2022, 1, 23), LocalTime.of(17, 2, 32), LocalTime.of(11, 23, 4)),
				new Room("ES23", 20));
		Lecture lecture2 = new Lecture(new AcademicInfo("LP2", "Linguagens de Programação 2", "T02A", "LP21", 5),
				new TimeSlot("Qui", LocalDate.of(2022, 2, 23), LocalTime.of(12, 0, 0), LocalTime.of(13, 0, 0)),
				new Room("ES23", 20));
		Lecture lecture3 = new Lecture(new AcademicInfo("IA", "Inteligência Artificial", "T02A", "IA1", 5),
				new TimeSlot("Qui", LocalDate.of(2023, 2, 23), LocalTime.of(14, 0, 0), LocalTime.of(15, 0, 0)),
				new Room("ES23", 20));
		schedule.addLecture(lecture2);
		schedule.addLecture(lecture3);
		schedule.addLecture(lecture1);
		assertEquals(lecture1, schedule.getLectures().get(0));
		assertEquals(lecture2, schedule.getLectures().get(1));
		assertEquals(lecture3, schedule.getLectures().get(2));
	}

	@Test
	final void testScheduleLectureSorting() {
		Lecture lecture1 = new Lecture(new AcademicInfo("LEI-PL", "Engenharia de Software", "T02A", "LEIPL1", 5),
				new TimeSlot("Qui", LocalDate.of(2023, 2, 23), LocalTime.of(3, 2, 32), LocalTime.of(11, 23, 4)),
				new Room("ES23", 20));
		Lecture lecture2 = new Lecture(new AcademicInfo("LP2", "Linguagens de Programação 2", "T02A", "LP21", 5),
				new TimeSlot("Qui", LocalDate.of(2023, 2, 23), LocalTime.of(12, 0, 0), LocalTime.of(13, 0, 0)),
				new Room("ES23", 20));
		Lecture lecture3 = new Lecture(new AcademicInfo("IA", "Inteligência Artificial", "T02A", "IA1", 5),
				new TimeSlot("Qui", LocalDate.of(2023, 2, 23), LocalTime.of(14, 0, 0), LocalTime.of(15, 0, 0)),
				new Room("ES23", 20));

		List<Lecture> lectures = new ArrayList<>();
		lectures.add(lecture2);
		lectures.add(lecture3);
		lectures.add(lecture1);

		Schedule schedule = new Schedule(lectures);
		assertEquals(lecture1, schedule.getLectures().get(0));
		assertEquals(lecture2, schedule.getLectures().get(1));
		assertEquals(lecture3, schedule.getLectures().get(2));
	}
	@Test
	void testSetLecturesSorting() {
		Lecture lecture1 = new Lecture(new AcademicInfo("LEI-PL", "Engenharia de Software", "T02A", "LEIPL1", 5),
				new TimeSlot("Qui", LocalDate.of(2023, 2, 23), LocalTime.of(3, 2, 32), LocalTime.of(11, 23, 4)),
				new Room("ES23", 20));
		Lecture lecture2 = new Lecture(new AcademicInfo("LP2", "Linguagens de Programação 2", "T02A", "LP21", 5),
				new TimeSlot("Qui", LocalDate.of(2023, 2, 23), LocalTime.of(12, 0, 0), LocalTime.of(13, 0, 0)),
				new Room("ES23", 20));
		Lecture lecture3 = new Lecture(new AcademicInfo("IA", "Inteligência Artificial", "T02A", "IA1", 5),
				new TimeSlot("Qui", LocalDate.of(2023, 2, 23), LocalTime.of(14, 0, 0), LocalTime.of(15, 0, 0)),
				new Room("ES23", 20));

	    List<Lecture> lectures = new ArrayList<>();
	    lectures.add(lecture3);
	    lectures.add(lecture1);
	    lectures.add(lecture2);

	    Schedule schedule = new Schedule();
	    schedule.setLectures(lectures);

	    assertEquals(lecture1, schedule.getLectures().get(0));
	    assertEquals(lecture2, schedule.getLectures().get(1));
	    assertEquals(lecture3, schedule.getLectures().get(2));
	}


	@Test
	void testAddAndRemoveLecture() {
		Lecture lecture = new Lecture(new AcademicInfo("LEI-PL", "Engenharia de Software", "T02A", "LEIPL1", 5),
				new TimeSlot("Qui", LocalDate.of(2023, 2, 23), LocalTime.of(3, 2, 32), LocalTime.of(11, 23, 4)),
				new Room("ES23", 20));
		Schedule schedule = new Schedule();
		assertTrue(schedule.getLectures().isEmpty());
		schedule.addLecture(lecture);
		assertEquals(1, schedule.getLectures().size());
		assertEquals(lecture, schedule.getLectures().get(0));
		assertTrue(schedule.getLectures().contains(lecture));
		schedule.removeLecture(lecture);
		assertTrue(schedule.getLectures().isEmpty());
		assertFalse(schedule.getLectures().contains(lecture));
		schedule.removeLecture(lecture);
	}

	@Test
	void testSetStudentName() {
		Schedule schedule = new Schedule();
		String name = "John Doe";
		schedule.setStudentName(name);
		assertEquals(name, schedule.getStudentName());
	}
	
	@Test
	final void testSetStudentNameNull() {
	    Schedule schedule = new Schedule();
	    schedule.setStudentName(null);
	    assertNull(schedule.getStudentName());
	}
	@Test
	void testSetStudentNumberPositive() {
		Schedule schedule = new Schedule();
		Integer number = 12345;
		schedule.setStudentNumber(number);
		assertEquals(number, schedule.getStudentNumber());
	}
	@Test
	final void testSetStudentNumberNull() {
	    Schedule schedule = new Schedule();
	    schedule.setStudentNumber(null);
	    assertNull(schedule.getStudentNumber());
	}

	@Test
	final void testSetStudentNumberNegative() {
	    Schedule schedule = new Schedule();
	    assertThrows(IllegalArgumentException.class, () -> schedule.setStudentNumber(-12345));
	    assertNull(schedule.getStudentNumber());
	}
	@Test
	final void testToString() {
	    Schedule schedule = new Schedule();
	    String expected = "Unknown Student Name\nUnknown Student Number\nSchedule is empty";
	    assertEquals(expected, schedule.toString());

	    schedule = new Schedule(null, null, (Integer) null);
	    expected = "Unknown Student Name\nUnknown Student Number\nSchedule is empty";
	    assertEquals(expected, schedule.toString());

	    schedule = new Schedule(new ArrayList<Lecture>(), null, (Integer) null);
	    expected = "Unknown Student Name\nUnknown Student Number\nSchedule is empty";
	    assertEquals(expected, schedule.toString());
	    
	    schedule = new Schedule(null, null, (String) null);
	    expected = "Unknown Student Name\nUnknown Student Number\nSchedule is empty";
	    assertEquals(expected, schedule.toString());

	    schedule = new Schedule(new ArrayList<Lecture>(), null, (String) null);
	    expected = "Unknown Student Name\nUnknown Student Number\nSchedule is empty";
	    assertEquals(expected, schedule.toString());

	    Lecture lecture = new Lecture(new AcademicInfo("LEI-PL", "Engenharia de Software", "T02A", "LEIPL1", 5),
	            new TimeSlot("Qui", LocalDate.of(2023, 2, 23), LocalTime.of(3, 2, 32), LocalTime.of(11, 23, 4)),
	            new Room("ES23", 20));
	    List<Lecture> lectures = new ArrayList<>();
	    lectures.add(lecture);
	    schedule = new Schedule(lectures, "John Doe", 12345);
	    expected = "Student Name: John Doe\nStudent Number: 12345\nSchedule:\n23/02/2023 - 03:02:32-11:23:04 - Engenharia de Software - T02A - Room ES23\n";
	    assertEquals(expected, schedule.toString());
	}

	@Test
	final void testLoadCSV() {
		// Path null
		assertThrows(IllegalArgumentException.class, () -> Schedule.loadCSV(null));
		
		// Ficheiro txt
		assertThrows(IllegalArgumentException.class, () -> Schedule.loadCSV("./src/main/resources/horario_exemplo_txt.txt"));
		
		// Ficheiro apenas com linhas em branco, cabeçalho e linhas vazias
		String expected = "Unknown Student Name\nUnknown Student Number\nSchedule is empty";
		assertEquals(expected, Schedule.loadCSV("./src/main/resources/horario_exemplo_sem_dados.csv").toString());
		
		// Entrada com mais de 11 colunas
		assertThrows(IllegalStateException.class, () -> Schedule.loadCSV("./src/main/resources/horario_exemplo_12colunas.csv"));
		
		// Entrada com 11 colunas
		Lecture lecture3 = new Lecture(new AcademicInfo("ME", "Teoria dos Jogos e dos Contratos", "01789TP01", "MEA1", 30),
	            new TimeSlot("Sex", LocalDate.of(2022, 12, 2), LocalTime.of(13, 0, 0), LocalTime.of(14, 30, 0)),
	            new Room("AA2.25", 34));
		List<Lecture> lectures3 = new ArrayList<>();
		lectures3.add(lecture3);
		Schedule expected3 = new Schedule(lectures3);
		assertEquals(expected3.toString(), Schedule.loadCSV("./src/main/resources/horario_exemplo_11colunas.csv").toString());
		
		// Entrada com menos de 11 colunas
		Lecture lecture4 = new Lecture(new AcademicInfo("ME", "Teoria dos Jogos e dos Contratos", "01789TP01", "MEA1", 30),
	            new TimeSlot("Sex", LocalDate.of(2022, 12, 2), LocalTime.of(13, 0, 0), LocalTime.of(14, 30, 0)),
	            new Room(null, (Integer)null));
		List<Lecture> lectures4 = new ArrayList<>();
		lectures4.add(lecture4);
		Schedule expected4 = new Schedule(lectures4);
		assertEquals(expected4.toString(), Schedule.loadCSV("./src/main/resources/horario_exemplo_9colunas.csv").toString());
		
		// Entrada com campos vazios no meio
		Lecture lecture5 = new Lecture(new AcademicInfo("ME", "Teoria dos Jogos e dos Contratos", null, "MEA1", 30),
	            new TimeSlot("Sex", null, LocalTime.of(13, 0, 0), LocalTime.of(14, 30, 0)),
	            new Room("AA2.25", 34));
		List<Lecture> lectures5 = new ArrayList<>();
		lectures5.add(lecture5);
		Schedule expected5 = new Schedule(lectures5);
		assertEquals(expected5.toString(), Schedule.loadCSV("./src/main/resources/horario_exemplo_campos_vazios.csv").toString());

		
	}





}
