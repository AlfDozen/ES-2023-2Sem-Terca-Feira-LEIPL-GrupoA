package alfdozen.es_2023_2sem_terca_teira_leipl_grupoa;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * The ControllerWebCall class manages the GUI components and events in the
 * WebCall scene. The corresponding GUI is used to import a schedule from a
 * webcal URL, view the schedule, and save the schedule as a CSV or JSON file.
 *
 * @author alfdozen
 * @version 1.0.0
 */
public class ControllerWebCall implements Initializable {

	@FXML
	private Button importSchedule = new Button();
	@FXML
	private Button viewSchedule = new Button();
	@FXML
	private Button saveCSV = new Button();
	@FXML
	private Button backButton = new Button();
	@FXML
	private Button saveJSON = new Button();
	@FXML
	private TextField webcalURLTextField = new TextField();
	@FXML
	private AnchorPane window = new AnchorPane();

	/**
	 * Imports a schedule from the provided webcal URL and displays the schedule in
	 * the GUI. Called when the importSchedule button is clicked. If successful, it
	 * sets the visibility of viewSchedule, saveCSV, and saveJSON buttons to true.
	 * Shows an appropriate message dialog based on the success or failure of the
	 * operation.
	 */
	@FXML
	private void importScheduleFromWebcal() {
		String webcalURL = webcalURLTextField.getText();
		try {
			App.schedule = Schedule.loadWebcal(webcalURL);
			viewSchedule.setVisible(true);
			saveCSV.setVisible(true);
			saveJSON.setVisible(true);
			JOptionPane.showMessageDialog(null, App.SUCCESS_DESCRIPTION_MESSAGE, App.SUCCESS_MESSAGE,
					JOptionPane.INFORMATION_MESSAGE);
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(null, "URL inválido", App.ALERT_MESSAGE, JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Problema ao conectar à Internet ou ao ler o arquivo",
					App.ALERT_MESSAGE, JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Saves the imported schedule as a CSV file to the chosen location. Called when
	 * the saveCSV button is clicked. Opens a FileChooser dialog for the user to
	 * select the save location and file name. Shows an appropriate message dialog
	 * based on the success or failure of the operation.
	 */
	@FXML
	private void saveCSV() {
		FileChooser fileChooser = new FileChooser();
		File filePathToSave;
		String filenameToSave;
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("CSV", ".csv"));
		filePathToSave = fileChooser.showSaveDialog(new Stage());
		filenameToSave = filePathToSave.getAbsolutePath();

		try {
			Schedule.saveToCSV(App.schedule, filenameToSave);
			JOptionPane.showMessageDialog(null, "Ficheiro guardado com sucesso em CSV", App.SUCCESS_MESSAGE,
					JOptionPane.INFORMATION_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Erro ao gravar", App.ALERT_MESSAGE, JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Saves the imported schedule as a JSON file to the chosen location. Called
	 * when the saveJSON button is clicked. Opens a FileChooser dialog for the user
	 * to select the save location and file name. Shows an appropriate message
	 * dialog based on the success or failure of the operation.
	 */
	@FXML
	private void saveJSON() {
		FileChooser fileChooser = new FileChooser();
		String filenameToSave;
		File filePathToSave;
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("JSON", ".json"));
		filePathToSave = fileChooser.showSaveDialog(new Stage());
		filenameToSave = filePathToSave.getAbsolutePath();
		try {
			Schedule.saveToJSON(App.schedule, filenameToSave);
			JOptionPane.showMessageDialog(null, "Ficheiro guardado com sucesso em JSON", App.SUCCESS_MESSAGE,
					JOptionPane.INFORMATION_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Erro ao gravar", App.ALERT_MESSAGE, JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Displays the imported schedule in a new window. Called when the viewSchedule
	 * button is clicked. Changes the scene to the ViewSchedule scene.
	 * 
	 * @throws IOException If there's an error loading the ViewSchedule scene.
	 */
	@FXML
	private void viewSchedule() throws IOException {
		App.setRoot("/fxml/ViewSchedule");
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
			JOptionPane.showMessageDialog(null, "Erro ao tentar retornar à página inicial", App.ALERT_MESSAGE,
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Event handler for dealing with text. This method makes the uploadFile button
	 * visible.
	 */
	@FXML
	private void dealWithText() {
		importSchedule.setVisible(true);
	}

	/**
	 * Initializes the controller class. Called after the FXML file has been loaded.
	 * Sets the initial stage size based on the window's preferred width and height.
	 * 
	 * @param arg0 The location used to resolve relative paths for the root object,
	 *             or null if the location is not known.
	 * @param arg1 The resources used to localize the root object, or null if the
	 *             root object was not localized.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		App.setStageSize(window.getPrefWidth(), window.getPrefHeight());
	}
}
