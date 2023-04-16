package alfdozen.es_2023_2sem_terca_teira_leipl_grupoa;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class CreateSchedule implements Initializable{


	@FXML
	private Button backButton, save, selectAll, clearAll;

	@FXML
	private TextField studentName, studentNumber;	

	@FXML
	private GridPane lectures, calendar; 

	@FXML
	private AnchorPane window; 

	@FXML
	private void createSchedule() {

	}

	@FXML
	private void clearAll() {


	}

	@FXML
	private void selectAll() {

	}

	@FXML
	private void returnHome() {
		try {
			App.setRoot("/fxml/Main");
		} catch (IOException e) {
			System.err.println("Erro ao tentar retornar");
		}

	}

	//  ######################### MAIN #################################################### //


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		App.setStageSize(window.getPrefWidth(),window.getPrefHeight());

		List<Lecture> lecturesList = App.getAllLectures();
		Set<String> courses = new HashSet<String>();


		for(Lecture lec : lecturesList) {

			courses.add(lec.getAcademicInfo().getCourse());
		}

		int row = 0;
		
		for(String s: courses) {
						
			CheckBox item = new CheckBox(s);
			lectures.add(item, 0, row);
			row++;
		}

	}
}
