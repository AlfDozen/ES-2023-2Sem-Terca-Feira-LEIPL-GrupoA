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
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * The ControllerConvertFile class manages the GUI components and events in the
 * ConvertFile scene. The corresponding GUI is used to select a csv file in the
 * local file system and convert it to a json file (or vice versa).
 * 
 * @author alfdozen
 * @version 1.0.0
 */
public class ControllerConvertFile implements Initializable {

	@FXML
	private Button convertFileButton = new Button();
	@FXML
	private Button cancelButton = new Button();
	@FXML
	private Button chooseFileButton = new Button();
	@FXML
	private Label fileChosenPathLabel = new Label();
	@FXML
	private AnchorPane window = new AnchorPane();

	/**
	 * This method is called by the event of clicking on the chooseFileButton. It
	 * opens a file chooser dialog so the user can choose the csv or json file that
	 * is mean to be converted.
	 */
	@FXML
	private void chooseFile() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("CSV, JSON", "*.csv", "*.json"));
		File file = fileChooser.showOpenDialog(new Stage());
		if (file == null) {
			JOptionPane.showMessageDialog(null, "Não é possível ler o ficheiro selecionado", App.ALERT_MESSAGE,
					JOptionPane.ERROR_MESSAGE);
			fileChosenPathLabel.setText("Nenhum ficheiro selecionado");
			convertFileButton.setVisible(false);
			return;
		}
		String filePath = file.getAbsolutePath();
		String fileExtension = Schedule.getFileExtension(filePath);
		if (fileExtension.isBlank()) {
			JOptionPane.showMessageDialog(null, "O ficheiro selecionado não tem a extensão " + Schedule.FILE_FORMAT_JSON
					+ " ou " + Schedule.FILE_FORMAT_CSV, App.ALERT_MESSAGE, JOptionPane.ERROR_MESSAGE);
			fileChosenPathLabel.setText("Nenhum ficheiro selecionado");
			convertFileButton.setVisible(false);
			return;
		}
		fileChosenPathLabel.setText(filePath);
		if (fileExtension.equals(Schedule.FILE_FORMAT_CSV)) {
			convertFileButton.setText("Converter CSV para JSON");
			convertFileButton.setVisible(true);
		} else if (fileExtension.equals(Schedule.FILE_FORMAT_JSON)) {
			convertFileButton.setText("Converter JSON para CSV");
			convertFileButton.setVisible(true);
		} else {
			convertFileButton.setVisible(false);
		}
	}

	/**
	 * This method is called by the event of clicking on the convertFileButton. It
	 * converts the file that has been previously chosen and whose path is on the
	 * filePathTF text. After successfully converting the file, it calls the
	 * returnHome method to go back to the main scene.
	 */
	@FXML
	private void convertFile() {
		FileChooser fileChooser = new FileChooser();
		String fileToConvertPath = fileChosenPathLabel.getText();
		if ("Converter CSV para JSON".equals(convertFileButton.getText())) {
			fileChooser.getExtensionFilters().add(new ExtensionFilter("JSON", "*.json"));
			File fileToSave = fileChooser.showSaveDialog(new Stage());
			String fileToSavePath = fileToSave.getAbsolutePath();
			try {
				Schedule.convertCSV2JSON(fileToConvertPath, fileToSavePath, Schedule.DELIMITER.charAt(0));
			} catch (IOException | IllegalArgumentException e) {
				JOptionPane.showMessageDialog(null, "Erro ao converter ficheiro para JSON", App.ALERT_MESSAGE,
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			JOptionPane.showMessageDialog(null, "Ficheiro convertido para JSON com sucesso", App.SUCCESS_MESSAGE,
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			fileChooser.getExtensionFilters().add(new ExtensionFilter("CSV", "*.csv"));
			File fileToSave = fileChooser.showSaveDialog(new Stage());
			String fileToSavePath = fileToSave.getAbsolutePath();
			try {
				Schedule.convertJSON2CSV(fileToConvertPath, fileToSavePath, Schedule.DELIMITER.charAt(0));
			} catch (IOException | IllegalArgumentException e) {
				JOptionPane.showMessageDialog(null, "Erro ao converter ficheiro para CSV", App.ALERT_MESSAGE,
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			JOptionPane.showMessageDialog(null, "Ficheiro convertido para CSV com sucesso", App.SUCCESS_MESSAGE,
					JOptionPane.INFORMATION_MESSAGE);
		}
		returnHome();
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
