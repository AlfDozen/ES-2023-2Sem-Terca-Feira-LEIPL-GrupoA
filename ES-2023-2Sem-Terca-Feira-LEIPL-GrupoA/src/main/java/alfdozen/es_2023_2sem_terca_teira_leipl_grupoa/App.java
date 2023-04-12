package alfdozen.es_2023_2sem_terca_teira_leipl_grupoa;


import javafx.application.Application;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
//import javafx.scene.web.WebEngine;
//import javafx.scene.web.WebView;


public class App extends Application implements Initializable{


	private String[] daysOfWeek = {"Segunda-feira", "Terça-feira", "Quarta-feira", "Quinta-feira", "Sexta-feira", "Sábado", "Domingo"};
	
	@FXML 
	private RadioButton monthbutton; 
	@FXML
	private RadioButton daybutton;
	@FXML
	private RadioButton weekbutton;
	
//	@FXML
//	private WebView webView = new WebView();
	
	@FXML
	private GridPane paneCalendar;

	@FXML
	private DatePicker datePicker;
	
    private static Scene scene;

	@Override
	public void start(Stage primaryStage) {
		try {

//			FXMLLoader loader = new FXMLLoader(Main.class.getResource("Controller.fxml"));
			scene = new Scene(loadFXML("Controller"), 1000, 680);
//			Parent root = loader.load();
//			Scene scene = new Scene(root);

			//			daybutton.getScene().setRoot(mainViewRoot);

//			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch(Exception e) {
			System.err.println("Erro ao tentar correr o conteudo do controller");
		}
	}

	   static void setRoot(String fxml) throws IOException {
	        scene.setRoot(loadFXML(fxml));
	    }
	   
	   private static Parent loadFXML(String fxml) throws IOException {
	        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
	        return fxmlLoader.load();
	    }

	@FXML
	private void showButtons() {
		// clear the GridPane
		daybutton.setVisible(true);
		weekbutton.setVisible(true);
		monthbutton.setVisible(true);
	}

	@FXML
	private void clearGridContent() {
		// clear the GridPane
		paneCalendar.getChildren().clear();
		paneCalendar.getRowConstraints().clear();
		paneCalendar.getColumnConstraints().clear();
	}

	@FXML
	private void defineGridLayout() {
		paneCalendar.setHgap(50);
		paneCalendar.setVgap(50);

		paneCalendar.prefWidthProperty().bind(monthbutton.getScene().widthProperty());
		paneCalendar.prefHeightProperty().bind(monthbutton.getScene().heightProperty());
	
	}
	
	@FXML
	private void allMonthLayout() {
		paneCalendar.setHgap(80);
		paneCalendar.setVgap(80);

		paneCalendar.prefWidthProperty().bind(monthbutton.getScene().widthProperty());
		paneCalendar.prefHeightProperty().bind(monthbutton.getScene().heightProperty());
	
	}


	@FXML
	private void setDefaultHours() {

		int startingHour = 8;
		int row = 1;

		for(int i = startingHour; i < 24 ; i++) {

			Label dayLabel = new Label(i+":00-"+(i+1)+":00");
			paneCalendar.add(dayLabel, 0,row);
			row++;
		}
	}

	//   ######################### MOSTRAR CALENDARIO #################################################### //

	@FXML
	public void showCalendar() {
		
	//  ######################### MÊS #################################################### //
		
		if(monthbutton.isSelected()) {
			
		clearGridContent();

		for (int i = 0; i < daysOfWeek.length; i++) {

			Label dayLabel = new Label(daysOfWeek[i]);

			paneCalendar.add(dayLabel, i, 0);
		}

		// Add date labels
		int numRows = 6; // number of rows in the calendar grid
		int numCols = 7; // number of columns in the calendar grid
		int dayOfMonth = 1; // starting day of the month
		
		LocalDate selectDate = datePicker.getValue();
		LocalDate firstDay = selectDate.minusDays(selectDate.getDayOfMonth() - 1);
		
		DayOfWeek dayOfWeek = firstDay.getDayOfWeek();
		
		for (int col = dayOfWeek.getValue()-1; col < numCols; col++) {
			Label dateLabel = new Label(Integer.toString(dayOfMonth));
			paneCalendar.add(dateLabel, col, 1);
			dayOfMonth++;
		}
			
			
		for (int row = 2; row <= numRows; row++) {			
			for (int col = 0; col < numCols; col++) {
				if (dayOfMonth <= getNumDaysInMonth()) {
					Label dateLabel = new Label(Integer.toString(dayOfMonth));
					paneCalendar.add(dateLabel, col, row);
					dayOfMonth++;
				}
			}
		}

		allMonthLayout();
	}
		
	//  ######################### SEMANA #################################################### //

		if(weekbutton.isSelected()) {
		
		clearGridContent();
		setDefaultHours();

		LocalDate selectedDate = datePicker.getValue();
		DayOfWeek dayOfWeek = selectedDate.getDayOfWeek();
		LocalDate startOfWeek = selectedDate.minusDays(dayOfWeek.getValue() - 1);

		datePicker.getValue().plusDays(1);

		for (int i = 0; i < daysOfWeek.length; i++) {

			Label dayLabel = new Label(daysOfWeek[i] + "\n" + startOfWeek.plusDays(i).format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
			dayLabel.setMinHeight(50);
			dayLabel.setMinHeight(50);

			paneCalendar.add(dayLabel, i+1, 0);
		}

		defineGridLayout();
		}

	//  ######################### DIA #################################################### //

		if(daybutton.isSelected()) {
		
		clearGridContent();
		setDefaultHours();

		LocalDate selectedDate = datePicker.getValue();
		DayOfWeek dayOfWeek = selectedDate.getDayOfWeek();

		Label dayLabel = new Label(daysOfWeek[dayOfWeek.getValue()-1] + "\n" + selectedDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
		dayLabel.setMinHeight(50);
		dayLabel.setMinHeight(50);

		paneCalendar.add(dayLabel, 1, 0);

		defineGridLayout();
		}
	}

	
	// Returns the number of days in the current month
		private int getNumDaysInMonth() {
			// Replace with your own logic to determine the number of days in the current month
			int numberOfDays = 0;

			switch(datePicker.getValue().getMonthValue()) {

			case 1 : 
				numberOfDays = 31; break;
			case 2 : 
				numberOfDays = 28; break;
			case 3 : 
				numberOfDays = 31; break;
			case 4 : 
				numberOfDays = 30; break;
			case 5 : 
				numberOfDays = 31; break;
			case 6 : 
				numberOfDays = 30; break;
			case 7 : 
				numberOfDays = 31; break;
			case 8 : 
				numberOfDays = 31; break;
			case 9 : 
				numberOfDays = 30; break;
			case 10 : 
				numberOfDays = 31; break;
			case 11 : 
				numberOfDays = 30; break;
			case 12 : 
				numberOfDays = 31; break;
			}
			return numberOfDays;
		}
	
	
	//  ######################### MAIN #################################################### //

	public static void main(String[] args) {
		launch(args);
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		datePicker.setValue(LocalDate.now());
		showButtons();

	}
}
