package alfdozen.es_2023_2sem_terca_teira_leipl_grupoa;


import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;


public class ViewSchedule implements Initializable{


	private String[] daysOfWeek = {"Segunda-feira", "Terça-feira", "Quarta-feira", "Quinta-feira", "Sexta-feira", "Sábado", "Domingo"};

	@FXML 
	private Tab monthTab;
	@FXML 
	private Tab dayTab;
	@FXML 
	private Tab weekTab;

	@FXML
	private GridPane paneCalendarDay;
	@FXML
	private GridPane paneCalendarWeek;
	@FXML
	private GridPane paneCalendarMonth;

	@FXML
	private DatePicker datePickerDay;
	@FXML
	private DatePicker datePickerWeek;
	@FXML
	private DatePicker datePickerMonth;

	@FXML
	private Button backButtonDay;
	@FXML
	private Button backButtonWeek;
	@FXML
	private Button backButtonMonth;


	@FXML
	private void clearGridContent(GridPane paneCalendar) {
		// clear the GridPane
		paneCalendar.getChildren().clear();
		paneCalendar.getRowConstraints().clear();
		paneCalendar.getColumnConstraints().clear();
	}

	@FXML
	private void defineGridLayout(GridPane paneCalendar) {
		paneCalendar.setHgap(50);
		paneCalendar.setVgap(50);

		//		paneCalendar.prefWidthProperty().bind(monthtab.getScene().widthProperty());
		//		paneCalendar.prefHeightProperty().bind(monthtab.getScene().heightProperty());

	}

	@FXML
	private void allMonthLayout(GridPane paneCalendar) {
		paneCalendar.setHgap(80);
		paneCalendar.setVgap(80);

		//		paneCalendar.prefWidthProperty().bind(monthtab.getScene().widthProperty());
		//		paneCalendar.prefHeightProperty().bind(monthtab.getScene().heightProperty());

	}


	@FXML
	private void setDefaultHours(GridPane paneCalendar) {

		int startingHour = 8;
		int row = 1;

		for(int i = startingHour; i < 24 ; i++) {

			Label dayLabel = new Label(i+":00-"+(i+1)+":00");
			paneCalendar.add(dayLabel, 0,row);
			row++;
		}
	}

	@FXML
	private void returnHome() {
		try {
			MainScreen.setRoot("/fxml/Main");
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

			datePickerWeek.getValue().plusDays(1);

			for (int i = 0; i < daysOfWeek.length; i++) {

				Label dayLabel = new Label(daysOfWeek[i] + "\n" + startOfWeek.plusDays(i).format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
				dayLabel.setMinHeight(50);
				dayLabel.setMinHeight(50);

				paneCalendarWeek.add(dayLabel, i+1, 0);
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

		System.out.println("Set Pickers");
		datePickerDay.setValue(LocalDate.now());
		datePickerMonth.setValue(LocalDate.now());
		datePickerWeek.setValue(LocalDate.now());

	}
}
