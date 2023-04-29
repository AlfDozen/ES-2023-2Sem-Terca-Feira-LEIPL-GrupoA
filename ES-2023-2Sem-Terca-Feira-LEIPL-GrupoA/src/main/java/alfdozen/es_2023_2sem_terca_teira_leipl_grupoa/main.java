package alfdozen.es_2023_2sem_terca_teira_leipl_grupoa;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class main {
	
	public static void main(String[] args) {
		String webcalURI = "webcal://fenix.iscte-iul.pt/publico/publicPersonICalendar.do?method=iCalendar&username=pmaaa2@iscte.pt&password=eeQiuTsFNkE1d3FtEazt4AhkLYgM0GCTHLpMgBBKOf2MVlcqTJyFEikS89IHlkMs0ACDjLXI3fG4KGUBNzdFIMg3zucfEnxwoRPWPWUKZfbyq04Wgd8PSN6aYngvygHR";
		Schedule schedule = new Schedule();
		try {
			schedule = Schedule.loadWebcal(webcalURI);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(schedule);
		String httpURI = webcalURI.replaceFirst("webcal", "https");
		try {
			InputStream in = new URL(httpURI).openStream();
			Files.copy(in, Paths.get("estaravazio.txt"), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
