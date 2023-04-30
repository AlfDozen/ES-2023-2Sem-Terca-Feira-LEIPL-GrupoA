package alfdozen.es_2023_2sem_terca_teira_leipl_grupoa;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Set;

import javax.swing.JOptionPane;

import java.util.HashSet;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

//PARA APAGAR: CLASSE DE PAGINA DE CRIAR HORARIO (A SEGUNDA PÁGINA, NÃO A DE IR BUSCAR O HORÁRIO PRIMEIRO)
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

	private FileChooser fileChooserToSave;
	private File filePathToSave;
	private String filenameToSave;


	@FXML
	private void createSchedule() {

		List<Lecture> lecList = new ArrayList<Lecture>();

		for(Lecture lec :  App.SCHEDULE.getLectures()) {
			if(lectures.getSelectionModel().getSelectedItems().contains(lec.getAcademicInfo().getCourse())) {
				lecList.add(lec);
			}
		}

		App.SCHEDULE = new Schedule(lecList,studentName.getText(),Integer.valueOf(studentNumber.getText()));
	}

	@FXML
	private void clearAll() {
		lectures.getSelectionModel().clearSelection();
		
	}

	@FXML
	private void selectAll() {
		lectures.getSelectionModel().selectAll();
		lectures.setStyle("-fx-selection-bar: #0000ff");
	}

	@FXML
	private void returnHome() {
		try {
			App.setRoot("/fxml/Main");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@FXML
	private void saveCSV() {
		//PARA GRAVAR
		fileChooserToSave = new FileChooser();
		fileChooserToSave.getExtensionFilters().addAll(new ExtensionFilter("CSV", ".CSV"));
		filePathToSave = fileChooserToSave.showSaveDialog(new Stage());
		filenameToSave = filePathToSave.getAbsolutePath();

		try {
			if(filenameToSave != null) {
				Schedule.saveToCSV(App.SCHEDULE, filenameToSave);
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,"Deu cócó ao gravar", "Alerta" , JOptionPane.ERROR_MESSAGE);
		}
	}

	@FXML
	private void saveJSON() {
		//PARA GRAVAR
		fileChooserToSave = new FileChooser();
		fileChooserToSave.getExtensionFilters().addAll(new ExtensionFilter("JSON", ".json"));
		filePathToSave = fileChooserToSave.showSaveDialog(new Stage());
		filenameToSave = filePathToSave.getAbsolutePath();

		try {
			if(filenameToSave != null) {
				Schedule.saveToJSON(App.SCHEDULE, filenameToSave);
			}

		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,"Deu cócó ao gravar", "Alerta" , JOptionPane.ERROR_MESSAGE);
		}
	}


	@FXML
	private void viewSchedule() {

		String[] buttons = {"Confirmar", "Cancelar"};
		int confirmation = JOptionPane.showOptionDialog(null, "Irá perder todos os dados não guardados. De certeza que pretende continuar?",
				"Ir para consultar calendário",
				JOptionPane.WARNING_MESSAGE, JOptionPane.QUESTION_MESSAGE, null, buttons, buttons[0]);
		
		System.out.println(confirmation);

		if(confirmation == 0) {

			try {
				App.setRoot("/fxml/ViewSchedule");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	//  ######################### MAIN #################################################### //


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		App.setStageSize(window.getPrefWidth(),window.getPrefHeight());

		List<Lecture> lecturesList = App.SCHEDULE.getLectures();
		Set<String> courses = new HashSet<String>();

		for(Lecture lec : lecturesList) {
			courses.add(lec.getAcademicInfo().getCourse());
		}

		for(String s: courses) {
			lectures.getItems().add(s);
		}
		
		lectures.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		lectures.setStyle("-fx-selection-bar: #0033ff");

	}
}
