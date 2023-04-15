package alfdozen.es_2023_2sem_terca_teira_leipl_grupoa;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.File;
import java.io.FileWriter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

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
//	@JsonIgnore
	private String studentName;
//	@JsonIgnore
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

	List<Lecture> getLectures() {
		return new ArrayList<>(this.lectures);
	}

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

	String getStudentName() {
		return studentName;
	}

	void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	Integer getStudentNumber() {
		return studentNumber;
	}



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

	
	public static void saveToJSON(Schedule schedule, String fileName) throws IOException {
		// Create an ObjectMapper instance
		
		List<Lecture> lectures = schedule.getLectures();
		ObjectMapper mapper = new ObjectMapper();

		ArrayNode lecturesArray = mapper.createArrayNode();
	
		for (Lecture lecture : lectures) {
			  ObjectNode lectureNode = mapper.createObjectNode();
			  lectureNode.put("Curso", lecture.getAcademicInfo().getDegree());
			  lectureNode.put("Unidade Curricular", lecture.getAcademicInfo().getCourse());
			  lectureNode.put("Turno", lecture.getAcademicInfo().getShift());
			  lectureNode.put("Turma", lecture.getAcademicInfo().getClassGroup());
			  lectureNode.put("Inscritos no turno", lecture.getAcademicInfo().getStudentsEnrolled());
			  lectureNode.put("Dia da semana", lecture.getTimeSlot().getWeekDay());
			  lectureNode.put("Data da aula", lecture.getTimeSlot().getDateString());
			  lectureNode.put("Hora início da aula", lecture.getTimeSlot().getTimeBeginString());
			  lectureNode.put("Hora fim da aula", lecture.getTimeSlot().getTimeEndString());
			  lectureNode.put("Sala atribuída à aula", lecture.getRoom().getName());
			  lectureNode.put("Lotação da sala", lecture.getRoom().getCapacity());

			  lecturesArray.add(lectureNode);
			}

		String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(lecturesArray);
	
		// Write the JSON to a file
		try (FileWriter fileWriter = new FileWriter(fileName)) {
		    fileWriter.write(json);
		    System.out.println("Schedule saved to JSON file successfully.");
		} catch (IOException e) {
		    System.out.println("Error writing JSON file: " + e.getMessage());
		}

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
