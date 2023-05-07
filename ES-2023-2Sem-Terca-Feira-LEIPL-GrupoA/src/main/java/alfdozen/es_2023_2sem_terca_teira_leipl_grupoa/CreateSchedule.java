package alfdozen.es_2023_2sem_terca_teira_leipl_grupoa;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Set;

import javax.swing.JOptionPane;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.Entry;
import com.calendarfx.model.Calendar.Style;
import com.calendarfx.view.page.WeekPage;

import java.util.HashSet;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class CreateSchedule implements Initializable{


	@FXML
	private Button backButton, save, selectAll, clearAll;

	@FXML
	private TextField studentName, studentNumber;	

	@FXML
	private WeekPage calendar; 
	
	@FXML
	private ListView<String> lectures;

	@FXML
	private AnchorPane window; 

	private FileChooser fileChooserToSave;
	private File filePathToSave;
	private String filenameToSave;
	private String[] daysOfWeek = {"Segunda-feira", "Terça-feira", "Quarta-feira", "Quinta-feira", "Sexta-feira", "Sábado", "Domingo"};
	List<String> selectedItems = new ArrayList<>();
	
	Calendar<Lecture> iscte = new Calendar<>("ISCTE");

	@FXML
	private void createSchedule() {

		List<Lecture> lecList = new ArrayList<Lecture>();

		for(Lecture lec :  App.schedule.getLectures()) {
			if(lectures.getSelectionModel().getSelectedItems().contains(lec.getAcademicInfo().getCourse())) {
				lecList.add(lec);
			}
		}
		App.setSchedule(new Schedule(lecList,studentName.getText(),Integer.valueOf(studentNumber.getText())));
	}

	@FXML
	private void clearAll() {
		lectures.getSelectionModel().clearSelection();
		clearSchedule();
	}

	@FXML
	private void selectAll() {
		lectures.getSelectionModel().selectAll();
		lectures.setStyle("-fx-selection-bar: #0000ff");
		setSelectedFields();
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


		try {
			if(filePathToSave != null) {
				filenameToSave = filePathToSave.getAbsolutePath();
				Schedule.saveToCSV(App.schedule, filenameToSave);
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


		try {
			if(filePathToSave != null) {
				filenameToSave = filePathToSave.getAbsolutePath();
				Schedule.saveToJSON(App.schedule, filenameToSave);
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

	@FXML
	private void setSelectedFields() {

		selectedItems.clear();
		selectedItems.addAll(lectures.getSelectionModel().getSelectedItems());
		showSchedule();
	}


	private void clearSchedule() {
		selectedItems.clear();
	}


	private void showSchedule() {

		iscte.clear();

		System.out.println("lista selected " + lectures.getSelectionModel().getSelectedItems());

		//função semana tipica
		List<Lecture> listi = App.schedule.getCommonWeekLecture(lectures.getSelectionModel().getSelectedItems());

		if(!listi.isEmpty()) {

			for(Lecture lec : listi) {

				if(lec.getTimeSlot().getDate() != null){
				
				Entry<Lecture> aulas = new Entry<>();
				
				aulas.setTitle(lec.toString());
				LocalDate classDay = lec.getTimeSlot().getDate();
				LocalTime begin = lec.getTimeSlot().getTimeBegin();
				LocalTime end = lec.getTimeSlot().getTimeEnd();

				aulas.setInterval(classDay, begin, classDay, end);

				iscte.addEntry(aulas);
				
				}
			}
		}
	}


	//  ######################### MAIN #################################################### //


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		App.setStageSize(window.getPrefWidth(),window.getPrefHeight());

		List<Lecture> lecturesList = App.schedule.getLectures();
		Set<String> courses = new HashSet<String>();

		for(Lecture lec : lecturesList) {
			courses.add(lec.getAcademicInfo().getCourse());
		}

		for(String s: courses) {
			lectures.getItems().add(s);
		}

		lectures.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		lectures.setStyle("-fx-selection-bar: #0033ff");
		
		iscte.setStyle(Style.STYLE1);

	}
}
