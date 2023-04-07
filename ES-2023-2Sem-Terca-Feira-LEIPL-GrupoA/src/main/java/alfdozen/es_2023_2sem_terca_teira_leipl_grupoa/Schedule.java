package alfdozen.es_2023_2sem_terca_teira_leipl_grupoa;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Schedule {

    private List<Lecture> lectures;
    private String studentName;
    private Integer studentNumber;

     Schedule(String studentName, Integer studentNumber) {
        this.studentName = studentName;
        this.studentNumber = studentNumber;
        this.lectures = new ArrayList<>();
    }

     List<Lecture> getLectures() {
        return lectures;
    }

     void setLectures(List<Lecture> lectures) {
        this.lectures = lectures;
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
        this.studentNumber = studentNumber;
    }

     void addLecture(Lecture lecture) {
        this.lectures.add(lecture);
        Collections.sort(this.lectures, (l1, l2) -> l1.getTimeSlot().compareTo(l2.getTimeSlot()));
    }

     void removeLecture(Lecture lecture) {
        this.lectures.remove(lecture);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Student Name: ").append(studentName).append("\n");
        sb.append("Student Number: ").append(studentNumber).append("\n");
        sb.append("Schedule:\n");
        for (Lecture lecture : lectures) {
            sb.append(lecture.toString()).append("\n");
        }
        return sb.toString();
    }
}
