package alfdozen.es_2023_2sem_terca_teira_leipl_grupoa;

import java.io.IOException;
import java.util.Set;

public class Main {
	
	public static void main(String[] args) throws IOException {
		Schedule scd = Schedule.loadCSV("./src/main/resources/horario_exemplo_completo.csv");
		Set<String> uc = scd.getUniqueLecturesCourses();
		String s = "[Seminário de Projecto I (Piudhist), Teoria dos Jogos e dos Contratos, Gestão de Conflitos, Cálculo I, Competências Académicas I, Fundamentos de Arquitectura de Computadores, Investimentos II]";
		if(s.equals((String) uc.toString())) {
			System.out.println(true);
		} else {
			System.out.println(false);
		}
		
		
		scd = new Schedule();
		uc = scd.getUniqueLecturesCourses();
		s = "[]";
		if(s.equals((String) uc.toString())) {
			System.out.println(true);
		} else {
			System.out.println(false);
		}
		
	}

}
