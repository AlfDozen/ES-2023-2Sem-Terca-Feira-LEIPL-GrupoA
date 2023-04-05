package alfdozen.es_2023_2sem_terca_teira_leipl_grupoa;

/**
 * @author alfdozen
 * 
 *         The AcademicInfo class is used to define various academic information
 *         associated with the lecture: the university degree, the lecture
 *         course, the lecture shift, the associated student class and the
 *         number of students enrolled in the lecture. Since the information
 *         used to instantiate this class will frequently come from documents,
 *         the constructor accepts all arguments as Strings. Null arguments are
 *         accepted by the constructor and setters. With the function isComplete
 *         it can be checked if there are still null attributes.
 * 
 */

public final class AcademicInfo {

	static final String NEGATIVE_EXCEPTION = "The number of students enrolled can't be negative";
	static final String NOT_NUMBER_EXCEPTION = "The provided string doesn't correspond to a number";
	private String degree;
	private String course;
	private String shift;
	private String classGroup;
	private Integer studentsEnrolled;

	AcademicInfo(String degree, String course, String shift, String classGroup, Integer studentsEnrolled) {
		this.degree = degree;
		this.course = course;
		this.shift = shift;
		this.classGroup = classGroup;
		if (studentsEnrolled != null && studentsEnrolled < 0) {
			throw new IllegalArgumentException(NEGATIVE_EXCEPTION);
		}
		this.studentsEnrolled = studentsEnrolled;
	}

	AcademicInfo(String degree, String course, String shift, String classGroup, String studentsEnrolled) {
		this.degree = degree;
		this.course = course;
		this.shift = shift;
		this.classGroup = classGroup;
		if (studentsEnrolled == null) {
			this.studentsEnrolled = null;
			return;
		}
		try {
			this.studentsEnrolled = Integer.parseInt(studentsEnrolled);
		} catch (NumberFormatException e) {
			throw new NumberFormatException(NOT_NUMBER_EXCEPTION);
		}
		if (this.studentsEnrolled < 0) {
			throw new IllegalArgumentException(NEGATIVE_EXCEPTION);
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
		if (course == null) {
			return null;
		}
		StringBuilder abb = new StringBuilder();
		for (int i = 0; i < course.length(); i++) {
			char c = course.charAt(i);
			if (Character.isUpperCase(c)) {
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
		if (studentsEnrolled != null && studentsEnrolled < 0) {
			throw new IllegalArgumentException(NEGATIVE_EXCEPTION);
		}
		this.studentsEnrolled = studentsEnrolled;
	}

	void setStudentsEnrolled(String studentsEnrolled) {
		if (studentsEnrolled == null) {
			this.studentsEnrolled = null;
			return;
		}
		Integer number;
		try {
			number = Integer.parseInt(studentsEnrolled);
		} catch (NumberFormatException e) {
			throw new NumberFormatException(NOT_NUMBER_EXCEPTION);
		}
		if (number < 0) {
			throw new IllegalArgumentException(NEGATIVE_EXCEPTION);
		}
		this.studentsEnrolled = number;
	}

	boolean isComplete() {
		return degree != null && course != null && shift != null && classGroup != null && studentsEnrolled != null;
	}

	@Override
	public String toString() {
		String str = "Degree ";
		if (degree == null) {
			str += Lecture.FOR_NULL;
		} else {
			str += degree;
		}
		if (course == null) {
			str += " - Course " + Lecture.FOR_NULL;
		} else {
			str += " - Course " + course;
		}
		if (shift == null) {
			str += " - Shift " + Lecture.FOR_NULL;
		} else {
			str += " - Shift " + shift;
		}
		if (classGroup == null) {
			str += " - Class " + Lecture.FOR_NULL;
		} else {
			str += " - Class " + classGroup;
		}
		if (studentsEnrolled == null) {
			str += " - " + Lecture.FOR_NULL;
		} else {
			str += " - " + studentsEnrolled;
		}
		return str + " Enrolled Students";
	}
}
