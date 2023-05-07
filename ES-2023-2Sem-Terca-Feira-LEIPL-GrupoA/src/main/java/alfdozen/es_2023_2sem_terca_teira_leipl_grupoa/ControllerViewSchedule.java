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
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * The ViewSchedule class is the controller for the ViewSchedule.fxml file. It
 * manages the GUI components and events in the ViewSchedule scene. The
 * corresponding GUI is used to display the current schedule as a calendar view,
 * save the schedule as a CSV or JSON file, return to the Main menu, create a new
 * schedule or view any schedule conflicts.
 * 
 * @author alfdozen
 * @version 1.0.0
 */
public class ControllerViewSchedule implements Initializable {

	public static final int NUM_COLUMNS = 7;
	public static final int SLEEP_TIME = 10000;
	public static final int WIDTH = 700;
	public static final int HEIGHT = 750;
	static final String ALERT_MESSAGE = "Alerta";
	static final String ERROR_MESSAGE = "Erro";
	static final String POP_UP_MESSAGE = "Erro ao guardar o ficheiro";

	@FXML
	private AnchorPane window = new AnchorPane();
	@FXML
	private CalendarView calendarView = new CalendarView();

	/**
	 * This method is called by the event of clicking on the cancelButton. It
	 * returns to the main scene.
	 */
	@FXML
	private void returnHome() {
		try {
			App.setRoot("/fxml/Main");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Erro ao voltar para o menu principal", ERROR_MESSAGE,
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Event handler for saving the schedule to a CSV file. Shows a file chooser
	 * dialog to select the save location. Saves the schedule to the selected file
	 * in CSV format. Displays a success message with the file path if the save is
	 * successful. Displays an error message if there is an exception while saving.
	 */
	@FXML
	private void saveFileCSV() {
		FileChooser fileChooserToSave = new FileChooser();
		fileChooserToSave.getExtensionFilters().addAll(new ExtensionFilter("CSV", "*.csv"));
		File filePathToSave = fileChooserToSave.showSaveDialog(new Stage());
		try {
			if (filePathToSave != null) {
				String filenameToSave = filePathToSave.getAbsolutePath();
				Schedule.saveToCSV(App.schedule, filenameToSave);
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, POP_UP_MESSAGE, ALERT_MESSAGE, JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Event handler for saving the schedule to a JSON file. Shows a file chooser
	 * dialog to select the save location. Saves the schedule to the selected file
	 * in JSON format. Displays a success message with the file path if the save is
	 * successful. Displays an error message if there is an exception while saving.
	 */
	@FXML
	private void saveFileJSON() {
		FileChooser fileChooserToSave = new FileChooser();
		fileChooserToSave.getExtensionFilters().addAll(new ExtensionFilter("JSON", "*.json"));
		File filePathToSave = fileChooserToSave.showSaveDialog(new Stage());
		try {
			if (filePathToSave != null) {
				String filenameToSave = filePathToSave.getAbsolutePath();
				Schedule.saveToJSON(App.schedule, filenameToSave);
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, POP_UP_MESSAGE, ALERT_MESSAGE, JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Loads the "CreateSchedule" scene when the "Create Schedule" button is
	 * pressed.
	 */
	@FXML
	private void createSchedule() {
		try {
			App.setRoot("/fxml/CreateSchedule");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Erro ao abrir o menu de criar horário", ERROR_MESSAGE,
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Calls showLectureConflicts from App to show a new stage with a list of all
	 * the lecture conflicts in the schedule. The conflicts are displayed using the
	 * "Overlayed.fxml" file.
	 */
	@FXML
	private void conflicts() {
		App.showLectureConflicts();
	}

	/**
	 * Populates the calendar view with the entries of the lectures in the current
	 * schedule. Creates a new calendar source, adds the lecture entries to it and
	 * sets it as the calendar source for the calendar view. Also starts a thread to
	 * update the current time on the calendar every 10 seconds.
	 */
	private void setLecturesEntries() {
		try {
			Calendar<Lecture> iscte = new Calendar<>("ISCTE");
			iscte.setStyle(Style.STYLE1);
			for (Lecture lec : App.schedule.getLectures()) {
				if (lec.getTimeSlot().getDate() != null) {
					Entry<Lecture> aulas = new Entry<>();
					aulas.setTitle(lec.toString());
					LocalDate classDay = lec.getTimeSlot().getDate();
					LocalTime begin = lec.getTimeSlot().getTimeBegin();
					LocalTime end = lec.getTimeSlot().getTimeEnd();
					aulas.setInterval(classDay, begin, classDay, end);
					iscte.addEntry(aulas);
				}
			}
			CalendarSource iscteCalendarSource = new CalendarSource("ISCTE");
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
							sleep(SLEEP_TIME);
						} catch (InterruptedException e) {
							interrupt();
						}
					}
				}
			};

			updateTimeThread.setPriority(Thread.MIN_PRIORITY);
			updateTimeThread.setDaemon(true);
			updateTimeThread.start();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao definir e mostrar horário", ERROR_MESSAGE,
					JOptionPane.ERROR_MESSAGE);
		}

	}

	/**
	 * Initializes the controller class. This method is automatically called after
	 * the FXML file has been loaded. It sets the stage size and title, enables
	 * timezone support, and hides the source view. It also adds a listener to the
	 * calendar sources, which hides the "Show Calendars" button if it exists.
	 * Finally, it calls the setLecturesEntries method to populate the calendar view
	 * with lectures.
	 * 
	 * @param arg0 the URL to the FXML file
	 * @param arg1 the resource bundle associated with the FXML file
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		App.setStageSize(calendarView.getPrefWidth(), calendarView.getPrefHeight());
		calendarView.setEnableTimeZoneSupport(true);
		calendarView.setShowAddCalendarButton(false);
		calendarView.getSourceView().setVisible(false);
		calendarView.getCalendarSources().addListener((ListChangeListener<CalendarSource>) change -> {
			ToggleButton toggleButton = (ToggleButton) calendarView.lookup(".calendar-show-calendars-button");
			if (toggleButton != null) {
				toggleButton.setVisible(false);
			}
		});
		setLecturesEntries();
	}
}
