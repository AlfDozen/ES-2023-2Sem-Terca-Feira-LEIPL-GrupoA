package alfdozen.es_2023_2sem_terca_teira_leipl_grupoa;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
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
import com.calendarfx.model.Calendar.Style;
import com.calendarfx.view.DetailedWeekView;
import com.calendarfx.view.page.WeekPage;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

//PARA APAGAR: CLASSE DE PAGINA DE CRIAR HORARIO (A SEGUNDA PÁGINA, NÃO A DE IR BUSCAR O HORÁRIO PRIMEIRO)
public class CreateSchedule implements Initializable {

	@FXML
	private Button backButton, save, selectAll, clearAll;

	@FXML
	private TextField studentName, studentNumber;

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

	private LocalDate[] diazinhos = new LocalDate[7];

	CalendarSource iscte = new CalendarSource("Semana");
	Calendar<Lecture> dias = new Calendar<>("week");
	String[] semana = { "Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Sábado", "Domingo" };

	final static int NUM_DAYS = 7;

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
		// PARA GRAVAR
		fileChooserToSave = new FileChooser();
		fileChooserToSave.getExtensionFilters().addAll(new ExtensionFilter("CSV", ".CSV"));
		filePathToSave = fileChooserToSave.showSaveDialog(new Stage());

		try {
			if (filePathToSave != null) {
				filenameToSave = filePathToSave.getAbsolutePath();
				Schedule.saveToCSV(App.SCHEDULE, filenameToSave);
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Deu cócó ao gravar", "Alerta", JOptionPane.ERROR_MESSAGE);
		}
	}

	@FXML
	private void saveJSON() {
		// PARA GRAVAR
		fileChooserToSave = new FileChooser();
		fileChooserToSave.getExtensionFilters().addAll(new ExtensionFilter("JSON", ".json"));
		filePathToSave = fileChooserToSave.showSaveDialog(new Stage());

		try {
			if (filePathToSave != null) {
				filenameToSave = filePathToSave.getAbsolutePath();
				Schedule.saveToJSON(App.SCHEDULE, filenameToSave);
			}

		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Deu cócó ao gravar", "Alerta", JOptionPane.ERROR_MESSAGE);
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

	@FXML
	private void viewSchedule() {

		String[] buttons = { "Confirmar", "Cancelar" };
		int confirmation = JOptionPane.showOptionDialog(null,
				"De certeza que pretende continuar?",
				"Ir para consultar calendário", JOptionPane.WARNING_MESSAGE, JOptionPane.QUESTION_MESSAGE, null,
				buttons, buttons[0]);

		System.out.println(confirmation);

		if (confirmation == 0) {

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
		calendar.clearSelection();
		for (Calendar<Lecture> cal : iscte.getCalendars()) {
			cal.clear();
		}
	}

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
				int day = calendar.getStartDate().getDayOfWeek().getValue();

				Entry<Lecture> aulas = new Entry<>();

				String entryText = semana[classDay] + " - " + lec.getTimeSlot().getTimeBeginString() + "-"
						+ lec.getTimeSlot().getTimeEndString() + " - " + lec.getAcademicInfo().getCourse();

				aulas.setTitle(entryText);

				for (LocalDate weekDay : diazinhos) {
					if (weekDay.getDayOfWeek().getValue() == classDay + 1) {
						aulas.setInterval(weekDay, begin, weekDay, end);
						break;
					}
				}

				iscte.getCalendars().get(0).addEntry(aulas);
			}
		}
	}

	// ######################### MAIN
	// #################################################### //

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		App.setStageSize(window.getPrefWidth(), window.getPrefHeight());

		iscte.getCalendars().addAll(dias);
		calendar.getCalendarSources().setAll(iscte);

		for (int i = 0; i < NUM_DAYS; i++) {
			diazinhos[i] = calendar.getStartDate().plusDays(i);
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