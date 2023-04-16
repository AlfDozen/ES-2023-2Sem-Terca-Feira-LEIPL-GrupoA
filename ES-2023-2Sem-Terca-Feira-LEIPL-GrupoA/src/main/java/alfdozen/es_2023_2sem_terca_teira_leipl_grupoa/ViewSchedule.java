package alfdozen.es_2023_2sem_terca_teira_leipl_grupoa;


import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;


public class ViewSchedule implements Initializable{


	private String[] daysOfWeek = {"Segunda-feira", "Terça-feira", "Quarta-feira", "Quinta-feira", "Sexta-feira", "Sábado", "Domingo"};

	private List<String> hours = new ArrayList();

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
		paneCalendar.setHgap(100);
		paneCalendar.setVgap(100);

		//		paneCalendar.prefWidthProperty().bind(monthTab.App.scene.widthProperty());
		//		paneCalendar.prefHeightProperty().bind(monthtab.getScene().heightProperty());

	}

	private void allMonthLayout(GridPane paneCalendar) {
		paneCalendar.setHgap(80);
		paneCalendar.setVgap(80);

		//		paneCalendar.prefWidthProperty().bind(monthtab.getScene().widthProperty());
		//		paneCalendar.prefHeightProperty().bind(monthtab.getScene().heightProperty());

	}


	private void setDefaultHours(GridPane paneCalendar) {

		int startingHour = 8;
		int row = 1;
		int previous = 8;

		for(double i = startingHour+0.5; i < 23.5 ; i+=0.5) {

			if((int) i == previous) {

				if((int) i < 10) {
					Label dayLabel = new Label("0"+(int)i +":00-0"+ (int)i +":30");
					paneCalendar.add(dayLabel, 0,row);
					hours.add("0"+(int)i +":00");

				}else {

					Label dayLabel = new Label((int)i +":00-"+ (int)i +":30");
					paneCalendar.add(dayLabel, 0,row);
					hours.add((int)i +":00");
				}

			}else {

				if((int) i < 10) {

					Label dayLabel = new Label("0"+previous +":30-0"+ (int)i +":00");
					paneCalendar.add(dayLabel, 0,row);
					previous++;
					hours.add("0"+previous +":30");

				}else {

					if(previous < 10) {

						Label dayLabel = new Label("0"+previous +":30-"+ (int)i +":00");
						paneCalendar.add(dayLabel, 0,row);
						previous++;
						hours.add("0"+previous +":30");

					}else {

						Label dayLabel = new Label(previous +":30-"+ (int)i +":00");
						paneCalendar.add(dayLabel, 0,row);
						previous++;
						hours.add(previous +":30");
					}
				}
			}
			row++;
		}
	}

	@FXML
	private void returnHome() {
		try {
			App.setRoot("/fxml/Main");
		} catch (IOException e) {
			System.err.println("Erro ao tentar retornar");
		}

	}

	//   ######################### MOSTRAR CALENDARIO #################################################### //


	//  ######################### MÊS #################################################### //

	@FXML
	public void showCalendarMonth() {

		if(datePickerMonth.getValue() != null) {

			clearGridContent(paneCalendarMonth);

			for (int i = 0; i < daysOfWeek.length; i++) {

				Label dayLabel = new Label(daysOfWeek[i]);

				paneCalendarMonth.add(dayLabel, i, 0);
			}

			// Add date labels
			int numRows = 6; // number of rows in the calendar grid
			int numCols = 7; // number of columns in the calendar grid
			int dayOfMonth = 1; // starting day of the month

			LocalDate selectDate = datePickerMonth.getValue();
			LocalDate firstDay = selectDate.minusDays(selectDate.getDayOfMonth() - 1);

			DayOfWeek dayOfWeek = firstDay.getDayOfWeek();


			//cabeçalho apresentar o dia
			for (int col = dayOfWeek.getValue()-1; col < numCols; col++) {
				Label dateLabel = new Label(Integer.toString(dayOfMonth));
				paneCalendarMonth.add(dateLabel, col, 1);
				dayOfMonth++;
			}


			for (int row = 2; row <= numRows; row++) {			
				for (int col = 0; col < numCols; col++) {
					if (dayOfMonth <= getNumDaysInMonth()) {
						Label dateLabel = new Label(Integer.toString(dayOfMonth));
						paneCalendarMonth.add(dateLabel, col, row);
						dayOfMonth++;
					}
				}
			}

			allMonthLayout(paneCalendarMonth);
		}
	}

	//  ######################### SEMANA #################################################### //

	@FXML
	public void showCalendarWeek() {

		if(datePickerWeek.getValue() != null) {

			clearGridContent(paneCalendarWeek);
			setDefaultHours(paneCalendarWeek);

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

				if(lec.getTimeSlot().getDateString().equals(Schedule.FOR_NULL)) {
					continue;
				}

				if(lec.getTimeSlot().getDate().compareTo(startOfWeek.plusDays(7)) > 0 ) {
					System.out.println(lec.getTimeSlot().getDateString());
					break;
				}


				if(lec.getTimeSlot().getDate().compareTo(startOfWeek) >= 0 ) {

					int hourBegin = hours.indexOf(lec.getTimeSlot().getTimeBeginString().substring(0,5));
					int hourEnd = hours.indexOf(lec.getTimeSlot().getTimeEndString().substring(0,5));

					System.out.println(lec.getTimeSlot().getTimeBeginString().substring(0,5)+"-"+lec.getTimeSlot().getTimeEndString().substring(0,5));

					int day = lec.getTimeSlot().getDate().compareTo(startOfWeek)+1;

					System.out.println("row: " + hourBegin + " collumn: " + day);
					System.out.println("row: " + hourEnd + " collumn: " + day);



					for(int row = hourBegin; row <= hourEnd; row++) {

						if(lec.getRoom().getName() == null ) {
						
//							TextArea hourClass = new TextArea(lec.getAcademicInfo().getCourseAbbreviation() + "- sem sala" ); 
							Label hourClass= new Label(lec.getAcademicInfo().getCourseAbbreviation() + "- sem sala" );
							hourClass.setWrapText(true);
							hourClass.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, new Insets(3))));
							hourClass.setTextFill(Color.color(0.0, 0.0, 0.0));
							hourClass.setMaxWidth(50);
							hourClass.setMaxHeight(50);
							paneCalendarWeek.add(hourClass, day, row);
							
						}else {
							
//							TextArea hourClass = new TextArea(lec.getAcademicInfo().getCourseAbbreviation() + "-" + lec.getRoom().getName()); 
							Label hourClass= new Label(lec.getAcademicInfo().getCourseAbbreviation() + "-" + lec.getRoom().getName());
							hourClass.setWrapText(true);
							hourClass.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, new Insets(3))));
							hourClass.setTextFill(Color.color(0.0, 0.0, 0.0));
							hourClass.setMinWidth(50);
							hourClass.setMinHeight(50);
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
	public void showCalendarDay() {

		if(datePickerDay.getValue() != null) {

			clearGridContent(paneCalendarDay);
			setDefaultHours(paneCalendarDay);

			LocalDate selectedDate = datePickerDay.getValue();
			DayOfWeek dayOfWeek = selectedDate.getDayOfWeek();

			Label dayLabel = new Label(daysOfWeek[dayOfWeek.getValue()-1] + "\n" + selectedDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
			dayLabel.setMinHeight(50);
			dayLabel.setMinHeight(50);

			paneCalendarDay.add(dayLabel, 1, 0);

			defineGridLayout(paneCalendarDay);
		}
	}

	public boolean validateEntry(int value, int option) {

		return value == option;
	}

	// Returns the number of days in the current month
	private int getNumDaysInMonth() {
		// Replace with your own logic to determine the number of days in the current month
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
