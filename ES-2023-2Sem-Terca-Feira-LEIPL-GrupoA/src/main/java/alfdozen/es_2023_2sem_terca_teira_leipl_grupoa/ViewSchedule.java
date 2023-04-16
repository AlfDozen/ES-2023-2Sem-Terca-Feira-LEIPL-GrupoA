package alfdozen.es_2023_2sem_terca_teira_leipl_grupoa;

import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;


public class ViewSchedule implements Initializable{


	private String[] daysOfWeek = {"Segunda-feira", "Terça-feira", "Quarta-feira", "Quinta-feira", "Sexta-feira", "Sábado", "Domingo"};

	private List<String> hours = new ArrayList<String>();

	private Map<Integer,ListView<Label>> monthItems = new HashMap<>();

	@FXML 
	private Tab monthTab, dayTab, weekTab;

	@FXML
	private GridPane paneCalendarDay, paneCalendarWeek, paneCalendarMonth;

	@FXML
	private DatePicker datePickerDay, datePickerWeek, datePickerMonth;

	@FXML
	private Button backButtonDay, backButtonWeek, backButtonMonth;

	@FXML
	private TabPane window; 


	private void clearGridContent(GridPane paneCalendar) {
		// clear the GridPane
		paneCalendar.getChildren().clear();
		paneCalendar.getRowConstraints().clear();
		paneCalendar.getColumnConstraints().clear();
	}

	private void defineGridLayout(GridPane paneCalendar) {
		paneCalendar.setHgap(10);
		paneCalendar.setVgap(10);

		//		paneCalendar.prefWidthProperty().bind(monthTab.App.scene.widthProperty());
		//		paneCalendar.prefHeightProperty().bind(monthtab.getScene().heightProperty());

	}

	private void allMonthLayout(GridPane paneCalendar) {
		paneCalendar.setHgap(10);
		paneCalendar.setVgap(10);

		//		paneCalendar.prefWidthProperty().bind(monthtab.getScene().widthProperty());
		//		paneCalendar.prefHeightProperty().bind(monthtab.getScene().heightProperty());

	}


	private void setDefaultHours(GridPane paneCalendar) {

		int startingHour = 8;
		int row = 1;
		int previous = 8;

		for(double i = startingHour+0.5; i < 23.5 ; i+=0.5) {

			int truncHour = (int)  Math.floor(i);

			if(truncHour == previous) {

				Label dayLabel = new Label(truncHour+":00-"+ truncHour+":30");
				paneCalendar.add(dayLabel, 0,row);

			}else {

				Label dayLabel = new Label(previous+":30-"+ truncHour +":00");
				paneCalendar.add(dayLabel, 0,row);
				previous++;
			}
			row++;
		}
	}

	@FXML
	private void returnHome() {
		try {
			App.setRoot("/fxml/Main");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	//   ######################### MOSTRAR CALENDARIO #################################################### //


	//  ######################### MÊS #################################################### //


	private void fillMap(int month, int year) {
		
		
		for(int i = 1; i <= 31; i++) {
			
			ListView<Label> listvu = new ListView<Label>();
			listvu.setPrefSize(150, 150);
			
			monthItems.put(i, listvu);
		}
		
		for(Lecture lec : App.getAllLectures()) {
			
			if(lec.getTimeSlot().getDate() != null
					&& lec.getTimeSlot().getDate().getMonthValue() == month
					&& lec.getTimeSlot().getDate().getYear() == year) {

				Label hourClass= new Label(lec.getTimeSlot().getTimeBegin() + " - " + lec.getTimeSlot().getTimeEnd() + lec.getAcademicInfo().getCourseAbbreviation() + " - " + lec.getRoom());

				int dayMonth = lec.getTimeSlot().getDate().getDayOfMonth();

					monthItems.get(dayMonth).getItems().add(hourClass);
			}
		}
	}

	@FXML
	public void showCalendarMonth() {

		if(datePickerMonth.getValue() != null) {

			clearGridContent(paneCalendarMonth);
			monthItems.clear();

			for (int i = 1; i <= daysOfWeek.length; i++) {

				Label dayLabel = new Label(daysOfWeek[i-1]);

				paneCalendarMonth.add(dayLabel, i, 0);
			}

			// Add date labels
			int numRows = 6; // number of rows in the calendar grid
			int numCols = 7; // number of columns in the calendar grid
			int dayOfMonth = 1; // starting day of the month

			LocalDate selectDate = datePickerMonth.getValue();
			LocalDate firstDay = selectDate.minusDays(selectDate.getDayOfMonth() - 1);

			DayOfWeek dayOfWeek = firstDay.getDayOfWeek();

			
			fillMap(selectDate.getMonthValue(),selectDate.getYear());

			for (int col = dayOfWeek.getValue(); col <= numCols; col++) {
				
				Label dateLabel = new Label(Integer.toString(dayOfMonth));
				monthItems.get(dayOfMonth).getItems().add(0, dateLabel);
				
				
				paneCalendarMonth.add(monthItems.get(dayOfMonth), col, 1);
				dayOfMonth++;
			}


			for (int row = 2; row <= numRows; row++) {			
				for (int col = 1; col <= numCols; col++) {
					if (dayOfMonth <= getNumDaysInMonth()) {
						Label dateLabel = new Label(Integer.toString(dayOfMonth));
						monthItems.get(dayOfMonth).getItems().add(0, dateLabel);
						
						paneCalendarMonth.add(monthItems.get(dayOfMonth), col, row);
						dayOfMonth++;
					}
				}
			}

			allMonthLayout(paneCalendarMonth);
		}
	}

	//  ######################### APOIO #################################################### //

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


	public void procedureForHours(GridPane gridPane) {

		hours.clear();

		ObservableList<Node> children = gridPane.getChildren();

		for (Node node : children) {
			if (GridPane.getRowIndex(node) > 0 && GridPane.getColumnIndex(node) == 0) {
				hours.add(((Label) node).getText().substring(0,5));
			}
		}
	}

	//  ######################### SEMANA #################################################### //

	@FXML
	public void bootCalendarWeek() {
		clearGridContent(paneCalendarWeek);
		setDefaultHours(paneCalendarWeek);
		procedureForHours(paneCalendarDay);
	}


	@FXML
	public void showCalendarWeek() {

		clearGridContent(paneCalendarWeek);
		setDefaultHours(paneCalendarWeek);

		if(datePickerWeek.getValue() != null) {

			LocalDate selectedDate = datePickerWeek.getValue();
			DayOfWeek dayOfWeek = selectedDate.getDayOfWeek();
			LocalDate startOfWeek = selectedDate.minusDays(dayOfWeek.getValue() - 1);

			for (int i = 0; i < daysOfWeek.length; i++) {

				Label dayLabel = new Label(daysOfWeek[i] + "\n" + startOfWeek.plusDays(i).format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
				dayLabel.setMinHeight(50);
				dayLabel.setMinHeight(50);

				paneCalendarWeek.add(dayLabel, i+1, 0);
			}

			for(Lecture lec : App.getAllLectures()) {

				if(lec.getTimeSlot().getDate() == null) {
					continue;
				}

				//Validar que a data da Lecture a ver está dentro da semana que está a ser mostrada no ecrã definida pelo DatePicker (seleccao da data)
				if(lec.getTimeSlot().getDate().compareTo(startOfWeek.plusDays(7)) >= 0 ) {
					break;
				}

				if(lec.getTimeSlot().getDate().compareTo(startOfWeek) >= 0 ) {

					int hourBegin = hours.indexOf(lec.getTimeSlot().getTimeBeginString().substring(0,5))+1;
					int hourEnd = hours.indexOf(lec.getTimeSlot().getTimeEndString().substring(0,5));

					int day = lec.getTimeSlot().getDate().getDayOfWeek().getValue();

					for(int row = hourBegin; row <= hourEnd; row++) {

						Node content =getNodeByRowColumnIndex(row, day, paneCalendarWeek);

						if(content == null ) {

							Label hourClass= new Label(lec.getAcademicInfo().getCourseAbbreviation() + "-" + lec.getRoom().getName());
							ListView<Label> lecList = new ListView<>();
							lecList.getItems().add(hourClass);

							paneCalendarWeek.add(lecList, day, row);

						}else {

							Label hourClass= new Label(lec.getAcademicInfo().getCourseAbbreviation() + "-" + lec.getRoom().getName());
							((ListView<Label>) content).getItems().add(hourClass);

							paneCalendarWeek.add(hourClass, day, row);
						}
					}
				}
			}

			defineGridLayout(paneCalendarWeek);
		}
	}

	//  ######################### DIA #################################################### //

	@FXML
	public void bootCalendarDay() {
		clearGridContent(paneCalendarDay);
		setDefaultHours(paneCalendarDay);
		procedureForHours(paneCalendarDay);
	}

	@FXML
	public void showCalendarDay() {

		if(datePickerDay.getValue() != null) {

			clearGridContent(paneCalendarDay);
			setDefaultHours(paneCalendarDay);

			LocalDate selectedDate = datePickerDay.getValue();
			DayOfWeek dayOfWeek = selectedDate.getDayOfWeek();

			//LAMBER HORARIO DE AULAS PARA O DIA
			for(Lecture lec : App.getAllLectures()) {

				if(lec.getTimeSlot().getDate() == null) {
					continue;
				}

				if(lec.getTimeSlot().getDate().compareTo(selectedDate) == 0 ) {

					int hourBegin = hours.indexOf(lec.getTimeSlot().getTimeBeginString().substring(0,5))+1;
					int hourEnd = hours.indexOf(lec.getTimeSlot().getTimeEndString().substring(0,5));

					//POR CABECALHO DO DIA
					Label dayLabel = new Label(daysOfWeek[dayOfWeek.getValue()-1] + "\n" + selectedDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
					dayLabel.setMinHeight(50);
					dayLabel.setMinHeight(50);
					paneCalendarDay.add(dayLabel, 1, 0);


					//VER HORAS DE INICIO E FIM
					for(int row = hourBegin; row <= hourEnd; row++) {

						Node content =getNodeByRowColumnIndex(row, 1, paneCalendarDay);

						if(content == null ) {

							Label hourClass= new Label(lec.getAcademicInfo().getCourseAbbreviation() + "-" + lec.getRoom().getName());
							ListView<Label> lecList = new ListView<>();
							lecList.getItems().add(hourClass);

							paneCalendarDay.add(lecList, 1, row);

						}else {

							Label hourClass= new Label(lec.getAcademicInfo().getCourseAbbreviation() + "-" + lec.getRoom().getName());
							((ListView<Label>) content).getItems().add(hourClass);

						}
					}
				}
			}

			defineGridLayout(paneCalendarDay);
		}
	}

	public boolean validateEntry(int value, int option) {
		return value == option;
	}

	private int getNumDaysInMonth() {
		int value = datePickerMonth.getValue().getMonthValue(); 

		if(validateEntry(value, 2))
			return 28;
		else 
			if(validateEntry(value, 4) || validateEntry(value, 6) || validateEntry(value, 9) || validateEntry(value, 11))
				return 30;

		return 31;
	}


	//  ######################### MAIN #################################################### //


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		App.setStageSize(window.getPrefWidth(),window.getPrefHeight());
		datePickerDay.setValue(LocalDate.now());
		datePickerMonth.setValue(LocalDate.now());
		datePickerWeek.setValue(LocalDate.now());

	}
}
