package alfdozen.es_2023_2sem_terca_teira_leipl_grupoa;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import alfdozen.es_2023_2sem_terca_teira_leipl_grupoa.AcademicInfo;
import alfdozen.es_2023_2sem_terca_teira_leipl_grupoa.Lecture;
import alfdozen.es_2023_2sem_terca_teira_leipl_grupoa.Room;
import alfdozen.es_2023_2sem_terca_teira_leipl_grupoa.Schedule;
import alfdozen.es_2023_2sem_terca_teira_leipl_grupoa.TimeSlot;

 class ScheduleTest {

	 
   
    @Test
    final void testScheduleEmptyConstructor() {
        Schedule schedule = new Schedule();
        assertNotNull(schedule.getLectures());
        assertNull(schedule.getStudentName());
        assertNull(schedule.getStudentNumber());
    }

    @Test
    final void testScheduleLecturesConstructor() {
        Lecture lecture = new Lecture(new AcademicInfo("LEI-PL", "Engenharia de Software", "T02A", "LEIPL1", 5),
                new TimeSlot("Qui", LocalDate.of(2023, 2, 23), LocalTime.of(3, 2, 32), LocalTime.of(11, 23, 4)),
                new Room("ES23", 20));
        List<Lecture> lectures = new ArrayList<>();
        lectures.add(lecture);
        Schedule schedule = new Schedule(lectures);
        assertEquals(lectures, schedule.getLectures());
        assertNull(schedule.getStudentName());
        assertNull(schedule.getStudentNumber());
    }

    @Test
    final void testScheduleFullConstructor() {
        Lecture lecture = new Lecture(new AcademicInfo("LEI-PL", "Engenharia de Software", "T02A", "LEIPL1", 5),
                new TimeSlot("Qui", LocalDate.of(2023, 2, 23), LocalTime.of(3, 2, 32), LocalTime.of(11, 23, 4)),
                new Room("ES23", 20));
        List<Lecture> lectures = new ArrayList<>();
        lectures.add(lecture);
        String studentName = "John Doe";
        Integer studentNumber = 12345;
        Schedule schedule = new Schedule(lectures, studentName, studentNumber);
        assertEquals(lectures, schedule.getLectures());
        assertEquals(studentName, schedule.getStudentName());
        assertEquals(studentNumber, schedule.getStudentNumber());
    }

    @Test
    final void testSchedulePartialConstructor() {
        String studentName = "John Doe";
        String studentNumber = "12345";
        Schedule schedule = new Schedule(null, studentName, studentNumber);
        assertNotNull(schedule.getLectures());
        assertEquals(studentName, schedule.getStudentName());
        assertEquals(Integer.valueOf(studentNumber), schedule.getStudentNumber());
    }

    @Test
    final void testScheduleNameNumberConstructor() {
        String studentName = "John Doe";
        Integer studentNumber = 12345;
        Schedule schedule = new Schedule(studentName, studentNumber);
        assertNotNull(schedule.getLectures());
        assertEquals(studentName, schedule.getStudentName());
        assertEquals(studentNumber, schedule.getStudentNumber());
    }

    @Test
    final void testScheduleNameStringNumberConstructor() {
        String studentName = "John Doe";
        String studentNumber = "12345";
        Schedule schedule = new Schedule(studentName, studentNumber);
        assertNotNull(schedule.getLectures());
        assertEquals(studentName, schedule.getStudentName());
        assertEquals(Integer.valueOf(studentNumber), schedule.getStudentNumber());
    }

    
    
    @Test
    final void testScheduleListOfLecture() {
    	AcademicInfo academicInfo1 = new AcademicInfo("LEI-PL", "Engenharia de Software", "T02A", "LEIPL1", 5);
    	TimeSlot timeSlot1 = new TimeSlot("Qui", LocalDate.of(2023, 2, 23), LocalTime.of(3, 2, 32),
    			LocalTime.of(11, 23, 4));
    	Room room1 = new Room("ES23", 20);
    	Lecture lecture1 = new Lecture(academicInfo1, timeSlot1, room1);

    	AcademicInfo academicInfo2 = new AcademicInfo("LEI-PL", "Engenharia de Software", "T02A", "LEIPL1", 5);
    	TimeSlot timeSlot2 = new TimeSlot("Ter", LocalDate.of(2023, 2, 22), LocalTime.of(4, 3, 22),
    			LocalTime.of(14, 53, 43));
    	Room room2 = new Room("B.001", 100);
    	Lecture lecture2 = new Lecture(academicInfo2, timeSlot2, room2);

    	List<Lecture> lectures = new ArrayList<>();
    	lectures.add(lecture1);
    	lectures.add(lecture2);

    	Schedule schedule = new Schedule(lectures);
    	assertEquals(lectures, schedule.getLectures());

    	Schedule scheduleNull = new Schedule(null);
    	assertNotNull(scheduleNull.getLectures());
    	assertTrue(scheduleNull.getLectures().isEmpty());
    }
}
    
    

	
    
    
