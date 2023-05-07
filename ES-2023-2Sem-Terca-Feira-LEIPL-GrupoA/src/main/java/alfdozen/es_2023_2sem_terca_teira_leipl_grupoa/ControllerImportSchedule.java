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

	@FXML
	private Button saveFileCSVButton = new Button();

	@FXML
	private Button saveFileJSONButton = new Button();

	@FXML
	private Button cancelButton = new Button();

	@FXML
	private Button chooseFileButton = new Button();

	@FXML
	private Button viewScheduleButton = new Button();

	@FXML
	private Button importFileButton = new Button();

	@FXML
	private RadioButton optionLocalRadioButton = new RadioButton();

	@FXML
	private RadioButton optionOnlineRadioButton = new RadioButton();

	@FXML
	private RadioButton optionCSVRadioButton = new RadioButton();

	@FXML
	private RadioButton optionJSONRadioButton = new RadioButton();

	@FXML
	private ToggleGroup fileTypeChooser = new ToggleGroup();

	@FXML
	private ToggleGroup extensionChooser = new ToggleGroup();

	@FXML
	private Label fileChosenPathLabel = new Label();

	@FXML
	private Label onlineInstructionLabel = new Label();

	@FXML
	private Label extensionInstructionLabel = new Label();

	@FXML
	private TextField inputOnlineTextField = new TextField();

	@FXML
	private AnchorPane window = new AnchorPane();

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
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("CSV, JSON", "*.csv", "*.json"));
		File filePath;
		filePath = fileChooser.showOpenDialog(new Stage());
		if (filePath == null) {
			JOptionPane.showMessageDialog(null, App.ERROR_SELECT_FILE_MESSAGE, App.ALERT_MESSAGE,
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
					App.ALERT_MESSAGE, JOptionPane.INFORMATION_MESSAGE);
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
		inputOnlineTextField.setText("");
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
			JOptionPane.showMessageDialog(null, App.ERROR_SELECT_FILE_MESSAGE, App.ALERT_MESSAGE,
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		String filenameToSave = filePathToSave.getAbsolutePath();
		try {
			Schedule.saveToCSV(App.schedule, filenameToSave);
			JOptionPane.showMessageDialog(null, "Ficheiro guardado com sucesso em " + filenameToSave,
					App.SUCCESS_MESSAGE, JOptionPane.INFORMATION_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Erro ao gravar", App.ALERT_MESSAGE, JOptionPane.ERROR_MESSAGE);
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
			JOptionPane.showMessageDialog(null, App.ERROR_SELECT_FILE_MESSAGE, App.ALERT_MESSAGE,
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		String filenameToSave = filePathToSave.getAbsolutePath();
		try {
			Schedule.saveToJSON(App.schedule, filenameToSave);
			JOptionPane.showMessageDialog(null, "Ficheiro guardado com sucesso em " + filenameToSave,
					App.SUCCESS_MESSAGE, JOptionPane.INFORMATION_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Erro ao gravar", App.ALERT_MESSAGE, JOptionPane.ERROR_MESSAGE);
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
			Schedule importedSchedule = null;
			if (optionOnlineRadioButton.isSelected()) {
				String fileExtension = "";
				if (optionCSVRadioButton.isSelected()) {
					fileExtension = ".csv";
				} else if (optionJSONRadioButton.isSelected()) {
					fileExtension = ".json";
				} else {
					JOptionPane.showMessageDialog(null, "Selecione o tipo de ficheiro do URL", App.ALERT_MESSAGE,
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				if (optionOnlineRadioButton.getText().isBlank()) {
					JOptionPane.showMessageDialog(null, "URL do ficheiro remoto inválido", App.ALERT_MESSAGE,
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				importedSchedule = Schedule.downloadFileFromURL(inputOnlineTextField.getText(), fileExtension);
				if (Schedule.lecturesInScheduleAllNull(importedSchedule)) {
					JOptionPane.showMessageDialog(null,
							"O horário importado está vazio. É provável que o URL não corresponda ao formato selecionado.",
							App.ALERT_MESSAGE, JOptionPane.INFORMATION_MESSAGE);
					viewScheduleButton.setVisible(false);
					saveFileJSONButton.setVisible(false);
					saveFileCSVButton.setVisible(false);
					return;
				} else {
					JOptionPane.showMessageDialog(null, App.SUCCESS_DESCRIPTION_MESSAGE, App.SUCCESS_MESSAGE,
							JOptionPane.INFORMATION_MESSAGE);
				}
				App.schedule = importedSchedule;

			} else {
				String extension = Schedule.getFileExtension(fileChosenPathLabel.getText());
				switch (extension) {
					case Schedule.FILE_FORMAT_CSV:
						importedSchedule = Schedule.loadCSV(fileChosenPathLabel.getText());
						break;
					case Schedule.FILE_FORMAT_JSON:
						importedSchedule = Schedule.loadJSON(fileChosenPathLabel.getText());
						break;
					default:
						throw new IllegalArgumentException("Invalid file extension");
				}
				App.schedule = importedSchedule;
				JOptionPane.showMessageDialog(null, App.SUCCESS_DESCRIPTION_MESSAGE, App.SUCCESS_MESSAGE,
						JOptionPane.INFORMATION_MESSAGE);
			}
			viewScheduleButton.setVisible(true);
			saveFileJSONButton.setVisible(true);
			saveFileCSVButton.setVisible(true);
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(null,
					"Erro ao importar ficheiro." + "Verifique que o formato corresponde ao selecionado",
					App.ALERT_MESSAGE, JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**
	 * Event handler for dealing with text. This method makes the uploadFile button
	 * visible.
	 */
	@FXML
	private void dealWithText() {
		if (optionCSVRadioButton.isSelected() || optionJSONRadioButton.isSelected()) {
			importFileButton.setVisible(true);
		}
	}

	/**
	 * Event handler for showing the import file button if the online text field is
	 * not empty. The import file button will become visible allowing the user to
	 * import a file.
	 */
	@FXML
	private void showImportButton() {
		if (!inputOnlineTextField.getText().isBlank()) {
			importFileButton.setVisible(true);
		}
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
			JOptionPane.showMessageDialog(null, "Erro ao regressar ao menu principal", App.ERROR_MESSAGE,
					JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
	}

	/**
	 * This method is called after its root element has been completely processed
	 * and initializes the GUI components and stage in the controller needed for the
	 * main scene.
	 *
	 * @param arg0 the location used to resolve relative paths for the root object,
	 *             or null if the location is not known.
	 * @param arg1 the resources used to localize the root object, or null if the
	 *             root object was not localized.  
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		App.setStageSize(window.getPrefWidth(), window.getPrefHeight());
	}
}
