package alfdozen.es_2023_2sem_terca_teira_leipl_grupoa;


import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.lang.System.Logger;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Set;

import javax.swing.JOptionPane;

import java.util.HashSet;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
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
	private List<String> hours = new ArrayList<String>();
	private String[] daysOfWeek = {"Segunda-feira", "Terça-feira", "Quarta-feira", "Quinta-feira", "Sexta-feira", "Sábado", "Domingo"};
	List<String> selectedItems = new ArrayList<>();


	@FXML
	private void createSchedule() {

		List<Lecture> lecList = new ArrayList<Lecture>();

		for(Lecture lec :  App.getSchedule().getLectures()) {
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
				Schedule.saveToCSV(App.getSchedule(), filenameToSave);
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
				Schedule.saveToJSON(App.getSchedule(), filenameToSave);
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
		if(calendar != null) {
			calendar.getChildren().clear();
		}
		selectedItems.clear();
	}


	private void showSchedule() {

		//		System.out.println(lectures.getSelectionModel().getSelectedItems().toString());

		if(calendar != null) {
			calendar.getChildren().clear();
		}

		int columns = ViewSchedule.NUM_COLUMNS;
		
		System.out.println("lista selected " + lectures.getSelectionModel().getSelectedItems());

		//função semana tipica
		List<String> listi = App.getSchedule().getCommonWeekLecture(lectures.getSelectionModel().getSelectedItems());

		procedureForHours(calendar,columns);

		setLabelWeekDays(columns*2, calendar);

		if(!listi.isEmpty()) {

			for(String lec : listi) {

				String[] info = lec.split(" - ");

				int day = Integer.parseInt(info[0]);
				int hourBegin = hours.indexOf(info[1].substring(0,5)) + 1;
				int hourEnd = hours.indexOf(info[1].substring(9,14)) + 1 ;
				String course = info[2];

//				System.out.println("Lecture " + lec.toString());
				System.out.println("hourBegin " + hourBegin + " hourEnd " + hourEnd);
				System.out.println("hours " + hours.toString());

				buildHours(day,course,hourBegin,hourEnd, calendar);
			}
		}
	}


	//  ######################### APOIO #################################################### //


	public void procedureForHours(GridPane gridPane, int columns) {

		hours.clear();

		int startingHour = 8;

		for(double i = startingHour; i < 23.5 ; i+=0.5) {

			int truncHour = (int) Math.floor(i);
			double min = i - truncHour;

			if(truncHour < 10) {
				if(min == 0.5) {
					hours.add("0"+truncHour+":30");
				}else{
					hours.add("0"+truncHour+":00");
				}
			}else {
				if(min == 0.5) {
					hours.add(truncHour+":30");
				}else{
					hours.add(truncHour+":00");
				}
			}
		}

		setDefaultHours(gridPane,columns);
	}

	private void setDefaultHours(GridPane paneCalendar, int columns) {

		for(int i = 1; i < hours.size(); i+=1) {

			Label dayLabel= new Label(hours.get(i-1)+"-"+hours.get(i));
			dayLabel.setPrefSize(100,50);
			dayLabel.setAlignment(Pos.CENTER);
			paneCalendar.add(dayLabel, 0,i);
		}
		setSeparator(paneCalendar, columns);
	}


	private void setSeparator(GridPane paneCalendar, int columns) {

		for(int i = 1; i < hours.size(); i+=1) {
			for(int j = 1; j < columns*2; j+=2) {

				Separator sep = new Separator(Orientation.VERTICAL);
				paneCalendar.add(sep,j,i);
			}
		}
	}


	//DESENHAR AS HORAS (REPRESENTAR AS LISTIVIEWS) NO CALENDARIO
	private void buildHours(int day, String course, int hourBegin, int hourEnd, GridPane paneCalendar) {

		Label hourClass= new Label(course + " - " + hourBegin +"-"+ hourEnd);

		//VER HORAS DE INICIO E FIM
		for(int row = hourBegin; row <= hourEnd; row++) {

			Node content = getNodeByRowColumnIndex(row, day*2, paneCalendar);

			if(content == null ) {
				ListView<Label> lecList = new ListView<Label>();
				lecList.setPrefHeight(50);
				lecList.getItems().add(hourClass);
				paneCalendar.add(lecList, day*2, hourBegin,1, hourEnd-hourBegin);

			}else {

				((ListView<Label>) content).getItems().add(hourClass);
			}
		}
	}


	public Node getNodeByRowColumnIndex(final int row, final int column, GridPane gridPane) {
		Node result = null;
		ObservableList<Node> children = gridPane.getChildren();

		for (Node node : children) {
			if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
				result = node;
				break;
			}
		}

		return result;
	}

	private void setLabelWeekDays(int days, GridPane paneCalendar) {

		for (int i = 2; i <= days; i+=2) {
			int dayDate = (i/2)-1;
			Label dayLabel = new Label(daysOfWeek[dayDate]);
			dayLabel.setPrefSize(paneCalendar.getWidth()*0.125, 75);
			dayLabel.setWrapText(true);
			dayLabel.setTextAlignment(TextAlignment.CENTER);
			dayLabel.setAlignment(Pos.CENTER);
			paneCalendar.add(dayLabel, i, 0);
		}
	}

	//  ######################### MAIN #################################################### //


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		App.setStageSize(window.getPrefWidth(),window.getPrefHeight());

		List<Lecture> lecturesList = App.getSchedule().getLectures();
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
