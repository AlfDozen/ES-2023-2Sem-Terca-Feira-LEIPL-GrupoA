package alfdozen.es_2023_2sem_terca_teira_leipl_grupoa;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import com.calendarfx.model.Entry;
import com.calendarfx.view.CalendarView;
import com.calendarfx.model.Calendar;
import com.calendarfx.model.Calendar.Style;
import com.calendarfx.model.CalendarSource;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;


public class ViewSchedule implements Initializable{

	private String[] daysOfWeek = {"Segunda-feira", "Terça-feira", "Quarta-feira", "Quinta-feira", "Sexta-feira", "Sábado", "Domingo"};

	public static final int NUM_COLUMNS = 7;

	private FileChooser fileChooserToSave;
	private File filePathToSave;
	private String filenameToSave;

	@FXML
	private AnchorPane window;

	@FXML
	private CalendarView calendarView;



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
	private void createSchedule() {
		try {
			App.setRoot("/fxml/CreateSchedule");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void conflicts() {
		try {

			FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/fxml/Overlayed.fxml"));
			Parent root = fxmlLoader.load();
			Scene scene = new Scene(root, 700, 750);

			Stage newStage = new Stage();

			newStage.setScene(scene);
			newStage.setTitle("ListView Window");
			newStage.setResizable(false);
			newStage.centerOnScreen();
			newStage.show();
			

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//  ######################### APOIO CALCULO #################################################### //


	private void setLecturesEntries() {

		try {

			Calendar<Lecture> iscte = new Calendar<>("ISCTE");
			iscte.setStyle(Style.STYLE1);

			for(Lecture lec : App.getSchedule().getLectures()) {

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

			CalendarSource iscteCalendarSource = new CalendarSource("ISCTE");//
			iscteCalendarSource.getCalendars().addAll(iscte);

			calendarView.getCalendarSources().setAll(iscteCalendarSource);

			calendarView.setRequestedTime(LocalTime.now());

			Thread updateTimeThread = new Thread("Calendar: Update Time Thread") {
				@Override
				public void run() {
					while (true) {
						Platform.runLater(() -> {
							calendarView.setToday(LocalDate.now());
							calendarView.setTime(LocalTime.now());
						});

						try {
							// update every 10 seconds
							sleep(10000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

					}
				}
			};

			updateTimeThread.setPriority(Thread.MIN_PRIORITY);
			updateTimeThread.setDaemon(true);
			updateTimeThread.start();

		} catch (Exception e) {
			System.out.println("caçei excepção");
		}

	}


	//  ######################### INITIALIZE #################################################### //


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		App.setStageSize(calendarView.getPrefWidth(),calendarView.getPrefHeight());
		App.getStage().setTitle("Calendar");
		App.getStage().centerOnScreen();
		calendarView.setEnableTimeZoneSupport(true);

		calendarView.setShowAddCalendarButton(false);
		calendarView.getSourceView().setVisible(false);
		
		 calendarView.getCalendarSources().addListener((ListChangeListener<CalendarSource>) change -> {
		        // Get a reference to the ToggleButton and hide it
		        ToggleButton toggleButton = (ToggleButton) calendarView.lookup(".calendar-show-calendars-button");
		        if (toggleButton != null) {
		            toggleButton.setVisible(false);
		        }
		    });
		
		setLecturesEntries();

	}
}