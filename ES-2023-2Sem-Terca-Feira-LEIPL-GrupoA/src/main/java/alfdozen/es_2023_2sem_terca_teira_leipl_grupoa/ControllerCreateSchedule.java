/**
 * ControllerCreateSchedule is a controller class for the Create Schedule page. 
 * It allows users to select courses and create their schedule.
 * Users can save their schedule as a CSV or JSON file.
 * @author alfdozen
 * @version 1.0.0
 */
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
import javafx.scene.Scene;
import javafx.scene.Parent;
import javax.swing.JOptionPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import java.util.HashSet;
import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.view.DetailedWeekView;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class ControllerCreateSchedule implements Initializable {
	
	private static final String ALERT_MESSAGE = "Alerta";
	private static final String ERROR_SAVING = "Erro ao gravar";
	private static final String ERROR_MESSAGE = "Erro";
	public static final int WIDTH = 700;
	public static final int HEIGHT = 750;

	@FXML
	private Button backButton;
	@FXML
	private Button save;
	@FXML
	private Button selectAll;
	@FXML
	private Button clearAll;
	@FXML
	private TextField studentName;
	@FXML
	private TextField studentNumber;
	@FXML
	private DetailedWeekView calendar;
	@FXML
	private ListView<String> lectures;
	@FXML
	private AnchorPane window;

	private FileChooser fileChooserToSave;
	private File filePathToSave;
	private String filenameToSave;
	List<String> selectedItems = new ArrayList<>();

	private LocalDate[] daysDates = new LocalDate[7];

	CalendarSource iscte = new CalendarSource("Semana");
	Calendar<Lecture> week = new Calendar<>("week");
	String[] semana = { "Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Sábado", "Domingo" };

	/**
	 * Creates a schedule based on the selected courses.
	 * Adds each course to the calendar view and sets the course color.
	 * Handles conflicts between overlapping courses.
	 */
	@FXML
	private void createSchedule() {
		List<Lecture> lecList = new ArrayList<>();

		for (Lecture lec : App.SCHEDULE.getLectures()) {
			if (lectures.getSelectionModel().getSelectedItems().contains(lec.getAcademicInfo().getCourse())) {
				lecList.add(lec);
			}
		}
		if (studentNumber.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "O número de aluno não pode estar vazio", App.ERROR_TITLE_DIALOG,
					JOptionPane.ERROR_MESSAGE);
			return;
		} else if (studentName.getText() == null) {
			App.setSchedule(new Schedule(lecList, null, Integer.valueOf(studentNumber.getText())));
		} else {
			App.setSchedule(new Schedule(lecList, studentName.getText(), Integer.valueOf(studentNumber.getText())));
		}
		viewSchedule();
	}

	/**
	 * Clears all the selected courses and the created schedule.
	 * Removes all courses from the ListView and calendar view.
	 */
	@FXML
	private void clearAll() {
		lectures.getSelectionModel().clearSelection();
		clearSchedule();
	}

	/**
	 * Selects all courses in the ListView.
	 * Adds all available courses to the selected courses list.
	 */
	@FXML
	private void selectAll() {
		lectures.getSelectionModel().selectAll();
		lectures.setStyle("-fx-selection-bar: #0000ff");
		setSelectedFields();
	}

	/**
	 * Returns the user to the main menu. Called when the backButton is clicked.
	 * Changes the scene to the Main scene. Shows an appropriate message dialog if
	 * there's an error returning to the main menu.
	 */
	@FXML
	private void returnHome() {
		try {
			App.setRoot("/fxml/Main");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Erro ao regressar ao menu principal", ERROR_MESSAGE,
					JOptionPane.ERROR_MESSAGE);
			System.exit(1);
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
		fileChooserToSave = new FileChooser();
		fileChooserToSave.getExtensionFilters().addAll(new ExtensionFilter("CSV", ".CSV"));
		filePathToSave = fileChooserToSave.showSaveDialog(new Stage());
		try {
			if (filePathToSave != null) {
				filenameToSave = filePathToSave.getAbsolutePath();
				Schedule.saveToCSV(App.SCHEDULE, filenameToSave);
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, ERROR_SAVING, ALERT_MESSAGE, JOptionPane.ERROR_MESSAGE);
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
		fileChooserToSave = new FileChooser();
		fileChooserToSave.getExtensionFilters().addAll(new ExtensionFilter("JSON", ".json"));
		filePathToSave = fileChooserToSave.showSaveDialog(new Stage());
		try {
			if (filePathToSave != null) {
				filenameToSave = filePathToSave.getAbsolutePath();
				Schedule.saveToJSON(App.SCHEDULE, filenameToSave);
			}

		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, ERROR_SAVING, ALERT_MESSAGE, JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	* Shows a new stage with a list of all the lecture conflicts in the schedule.
	* The conflicts are displayed using the "Overlayed.fxml" file.
	* If there is an IO exception while loading the FXML file, no action is taken.
	*/
	@FXML
	private void conflicts() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/fxml/Overlayed.fxml"));
			Parent root = fxmlLoader.load();
			Scene scene = new Scene(root, WIDTH, HEIGHT);

			Stage newStage = new Stage();

			newStage.setScene(scene);
			newStage.setTitle("ListView Window");
			newStage.setResizable(false);
			newStage.centerOnScreen();
			newStage.show();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Erro ao abrir o menu de mostrar conflitos do horário", ERROR_MESSAGE,
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Event handler for viewing the schedule. Sets the root view to the
	 * "ViewSchedule" FXML file.
	 */
	@FXML
	private void viewSchedule() {
		String[] buttons = { "Confirmar", "Cancelar" };
		int confirmation = JOptionPane.showOptionDialog(null,
				"De certeza que pretende continuar?",
				"Ir para consultar calendário", JOptionPane.WARNING_MESSAGE, JOptionPane.QUESTION_MESSAGE, null,
				buttons, buttons[0]);

		if (confirmation == 0) {
			try {
				App.setRoot("/fxml/ViewSchedule");
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Erro ao mostrar calendário", ERROR_MESSAGE,
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
     * Sets the selected fields in the ListView and updates the calendar view accordingly.
     * Clears the previous selection and adds the newly selected items to the list.
     * Updates the calendar view to display the new schedule.
     */
	@FXML
	private void setSelectedFields() {
		selectedItems.clear();
		selectedItems.addAll(lectures.getSelectionModel().getSelectedItems());
		showSchedule();
	}

	/**
     * Clears the current schedule from the calendar view.
     * Removes all selected items and entries from the calendar.
     */
	private void clearSchedule() {
		selectedItems.clear();
		calendar.clearSelection();
		for (Calendar<Lecture> cal : iscte.getCalendars()) {
			cal.clear();
		}
	}

	/**
     * Displays the selected schedule on the calendar view.
     * Clears the previous calendar entries and adds the new schedule entries.
     * Each entry contains information about the lecture, including day, time, and course name.
     */
	private void showSchedule() {
		calendar.clearSelection();
		for (Calendar<Lecture> cal : iscte.getCalendars()) {
			cal.clear();
		}
		List<Lecture> listi = App.SCHEDULE.getCommonWeekLecture(lectures.getSelectionModel().getSelectedItems());
		if (!listi.isEmpty()) {
			for (Lecture lec : listi) {
				int classDay = Integer.parseInt(lec.getTimeSlot().getWeekDay()) - 1;

				LocalTime begin = lec.getTimeSlot().getTimeBegin();
				LocalTime end = lec.getTimeSlot().getTimeEnd();

				Entry<Lecture> aulas = new Entry<>();

				String entryText = semana[classDay] + " - " + lec.getTimeSlot().getTimeBeginString() + "-"
						+ lec.getTimeSlot().getTimeEndString() + " - " + lec.getAcademicInfo().getCourse();

				aulas.setTitle(entryText);

				for (LocalDate weekDay : daysDates) {
					if (weekDay.getDayOfWeek().getValue() == classDay + 1) {
						aulas.setInterval(weekDay, begin, weekDay, end);
						break;
					}
				}

				iscte.getCalendars().get(0).addEntry(aulas);
			}
		}
	}

	/**
	* Initializes the controller class.
	* This method is automatically called after the FXML file has been loaded.
	* It sets the stage size and title, enables timezone support, and hides the source view.
	* It also adds a listener to the calendar sources, which hides the "Show Calendars" button if it exists.
	* Finally, it calls the setLecturesEntries method to populate the calendar view with lectures.
	* 
	* @param arg0 the URL to the FXML file
	* @param arg1 the resource bundle associated with the FXML file
	*/
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		App.setStageSize(window.getPrefWidth(), window.getPrefHeight());

		iscte.getCalendars().addAll(week);
		calendar.getCalendarSources().setAll(iscte);

		for (int i = 0; i < semana.length; i++) {
			daysDates[i] = calendar.getStartDate().plusDays(i);
		}

		List<Lecture> lecturesList = App.SCHEDULE.getLectures();
		Set<String> courses = new HashSet<>();

		for (Lecture lec : lecturesList) {
			courses.add(lec.getAcademicInfo().getCourse());
		}

		for (String s : courses) {
			lectures.getItems().add(s);
		}

		lectures.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		lectures.setStyle("-fx-selection-bar: #0033ff");
	}
}