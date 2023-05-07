package alfdozen.es_2023_2sem_terca_teira_leipl_grupoa;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 * The ControllerUploadSchedule class manages the GUI components and events in
 * the UploadSchedule scene. The corresponding GUI is used to select a csv or
 * json file in the local file system or remotely and save it to a json or csv
 * file. It is also used to view the uploaded schedule.
 * 
 * @author alfdozen
 * @version 1.0.0
 */
public class ControllerImportSchedule implements Initializable {
	
	private static final String ALERT_MESSAGE = "Alerta";
	private static final String ERROR_MESSAGE = "Erro";
	private static final String SUCCCESS_MESSAGE = "Sucesso";
	private static final String ERROR_SELECT_FILE_MESSAGE = "Por favor, selecione um ficheiro";
	
	@FXML
	private Button saveFileCSVButton;

	@FXML
	private Button saveFileJSONButton;

	@FXML
	private Button cancelButton;

	@FXML
	private Button chooseFileButton;

	@FXML
	private Button viewScheduleButton;

	@FXML
	private Button importFileButton;

	@FXML
	private RadioButton optionLocalRadioButton;

	@FXML
	private RadioButton optionOnlineRadioButton;
	
	@FXML
	private RadioButton optionCSVRadioButton;
	
	@FXML
	private RadioButton optionJSONRadioButton;

	@FXML
	private ToggleGroup fileTypeChooser;
	
	@FXML
	private ToggleGroup extensionChooser;

	@FXML
	private Label fileChosenPathLabel;

	@FXML
	private Label onlineInstructionLabel;
	
	@FXML
	private Label extensionInstructionLabel;

	@FXML
	private TextField inputOnlineTextField;

	@FXML
	private AnchorPane window;

	/**
	 * Event handler for viewing the schedule. Sets the root view to the
	 * "ViewSchedule" FXML file.
	 * 
	 * @throws IOException if there is an error setting the root view.
	 */
	@FXML
	private void viewSchedule() throws IOException {
		App.setRoot("/fxml/ViewSchedule");
	}

	/**
	 * This method is called by the event of clicking on the chooseFileButton. It
	 * opens a file chooser dialog so the user can choose the csv or json file that
	 * is mean to be converted.
	 */
	@FXML
	private void chooseFile() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new ExtensionFilter("CSV","*.csv"));
		fileChooser.getExtensionFilters().add(new ExtensionFilter("JSON","*.json"));
		File filePath;
		filePath = fileChooser.showOpenDialog(new Stage());
		if (filePath == null) {
			JOptionPane.showMessageDialog(null, ERROR_SELECT_FILE_MESSAGE, ALERT_MESSAGE,
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		fileChosenPathLabel.setText(filePath.getAbsolutePath());
		String extension = Schedule.getFileExtension(filePath.getName());
		if (extension.equals(Schedule.FILE_FORMAT_CSV) || extension.equals(Schedule.FILE_FORMAT_JSON)) {
			importFileButton.setVisible(true);
			fileChosenPathLabel.setVisible(true);
		} else {
			JOptionPane.showMessageDialog(null,
					"O ficheiro importado tem extensão: " + extension + "! Apenas são aceites extensões .json ou .csv",
					ALERT_MESSAGE, JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**
	 * Sets the UI components to represent the local file import mode. Selects the
	 * optionLocalRadioButton. Makes the fileChosenPathLabel, chooseFileButton
	 * visible. Hides the onlineInstructionLabel, inputOnlineTextField,
	 * viewScheduleButton, uploadFileButton, saveFileCSVButton, and
	 * saveFileJSONButton.
	 */
	@FXML
	private void setLocalButtons() {
		optionLocalRadioButton.setSelected(true);
		fileChosenPathLabel.setVisible(true);
		chooseFileButton.setVisible(true);
		onlineInstructionLabel.setVisible(false);
		inputOnlineTextField.setVisible(false);
		viewScheduleButton.setVisible(false);
		importFileButton.setVisible(false);
		saveFileCSVButton.setVisible(false);
		saveFileJSONButton.setVisible(false);
		optionCSVRadioButton.setVisible(false);
		optionJSONRadioButton.setVisible(false);
		extensionInstructionLabel.setVisible(false);
	}

	/**
	 * Sets the UI components to represent the remote file import mode. Selects the
	 * optionOnlineRadioButton. Makes the fileChosenPathLabel, chooseFileButton
	 * hidden. Makes the inputOnlineTextField, onlineInstructionLabel visible. Hides
	 * the viewScheduleButton, uploadFileButton, saveFileCSVButton, and
	 * saveFileJSONButton.
	 */
	@FXML
	private void setRemoteButtons() {
		optionOnlineRadioButton.setSelected(true);
		fileChosenPathLabel.setVisible(false);
		chooseFileButton.setVisible(false);
		inputOnlineTextField.setVisible(true);
		onlineInstructionLabel.setVisible(true);
		viewScheduleButton.setVisible(false);
		importFileButton.setVisible(false);
		saveFileCSVButton.setVisible(false);
		saveFileJSONButton.setVisible(false);
		extensionInstructionLabel.setVisible(true);
		optionCSVRadioButton.setVisible(true);
		optionJSONRadioButton.setVisible(true);
	}

	/**
	 * Event handler for saving the schedule to a CSV file. Shows a file chooser
	 * dialog to select the save location. Saves the schedule to the selected file
	 * in CSV format. Displays a success message with the file path if the save is
	 * successful. Displays an error message if there is an exception while saving.
	 */
	@FXML
	private void saveFileCSV() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("CSV", ".csv"));
		File filePathToSave = fileChooser.showSaveDialog(new Stage());
		if (filePathToSave == null) {
			JOptionPane.showMessageDialog(null, ERROR_SELECT_FILE_MESSAGE, ALERT_MESSAGE,
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		String filenameToSave = filePathToSave.getAbsolutePath();
		try {
			Schedule.saveToCSV(App.SCHEDULE, filenameToSave);
			JOptionPane.showMessageDialog(null, "Ficheiro guardado com sucesso em " + filenameToSave, SUCCCESS_MESSAGE,
					JOptionPane.INFORMATION_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Erro ao gravar", ALERT_MESSAGE, JOptionPane.ERROR_MESSAGE);
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
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("JSON", ".json"));
		File filePathToSave = fileChooser.showSaveDialog(new Stage());
		if (filePathToSave == null) {
			JOptionPane.showMessageDialog(null, ERROR_SELECT_FILE_MESSAGE, ALERT_MESSAGE,
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		String filenameToSave = filePathToSave.getAbsolutePath();
		try {
			Schedule.saveToJSON(App.SCHEDULE, filenameToSave);
			JOptionPane.showMessageDialog(null, "Ficheiro guardado com sucesso em " + filenameToSave, SUCCCESS_MESSAGE,
					JOptionPane.INFORMATION_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Erro ao gravar", ALERT_MESSAGE, JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Event handler for importing a file. If the optionOnlineRadioButton is
	 * selected, it checks for a valid remote file URL with the .csv or .json
	 * extension. If the optionOnlineRadioButton is not selected, it loads the file
	 * specified by the filename. Displays success or error messages using
	 * JOptionPane dialogs. Makes the viewScheduleButton, saveFileJSONButton, and
	 * saveFileCSVButton visible on successful import.
	 */
	@FXML
	private void importFile() {
		try {
			if (optionOnlineRadioButton.isSelected()) {
				String extension = Schedule.getFileExtension(inputOnlineTextField.getText());
				if (optionOnlineRadioButton.getText().isBlank() || (!extension.equals(Schedule.FILE_FORMAT_CSV)
						&& !extension.equals(Schedule.FILE_FORMAT_JSON))) {
					JOptionPane.showMessageDialog(null, "URL do ficheiro remoto inválido", ALERT_MESSAGE,
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				if(optionCSVRadioButton.isSelected())
					App.SCHEDULE  = Schedule.downloadFileFromURL(inputOnlineTextField.getText(),Schedule.FILE_FORMAT_CSV);
				else if(optionJSONRadioButton.isSelected())
					App.SCHEDULE  = Schedule.downloadFileFromURL(inputOnlineTextField.getText(),Schedule.FILE_FORMAT_JSON);
			} else {
				App.SCHEDULE = Schedule.downloadFileFromURL(fileChosenPathLabel.getText(), Schedule.getFileExtension(fileChosenPathLabel.getText()));
			}
			JOptionPane.showMessageDialog(null, "Ficheiro importado com sucesso", SUCCCESS_MESSAGE,
					JOptionPane.INFORMATION_MESSAGE);
			viewScheduleButton.setVisible(true);
			saveFileJSONButton.setVisible(true);
			saveFileCSVButton.setVisible(true);
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null, "Erro ao importar ficheiro", ALERT_MESSAGE,
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**
	 * Event handler for dealing with text. This method makes the uploadFile button
	 * visible.
	 */
	@FXML
	private void dealWithText() {
		if(optionCSVRadioButton.isSelected() || optionJSONRadioButton.isSelected())
			importFileButton.setVisible(true);
	}
	
	/**
	 * Event handler for showing the import file button if the online text field is not empty.
	 * The import file button will become visible allowing the user to import a file.
	 */
	@FXML
	private void showImportButton() {
		if(!inputOnlineTextField.getText().isBlank())
			importFileButton.setVisible(true);
	}

	/**
	 * This method is called by the event of clicking on the cancelButton. It
	 * returns to the main scene.
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
	 * This method is called after its root element has been completely processed
	 * and initializes the GUI components and stage in the controller needed for the
	 * main scene.
	 *
	 * @param arg0  the location used to resolve relative paths for the root
	 *                  object, or null if the location is not known.
	 * @param arg1 the resources used to localize the root object, or null if
	 *                  the root object was not localized.  
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		App.setStageSize(window.getPrefWidth(), window.getPrefHeight());
	}
}
