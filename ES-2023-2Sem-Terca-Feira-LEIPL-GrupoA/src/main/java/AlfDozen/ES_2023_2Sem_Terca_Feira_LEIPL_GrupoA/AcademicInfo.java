package AlfDozen.ES_2023_2Sem_Terca_Feira_LEIPL_GrupoA;

public final class AcademicInfo {

	private static final String negativeException = "The number of students enrolled can't be negative";
	private String degree;
	private String course;
	private String shift;
	private String classGroup;
	private Integer studentsEnrolled;
	
	AcademicInfo(String degree, String course, String shift, String classGroup, Integer studentsEnrolled){
		this.degree = degree;
		this.course = course;
		this.shift = shift;
		this.classGroup = classGroup;
		if(studentsEnrolled != null && studentsEnrolled < 0) {
			throw new IllegalArgumentException(negativeException);
		}
		this.studentsEnrolled = studentsEnrolled;
	}
	
	AcademicInfo(String degree, String course, String shift, String classGroup, String studentsEnrolled){
		this.degree = degree;
		this.course = course;
		this.shift = shift;
		this.classGroup = classGroup;
		if(studentsEnrolled == null) {
			this.studentsEnrolled = null;
			return;
		}
		try {
			this.studentsEnrolled = Integer.parseInt(studentsEnrolled);
		} catch (NumberFormatException e) {
			throw new NumberFormatException("The provided string doesn't correspond to a number");
		}
		if(this.studentsEnrolled < 0) {
			throw new IllegalArgumentException(negativeException);
		}
	}
	
	String getDegree() {
		return degree;
	}

	void setDegree(String degree) {
		this.degree = degree;
	}

	String getCourse() {
		return course;
	}

	void setCourse(String course) {
		this.course = course;
	}

	String getCourseAbbreviation() {
		if(course == null) {
			return null;
		}
		StringBuilder abb = new StringBuilder();
		for(int i = 0; i < course.length(); i++) {
			char c = course.charAt(i);
			if(Character.isUpperCase(c)) {
				abb.append(c);
			}
		}
		return abb.toString();
	}
	
	String getShift() {
		return shift;
	}

	void setShift(String shift) {
		this.shift = shift;
	}
	
	String getClassGroup() {
		return classGroup;
	}
	
	void setClassGroup(String classGroup) {
		this.classGroup = classGroup;
	}
	
	Integer getStudentsEnrolled() {
		return studentsEnrolled;
	}

	void setStudentsEnrolled(Integer studentsEnrolled) {
		if(studentsEnrolled != null && studentsEnrolled < 0) {
			throw new IllegalArgumentException(negativeException);
		}
		this.studentsEnrolled = studentsEnrolled;
	}
	
	void setStudentsEnrolled(String studentsEnrolled) {
		if(studentsEnrolled == null) {
			this.studentsEnrolled = null;
			return;
		}
		Integer number;
		try {
			number = Integer.parseInt(studentsEnrolled);
		} catch (NumberFormatException e) {
			throw new NumberFormatException("The provided string doesn't correspond to a number");
		}
		if(number < 0) {
			throw new IllegalArgumentException(negativeException);
		}
		this.studentsEnrolled = number;
	}
	
	boolean isComplete() {
		return degree != null && course != null && shift != null &&
				classGroup != null && studentsEnrolled != null;
	}
	
	@Override
	public String toString() {
		return "Degree " + (degree == null ? Lecture.FORNULL : degree) +
				" - Course " + (course == null ? Lecture.FORNULL : course) +
				" - Shift " + (shift == null ? Lecture.FORNULL : shift) +
				" - Class " + (classGroup == null ? Lecture.FORNULL : classGroup) + 
				" - " + (studentsEnrolled == null ? Lecture.FORNULL : studentsEnrolled) + " Enrolled Students";
	}  
}
