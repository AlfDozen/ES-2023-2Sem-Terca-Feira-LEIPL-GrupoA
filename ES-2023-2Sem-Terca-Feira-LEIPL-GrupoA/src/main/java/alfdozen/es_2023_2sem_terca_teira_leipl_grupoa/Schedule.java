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
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

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
	static final String READ_WRITE_EXCEPTION = "Error: File read or write";
	static final String WRONG_FILE_FORMAT_EXCEPTION = "The file format should be ";
	static final String ENCODING = "ISO-8859-1";
	static final String DELIMITER = ",";
	static final String FILE_FORMAT_CSV = ".csv";
	static final String FILE_FORMAT_JSON = ".json";
	static final String FILE_EXISTS_EXCEPTION = "The file already exists!";
	static final String FILE_NOT_EXISTS_EXCEPTION = "The provided file does not exist!";
	static final String FILE_NULL_EXCEPTION = "The file cannot be null!";
	static final String DELIMITER_NULL_EXCEPTION = "The delimiter cannot be null!";
	static final String FOLDER_NOT_EXISTS_EXCEPTION = "The provided parent folder does not exist!";
	static final String FILE_MISSING_DATA = "At least 1 record of the data provided does not have the required values filled!";
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
	Converts a CSV file to a JSON file.

	@param csvSourcePath the path of the CSV file to convert.
	@param jsonDestinationPath the path of the JSON file to create.
	@param delimiter the delimiter character used in the CSV file.
	@throws IOException if there is an I/O error reading or writing the files.
	@throws IllegalArgumentException if any of the input arguments are null or invalid.
	*/
	static void convertCSV2JSON(String csvSourcePath, String jsonDestinationPath, Character delimiter) throws IOException{
		validateArguments(csvSourcePath, jsonDestinationPath, delimiter, FILE_FORMAT_CSV, FILE_FORMAT_JSON);

		File csvFile = new File(csvSourcePath);
		File jsonFile = new File(jsonDestinationPath);
		
		if (!csvFile.isFile())
			throw new IllegalArgumentException(FILE_NOT_EXISTS_EXCEPTION);
		if (!jsonFile.getParentFile().isDirectory())
			throw new IllegalArgumentException(FOLDER_NOT_EXISTS_EXCEPTION);
		if (jsonFile.isFile())
			throw new IllegalArgumentException(FILE_EXISTS_EXCEPTION);
			
		String destinationTempFilePath = "src/resources/tmpfile.csv";
		
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(destinationTempFilePath));
			BufferedReader reader = new BufferedReader(new FileReader(csvSourcePath));){
			
			String line;
			while((line = reader.readLine()) != null) {
				if(!line.isBlank()) {
					writer.append(line);
					writer.newLine();
				}
			}
		}
		try (InputStream inputStream = new FileInputStream(destinationTempFilePath)){
			
			CsvMapper csvMapper = new CsvMapper();
			CsvSchema csvSchema = CsvSchema.builder()
                    .setColumnSeparator(delimiter)
                    .setUseHeader(true)
                    .build();
			
	        List<Object> csvData = csvMapper.readerFor(Map.class)
	            .with(csvSchema)
	            .readValues(new InputStreamReader(inputStream))
	            .readAll();
			
	        Files.deleteIfExists(Paths.get(destinationTempFilePath));
	        
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, csvData);
		} catch (IOException e) {
			throw new IOException(READ_WRITE_EXCEPTION);
		}
	}

	/**
	Converts a JSON file to a CSV file.

	@param jsonSourcePath the path of the JSON file to convert.
	@param csvDestinationPath the path of the CSV file to create.
	@param delimiter the delimiter character used in the CSV file.
	@throws IOException if there is an I/O error reading or writing the files.
	@throws IllegalArgumentException if any of the input arguments are null or invalid.
	*/
	static void convertJSON2CSV(String jsonSourcePath, String csvDestinationPath, Character delimiter) throws IOException {
		validateArguments(jsonSourcePath, csvDestinationPath, delimiter, FILE_FORMAT_JSON, FILE_FORMAT_CSV);
		
		File jsonFile = new File(jsonSourcePath);
		File csvFile = new File(csvDestinationPath);
		
		if (!jsonFile.isFile())
			throw new IllegalArgumentException(FILE_NOT_EXISTS_EXCEPTION);
		if (!csvFile.getParentFile().isDirectory())
			throw new IllegalArgumentException(FOLDER_NOT_EXISTS_EXCEPTION);
		if (csvFile.isFile())
			throw new IllegalArgumentException(FILE_EXISTS_EXCEPTION);
		
		try (Reader reader = new InputStreamReader(new FileInputStream(jsonFile));
			OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(csvFile))){
			
			ObjectMapper jsonMapper = new ObjectMapper();
			List<Object> data = jsonMapper.readValue(reader, List.class);

			CsvMapper csvMapper = new CsvMapper();
			
			Map<String, Object> firstRecord = (Map<String, Object>) data.get(0);
			CsvSchema.Builder csvSchemaBuilder = CsvSchema.builder().setUseHeader(true).disableQuoteChar();

			for (String columnName : firstRecord.keySet()) 
			    csvSchemaBuilder.addColumn(columnName);

			CsvSchema csvSchema = csvSchemaBuilder.build().withColumnSeparator(delimiter);
			
			csvMapper.writerFor(List.class)
			        .with(csvSchema)
			        .writeValue(writer, data);

		} catch (Exception e) {
			throw new IOException(READ_WRITE_EXCEPTION);
		}

	}
	
	/**
	 * Validates the input arguments for file conversion methods.
	 *
	 * @param sourcePath      the path of the source file to be converted.
	 * @param destinationPath the path of the destination file to be created.
	 * @param delimiter       the delimiter character used in the CSV file (if applicable).
	 * @param sourceFormat    the expected file format of the source file (e.g., ".csv", ".json").
	 * @param destinationFormat the expected file format of the destination file (e.g., ".csv", ".json").
	 * @throws IllegalArgumentException if any of the input arguments are null or invalid.
	 */
	static void validateArguments(String sourcePath, String destinationPath, Character delimiter, String sourceFormat, String destinationFormat) {
	    if (sourcePath == null || destinationPath == null)
	        throw new IllegalArgumentException(FILE_NULL_EXCEPTION);
	    if (delimiter == null)
	        throw new IllegalArgumentException(DELIMITER_NULL_EXCEPTION);
	    if (!sourcePath.endsWith(sourceFormat))
	        throw new IllegalArgumentException(WRONG_FILE_FORMAT_EXCEPTION + sourceFormat);
	    if (!destinationPath.endsWith(destinationFormat))
	        throw new IllegalArgumentException(WRONG_FILE_FORMAT_EXCEPTION + destinationFormat);
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
	
	
	public static void main (String[] args) {
		try {
			convertJSON2CSV("src/resources/horario_exemplo_json_completo.json", "src/resources/qq.csv", ';');
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
