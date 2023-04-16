package alfdozen.es_2023_2sem_terca_teira_leipl_grupoa;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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
	final void testScheduleConstructorStringNegativeStudentNumber() {
		assertThrows(IllegalArgumentException.class, () -> new Schedule("John Doe", "-123"));
	}

	@Test
	final void testScheduleConstructorInvalidStringStudentNumber() {
		assertThrows(NumberFormatException.class, () -> new Schedule("John Doe", "abc"));
	}

	@Test
	final void testAddSortingLectures() {
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
	final void testAddSortingLectures2() {
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
		assertThrows(IllegalArgumentException.class, () -> schedule.removeLecture(lecture));
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

	@ParameterizedTest
	@CsvSource({
			// null json
			"src/resources/horario_exemplo.csv,,';', IllegalArgumentException",
			// null csv
			", src/resources/horario-exemplo-output.json, ';', IllegalArgumentException",
			// falta extensão .csv
			"src/resources/horario_exemplo, src/resources/horario-exemplo-output.json, ';', IllegalArgumentException",
			// falta extensão .json
			"src/resources/horario_exemplo.csv, src/resources/horario-exemplo-output, ';', IllegalArgumentException",
			// nao existe parent directory json
			"src/resources/horario_exemplo.csv, src/resources/naoexiste/horario-exemplo-output.json, ';', IllegalArgumentException",
			// sucesso
			"src/resources/horario_exemplo.csv, src/resources/horario-exemplo-APAGAR-APÓS-CORRER2.json, ';', N/A",
			// json já existe
			"src/resources/horario_exemplo.csv, src/resources/horario-exemplo_completo.json, ';', IllegalArgumentException",
			// não existe csv
			"src/resources/horario_exemplo_csv_nao_existe.csv, src/resources/horario-exemplo_completo.json, ';', IllegalArgumentException",
			// delimiter null
			"src/resources/horario_exemplo.csv, src/resources/horario-exemplo-APAGAR-APÓS-CORRER2.json,, IllegalArgumentException", })
	final void testConvertCSV2JSONArguments(String csvSourcePath, String jsonDestinationPath, Character delimiter,
			String expectedException) {
		if (expectedException.equals("IllegalArgumentException")) {
			assertThrows(IllegalArgumentException.class,
					() -> Schedule.convertCSV2JSON(csvSourcePath, jsonDestinationPath, delimiter));
		} else {
			assertFalse(Files.exists(Paths.get(jsonDestinationPath)));
			try {
				Schedule.convertCSV2JSON(csvSourcePath, jsonDestinationPath, delimiter);
				assertTrue(Files.exists(Paths.get(jsonDestinationPath)));
				Files.deleteIfExists(Paths.get(jsonDestinationPath));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@ParameterizedTest
	@CsvSource({
			// null csv
			"src/resources/horario_exemplo_json_completo.json,, ';', IllegalArgumentException",
			// null json
			", src/resources/horario_exemplo_csv_outputJSON2CSV.csv, ';', IllegalArgumentException",
			// falta extensão .json
			"src/resources/horario_exemplo_json_completo, src/resources/horario_exemplo_csv_outputJSON2CSV.csv, ';', IllegalArgumentException",
			// falta extensão .csv
			"src/resources/horario_exemplo_json_completo.json, src/resources/horario_exemplo_csv_outputJSON2CSV, ';', IllegalArgumentException",
			// nao existe parent directory csv
			"src/resources/horario_exemplo_json_completo.json, src/resources/naoexiste/horario_exemplo_csv_outputJSON2CSV.csv, ';', IllegalArgumentException",
			// csv já existe
			"src/resources/horario_exemplo_json_completo.json, src/resources/horario_exemplo.csv, ';', IllegalArgumentException",
			// json não existe
			"src/resources/horario_exemplo_json_completo_nao_existe.json, src/resources/horario_exemplo_csv_iso.csv, ';', IllegalArgumentException",
			// sucesso
			"src/resources/horario_exemplo_json_completo.json, src/resources/horario_exemplo_csv_outputJSON2CSV-APAGAR-APÓS-CORRER1.csv, ';', N/A",
			// delimiter null
			"src/resources/horario_exemplo_json_completo.json, src/resources/horario_exemplo_csv_outputJSON2CSV-APAGAR-APÓS-CORRER2.csv,, IllegalArgumentException", })
	final void testConvertJSON2CSVArguments(String jsonSourcePath, String csvDestinationPath, Character delimiter,
			String expectedException) {
		if (expectedException.equals("IllegalArgumentException")) {
			assertThrows(IllegalArgumentException.class,
					() -> Schedule.convertJSON2CSV(jsonSourcePath, csvDestinationPath, delimiter));
		} else {
			assertFalse(Files.exists(Paths.get(csvDestinationPath)));
			try {
				Schedule.convertJSON2CSV(jsonSourcePath, csvDestinationPath, delimiter);
				assertTrue(Files.exists(Paths.get(csvDestinationPath)));
				Files.deleteIfExists(Paths.get(csvDestinationPath));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	final void testSaveToCSV() {
		Lecture lecture = new Lecture(
				new AcademicInfo("PIUDHIST", "Seminário de Projecto I (Piudhist)", "SP-I_(Piudhist)S01", "DHMCMG1", 0),
				new TimeSlot("Seg", LocalDate.of(2022, 10, 31), LocalTime.of(18, 0, 0), LocalTime.of(20, 0, 0)),
				new Room("AA2.23", 50));
		Lecture lecture2 = new Lecture(new AcademicInfo(null, null, null, null, (String) null),
				new TimeSlot(null, null, (String) null, null), new Room(null, (String) null));
		Lecture lecture3 = new Lecture(
				new AcademicInfo("LETI, LEI, LEI-PL, LIGE, LIGE-PL", "Fundamentos de Arquitectura de Computadores",
						"L0705TP23", "ET-A9, ET-A8, ET-A7, ET-A12, ET-A11, ET-A10", 44),
				new TimeSlot("Sex", LocalDate.of(2022, 9, 16), LocalTime.of(13, 0, 0), LocalTime.of(14, 30, 0)),
				new Room("AA2.23", 50));
		List<Lecture> lectures = new ArrayList<>();
		lectures.add(lecture);
		lectures.add(lecture2);
		lectures.add(lecture3);
		Schedule expected = new Schedule(lectures);
		Schedule created = new Schedule();
		try {
			Schedule.saveToCSV(expected, "teste.csv");
			created = Schedule.loadCSV("teste.csv");
			Path path = Paths.get("teste.csv");
			Files.deleteIfExists(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<Lecture> expectedLectures = expected.getLectures();
		List<Lecture> createdLectures = created.getLectures();
		assertEquals(expectedLectures.size() - 1, createdLectures.size());
		// Devido ao sort, o index 0 do expectedLectures tem uma Lecture null. Esta
		// Lecture não existe no createdLectures uma vez que o loadCSV a ignora.
		assertEquals(expectedLectures.get(1).getAcademicInfo().toString(),
				createdLectures.get(0).getAcademicInfo().toString());
		assertEquals(expectedLectures.get(1).getRoom().toString(), createdLectures.get(0).getRoom().toString());
		assertEquals(expectedLectures.get(1).getTimeSlot().toString(), createdLectures.get(0).getTimeSlot().toString());
		assertEquals(expectedLectures.get(2).getAcademicInfo().toString(),
				createdLectures.get(1).getAcademicInfo().toString());
		assertEquals(expectedLectures.get(2).getRoom().toString(), createdLectures.get(1).getRoom().toString());
		assertEquals(expectedLectures.get(2).getTimeSlot().toString(), createdLectures.get(1).getTimeSlot().toString());

		IOException saveException = assertThrows(IOException.class,
				() -> Schedule.saveToCSV(expected, "ZD:\\\\THIS_IS_ER00R"));
		assertEquals(Schedule.SAVE_FILE_EXCEPTION, saveException.getMessage());
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
	final void testLoadCSV() throws IOException {
		// Path null
		assertThrows(IllegalArgumentException.class, () -> Schedule.loadCSV(null));

		// Ficheiro txt
		assertThrows(IllegalArgumentException.class,
				() -> Schedule.loadCSV("./src/main/resources/horario_exemplo_txt.txt"));

		// Ficheiro apenas com linhas em branco, cabeçalho e linhas vazias
		String expected = "Unknown Student Name\nUnknown Student Number\nSchedule is empty";
		assertEquals(expected, Schedule.loadCSV("./src/main/resources/horario_exemplo_sem_dados.csv").toString());

		// Entrada com mais de 11 colunas
		assertThrows(IllegalStateException.class,
				() -> Schedule.loadCSV("./src/main/resources/horario_exemplo_12colunas.csv"));

		// Entrada com 11 colunas
		Lecture lecture3 = new Lecture(
				new AcademicInfo("ME", "Teoria dos Jogos e dos Contratos", "01789TP01", "MEA1", 30),
				new TimeSlot("Sex", LocalDate.of(2022, 12, 2), LocalTime.of(13, 0, 0), LocalTime.of(14, 30, 0)),
				new Room("AA2.25", 34));
		List<Lecture> lectures3 = new ArrayList<>();
		lectures3.add(lecture3);
		Schedule expected3 = new Schedule(lectures3);
		assertEquals(expected3.toString(),
				Schedule.loadCSV("./src/main/resources/horario_exemplo_11colunas.csv").toString());

		// Entrada com menos de 11 colunas
		Lecture lecture4 = new Lecture(
				new AcademicInfo("ME", "Teoria dos Jogos e dos Contratos", "01789TP01", "MEA1", 30),
				new TimeSlot("Sex", LocalDate.of(2022, 12, 2), LocalTime.of(13, 0, 0), LocalTime.of(14, 30, 0)),
				new Room(null, (Integer) null));
		List<Lecture> lectures4 = new ArrayList<>();
		lectures4.add(lecture4);
		Schedule expected4 = new Schedule(lectures4);
		assertEquals(expected4.toString(),
				Schedule.loadCSV("./src/main/resources/horario_exemplo_9colunas.csv").toString());

		// Entrada com campos vazios no meio
		Lecture lecture5 = new Lecture(new AcademicInfo("ME", "Teoria dos Jogos e dos Contratos", null, "MEA1", 30),
				new TimeSlot("Sex", null, LocalTime.of(13, 0, 0), LocalTime.of(14, 30, 0)), new Room("AA2.25", 34));
		List<Lecture> lectures5 = new ArrayList<>();
		lectures5.add(lecture5);
		Schedule expected5 = new Schedule(lectures5);
		assertEquals(expected5.toString(),
				Schedule.loadCSV("./src/main/resources/horario_exemplo_campos_vazios.csv").toString());

		// Ficheiro que não se consegue ler
		IOException saveException = assertThrows(IOException.class, () -> Schedule.loadCSV("ZD:\\\\THIS_IS_ER00R.csv"));
		assertEquals(Schedule.READ_EXCEPTION, saveException.getMessage());

	}

	@Test
	final void testSaveToJSON() {
		Lecture lecture = new Lecture(
				new AcademicInfo("PIUDHIST", "Seminário de Projecto I (Piudhist)", "SP-I_(Piudhist)S01", "DHMCMG1", 0),
				new TimeSlot("Seg", LocalDate.of(2022, 10, 31), LocalTime.of(18, 0, 0), LocalTime.of(20, 0, 0)),
				new Room("AA2.23", 50));
		Lecture lecture2 = new Lecture(new AcademicInfo(null, null, null, null, (String) null),
				new TimeSlot(null, null, (String) null, null), new Room(null, (String) null));
		Lecture lecture3 = new Lecture(
				new AcademicInfo("LETI, LEI, LEI-PL, LIGE, LIGE-PL", "Fundamentos de Arquitectura de Computadores",
						"L0705TP23", "ET-A9, ET-A8, ET-A7, ET-A12, ET-A11, ET-A10", 44),
				new TimeSlot("Sex", LocalDate.of(2022, 9, 16), LocalTime.of(13, 0, 0), LocalTime.of(14, 30, 0)),
				new Room("AA2.23", 50));
		List<Lecture> lectures = new ArrayList<>();
		lectures.add(lecture);
		lectures.add(lecture2);
		lectures.add(lecture3);
		Schedule expected = new Schedule(lectures);

		try {
			Schedule.saveToJSON(expected, "teste.json");
		} catch (IOException e) {
			e.printStackTrace();
		}
//		Path path1 = Paths.get("./src/main/resources/Method_save_JSON_horario.json");
//		Path path2 = Paths.get("teste.json");
//		long result = 0;
//		try {
//			result = Files.mismatch(path1, path2);
//			Files.deleteIfExists(path2);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		assertEquals(-1, result);

		IOException saveException = assertThrows(IOException.class,
				() -> Schedule.saveToJSON(expected, "ZD:\\THIS_IS_ER00R"));
		assertEquals(Schedule.SAVE_FILE_EXCEPTION, saveException.getMessage());
	}

	@Test
	final void testLoadJSON() throws IOException {
		// Path null
		assertThrows(IllegalArgumentException.class, () -> Schedule.loadJSON(null));

		// Ficheiro txt
		assertThrows(IllegalArgumentException.class,
				() -> Schedule.loadJSON("./src/main/resources/horario_exemplo_txt.txt"));

		// Path errado
		assertThrows(IOException.class,
				() -> Schedule.loadJSON("./src/main/resources/horario_exemplo_nao_existe.json"));

		// Entrada com campos vazios
		Lecture lecture4 = new Lecture(new AcademicInfo("ME", null, "01789TP01", "MEA1", 30),
				new TimeSlot("Sex", null, null, LocalTime.of(14, 30, 0)), new Room(null, (Integer) null));
		List<Lecture> lectures4 = new ArrayList<>();
		lectures4.add(lecture4);
		Schedule expected4 = new Schedule(lectures4);
		assertEquals(expected4.toString(),
				Schedule.loadJSON("./src/main/resources/horario_exemplo_json_campos_vazios.json").toString());

		// Entrada tudo ok
		Lecture lecture3 = new Lecture(
				new AcademicInfo("ME", "Teoria dos Jogos e dos Contratos", "01789TP01", "MEA1", 30),
				new TimeSlot("Sex", LocalDate.of(2022, 12, 2), LocalTime.of(13, 0, 0), LocalTime.of(14, 30, 0)),
				new Room("AA2.25", 34));
		List<Lecture> lectures3 = new ArrayList<>();
		lectures3.add(lecture3);
		Schedule expected3 = new Schedule(lectures3);
		assertEquals(expected3.toString(),
				Schedule.loadJSON("./src/main/resources/horario_exemplo_json.json").toString());

		// Entrada tudo ok
		Lecture lecture5 = new Lecture(
				new AcademicInfo("ME", "Teoria dos Jogos e dos Contratos", "01789TP01", "MEA1", 30),
				new TimeSlot("Sex", LocalDate.of(2022, 12, 2), LocalTime.of(13, 0, 0), LocalTime.of(14, 30, 0)),
				new Room("AA2.25", 34));
		List<Lecture> lectures5 = new ArrayList<>();
		lectures5.add(lecture5);
		Schedule expected5 = new Schedule(lectures5);
		assertEquals(expected5.toString(),
				Schedule.loadJSON("./src/main/resources/horario_exemplo_json2.JSON").toString());
	}
}
