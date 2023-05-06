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
 * The ControllerConvertFile class manages the GUI components and events in the
 * ConvertFile scene. The corresponding GUI is used to select a csv file in the
 * local file system and convert it to a json file (or vice versa).
 * 
 * @author alfdozen
 * @version 1.0.0
 */
public class ControllerConvertFile implements Initializable {

	@FXML
	private Button convertFileButton;
	@FXML
	private Button cancelButton;
	@FXML
	private Button chooseFileButton;
	@FXML
	private TextField filePathTF;
	@FXML
	private AnchorPane window;

	/**
	 * This method is called by the event of clicking on the chooseFileButton. It
	 * opens a file chooser dialog so the user can choose the csv or json file that
	 * is mean to be converted.
	 */
	@FXML
	private void chooseFile() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new ExtensionFilter("JSON", "*.json"));
		fileChooser.getExtensionFilters().add(new ExtensionFilter("CSV", "*.csv"));
		File file = fileChooser.showOpenDialog(new Stage());
		if (file == null) {
			JOptionPane.showMessageDialog(null, "Não é possível ler o ficheiro selecionado", "Alerta",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		String filePath = file.getAbsolutePath();
		String fileExtension = Schedule.getFileExtension(filePath);
		if (fileExtension.isBlank()) {
			JOptionPane.showMessageDialog(null, "O ficheiro selecionado não tem a extensão " + Schedule.FILE_FORMAT_JSON
					+ " ou " + Schedule.FILE_FORMAT_CSV, "Alerta", JOptionPane.ERROR_MESSAGE);
			filePathTF.setText("Nenhum ficheiro selecionado");
			return;
		}
		filePathTF.setText(filePath);
		if (fileExtension.equals(Schedule.FILE_FORMAT_CSV)) {
			convertFileButton.setText("Converter CSV para JSON");
			convertFileButton.setVisible(true);
		} else if (fileExtension.equals(Schedule.FILE_FORMAT_JSON)) {
			convertFileButton.setText("Converter JSON para CSV");
			convertFileButton.setVisible(true);
		} else {
			convertFileButton.setText("Converter");
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
		String fileToConvertPath = filePathTF.getText();
		if ("Converter CSV para JSON".equals(convertFileButton.getText())) {
			fileChooser.getExtensionFilters().add(new ExtensionFilter("JSON", "*.json"));
			File fileToSave = fileChooser.showSaveDialog(new Stage());
			String fileToSavePath = fileToSave.getAbsolutePath();
			try {
				Schedule.convertCSV2JSON(fileToConvertPath, fileToSavePath, Schedule.DELIMITER.charAt(0));
			} catch (IOException | IllegalArgumentException e) {
				JOptionPane.showMessageDialog(null, "Erro ao converter ficheiro para JSON", "Alerta",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			JOptionPane.showMessageDialog(null, "Ficheiro convertido para JSON com sucesso", "Sucesso",
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			fileChooser.getExtensionFilters().add(new ExtensionFilter("CSV", "*.csv"));
			File fileToSave = fileChooser.showSaveDialog(new Stage());
			String fileToSavePath = fileToSave.getAbsolutePath();
			try {
				Schedule.convertJSON2CSV(fileToConvertPath, fileToSavePath, Schedule.DELIMITER.charAt(0));
			} catch (IOException | IllegalArgumentException e) {
				JOptionPane.showMessageDialog(null, "Erro ao converter ficheiro para CSV", "Alerta",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			JOptionPane.showMessageDialog(null, "Ficheiro convertido para JSON com sucesso", "Sucesso",
					JOptionPane.INFORMATION_MESSAGE);
			returnHome();
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
			JOptionPane.showMessageDialog(null, "Erro ao regressar ao menu principal", "Erro",
					JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
	}

	/**
	 * This method initializes the GUI components and stage needed for the
	 * ConvertFile scene.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		App.setStageSize(window.getPrefWidth(), window.getPrefHeight());
	}
}
