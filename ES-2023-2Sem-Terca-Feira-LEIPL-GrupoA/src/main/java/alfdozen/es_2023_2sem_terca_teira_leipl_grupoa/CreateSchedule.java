package alfdozen.es_2023_2sem_terca_teira_leipl_grupoa;


import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.HashSet;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;


public class CreateSchedule implements Initializable{


	@FXML
	private Button backButton, save, selectAll, clearAll;

	@FXML
	private TextField studentName, studentNumber;	

	@FXML
	private GridPane calendar; 

	@FXML
	private ListView<String> lectures;
	
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
		lectures.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	}

	@FXML
	private void returnHome() {
		try {
			App.setRoot("/fxml/Main");
		} catch (IOException e) {
			e.printStackTrace();
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

		for(String s: courses) {
			lectures.getItems().add(s);
		}

	}
}
