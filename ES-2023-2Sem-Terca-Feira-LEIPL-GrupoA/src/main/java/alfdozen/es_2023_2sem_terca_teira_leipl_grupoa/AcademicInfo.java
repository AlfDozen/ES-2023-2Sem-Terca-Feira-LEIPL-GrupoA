package alfdozen.es_2023_2sem_terca_teira_leipl_grupoa;

import java.util.Objects;

/**
 * The AcademicInfo class is used to define various academic information
 * associated with the lecture. Namely, the university degree, the lecture
 * course, the lecture shift, the associated student class and the number of
 * students enrolled in the lecture. Since the information used to instantiate
 * this class will frequently come from documents, the constructor accepts all
 * arguments as Strings. Null arguments are accepted by the constructor and
 * setters. With the function isComplete it can be checked if there are still
 * null attributes.
 * 
 * @author alfdozen
 * @version 1.0.0
 */
final class AcademicInfo {

	static final String NEGATIVE_EXCEPTION = "The number of students enrolled can't be negative";
	static final String NOT_NUMBER_EXCEPTION = "The provided string doesn't correspond to a number";
	private String degree;
	private String course;
	private String shift;
	private String classGroup;
	private Integer studentsEnrolled;

	/**
	 * Constructor creates academic information with name of degree, name of the
	 * course, designation of the shift, designation of the student class and the
	 * number of students enrolled.
	 * 
	 * @param degree           the name of the degree.
	 * @param course           the name of the course.
	 * @param shift            the designation of the shift.
	 * @param classGroup       the designation of the student class.
	 * @param studentsEnrolled the number of students enrolled.
	 * @throws IllegalArgumentException if the number of students enrolled is
	 *                                  negative.
	 */
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

	/**
	 * Constructor creates academic information with name of degree, name of the
	 * course, designation of the shift, designation of the student class and the
	 * number of students enrolled. Receives the number of students enrolled as a
	 * String and converts to Integer.
	 * 
	 * @param degree           the name of the degree.
	 * @param course           the name of the course.
	 * @param shift            the designation of the shift.
	 * @param classGroup       the designation of the student class.
	 * @param studentsEnrolled the number of students enrolled as a String.
	 * @throws NumberFormatException    if the number of students enrolled String
	 *                                  cannot be parsed to an Integer.
	 * @throws IllegalArgumentException if the number of students enrolled is
	 *                                  negative.
	 */
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

	/**
	 * Return the name of the degree.
	 * 
	 * @return the name of the degree.
	 */
	String getDegree() {
		return degree;
	}

	/**
	 * Sets the name of the degree.
	 * 
	 * @param degree the name of the degree.
	 */
	void setDegree(String degree) {
		this.degree = degree;
	}

	/**
	 * Return the name of the course.
	 * 
	 * @return the name of the course.
	 */
	String getCourse() {
		return course;
	}

	/**
	 * Sets the name of the course.
	 * 
	 * @param course the name of the course.
	 */
	void setCourse(String course) {
		this.course = course;
	}

	/**
	 * Return the abbreviated name of the course. This abbreviation is built using
	 * all the upper case characters in the name of the course.
	 * 
	 * @return the abbreviated name of the course.
	 */
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

	/**
	 * Return the designation of the shift.
	 * 
	 * @return the designation of the shift.
	 */
	String getShift() {
		return shift;
	}

	/**
	 * Sets the designation of the shift.
	 * 
	 * @param shift the designation of the shift.
	 */
	void setShift(String shift) {
		this.shift = shift;
	}

	/**
	 * Return the designation student class.
	 * 
	 * @return the designation of the student class.
	 */
	String getClassGroup() {
		return classGroup;
	}

	/**
	 * Sets the designation of the student class.
	 * 
	 * @param classGroup the designation of the student class.
	 */
	void setClassGroup(String classGroup) {
		this.classGroup = classGroup;
	}

	/**
	 * Return the number of students enrolled.
	 * 
	 * @return the number of students enrolled.
	 */
	Integer getStudentsEnrolled() {
		return studentsEnrolled;
	}

	/**
	 * Sets the number of students enrolled.
	 * 
	 * @param studentsEnrolled the number of students enrolled.
	 * @throws IllegalArgumentException if the number of students enrolled is
	 *                                  negative.
	 */
	void setStudentsEnrolled(Integer studentsEnrolled) {
		if (studentsEnrolled != null && studentsEnrolled < 0) {
			throw new IllegalArgumentException(NEGATIVE_EXCEPTION);
		}
		this.studentsEnrolled = studentsEnrolled;
	}

	/**
	 * Sets the number of students enrolled. Receives the number of students
	 * enrolled as a String and converts to Integer.
	 * 
	 * @param studentsEnrolled the number of students enrolled as a String.
	 * @throws NumberFormatException    if the number of students enrolled String
	 *                                  cannot be parsed to an Integer.
	 * @throws IllegalArgumentException if the number of students enrolled is
	 *                                  negative.
	 */
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

	/**
	 * Checks if the name of degree, name of the course, the designation of the
	 * shift, the designation of the student class and the number of students
	 * enrolled are not null. If a null is found, the method returns false.
	 * Otherwise, it returns true.
	 * 
	 * @return true if all the academic information is complete (not null); false
	 *         otherwise.
	 */
	boolean isComplete() {
		return degree != null && course != null && shift != null && classGroup != null && studentsEnrolled != null;
	}

	/**
	 * Return a string representation of the academic information, including the
	 * name of the degree, the name of the course, the designation of the shift, the
	 * designation of the student class and the number of students enrolled. If any
	 * of this information is not defined, that information is instead given as
	 * "Unknown".
	 * 
	 * @return a string representation of the academic information.
	 */
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

	/**
	 * Computes a hash code for this object based on its course, degree, class
	 * group, shift, and number of students enrolled.
	 * 
	 * @return a hash code value for this object
	 */
	@Override
	public int hashCode() {
		return Objects.hash(classGroup, course, degree, shift, studentsEnrolled);
	}

	/**
	 * Indicates whether some other object is "equal to" this one. Two AcademicInfo
	 * objects are considered equal if they have the same course code, degree, class
	 * group, shift, and number of students enrolled.
	 * 
	 * @param obj the object to compare to
	 * @return true if this object is the same as the obj argument; false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		AcademicInfo other = (AcademicInfo) obj;
		return Objects.equals(classGroup, other.classGroup) && Objects.equals(course, other.course)
				&& Objects.equals(degree, other.degree) && Objects.equals(shift, other.shift)
				&& Objects.equals(studentsEnrolled, other.studentsEnrolled);
	}

}
