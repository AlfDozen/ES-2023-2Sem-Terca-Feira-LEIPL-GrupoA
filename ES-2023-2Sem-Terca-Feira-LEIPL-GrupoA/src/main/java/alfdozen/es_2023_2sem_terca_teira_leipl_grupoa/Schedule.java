package alfdozen.es_2023_2sem_terca_teira_leipl_grupoa;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author alfdozen
 * 
 *      The Schedule class is used to represent a schedule of lectures for a student. 
 *		It contains a list of Lecture and information about the student, such as their name and student number. 
 *		The constructor can be used to create an empty schedule or a schedule with a list of lectures, as well as providing student information.  
 *		The student number must be a positive integer and will be validated by the class. 
 *		The class can also add or remove lectures to/from the schedule.
 *		The class can be sorted by the time slots of the lectures in the schedule. 
 *		The toString() method returns a String representation of the schedule, including the student name and number, as well as the list of lectures. 
 *		If the student name or number is not provided, the string "Unknown" will be used instead. 
 *		If the schedule is empty, the string "Schedule is empty" will be returned.
 * 
 */
final class Schedule {
	static final String FOR_NULL = "Unknown";
	static final String NEGATIVE_EXCEPTION = "The studentNumber can't be negative";
	static final String NOT_NUMBER_EXCEPTION = "The provided string doesn't correspond to a number";
	static final String READ_EXCEPTION = "Error: File read";
	static final String WRONG_FILE_FORMAT_EXCEPTION = "The file format should be ";
	static final String FILE_NULL_EXCEPTION = "The file cannot be null!";
	static final String ROW_EXCEPTION = "The row has more columns that the expected";
	static final String DELIMITER = ";";
	static final String FILE_FORMAT_CSV = ".csv";
	static final String HEADER = "Curso;Unidade Curricular;Turno;Turma;Inscritos no turno;Dia da semana;Hora início da aula;Hora fim da aula;Data da aula;Sala atribuída à aula;Lotação da sala";
	static final String EMPTY_ROW = ";;;;;;;;;;";
	static final Integer NUMBER_COLUMNS = 11;
	private List<Lecture> lectures;
	private String studentName;
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
		return lectures;
	}

	void setLectures(List<Lecture> lectures) {
		if (lectures == null) {
			this.lectures = new ArrayList<>();
		} else {
			this.lectures = lectures;
			Collections.sort(this.lectures);
		}
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
		Collections.sort(this.lectures, (l1, l2) -> l1.getTimeSlot().compareTo(l2.getTimeSlot()));
	}

	void removeLecture(Lecture lecture) {
		if (!lectures.isEmpty()) {
			this.lectures.remove(lecture);
		}
	}

	/**
	 * This method allows you to load a schedule via a csv file 
	 * @param path (String) - file path of the csv file
	 * @return returns a schedule object with all existing lectures in the file given as input
	 * @throws IllegalArgumentException is thrown if the file path is null
	 * @throws IllegalArgumentException is thrown if the file is in the wrong format
	 * @throws IllegalArgumentException is thrown if an error is given when the file is read
	 */
	static Schedule loadCSV(String path) {
		if(path == null) {
			throw new IllegalArgumentException(FILE_NULL_EXCEPTION);
		}
		if(!path.endsWith(FILE_FORMAT_CSV)) {
			throw new IllegalArgumentException(WRONG_FILE_FORMAT_EXCEPTION + FILE_FORMAT_CSV);
		}
		Schedule schedule = new Schedule();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
			String line = "";
			while((line = br.readLine()) != null) {
				if(!line.isBlank() && !line.equals(HEADER) && !line.equals(EMPTY_ROW)) {
					Lecture lecture = buildLecture(line);
					schedule.addLecture(lecture);
				}
			}
			br.close();
		} catch(IOException e) {
			throw new IllegalArgumentException(READ_EXCEPTION);
		}
		return schedule;
	}
	
	/**
	 * This method build a lecture object from a csv file entry
	 * @param line (String) - csv file entry
	 * @return returns a lecture object from the csv file entry given as input
	 * @throws IllegalStateException is thrown if the number of columns is greater than expected
	 */
	private static Lecture buildLecture(String line) {
		String[] tempArr = line.split(DELIMITER);
		if(tempArr.length > NUMBER_COLUMNS) {
			throw new IllegalStateException(ROW_EXCEPTION);
		}
		String[] finalArr = new String[NUMBER_COLUMNS];
		if(tempArr.length == NUMBER_COLUMNS) {
			finalArr = buildLine(tempArr);
		}
		if(tempArr.length < NUMBER_COLUMNS) {
			finalArr = buildLine(tempArr);
			for(int i = tempArr.length; i < NUMBER_COLUMNS; i++) {
				finalArr[i] = null;
			}
		}
		AcademicInfo academicInfo = new AcademicInfo(finalArr[0], finalArr[1], finalArr[2], finalArr[3], finalArr[4]);
		TimeSlot timeSlot = new TimeSlot(finalArr[5], finalArr[8], finalArr[6], finalArr[7]);
		Room room = new Room(finalArr[9], finalArr[10]);
		return new Lecture(academicInfo, timeSlot, room);
	}
	
	/**
	 * This method allows you to replace empty fields in the csv file entry with null values
	 * @param array (String[]) - vector with the parsed csv file entry
	 * @return returns a String[]
	 */
	private static String[] buildLine(String[] array) {
		String[] finalArr = new String[NUMBER_COLUMNS];
		for(int i = 0; i < array.length; i++) {
			if(array[i].equals("")) {
				finalArr[i] = null;
			} else {
				finalArr[i] = array[i];
			}
		}
		return finalArr;
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
	            str += lecture.toString() + "\n";
	        }
	    }
	    return str;
	}

}
