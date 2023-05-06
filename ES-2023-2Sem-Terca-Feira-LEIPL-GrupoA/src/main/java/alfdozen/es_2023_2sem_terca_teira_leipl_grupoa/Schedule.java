package alfdozen.es_2023_2sem_terca_teira_leipl_grupoa;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDateTime;
import java.util.Set;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

/**
 * The Schedule class is used to represent a schedule of lectures for a student.
 * It contains a list of Lectures and information about the student, such as
 * their name and student number. The constructor can be used to create an empty
 * schedule or a schedule with a list of lectures, as well as providing student
 * information. The student number must be a positive integer and will be
 * validated by the class. The class can also add or remove lectures to/from the
 * schedule. The class can be sorted by the time slots of the lectures in the
 * schedule. The toString() method returns a String representation of the
 * schedule, including the student name and number, as well as the list of
 * lectures. If the student name or number is not provided, the string "Unknown"
 * will be used instead. If the schedule is empty, the string "Schedule is
 * empty" will be returned.
 * 
 * @author alfdozen
 * @version 1.0.0
 */
final class Schedule {
	static final String FOR_NULL = "Unknown";
	static final String NEGATIVE_EXCEPTION = "The studentNumber can't be negative";
	static final String NOT_NUMBER_EXCEPTION = "The provided string doesn't correspond to a number";
	static final String SAVE_FILE_EXCEPTION = "The file could not be saved";
	static final String READ_EXCEPTION = "Error: File read";
	static final String READ_WRITE_EXCEPTION = "Error: File read or write";
	static final String WRONG_FILE_FORMAT_EXCEPTION = "The file format should be ";
	static final String FILE_NULL_EXCEPTION = "The file cannot be null";
	static final String ROW_EXCEPTION = "The row has more columns than the expected";
	static final String FILE_EXISTS_EXCEPTION = "The file already exists";
	static final String FILE_NOT_EXISTS_EXCEPTION = "The provided file does not exist";
	static final String DELIMITER_NULL_EXCEPTION = "The delimiter cannot be null";
	static final String FOLDER_NOT_EXISTS_EXCEPTION = "The provided parent folder does not exist";
	static final String FILE_MISSING_DATA = "At least 1 record of the data provided does not have the required"
			+ " values filled";
	static final String URI_NULL_EXCEPTION = "The URI cannot be null";
	static final String URI_NOT_WEBCAL_EXCEPTION = "The URI is not a webcal scheme";
	static final String WEBCAL_NOT_VCALENDAR_EXCEPTION = "The webcal URI does not lead to an ics file."
			+ " If the URI is correct, delete the current personal web calendar and create a new one.";
	static final String URI_NOT_VALID_EXCEPTION = "The URI is not valid.";
	static final String CONNECTING_TO_INTERNET_EXCEPTION = "Could not establish a HTTP connection and read from ics file.";
	static final String DELIMITER = ";";
	static final String FILE_FORMAT_CSV = ".csv";
	static final String FILE_FORMAT_JSON = ".json";
	
	private static final String EMPTY_ROW = ";;;;;;;;;;";
	private static final String PATH_TMP = "src/main/resources/tmpfile.csv";
	private static final String HEADER = "Curso;Unidade Curricular;Turno;Turma;Inscritos no turno;Dia da semana;"
			+ "Hora início da aula;Hora fim da aula;Data da aula;Sala atribuída à aula;Lotação da sala";
	private static final String WEBCAL_DATETIME_FORMAT = "yyyyMMdd\'T\'HHmmss\'Z\'";
	private static final Integer NUMBER_COLUMNS = 11;
	private static final Integer INDEX_DEGREE = 0;
	private static final Integer INDEX_COURSE = 1;
	private static final Integer INDEX_SHIFT = 2;
	private static final Integer INDEX_CLASSGROUP = 3;
	private static final Integer INDEX_STUDENTSENROLLED = 4;
	private static final Integer INDEX_WEEKDAY = 5;
	private static final Integer INDEX_TIMEBEGIN = 6;
	private static final Integer INDEX_TIMEEND = 7;
	private static final Integer INDEX_DATE = 8;
	private static final Integer INDEX_ROOM = 9;
	private static final Integer INDEX_CAPACITY = 10;
	private static final Pattern WEBCAL_PATTERN = Pattern.compile("webcal", Pattern.LITERAL);
	private static final Pattern BEGIN_VCALENDAR_PATTERN = Pattern.compile("BEGIN:VCALENDAR", Pattern.LITERAL);
	private static final Pattern USER_PATTERN = Pattern.compile("X-WR-CALNAME:", Pattern.LITERAL);
	private static final Pattern BEGIN_EVENT_PATTERN = Pattern.compile("BEGIN:VEVENT", Pattern.LITERAL);
	private static final Pattern DATETIME_START_PATTERN = Pattern.compile("DTSTART:", Pattern.LITERAL);
	private static final Pattern DATETIME_END_PATTERN = Pattern.compile("DTEND:", Pattern.LITERAL);
	private static final Pattern DESCRIPTION_PATTERN = Pattern.compile("DESCRIPTION:", Pattern.LITERAL);
	private static final Pattern DESCRIPTION_DELIMITER_PATTERN = Pattern.compile("\\n", Pattern.LITERAL);
	private static final Pattern COURSE_PATTERN = Pattern.compile("Unidade de execução:", Pattern.LITERAL);
	private static final Pattern SHIFT_PATTERN = Pattern.compile("Turno:", Pattern.LITERAL);
	private static final Pattern LOCATION_PATTERN = Pattern.compile("LOCATION:", Pattern.LITERAL);
	private static final Pattern LOCATION_DELIMITER_PATTERN = Pattern.compile("\\,", Pattern.LITERAL);

	private List<Lecture> lectures;
	private String studentName;
	private Integer studentNumber;

	/**
	 * Default constructor creates an empty Schedule.
	 */
	Schedule() {
		this.studentName = null;
		this.studentNumber = null;
		this.lectures = new ArrayList<>();
	}

	/**
	 * Constructor creates a Schedule with a list of lectures.
	 * 
	 * @param lectures A list of Lecture objects.
	 */
	Schedule(List<Lecture> lectures) {
		this.studentName = null;
		this.studentNumber = null;
		setLectures(lectures);
	}

	/**
	 * Constructor creates a Schedule with a list of lectures and student
	 * information.
	 * 
	 * @param lectures      A list of Lecture objects.
	 * @param studentName   The name of the student.
	 * @param studentNumber The student number as an Integer.
	 * @throws IllegalArgumentException if the studentNumber is negative.
	 */
	Schedule(List<Lecture> lectures, String studentName, Integer studentNumber) {
		if (studentNumber != null && studentNumber < 0) {
			throw new IllegalArgumentException(NEGATIVE_EXCEPTION);
		}
		setLectures(lectures);
		this.studentName = studentName;
		this.studentNumber = studentNumber;
	}

	/**
	 * Constructor creates a Schedule with a list of lectures and student
	 * information.
	 * 
	 * @param lectures      A list of Lecture objects.
	 * @param studentName   The name of the student.
	 * @param studentNumber The student number as a String.
	 * @throws NumberFormatException    if the provided studentNumber is not a valid
	 *                                  number.
	 * @throws IllegalArgumentException if the studentNumber is negative.
	 */
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

	/**
	 * Constructor creates a Schedule with student information.
	 * 
	 * @param studentName   The name of the student.
	 * @param studentNumber The student number as an Integer.
	 */
	Schedule(String studentName, Integer studentNumber) {
		this(new ArrayList<>(), studentName, studentNumber);
	}

	/**
	 * Constructor creates a Schedule with student information.
	 * 
	 * @param studentName   The name of the student.
	 * @param studentNumber The student number as a String.
	 */
	Schedule(String studentName, String studentNumber) {
		this(new ArrayList<>(), studentName, studentNumber);
	}

	/**
	 * Returns a copy of the list of lectures in the schedule.
	 * 
	 * @return A list of Lecture objects.
	 */
	List<Lecture> getLectures() {
		return new ArrayList<>(this.lectures);
	}

	/**
	 * Sets the list of lectures in the schedule and sorts it.
	 * 
	 * @param lectures A list of Lecture objects.
	 */
	void setLectures(List<Lecture> lectures) {
		if (lectures == null) {
			this.lectures = new ArrayList<>();
		} else {
			this.lectures = new ArrayList<>(lectures);
			sortLectures();
		}
	}

	/**
	 * Sorts the list of lectures in the schedule by their time slots.
	 */
	void sortLectures() {
		Collections.sort(this.lectures);
	}

	/**
	 * Returns the student name associated with the schedule.
	 * 
	 * @return A string representing the student's name.
	 */
	String getStudentName() {
		return studentName;
	}

	/**
	 * Sets the student name associated with the schedule.
	 * 
	 * @param studentName A string representing the student's name.
	 */
	void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	/**
	 * Returns the student number associated with the schedule.
	 * 
	 * @return An integer representing the student's number.
	 */
	Integer getStudentNumber() {
		return studentNumber;
	}

	/**
	 * Sets the student number associated with the schedule.
	 * 
	 * @param studentNumber An integer representing the student's number.
	 * @throws IllegalArgumentException if the studentNumber is negative.
	 */
	void setStudentNumber(Integer studentNumber) {
		if (studentNumber != null && studentNumber < 0) {
			throw new IllegalArgumentException(NEGATIVE_EXCEPTION);
		}
		this.studentNumber = studentNumber;
	}

	/**
	 * Adds a lecture to the schedule and sorts the schedule.
	 * 
	 * @param lecture A Lecture object to be added to the schedule.
	 */
	void addLecture(Lecture lecture) {
		this.lectures.add(lecture);
		sortLectures();
	}

	/**
	 * Removes a lecture from the schedule.
	 * 
	 * @param lecture A Lecture object to be removed from the schedule.
	 * @throws IllegalArgumentException if the schedule doesn't contain the
	 *                                  specified lecture.
	 */
	void removeLecture(Lecture lecture) {
		if (!this.lectures.contains(lecture)) {
			throw new IllegalArgumentException("The schedule doesn't contain this lecture");
		}
		this.lectures.remove(lecture);
	}

	/**
	 * This method allows you to load a schedule via a csv file
	 *
	 * @param filePath the path of the csv file
	 * @return a schedule object with all existing lectures in the file given as
	 *         input
	 * @throws IllegalArgumentException is thrown if the file path is null
	 * @throws IllegalArgumentException is thrown if the file is in the wrong format
	 * @throws IOException              is thrown if an error is given when the file
	 *                                  is read
	 */
	static Schedule loadCSV(String filePath) throws IOException {
		if (filePath == null) {
			throw new IllegalArgumentException(FILE_NULL_EXCEPTION);
		}
		if (!filePath.endsWith(FILE_FORMAT_CSV) && !filePath.endsWith(FILE_FORMAT_CSV.toUpperCase())) {
			throw new IllegalArgumentException(WRONG_FILE_FORMAT_EXCEPTION + FILE_FORMAT_CSV);
		}
		Schedule schedule = new Schedule();

		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8))) {
			String line = "";
			while (br.readLine().isBlank()) {
				continue;
			}
			while ((line = br.readLine()) != null) {
				if (!line.isBlank() && !line.equals(EMPTY_ROW)) {
					Lecture lecture = buildLecture(line);
					schedule.addLecture(lecture);
				}
			}
		} catch (IOException e) {
			throw new IOException(READ_EXCEPTION);
		}
		return schedule;
	}

	/**
	 * This method build a lecture object from a csv file entry
	 *
	 * @param line a csv file entry
	 * @return a lecture object from the csv file entry given as input
	 * @throws IllegalStateException is thrown if the number of columns is greater
	 *                               than expected
	 */
	private static Lecture buildLecture(String line) {
		String[] tempArr = line.split(DELIMITER);
		if (tempArr.length > NUMBER_COLUMNS) {
			throw new IllegalStateException(ROW_EXCEPTION);
		}
		String[] finalArr = new String[NUMBER_COLUMNS];
		if (tempArr.length == NUMBER_COLUMNS) {
			finalArr = buildLine(tempArr);
		}
		if (tempArr.length < NUMBER_COLUMNS) {
			finalArr = buildLine(tempArr);
			for (int i = tempArr.length; i < NUMBER_COLUMNS; i++) {
				finalArr[i] = null;
			}
		}
		AcademicInfo academicInfo = new AcademicInfo(finalArr[INDEX_DEGREE], finalArr[INDEX_COURSE],
				finalArr[INDEX_SHIFT], finalArr[INDEX_CLASSGROUP], finalArr[INDEX_STUDENTSENROLLED]);
		TimeSlot timeSlot = new TimeSlot(finalArr[INDEX_WEEKDAY], finalArr[INDEX_DATE], finalArr[INDEX_TIMEBEGIN],
				finalArr[INDEX_TIMEEND]);
		Room room = new Room(finalArr[INDEX_ROOM], finalArr[INDEX_CAPACITY]);
		return new Lecture(academicInfo, timeSlot, room);
	}

	/**
	 * This method allows you to replace empty fields in the csv file entry with
	 * null values
	 *
	 * @param array vector with the parsed csv file entry
	 * @return a String[]
	 */
	private static String[] buildLine(String[] array) {
		String[] finalArr = new String[NUMBER_COLUMNS];
		for (int i = 0; i < array.length; i++) {
			if ("".equals(array[i])) {
				finalArr[i] = null;
			} else {
				finalArr[i] = array[i];
			}
		}
		return finalArr;
	}

	/**
	 * Method that saves a Schedule object to a CSV file.
	 * 
	 * @param schedule A schedule to be saved
	 * @param fileName A name for the CSV file
	 * @throws IOException if an I/O error occurs while writing to the file
	 */
	public static void saveToCSV(Schedule schedule, String fileName) throws IOException {
		File file = new File(fileName);
		try (FileWriter writer = new FileWriter(file, StandardCharsets.UTF_8)) {
			writer.write(HEADER + "\n");
			for (Lecture lecture : schedule.getLectures()) {
				String[] attrArray = buildLineToSaveCSV(lecture);
				writer.write(String.format("%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s%n", attrArray[INDEX_DEGREE],
						attrArray[INDEX_COURSE], attrArray[INDEX_SHIFT], attrArray[INDEX_CLASSGROUP],
						attrArray[INDEX_STUDENTSENROLLED], attrArray[INDEX_WEEKDAY], attrArray[INDEX_TIMEBEGIN],
						attrArray[INDEX_TIMEEND], attrArray[INDEX_DATE], attrArray[INDEX_ROOM],
						attrArray[INDEX_CAPACITY]));
			}
		} catch (IOException e) {
			throw new IOException(SAVE_FILE_EXCEPTION);
		}
	}

	/**
	 * Method to build the entry to save in the file csv
	 * 
	 * @param lecture A lecture to build the entry
	 * @return a String[]
	 */
	private static String[] buildLineToSaveCSV(Lecture lecture) {
		String[] attrArray = new String[NUMBER_COLUMNS];
		attrArray[INDEX_DEGREE] = lecture.getAcademicInfo().getDegree();
		attrArray[INDEX_COURSE] = lecture.getAcademicInfo().getCourse();
		attrArray[INDEX_SHIFT] = lecture.getAcademicInfo().getShift();
		attrArray[INDEX_CLASSGROUP] = lecture.getAcademicInfo().getClassGroup();
		attrArray[INDEX_WEEKDAY] = lecture.getTimeSlot().getWeekDay();
		attrArray[INDEX_TIMEBEGIN] = lecture.getTimeSlot().getTimeBeginString();
		attrArray[INDEX_TIMEEND] = lecture.getTimeSlot().getTimeEndString();
		attrArray[INDEX_DATE] = lecture.getTimeSlot().getDateString();
		attrArray[INDEX_ROOM] = lecture.getRoom().getName();
		for (int i = 0; i < attrArray.length; i++) {
			if (attrArray[i] == null || attrArray[i].equals(FOR_NULL)) {
				attrArray[i] = "";
			}
		}
		if (lecture.getAcademicInfo().getStudentsEnrolled() != null) {
			attrArray[INDEX_STUDENTSENROLLED] = lecture.getAcademicInfo().getStudentsEnrolled().toString();
		}
		if (lecture.getRoom().getCapacity() != null) {
			attrArray[INDEX_CAPACITY] = lecture.getRoom().getCapacity().toString();
		}
		return attrArray;
	}

	/**
	 * This method allows to load a schedule via a json file
	 * 
	 * @param path the file path of the json file
	 * @return a schedule object with all existing lectures in the file given as
	 *         input
	 * @throws IllegalArgumentException is thrown if the file path is null
	 * @throws IllegalArgumentException is thrown if the file is in the wrong format
	 * @throws IOException              is thrown if an error is given when the file
	 *                                  is read
	 */
	@SuppressWarnings("unchecked")
	static Schedule loadJSON(String path) throws IOException {
		if (path == null) {
			throw new IllegalArgumentException(FILE_NULL_EXCEPTION);
		}
		if (!path.endsWith(FILE_FORMAT_JSON) && !path.endsWith(FILE_FORMAT_JSON.toUpperCase())) {
			throw new IllegalArgumentException(WRONG_FILE_FORMAT_EXCEPTION + FILE_FORMAT_JSON);
		}
		Schedule schedule = new Schedule();
		String[] headerArr = HEADER.split(DELIMITER);
		try {
			Reader reader = new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8);
			ObjectMapper jsonMapper = new ObjectMapper();
			List<Object> data = jsonMapper.readValue(reader, List.class);
			for (int i = 0; i < data.size(); i++) {
				Map<String, Object> entry = (Map<String, Object>) data.get(i);
				Lecture lecture = buildLecture(entry, headerArr);
				schedule.addLecture(lecture);
			}
		} catch (IOException e) {
			throw new IOException(READ_EXCEPTION);
		}
		return schedule;
	}

	/**
	 * This method build a lecture object from a json file entry
	 * 
	 * @param entry     json entry in the form of a Map
	 * @param headerArr vector with the parsed header
	 * @return a lecture object from the json file entry given as input
	 */
	private static Lecture buildLecture(Map<String, Object> entry, String[] headerArr) {
		String[] finalArr = new String[NUMBER_COLUMNS];
		for (int j = 0; j < NUMBER_COLUMNS; j++) {
			String aux = (String) entry.get(headerArr[j]);
			if ("".equals(aux)) {
				finalArr[j] = null;
			} else {
				finalArr[j] = aux;
			}
		}
		AcademicInfo academicInfo = new AcademicInfo(finalArr[INDEX_DEGREE], finalArr[INDEX_COURSE],
				finalArr[INDEX_SHIFT], finalArr[INDEX_CLASSGROUP], finalArr[INDEX_STUDENTSENROLLED]);
		TimeSlot timeSlot = new TimeSlot(finalArr[INDEX_WEEKDAY], finalArr[INDEX_DATE], finalArr[INDEX_TIMEBEGIN],
				finalArr[INDEX_TIMEEND]);
		Room room = new Room(finalArr[INDEX_ROOM], finalArr[INDEX_CAPACITY]);
		return new Lecture(academicInfo, timeSlot, room);
	}

	/**
	 * This method saves a Schedule object to a JSON file.
	 * 
	 * @param schedule A Schedule object to be saved.
	 * @param fileName A name for the file to save the Schedule object.
	 * @throws IOException if an I/O error occurs while writing the JSON file.
	 */
	public static void saveToJSON(Schedule schedule, String fileName) throws IOException {
		List<Lecture> lectures = schedule.getLectures();
		ObjectMapper mapper = new ObjectMapper();
		ArrayNode lecturesArray = mapper.createArrayNode();
		for (Lecture lecture : lectures) {
			ObjectNode lectureNode = mapper.createObjectNode();
			String[] attrArray = new String[NUMBER_COLUMNS];
			attrArray[INDEX_DEGREE] = lecture.getAcademicInfo().getDegree();
			attrArray[INDEX_COURSE] = lecture.getAcademicInfo().getCourse();
			attrArray[INDEX_SHIFT] = lecture.getAcademicInfo().getShift();
			attrArray[INDEX_CLASSGROUP] = lecture.getAcademicInfo().getClassGroup();
			attrArray[INDEX_WEEKDAY] = lecture.getTimeSlot().getWeekDay();
			attrArray[INDEX_TIMEBEGIN] = lecture.getTimeSlot().getTimeBeginString();
			attrArray[INDEX_TIMEEND] = lecture.getTimeSlot().getTimeEndString();
			attrArray[INDEX_DATE] = lecture.getTimeSlot().getDateString();
			attrArray[INDEX_ROOM] = lecture.getRoom().getName();
			for (int i = 0; i < attrArray.length; i++) {
				if (attrArray[i] == null || attrArray[i].equals(FOR_NULL)) {
					attrArray[i] = "";
				}
			}
			if (lecture.getAcademicInfo().getStudentsEnrolled() != null) {
				attrArray[INDEX_STUDENTSENROLLED] = lecture.getAcademicInfo().getStudentsEnrolled().toString();
			}
			if (lecture.getRoom().getCapacity() != null) {
				attrArray[INDEX_CAPACITY] = lecture.getRoom().getCapacity().toString();
			}
			lectureNode.put("Curso", attrArray[INDEX_DEGREE]);
			lectureNode.put("Unidade Curricular", attrArray[INDEX_COURSE]);
			lectureNode.put("Turno", attrArray[INDEX_SHIFT]);
			lectureNode.put("Turma", attrArray[INDEX_CLASSGROUP]);
			lectureNode.put("Inscritos no turno", attrArray[INDEX_STUDENTSENROLLED]);
			lectureNode.put("Dia da semana", attrArray[INDEX_WEEKDAY]);
			lectureNode.put("Hora início da aula", attrArray[INDEX_TIMEBEGIN]);
			lectureNode.put("Hora fim da aula", attrArray[INDEX_TIMEEND]);
			lectureNode.put("Data da aula", attrArray[INDEX_DATE]);
			lectureNode.put("Sala atribuída à aula", attrArray[INDEX_ROOM]);
			lectureNode.put("Lotação da sala", attrArray[INDEX_CAPACITY]);
			lecturesArray.add(lectureNode);
		}
		try (FileWriter fileWriter = new FileWriter(fileName, StandardCharsets.UTF_8)) {
			String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(lecturesArray);
			fileWriter.write(json);
		} catch (IOException e) {
			throw new IOException(SAVE_FILE_EXCEPTION);
		}
	}

	/**
	 * Converts a CSV file to a JSON file.
	 * 
	 * @param csvSourcePath       the path of the CSV file to convert.
	 * @param jsonDestinationPath the path of the JSON file to create.
	 * @param delimiter           the delimiter character used in the CSV file.
	 * @throws IOException              if there is an I/O error reading or writing
	 *                                  the files.
	 * @throws IllegalArgumentException if any of the input arguments are null or
	 *                                  invalid.
	 */
	static void convertCSV2JSON(String csvSourcePath, String jsonDestinationPath, Character delimiter)
			throws IOException {
		validateArguments(csvSourcePath, jsonDestinationPath, delimiter, FILE_FORMAT_CSV, FILE_FORMAT_JSON);
		File csvFile = new File(csvSourcePath);
		if (!csvFile.isFile()) {
			throw new IllegalArgumentException(FILE_NOT_EXISTS_EXCEPTION);
		}
		File jsonFile = new File(jsonDestinationPath);
		if (!jsonFile.getParentFile().isDirectory()) {
			throw new IllegalArgumentException(FOLDER_NOT_EXISTS_EXCEPTION);
		}
		if (jsonFile.isFile()) {
			throw new IllegalArgumentException(FILE_EXISTS_EXCEPTION);
		}
		String destinationTempFilePath = PATH_TMP;

		try (BufferedWriter writer = new BufferedWriter(
				new FileWriter(destinationTempFilePath, StandardCharsets.UTF_8));
				BufferedReader reader = new BufferedReader(new FileReader(csvSourcePath, StandardCharsets.UTF_8))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if (!line.isBlank()) {
					writer.append(line);
					writer.newLine();
				}
			}
		}

		try (InputStream inputStream = new FileInputStream(destinationTempFilePath)) {
			CsvMapper csvMapper = new CsvMapper();
			CsvSchema csvSchema = CsvSchema.builder().setColumnSeparator(delimiter).setUseHeader(true).build();
			List<Object> csvData = csvMapper.readerFor(Map.class).with(csvSchema)
					.readValues(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).readAll();
			Files.deleteIfExists(Paths.get(destinationTempFilePath));
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, csvData);
		} catch (IOException e) {
			throw new IOException(READ_WRITE_EXCEPTION);
		}
	}

	/**
	 * Converts a JSON file to a CSV file.
	 * 
	 * @param jsonSourcePath     the path of the JSON file to convert.
	 * @param csvDestinationPath the path of the CSV file to create.
	 * @param delimiter          the delimiter character used in the CSV file.
	 * @throws IOException              if there is an I/O error reading or writing
	 *                                  the files.
	 * @throws IllegalArgumentException if any of the input arguments are null or
	 *                                  invalid.
	 */
	@SuppressWarnings("unchecked")
	static void convertJSON2CSV(String jsonSourcePath, String csvDestinationPath, Character delimiter)
			throws IOException {
		validateArguments(jsonSourcePath, csvDestinationPath, delimiter, FILE_FORMAT_JSON, FILE_FORMAT_CSV);
		File jsonFile = new File(jsonSourcePath);
		if (!jsonFile.isFile()) {
			throw new IllegalArgumentException(FILE_NOT_EXISTS_EXCEPTION);
		}
		File csvFile = new File(csvDestinationPath);
		if (!csvFile.getParentFile().isDirectory()) {
			throw new IllegalArgumentException(FOLDER_NOT_EXISTS_EXCEPTION);
		}
		if (csvFile.isFile()) {
			throw new IllegalArgumentException(FILE_EXISTS_EXCEPTION);
		}

		try (Reader reader = new InputStreamReader(new FileInputStream(jsonFile), StandardCharsets.UTF_8);
				OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(csvFile),
						StandardCharsets.UTF_8)) {
			ObjectMapper jsonMapper = new ObjectMapper();
			List<Object> data = jsonMapper.readValue(reader, List.class);
			CsvMapper csvMapper = new CsvMapper();
			Map<String, Object> firstRecord = (Map<String, Object>) data.get(0);
			CsvSchema.Builder csvSchemaBuilder = CsvSchema.builder().setUseHeader(true).disableQuoteChar();
			for (String columnName : firstRecord.keySet()) {
				csvSchemaBuilder.addColumn(columnName);
			}
			CsvSchema csvSchema = csvSchemaBuilder.build().withColumnSeparator(delimiter);
			csvMapper.writerFor(List.class).with(csvSchema).writeValue(writer, data);
		} catch (Exception e) {
			throw new IOException(READ_WRITE_EXCEPTION);
		}
	}

	/**
	 * Validates the input arguments for file conversion methods.
	 *
	 * @param sourcePath        the path of the source file to be converted.
	 * @param destinationPath   the path of the destination file to be created.
	 * @param delimiter         the delimiter character used in the CSV file (if
	 *                          applicable).
	 * @param sourceFormat      the expected file format of the source file (e.g.,
	 *                          ".csv", ".json").
	 * @param destinationFormat the expected file format of the destination file
	 *                          (e.g., ".csv", ".json").
	 * @throws IllegalArgumentException if any of the input arguments are null or
	 *                                  invalid.
	 */
	static void validateArguments(String sourcePath, String destinationPath, Character delimiter, String sourceFormat,
			String destinationFormat) {
		if (sourcePath == null || destinationPath == null) {
			throw new IllegalArgumentException(FILE_NULL_EXCEPTION);
		}
		if (delimiter == null) {
			throw new IllegalArgumentException(DELIMITER_NULL_EXCEPTION);
		}
		if (!sourcePath.endsWith(sourceFormat)) {
			throw new IllegalArgumentException(WRONG_FILE_FORMAT_EXCEPTION + sourceFormat);
		}
		if (!destinationPath.endsWith(destinationFormat)) {
			throw new IllegalArgumentException(WRONG_FILE_FORMAT_EXCEPTION + destinationFormat);
		}
	}
	
	/**
	 * Returns a set with the unique lecture's name of an object shedule
	 * 
	 * @return A set of unique lecture's name
	 */
	public Set<String> getUniqueLecturesCourses() {
		Set<String> uniqueCourses = new HashSet<String>();
		for(Lecture l : lectures) {
			uniqueCourses.add(l.getAcademicInfo().getCourse());
		}
		return uniqueCourses;
	}

	/**
	 * This method loads a schedule via a webcal URI. The webcal should lead to an
	 * iCalendar file. If there are no compatible events inside the iCalendar file,
	 * the method returns a Schedule without lectures.
	 * 
	 * @param uri the webcal URI that leads to an iCalendar file.
	 * @return a schedule object with all lectures in the iCalendar.
	 * @throws IllegalArgumentException is thrown if the URI is null or not a
	 *                                  webcall URI.
	 * @throws MalformedURLException    is thrown if the URI cannot be converted
	 *                                  into an HTTPS URL.
	 * @throws IOException              is thrown if an error is given when
	 *                                  connecting or reading file with HTTPS
	 *                                  protocol.
	 * @see <a href="https://en.wikipedia.org/wiki/Webcal">Webcal Wikipedia
	 *      Entry</a>
	 */
	static Schedule loadWebcal(String uri) throws IOException {
		if (uri == null) {
			throw new IllegalArgumentException(URI_NULL_EXCEPTION);
		}
		if (!WEBCAL_PATTERN.matcher(uri).lookingAt()) {
			throw new IllegalArgumentException(URI_NOT_WEBCAL_EXCEPTION);
		}
		String httpsURI = WEBCAL_PATTERN.matcher(uri).replaceFirst("https");
		try {
			URL httpURL = new URL(httpsURI);
			URLConnection connection = httpURL.openConnection();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
			reader.close();
			return readICalendar(reader);
		} catch (MalformedURLException e) {
			throw new MalformedURLException(URI_NOT_VALID_EXCEPTION);
		} catch (IOException e) {
			throw new IOException(CONNECTING_TO_INTERNET_EXCEPTION);
		}
	}

	/**
	 * This method reads an ICalendar file from the BufferedReader and parses it
	 * into a schedule. If user information is found, it is added to the schedule.
	 * Each event (if compatible) of the iCalendar is parsed into a lecture and
	 * added into the schedule. The schedule is then returned.
	 * 
	 * @param reader the buffered reader used to read the iCalendar file.
	 * @return a schedule object with all lectures in the iCalendar.
	 * @throws IllegalArgumentException is thrown if the file does not have the
	 *                                  standard iCalendar structure.
	 * @throws IOException              is thrown if an error is given when reading
	 *                                  file with HTTPS protocol.
	 */
	static Schedule readICalendar(BufferedReader reader) throws IOException {
		if (skipReadUntilStartsWith(reader, BEGIN_VCALENDAR_PATTERN) == null) {
			throw new IllegalArgumentException(WEBCAL_NOT_VCALENDAR_EXCEPTION);
		}
		Schedule schedule = new Schedule();
		String userLine = skipReadUntilStartsWith(reader, USER_PATTERN);
		if (userLine != null) {
			Integer indexEndUser = userLine.indexOf("@");
			if (indexEndUser != -1) {
				schedule.setStudentName(userLine.substring(0, indexEndUser));
			}
		}
		while (skipReadUntilStartsWith(reader, BEGIN_EVENT_PATTERN) != null) {
			Lecture lecture = transformEventToLecture(reader);
			if (lecture != null) {
				schedule.addLecture(lecture);
			}
		}
		return schedule;
	}

	/**
	 * This method reads lines from the BufferedReader until it finds a line that
	 * starts with the provided pattern. If found, the prefix matching the pattern
	 * is removed from the line string and it is returned. Otherwise, returns null.
	 * 
	 * @param reader       the buffered reader used to find the line with the prefix
	 *                     pattern.
	 * @param startPattern the pattern that the prefix of the line must match.
	 * @return a string of the line where the pattern matched the prefix, but
	 *         without said prefix.
	 * @throws IOException is thrown if an error is given when reading file with
	 *                     HTTPS protocol.
	 */
	static String skipReadUntilStartsWith(BufferedReader reader, Pattern startPattern) throws IOException {
		String nextLine = reader.readLine();
		while (nextLine != null && !startPattern.matcher(nextLine).lookingAt()) {
			nextLine = reader.readLine();
		}
		if (nextLine == null) {
			return null;
		}
		return startPattern.matcher(nextLine).replaceFirst("").trim();
	}

	/**
	 * This method reads the event information with the BufferedReader, transforms
	 * it into a lecture and returns the lecture. If the event does not mention a
	 * course, or if no event is found, the method returns null. The Fenix
	 * iCalendars do not mention room capacity, students enrolled, degree or class
	 * group. These attributes will be null regardless of how complete the iCalendar
	 * is.
	 * 
	 * @param reader the buffered reader used to parse the event.
	 * @return a lecture object with the event information.
	 * @throws IOException is thrown if an error is given when reading file with
	 *                     HTTPS protocol.
	 */
	static Lecture transformEventToLecture(BufferedReader reader) throws IOException {
		String dateTimeBegin = skipReadUntilStartsWith(reader, DATETIME_START_PATTERN);
		String dateTimeEnd = skipReadUntilStartsWith(reader, DATETIME_END_PATTERN);
		LocalDateTime[] dateTimes = buildDateTimeInformation(dateTimeBegin, dateTimeEnd);
		LocalDate date = null;
		LocalTime timeBegin = null;
		if (dateTimes[0] != null) {
			date = dateTimes[0].toLocalDate();
			timeBegin = dateTimes[0].toLocalTime();
		}
		LocalTime timeEnd = null;
		if (dateTimes[1] != null) {
			timeEnd = dateTimes[1].toLocalTime();
		}
		String[] info = buildInformation(reader);
		for (int i = 0; i < info.length; i++) {
			if (info[i] != null && info[i].isEmpty()) {
				info[i] = null;
			}
		}
		String course = info[0];
		if (course == null) {
			return null;
		}
		String shift = info[1];
		String roomName = info[2];
		TimeSlot timeslot = new TimeSlot(null, date, timeBegin, timeEnd);
		Room room = new Room(roomName, (String) null);
		AcademicInfo academicInfo = new AcademicInfo(null, course, shift, null, (String) null);
		return new Lecture(academicInfo, timeslot, room);
	}

	/**
	 * Transforms the event's datetime start and datetime end strings into
	 * LocalDateTime objects. Returns an array with both objects. If the a
	 * LocalDateTime object cannot be parsed from a string, that object is returned
	 * as null.
	 * 
	 * @param dateTimeBegin the beginning date and time of the event as String.
	 * @param dateTimeEnd   the ending date and time of the event as String.
	 * @return a localdatetime array with the beginning and ending datetimes of the
	 *         event.
	 */
	static LocalDateTime[] buildDateTimeInformation(String dateTimeBegin, String dateTimeEnd) {
		String datetimeFormat = WEBCAL_DATETIME_FORMAT;
		DateTimeFormatter formatterDateTime = DateTimeFormatter.ofPattern(datetimeFormat);
		LocalDateTime[] dateTimes = new LocalDateTime[2];
		try {
			if (dateTimeBegin != null) {
				dateTimes[0] = LocalDateTime.from(formatterDateTime.parse(dateTimeBegin)).plusHours(1);
			}
		} catch (DateTimeParseException ignore) {}
		try {
			if (dateTimeEnd != null) {
				dateTimes[1] = LocalDateTime.from(formatterDateTime.parse(dateTimeEnd)).plusHours(1);
			}
		} catch (DateTimeParseException ignore) {}
		return dateTimes;
	}

	/**
	 * This method searches for the course, shift and room name information in the
	 * event being read by the BufferedReader and returns them in an array. Any
	 * information not found is returned as null.
	 * 
	 * @param reader the buffered reader used to find and parse the event.
	 * @return a string array with the course, shift and room of the event
	 *         information.
	 * @throws IOException is thrown if an error is given when reading file with
	 *                     HTTPS protocol.
	 */
	static String[] buildInformation(BufferedReader reader) throws IOException {
		String nextLine = skipReadUntilStartsWith(reader, DESCRIPTION_PATTERN);
		if (nextLine == null) {
			return new String[3];
		}
		StringBuilder descSB = new StringBuilder();
		descSB.append(nextLine);
		nextLine = reader.readLine();
		while (nextLine != null && !LOCATION_PATTERN.matcher(nextLine).lookingAt()) {
			descSB.append(nextLine.substring(1));
			nextLine = reader.readLine();
		}
		String[] description = DESCRIPTION_DELIMITER_PATTERN.split(descSB.toString());
		String[] info = new String[3];
		for (int i = 0; i < description.length; i++) {
			if (COURSE_PATTERN.matcher(description[i]).lookingAt()) {
				info[0] = COURSE_PATTERN.matcher(description[i]).replaceFirst("").trim();
			}
			if (SHIFT_PATTERN.matcher(description[i]).lookingAt()) {
				info[1] = SHIFT_PATTERN.matcher(description[i]).replaceFirst("").trim();
			}
		}
		if (nextLine != null) {
			String[] location = LOCATION_DELIMITER_PATTERN.split(nextLine);
			info[2] = LOCATION_PATTERN.matcher(location[0]).replaceFirst("").trim();
		}
		return info;
	}

	// DUPLICADO PARA APAGAR AO JUNTAR AO MAIN
	static String getFileExtension(String fileName) {
		int dotIndex = fileName.lastIndexOf(".");
		if (dotIndex > 0) {
			return fileName.substring(dotIndex);
		}
		return "";
	}
	
	/**
	 * Returns a string representation of the schedule, including the student's name
	 * and number, and the list of lectures.
	 * 
	 * @return A string representing the schedule.
	 */
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		if (studentName == null) {
			str.append("Unknown Student Name\n");
		} else {
			str.append("Student Name: " + studentName + "\n");
		}
		if (studentNumber == null) {
			str.append("Unknown Student Number\n");
		} else {
			str.append("Student Number: " + studentNumber + "\n");
		}
		if (lectures.isEmpty()) {
			str.append("Schedule is empty");
		} else {
			str.append("Schedule:\n");
			for (Lecture lecture : lectures) {
				str.append(lecture + "\n");
			}
		}
		return str.toString();
	}
}
