package alfdozen.es_2023_2sem_terca_teira_leipl_grupoa;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({ AcademicInfoTest.class, LectureTest.class, RoomTest.class, TimeSlotTest.class, ScheduleTest.class })
public class AllTests {

}
