package alfdozen.es_2023_2sem_terca_teira_leipl_grupoa;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.logging.Level;
import java.io.File;
import java.io.FileWriter;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;


/**
 * @author alfdozen
 * 
 *         The Schedule class is used to represent a schedule of lectures for a
 *         student. It contains a list of Lecture and information about the
 *         student, such as their name and student number. The constructor can
 *         be used to create an empty schedule or a schedule with a list of
 *         lectures, as well as providing student information. The student
 *         number must be a positive integer and will be validated by the class.
 *         The class can also add or remove lectures to/from the schedule. The
 *         class can be sorted by the time slots of the lectures in the
 *         schedule. The toString() method returns a String representation of
 *         the schedule, including the student name and number, as well as the
 *         list of lectures. If the student name or number is not provided, the
 *         string "Unknown" will be used instead. If the schedule is empty, the
 *         string "Schedule is empty" will be returned.
 * 
 */

final class Schedule {
	static final String FOR_NULL = "Unknown";
	static final String NEGATIVE_EXCEPTION = "The studentNumber can't be negative";
	static final String NOT_NUMBER_EXCEPTION = "The provided string doesn't correspond to a number";

	private List<Lecture> lectures;
	@JsonIgnore
	private String studentName;
	@JsonIgnore
	private Integer studentNumber;

	Schedule() {
		this.studentName = null;
		this.studentNumber = null;
		this.lectures = new ArrayList<>();
	}

	Schedule(List<Lecture> lectures) {
		this.studentName = null;
		this.studentNumber = null;
		setLectures(lectures);
	}

	Schedule(List<Lecture> lectures, String studentName, Integer studentNumber) {
		if (studentNumber != null && studentNumber < 0) {
			throw new IllegalArgumentException(NEGATIVE_EXCEPTION);
		}
		setLectures(lectures);
		this.studentName = studentName;
		this.studentNumber = studentNumber;
	}

	Schedule(List<Lecture> lectures, String studentName, String studentNumber) {
		setLectures(lectures);
		this.studentName = studentName;
		if (studentNumber == null) {
			this.studentNumber = null;
			return;
		}
		try {
			this.studentNumber = Integer.parseInt(studentNumber);
		} catch (NumberFormatException e) {
			throw new NumberFormatException(NOT_NUMBER_EXCEPTION);
		}
		if (this.studentNumber < 0) {
			throw new IllegalArgumentException(NEGATIVE_EXCEPTION);
		}
	}

	Schedule(String studentName, Integer studentNumber) {
		this(new ArrayList<>(), studentName, studentNumber);
	}

	Schedule(String studentName, String studentNumber) {
		this(new ArrayList<>(), studentName, studentNumber);
	}

//	List<Lecture> getLectures() {
//		return new ArrayList<>(this.lectures);
//	}

	void setLectures(List<Lecture> lectures) {
		if (lectures == null) {
			this.lectures = new ArrayList<>();
		} else {
			this.lectures = new ArrayList<>(lectures);
			sortLectures();
		}
	}

	void sortLectures() {
		Collections.sort(this.lectures);
	}

//	String getStudentName() {
//		return studentName;
//	}

	void setStudentName(String studentName) {
		this.studentName = studentName;
	}

//	Integer getStudentNumber() {
//		return studentNumber;
//	}

	// TESTES
	public List<Lecture> getLectures() {
		return lectures;
	}

	public String getStudentName() {
		return studentName;
	}

	public Integer getStudentNumber() {
		return studentNumber;
	}

	//

	void setStudentNumber(Integer studentNumber) {
		if (studentNumber != null && studentNumber < 0) {
			throw new IllegalArgumentException(NEGATIVE_EXCEPTION);
		}
		this.studentNumber = studentNumber;
	}

	void addLecture(Lecture lecture) {
		this.lectures.add(lecture);
		sortLectures();
	}

	void removeLecture(Lecture lecture) {
		if (!this.lectures.contains(lecture)) {
			throw new IllegalArgumentException("The schedule doesn't contain this lecture");
		}
		this.lectures.remove(lecture);
	}

	/**
	 * Saves a given schedule to a CSV file with the specified file name. The file
	 * will be created if it does not already exist.
	 * 
	 * @param schedule the schedule to save
	 * @param fileName the name of the file to save to (without extension)
	 * @throws IOException if an I/O error occurs while writing the file
	 */
	public static void saveToCSV(Schedule schedule, String fileName) throws IOException { 
		// Create a new CSV file
		File file = new File(fileName + ".csv");
		if (!file.exists()) {
			file.createNewFile();
		}

		// Write the data to the CSV file
		FileWriter writer = new FileWriter(file);
		writer.write(
				"Curso;Unidade Curricular;Turno;Turma;Inscritos no turno;Dia da semana;Hora início da aula;Hora fim da aula;Data da aula;Sala atribuída à aula;Lotação da sala\n");

		// Loop through each lecture in the schedule and write its data to the CSV file
		for (Lecture lecture : schedule.getLectures()) {
			writer.write(String.format("%s;%s;%s;%s;%d;%s;%s;%s;%s;%s;%s\n",
					// Use lambda to check for null values in lecture data and insert empty strings
					// if necessary
					lecture.getAcademicInfo().getDegree() != null ? lecture.getAcademicInfo().getDegree() : "",
					lecture.getAcademicInfo().getCourse() != null ? lecture.getAcademicInfo().getCourse() : "",
					lecture.getAcademicInfo().getShift() != null ? lecture.getAcademicInfo().getShift() : "",
					lecture.getAcademicInfo().getClassGroup() != null ? lecture.getAcademicInfo().getClassGroup() : "",
					lecture.getAcademicInfo().getStudentsEnrolled() != null
							? lecture.getAcademicInfo().getStudentsEnrolled()
							: "",
					lecture.getTimeSlot().getWeekDay() != null ? lecture.getTimeSlot().getWeekDay() : "",
					lecture.getTimeSlot().getTimeBegin() != null ? lecture.getTimeSlot().getTimeBegin() : "",
					lecture.getTimeSlot().getTimeEnd() != null ? lecture.getTimeSlot().getTimeEnd() : "",
					lecture.getTimeSlot().getDate() != null ? lecture.getTimeSlot().getDate() : "",
					lecture.getRoom().getName() != null ? lecture.getRoom().getName() : "",
					lecture.getRoom().getCapacity() != null ? lecture.getRoom().getCapacity() : ""));
		}
		writer.close();

	}

	public static void saveToJSON(Schedule schedule, String fileName) throws IOException {
		// Create an ObjectMapper instance
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule()); // register the JavaTimeModule

//	 
//		
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false); // if needed
		mapper.configure(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS, true);
		mapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		mapper.getSerializerProvider().setNullValueSerializer(new ToStringSerializer());

		List<Lecture> lectures = schedule.getLectures();
		String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(lectures);

		// Write the JSON to a file
		FileWriter fileWriter = new FileWriter(fileName);
		fileWriter.write(json);
		fileWriter.close();


		System.out.println("Schedule saved to JSON file successfully.");

	}



	public static void main(String[] args) throws IOException {

		Lecture lecture1 = new Lecture(
				new AcademicInfo("PIUDHIST", "Seminário de Projecto I (Piudhist)", "SP-I_(Piudhist)S01", "DHMCMG1", 0),
				new TimeSlot("Seg", LocalDate.of(2022, 10, 31), LocalTime.of(18, 0, 0), LocalTime.of(20, 0, 0)),
				new Room("AA2.23", 50));
		Lecture lecture2 = new Lecture(
				new AcademicInfo("","Teoria dos Jogos e dos Contratos", "01789TP01", "MEA1", 30),
				new TimeSlot("Sex", null, LocalTime.of(13, 0, 0), LocalTime.of(14, 30, 0)), new Room("AA2.25", 34));
		Lecture lecture3 = new Lecture(
				new AcademicInfo("LETI , LEI , LEI-PL , LIGE , LIGE-PL", "Fundamentos de Arquitectura de Computadores",
						"L0705TP23", "ET-A9 , ET-A8 , ET-A7 , ET-A12 , ET-A11 , ET-A10", 44),
				new TimeSlot("Sex", LocalDate.of(2022, 9, 16), LocalTime.of(13, 0, 0), LocalTime.of(14, 30, 0)),
				new Room(null, (Integer) null));
		List<Lecture> lectures = new ArrayList<>();
		lectures.add(lecture1);
		lectures.add(lecture2);
		lectures.add(lecture3);
		Schedule expected = new Schedule(lectures);
		saveToJSON(expected, "json1");
		saveToCSV(expected, "CSV");

	}

	@Override
	public String toString() {
		String str = "";
		if (studentName == null) {
			str += "Unknown Student Name\n";
		} else {
			str += "Student Name: " + studentName + "\n";
		}
		if (studentNumber == null) {
			str += "Unknown Student Number\n";
		} else {
			str += "Student Number: " + studentNumber + "\n";
		}
		if (lectures.isEmpty()) {
			str += "Schedule is empty";
		} else {
			str += "Schedule:\n";
			for (Lecture lecture : lectures) {
				str += lecture + "\n";
			}
		}
		return str;
	}

}
