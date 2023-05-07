package alfdozen.es_2023_2sem_terca_teira_leipl_grupoa;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
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

public class ControllerLoadFileBeforeCreateSchedule implements Initializable {

	@FXML
	private Button backButton;
	@FXML
	private Button createScheduleButton;
	@FXML
	private Button uploadFileButton;
	@FXML
	private Button getFileButton;
	@FXML
	private RadioButton optionLocal;
	@FXML
	private RadioButton optionOnline;
	@FXML
	private RadioButton optionFenix;
	@FXML
	private RadioButton optionPrevious;
	@FXML
	private RadioButton optionCSVRadioButton;
	@FXML
	private RadioButton optionJSONRadioButton;
	@FXML
	private ToggleGroup file_chooser;
	@FXML
	private ToggleGroup extensionChooser;
	@FXML
	private Label fileChosen;
	@FXML
	private Label labelOnline;
	@FXML
	private Label labelFenix;
	@FXML
	private Label labelPreviousLoad;
	@FXML
	private Label labelPreviousNotLoad;
	@FXML
	private Label extensionInstructionLabel;
	@FXML
	private TextField urlChosen;
	@FXML
	private TextField webcalChosen;
	@FXML
	private AnchorPane window;

	/**
	 * Define the windows when the radiobutton "Ficheiro local" is selected.
	 */
	@FXML
	private void setLocal() {
		optionLocal.setSelected(true);
		fileChosen.setText("Nenhum ficheiro importado");
		fileChosen.setVisible(true);
		getFileButton.setVisible(true);

		labelOnline.setVisible(false);
		urlChosen.setVisible(false);
		labelFenix.setVisible(false);
		webcalChosen.setVisible(false);
		labelPreviousLoad.setVisible(false);
		labelPreviousNotLoad.setVisible(false);

		uploadFileButton.setVisible(false);
		createScheduleButton.setVisible(false);
		extensionInstructionLabel.setVisible(false);
		optionCSVRadioButton.setVisible(false);
		optionJSONRadioButton.setVisible(false);
	}

	/**
	 * This method define the windows when the radiobutton "Ficheiro online" is
	 * selected.
	 */
	@FXML
	private void setOnline() {
		optionOnline.setSelected(true);
		labelOnline.setVisible(true);
		urlChosen.setText("");
		urlChosen.setVisible(true);

		fileChosen.setVisible(false);
		getFileButton.setVisible(false);
		labelFenix.setVisible(false);
		webcalChosen.setVisible(false);
		labelPreviousLoad.setVisible(false);
		labelPreviousNotLoad.setVisible(false);

		uploadFileButton.setVisible(false);
		createScheduleButton.setVisible(false);
		extensionInstructionLabel.setVisible(true);
		optionCSVRadioButton.setVisible(true);
		optionJSONRadioButton.setVisible(true);
	}

	/**
	 * This method define the windows when the radiobutton "Ficheiro do Fénix" is
	 * selected.
	 */
	@FXML
	private void setFenix() {
		optionFenix.setSelected(true);
		labelFenix.setVisible(true);
		webcalChosen.setVisible(true);
		webcalChosen.setText("");

		fileChosen.setVisible(false);
		getFileButton.setVisible(false);
		labelOnline.setVisible(false);
		urlChosen.setVisible(false);
		labelPreviousLoad.setVisible(false);
		labelPreviousNotLoad.setVisible(false);

		uploadFileButton.setVisible(false);
		createScheduleButton.setVisible(false);
		extensionInstructionLabel.setVisible(false);
		optionCSVRadioButton.setVisible(false);
		optionJSONRadioButton.setVisible(false);
	}

	/**
	 * This method define the window when the radiobutton "Ficheiro carregado
	 * previamente" is selected.
	 */
	@FXML
	private void setPrevious() {
		optionPrevious.setSelected(true);

		fileChosen.setVisible(false);
		getFileButton.setVisible(false);
		labelOnline.setVisible(false);
		urlChosen.setVisible(false);
		labelFenix.setVisible(false);
		webcalChosen.setVisible(false);

		uploadFileButton.setVisible(false);
		extensionInstructionLabel.setVisible(false);
		optionCSVRadioButton.setVisible(false);
		optionJSONRadioButton.setVisible(false);

		if (Schedule.scheduleIsEmpty(App.SCHEDULE)) {
			labelPreviousLoad.setVisible(false);
			labelPreviousNotLoad.setVisible(true);
			createScheduleButton.setVisible(false);
		} else {
			labelPreviousLoad.setVisible(true);
			labelPreviousNotLoad.setVisible(false);
			createScheduleButton.setVisible(true);
		}
	}

	/**
	 * This method is called by the event of clicking on the getFileButton. It opens
	 * a file chooser dialog so the user can choose the csv or json file that is
	 * mean to be imported. After selecting the file, it is possible to import the
	 * file chosen.
	 */
	@FXML
	private void getFile() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("CSV, JSON", "*.csv", "*.json"));
		File file = fileChooser.showOpenDialog(new Stage());
		if (file == null) {
			JOptionPane.showMessageDialog(null, App.ERROR_SELECT_FILE_MESSAGE, App.ALERT_MESSAGE,
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		fileChosen.setText(file.getName());
		String filePath = file.getAbsolutePath();
		String fileExtension = Schedule.getFileExtension(filePath);
		if (".csv".equals(fileExtension) || ".json".equals(fileExtension)) {
			fileChosen.setText(filePath);
			uploadFileButton.setVisible(true);
		} else {
			JOptionPane.showMessageDialog(null,
					"O ficheiro importado tem extensão: " + fileExtension
							+ "! Apenas são aceites extensões .json ou .csv",
					App.ALERT_MESSAGE, JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**
	 * This method is called when something is typed in the input textfield existing
	 * in the remote and webcal options. After typing something, it is possible to
	 * import the file chosen.
	 */
	@FXML
	private void dealWithText() {
		uploadFileButton.setVisible(true);
	}

//	/**
//	 * Event handler for showing the import file button if the online text field is
//	 * not empty. The import file button will become visible allowing the user to
//	 * import a file.
//	 */
//	@FXML
//	private void showImportButton() {
//		if (urlChosen.getText().isBlank())
//			uploadFileButton.setVisible(false);
//	}

	/**
	 * This method is called by the event of clicking on the uploadFileButton. It
	 * imports the file that has been previously chosen according to the local,
	 * remote or webcal option. After successfully import the file, it is possible
	 * go to the create schedule scene.
	 */
	@FXML
	private void uploadFile() {
		Schedule scheduleUploaded = null;
		try {
			if (optionLocal.isSelected()) {
				String extension = Schedule.getFileExtension(fileChosen.getText());
				switch (extension) {
				case Schedule.FILE_FORMAT_CSV:
					scheduleUploaded = Schedule.loadCSV(fileChosen.getText());
					break;
				case Schedule.FILE_FORMAT_JSON:
					scheduleUploaded = Schedule.loadJSON(fileChosen.getText());
					break;
				default:
					throw new IllegalArgumentException("Invalid file extension");
				}
				JOptionPane.showMessageDialog(null, App.SUCCESS_DESCRIPTION_MESSAGE, App.ALERT_MESSAGE,
						JOptionPane.INFORMATION_MESSAGE);
				createScheduleButton.setVisible(true);
				
			} else if (optionOnline.isSelected()) {
				String filePath = urlChosen.getText();
				String fileExtension = "";
				if(optionCSVRadioButton.isSelected()) {
					fileExtension = ".csv";
				} else if(optionJSONRadioButton.isSelected()) {
					fileExtension = ".json";
				} else {
					JOptionPane.showMessageDialog(null, "Selecione o tipo de ficheiro do URL", App.ALERT_MESSAGE,
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				scheduleUploaded = Schedule.downloadFileFromURL(filePath, fileExtension);
				if(Schedule.lecturesInScheduleAllNull(scheduleUploaded)) {
					JOptionPane.showMessageDialog(null, "O horário importado está vazio. É provável que o URL não corresponda ao formato selecionado.", App.ALERT_MESSAGE,
							JOptionPane.INFORMATION_MESSAGE);
					createScheduleButton.setVisible(false);
					return;
				} else {
					JOptionPane.showMessageDialog(null, App.SUCCESS_DESCRIPTION_MESSAGE, App.SUCCESS_MESSAGE,
							JOptionPane.INFORMATION_MESSAGE);
				}
				createScheduleButton.setVisible(true);				
				
			} else {
				String filePath = webcalChosen.getText();
				if (filePath.isBlank()) {
					JOptionPane.showMessageDialog(null, "Link do calendário pessoal inválido", App.ALERT_MESSAGE,
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				scheduleUploaded = Schedule.loadWebcal(filePath);
				JOptionPane.showMessageDialog(null, App.SUCCESS_DESCRIPTION_MESSAGE, App.SUCCESS_MESSAGE,
						JOptionPane.INFORMATION_MESSAGE);
				createScheduleButton.setVisible(true);
			}
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null, "Erro ao importar ficheiro", App.ALERT_MESSAGE,
					JOptionPane.INFORMATION_MESSAGE);
		}

		App.SCHEDULE = scheduleUploaded;
	}

	/**
	 * This method is called by the event of clicking on the createScheduleButton.
	 * It returns to the create schedule scene.
	 */
	@FXML
	private void createSchedule() throws IOException {
		App.setRoot("/fxml/CreateSchedule");
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
			JOptionPane.showMessageDialog(null, "Erro ao regressar ao menu principal", "Erro",
					JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
	}

	/**
	 * This method is called after its root element has been completely processed
	 * and initializes the GUI components and stage in the controller needed for the
	 * ConvertFile scene.
	 *
	 * @param arg0 the location used to resolve relative paths for the root object,
	 *             or null if the location is not known.
	 * @param arg1 the resources used to localize the root object, or null if the
	 *             root object was not localized.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		App.setStageSize(window.getPrefWidth(), window.getPrefHeight());
	}	
}
