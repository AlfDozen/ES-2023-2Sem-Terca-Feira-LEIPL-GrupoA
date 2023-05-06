package alfdozen.es_2023_2sem_terca_teira_leipl_grupoa;

import java.io.File;
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

import javax.swing.JOptionPane;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;


public class ViewSchedule_old implements Initializable{


	private String[] daysOfWeek = {"Segunda-feira", "Terça-feira", "Quarta-feira", "Quinta-feira", "Sexta-feira", "Sábado", "Domingo"};

	private List<String> hours = new ArrayList<String>();

	private Map<Integer,ListView<Label>> monthItems = new HashMap<>();

	public static final int NUM_COLUMNS = 7;

	private FileChooser fileChooserToSave;
	private File filePathToSave;
	private String filenameToSave;

	@FXML 
	private Tab monthTab, dayTab, weekTab;

	@FXML
	private ListView<Label> classesList;

	@FXML
	private ScrollPane scrollCalendarDay, scrollCalendarWeek, scrollCalendarMonth;

	@FXML
	private GridPane paneCalendarDay, paneCalendarWeek, paneCalendarMonth, smallPaneCalendarMonth;

	@FXML
	private AnchorPane anchorDay, anchorWeek, anchorMonth;

	@FXML
	private DatePicker datePickerDay, datePickerWeek, datePickerMonth;

	@FXML
	private Button previousDay, nextDay, previousWeek, nextWeek, previousMonth, nextMonth,
	dateLabelDay, dateLabelWeek, dateLabelMonth;

	@FXML
	private TabPane window;


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
		filenameToSave = filePathToSave.getAbsolutePath();

		try {
			if(filenameToSave != null) {
				Schedule.saveToJSON(App.getSchedule(), filenameToSave);
			}

		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,"Deu cócó ao gravar", "Alerta" , JOptionPane.ERROR_MESSAGE);
		}
	}

	@FXML
	private void createSchedule() {
		try {
			App.setRoot("/fxml/CreateSchedule");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void selectDay() {
		datePickerDay.setValue(datePickerDay.getValue().plusDays(0));
	}

	@FXML
	private void nextMonth() {
		datePickerMonth.setValue(datePickerMonth.getValue().plusMonths(1));
		setLabelButtonMonth();
	}

	@FXML
	private void previousMonth() {
		datePickerMonth.setValue(datePickerMonth.getValue().plusMonths(-1));
		setLabelButtonMonth();
	}

	@FXML
	private void nextWeek() {
		datePickerWeek.setValue(datePickerWeek.getValue().plusWeeks(1));
		setLabelButtonWeek();
	}

	@FXML
	private void previousWeek() {
		datePickerWeek.setValue(datePickerWeek.getValue().plusWeeks(-1));
		setLabelButtonWeek();
	}


	@FXML
	private void nextDay() {
		datePickerDay.setValue(datePickerDay.getValue().plusDays(1));
		setLabelButtonDay();
	}

	@FXML
	private void previousDay() {
		datePickerDay.setValue(datePickerDay.getValue().plusDays(-1));
		setLabelButtonDay();
	}


	//   ######################### MOSTRAR CALENDARIO #################################################### //


	//  ######################### MÊS #################################################### //


	@FXML
	public void showCalendarMonth() {

		clearGridContent(paneCalendarMonth);
		monthItems.clear();

		changeDatePicker(datePickerMonth.getValue(), "Month");
		setLabelButtonMonth();

		for (int i = 1; i <= NUM_COLUMNS; i++) {

			Label dayLabel = new Label(daysOfWeek[i-1]);
			dayLabel.setFont(new Font("Arial", 14));
			//            dayLabel.setPrefSize(150, 200);
			paneCalendarMonth.add(dayLabel, i, 0);
			GridPane.setConstraints(dayLabel, i, 0);

		}

		// Add date labels
		int numRows = 6; // number of rows in the calendar grid
		int dayOfMonth = 1; // starting day of the month

		LocalDate selectDate = datePickerMonth.getValue();
		LocalDate firstDay = selectDate.minusDays(selectDate.getDayOfMonth() - 1);

		DayOfWeek dayOfWeek = firstDay.getDayOfWeek();

		fillMap(selectDate.getMonthValue(),selectDate.getYear());


		for (int col = dayOfWeek.getValue(); col <= NUM_COLUMNS; col++) {

			Label dateLabel = new Label(Integer.toString(dayOfMonth));
			monthItems.get(dayOfMonth).getItems().add(0, dateLabel);

			paneCalendarMonth.add(monthItems.get(dayOfMonth), col, 1);
			dayOfMonth++;
		}


		for (int row = 2; row <= numRows; row++) {			
			for (int col = 1; col <= NUM_COLUMNS; col++) {
				if (dayOfMonth <= getNumDaysInMonth()) {
					Label dateLabel = new Label(Integer.toString(dayOfMonth));
					monthItems.get(dayOfMonth).getItems().add(0, dateLabel);

					paneCalendarMonth.add(monthItems.get(dayOfMonth), col, row);
					dayOfMonth++;
				}
			}
		}
	}

	//  ######################### SEMANA #################################################### //

	@FXML
	public void bootCalendarWeek() {
		clearGridContent(paneCalendarWeek);
		procedureForHours(paneCalendarWeek,NUM_COLUMNS);

		changeDatePicker(datePickerWeek.getValue(), "Week");
		setLabelButtonWeek();
		showCalendarWeek();
	}


	@FXML
	public void showCalendarWeek() {

		LocalDate selectedDate = datePickerWeek.getValue();
		DayOfWeek dayOfWeek = selectedDate.getDayOfWeek();
		LocalDate startOfWeek = selectedDate.minusDays(dayOfWeek.getValue() - 1);

		setLabelWeekDays(NUM_COLUMNS*2, paneCalendarWeek, startOfWeek);

		for(Lecture lec : App.getSchedule().getLectures()) {

			if(lec.getTimeSlot().getDate() == null) {
				continue;
			}

			//Validar que a data da Lecture a ver está dentro da semana que está a ser mostrada no ecrã definida pelo DatePicker (seleccao da data)
			if(lec.getTimeSlot().getDate().compareTo(startOfWeek.plusDays(7)) >= 0 ) {
				break;
			}

			if(lec.getTimeSlot().getDate().compareTo(startOfWeek) >= 0 ) {

				int hourBegin = hours.indexOf(lec.getTimeSlot().getTimeBeginString().substring(0,5)) + 1;
				int hourEnd = hours.indexOf(lec.getTimeSlot().getTimeEndString().substring(0,5)) + 1 ;

//				System.out.println("Lecture " + lec.toString());
//				System.out.println("hourBegin " + hourBegin + " hourEnd " + hourEnd);
//				System.out.println("hours " + hours.toString());

				int day = lec.getTimeSlot().getDate().getDayOfWeek().getValue();

				buildHours(hourBegin,hourEnd, paneCalendarWeek, day, lec);

			}
		}
	}

	//  ######################### DIA #################################################### //


	private void smallCalendarMonth() {

		clearGridContent(smallPaneCalendarMonth);

		for (int i = 1; i <= NUM_COLUMNS; i++) {

			Label dayLabel = new Label(daysOfWeek[i-1]);
			smallPaneCalendarMonth.add(dayLabel, i, 0);
		}

		// Add date labels
		int numRows = 6; // number of rows in the calendar grid
		int dayOfMonth = 1; // starting day of the month

		LocalDate selectDate = datePickerDay.getValue();
		LocalDate firstDay = selectDate.minusDays(selectDate.getDayOfMonth()-1);

		DayOfWeek dayOfWeek = firstDay.getDayOfWeek();

		for (int col = dayOfWeek.getValue(); col <= NUM_COLUMNS; col++) {
			Label dateLabel = new Label(Integer.toString(dayOfMonth));
			smallPaneCalendarMonth.add(dateLabel, col, 1);
			dayOfMonth++;
		}

		for (int row = 2; row <= numRows; row++) {
			for (int col = 1; col <= NUM_COLUMNS; col++) {
				if (dayOfMonth <= getNumDaysInMonth()) {
					Label dateLabel = new Label(Integer.toString(dayOfMonth));
					smallPaneCalendarMonth.add(dateLabel, col, row);
					dayOfMonth++;
				}
			}
		}
	}


	@FXML
	public void bootCalendarDay() {
		clearGridContent(paneCalendarDay);
		classesList.getItems().clear();
		procedureForHours(paneCalendarDay,1);

		if(datePickerMonth != null && datePickerWeek != null) {
			changeDatePicker(datePickerDay.getValue(), "Day");
		}
		showCalendarDay();
	}

	@FXML
	public void showCalendarDay() {

		if(datePickerDay.getValue() != null) {

			setLabelButtonDay();
			smallCalendarMonth();

			LocalDate selectedDate = datePickerDay.getValue();

			setLabelWeekDays(2, paneCalendarDay, selectedDate);


			//LAMBER HORARIO DE AULAS PARA O DIA
			for(Lecture lec : App.getSchedule().getLectures()) {

				if(lec.getTimeSlot().getDate() == null) {
					continue;
				}

				if(lec.getTimeSlot().getDate().compareTo(selectedDate) == 0 ) {

					int hourBegin = hours.indexOf(lec.getTimeSlot().getTimeBeginString().substring(0,5)) + 1;
					int hourEnd = hours.indexOf(lec.getTimeSlot().getTimeEndString().substring(0,5)) + 1 ;

					buildHours(hourBegin,hourEnd, paneCalendarDay, 1, lec);

					Label classInfo = new Label(lec.getAcademicInfo().getCourse() + " - " + lec.getTimeSlot().toString() 
							+ " - " + lec.getAcademicInfo().getDegree() + " - " + lec.getAcademicInfo().getShift() + " - " + lec.getRoom().getName());
					classesList.getItems().add(classInfo);

				}
			}
		}

	}

	//  ######################### APOIO CALCULO #################################################### //


	private void fillMap(int month, int year) {


		for(int i = 1; i <= 31; i++) {

			ListView<Label> listvu = new ListView<Label>();
			listvu.setPrefSize(150, 150);
			listvu.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, null, null)));

			monthItems.put(i, listvu);
		}

		for(Lecture lec : App.getSchedule().getLectures()) {

			if(lec.getTimeSlot().getDate() != null
					&& lec.getTimeSlot().getDate().getMonthValue() == month
					&& lec.getTimeSlot().getDate().getYear() == year) {

				Label hourClass= new Label(lec.getTimeSlot().getTimeBegin() + " - " + lec.getTimeSlot().getTimeEnd() +" - "+ lec.getAcademicInfo().getCourseAbbreviation() + " - " + lec.getRoom());

				int dayMonth = lec.getTimeSlot().getDate().getDayOfMonth();

				monthItems.get(dayMonth).getItems().add(hourClass);
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



	//  ######################### APOIO GRAFICO #################################################### //



	//DESENHAR AS HORAS (REPRESENTAR AS LISTIVIEWS) NO CALENDARIO
	private void buildHours(int hourBegin, int hourEnd, GridPane paneCalendar, int day, Lecture lec) {

		Label hourClass= new Label(lec.getAcademicInfo().getCourseAbbreviation() + " - " + lec.getTimeSlot() + " - " +lec.getRoom().getName());

		//VER HORAS DE INICIO E FIM
		for(int row = hourBegin; row <= hourEnd; row++) {

			Node content =getNodeByRowColumnIndex(row, day*2, paneCalendar);

			if(content == null ) {
				ListView<Label> lecList = new ListView<Label>();
				lecList.setMaxWidth(paneCalendar.getColumnConstraints().get(day*2).getPrefWidth()/2);
				lecList.setPrefHeight(50);
				lecList.getItems().add(hourClass);
				paneCalendar.add(lecList, day*2, hourBegin,1, hourEnd-hourBegin);
				GridPane.setHalignment(lecList, HPos.RIGHT);

			}else {

				((ListView<Label>) content).getItems().add(hourClass);
			}
		}
	}

	private void clearGridContent(GridPane paneCalendar) {
		// clear the GridPane
		if(paneCalendar != null) {
			paneCalendar.getChildren().clear();
		}
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
				//				sep.setValignment(VPos.BOTTOM);
				paneCalendar.add(sep,j,i);

			}
		}
	}

	private void changeDatePicker(LocalDate date, String option) {

		switch(option) {

		case "Week":
			datePickerDay.setValue(date);
			datePickerMonth.setValue(date);
			break;

		case "Day":
			datePickerWeek.setValue(date);
			datePickerMonth.setValue(date);
			break;

		case "Month":
			datePickerDay.setValue(date);
			datePickerWeek.setValue(date);
			break;
		}
	}

	private void setLabelWeekDays(int days, GridPane paneCalendar, LocalDate startOfWeek) {

		for (int i = 2; i <= days; i+=2) {

			if(days == 2) {
				Label dayLabel = new Label(daysOfWeek[startOfWeek.getDayOfWeek().getValue()-1] + "\n" + startOfWeek.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
				dayLabel.setPrefSize(paneCalendar.getWidth()*0.75, 50);
				dayLabel.setWrapText(true);
				dayLabel.setTextAlignment(TextAlignment.CENTER);
				dayLabel.setAlignment(Pos.CENTER);
				paneCalendar.add(dayLabel, i, 0);
				break;

			}else {
				int dayDate = (i/2)-1;
				Label dayLabel = new Label(daysOfWeek[dayDate] + "\n" + startOfWeek.plusDays(dayDate).format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
				dayLabel.setPrefSize(paneCalendar.getWidth()*0.125, 75);
				dayLabel.setWrapText(true);
				dayLabel.setTextAlignment(TextAlignment.CENTER);
				dayLabel.setAlignment(Pos.CENTER);
				paneCalendar.add(dayLabel, i, 0);
			}
		}
	}


	private void setLabelButtonDay() {
		String strDay = datePickerDay.getValue().getMonth().name();
		dateLabelDay.setText(datePickerDay.getValue().getDayOfMonth() +" of " + 
				strDay.substring(0, 1).toUpperCase() + strDay.substring(1).toLowerCase() + " " + 
				datePickerDay.getValue().getYear());
	}

	private void setLabelButtonMonth() {
		String str = datePickerMonth.getValue().getMonth().name();
		dateLabelMonth.setText(str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase() 
				+ " of " + datePickerMonth.getValue().getYear());
	}


	private void setLabelButtonWeek() {
		DayOfWeek dayOfWeek = datePickerWeek.getValue().getDayOfWeek();
		LocalDate startOfWeek = datePickerWeek.getValue().minusDays(dayOfWeek.getValue() - 1);
		String str = datePickerMonth.getValue().getMonth().name();
		String str2 = startOfWeek.plusDays(6).getMonth().name();

		dateLabelWeek.setText("From " + startOfWeek.getDayOfMonth() + " of " + str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase() 
				+ " to " + startOfWeek.plusDays(6).getDayOfMonth() +" of " + str2.substring(0, 1).toUpperCase() + str2.substring(1).toLowerCase());
	}

	//  ######################### INITIALIZE #################################################### //


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		App.setStageSize(window.getPrefWidth(),window.getPrefHeight());

		datePickerDay.setValue(LocalDate.now());setLabelButtonDay();
		datePickerMonth.setValue(LocalDate.now());setLabelButtonMonth();
		datePickerWeek.setValue(LocalDate.now());setLabelButtonWeek();


	}
}