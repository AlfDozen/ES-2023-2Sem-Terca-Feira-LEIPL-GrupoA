package alfdozen.es_2023_2sem_terca_teira_leipl_grupoa;

import java.io.IOException;
import java.util.Set;

public class Main {
	
	public static void main(String[] args) throws IOException {
//		Schedule scd = Schedule.loadCSV("./src/main/resources/horario_exemplo_completo.csv");
//		Set<String> uc = scd.getUniqueLecturesCourses();
//		String s = "[Seminário de Projecto I (Piudhist), Teoria dos Jogos e dos Contratos, Gestão de Conflitos, Cálculo I, Competências Académicas I, Fundamentos de Arquitectura de Computadores, Investimentos II]";
//		if(s.equals((String) uc.toString())) {
//			System.out.println(true);
//		} else {
//			System.out.println(false);
//		}
//		
//		
//		scd = new Schedule();
//		uc = scd.getUniqueLecturesCourses();
//		s = "[]";
//		if(s.equals((String) uc.toString())) {
//			System.out.println(true);
//		} else {
//			System.out.println(false);
//		}
		
//		String uri = "webcal://fenix.iscte-iul.pt/publico/publicPersonICalendar.do?method=iCalendar&username=carfa2@iscte.pt&password=G39uVMI8jjWvt1HwyiqMBA1PeogZBT4vwQu7PMHxxpj2w8y0GbKvETn09fI5e6aSL4HdfMiWwDxfO8wTcUK4SccLmXkc1c7z2lAFFB9JXgsS1Frinjt4zaHClWv3BhOS";
//		Schedule s = Schedule.loadWebcal(uri);
//		System.out.println(s.toString());
		
		Schedule SCHEDULE = new Schedule();
		System.out.println(SCHEDULE);
		
	}

}
