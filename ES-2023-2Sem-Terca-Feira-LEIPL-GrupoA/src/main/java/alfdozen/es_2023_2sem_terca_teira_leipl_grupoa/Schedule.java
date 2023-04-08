package alfdozen.es_2023_2sem_terca_teira_leipl_grupoa;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
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
	static final String ENCODING = "ISO-8859-1";
	static final String DELIMITER = ",";
	static final String FILE_FORMAT_CSV = ".csv";
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

	static Schedule loadCSV(String path, String encoding) {
		if(!path.endsWith(FILE_FORMAT_CSV)) {
			throw new IllegalArgumentException(WRONG_FILE_FORMAT_EXCEPTION + FILE_FORMAT_CSV);
		}
		if(encoding == null) {
			encoding = ENCODING;
		}
		String delimiter = DELIMITER;
		Schedule schedule = new Schedule();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path), encoding));
			String line = "";
			boolean flag = true;
			while((line = br.readLine()) != null) {
				if(flag) {
					flag = false;
				} else {
					Lecture lecture = buildLecture(line, encoding, delimiter);
					schedule.addLecture(lecture);
				}
			}
			br.close();
		} catch(IOException e) {
			throw new IllegalArgumentException(READ_EXCEPTION);
		}
		return schedule;
	}
	
	static Schedule loadCSV(String path) {
		return loadCSV(path, ENCODING);
	}
	
	private static Lecture buildLecture(String line, String encoding, String delimiter) throws UnsupportedEncodingException {
		String[] tempArr;
		String lineEncoding = new String(line.getBytes(ENCODING), encoding);
		String lineParsed = parseString(lineEncoding);
		tempArr = lineParsed.split(delimiter);
		AcademicInfo academicInfo = new AcademicInfo(tempArr[0], tempArr[1], tempArr[2], tempArr[3], tempArr[4]);
		TimeSlot timeSlot;
		Room room;
		if(tempArr.length == 8) { // Falta a sala, a capacidade e a data
			timeSlot = new TimeSlot(tempArr[5], null, tempArr[6], tempArr[7]);
			room = new Room(null, (Integer)null);
		} else if(tempArr.length == 9) { // Falta a sala e a capacidade
			timeSlot = new TimeSlot(tempArr[5], tempArr[8], tempArr[6], tempArr[7]);
			room = new Room(null, (Integer)null);
		} else {
			if(tempArr[8].equals("")) { // Falta a data
				timeSlot = new TimeSlot(tempArr[5], null, tempArr[6], tempArr[7]);
				room = new Room(tempArr[9], tempArr[10]);
			} else { // Completo
				timeSlot = new TimeSlot(tempArr[5], tempArr[8], tempArr[6], tempArr[7]);
				room = new Room(tempArr[9], tempArr[10]);
			}
		}
		return new Lecture(academicInfo, timeSlot, room);
	}
	
	private static String parseString(String line) {
		String lineReplace = line.replace(", ", " | ");
		String lineRemove = lineReplace.replace("\"", "\'");
		if(lineRemove.contains("\'")) {
			String lineFinal = lineRemove;
			while(lineFinal.contains("\'")) {
				Integer nInicio = lineFinal.indexOf("\'");
				String temp = lineFinal.substring(nInicio+1);
				Integer nFim = temp.indexOf("\'");
				String subString = lineFinal.substring(nInicio+1, nInicio+nFim+1);
				String subStringReplace = subString.replace(",", ".");
				lineFinal = lineFinal.replace("\'" + subString + "\'", subStringReplace);
			}
			return lineFinal;
		}
		return lineRemove;
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
