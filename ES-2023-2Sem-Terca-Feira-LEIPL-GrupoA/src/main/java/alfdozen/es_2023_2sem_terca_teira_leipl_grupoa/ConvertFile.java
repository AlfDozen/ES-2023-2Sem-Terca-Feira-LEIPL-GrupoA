package alfdozen.es_2023_2sem_terca_teira_leipl_grupoa;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

// PARA APAGAR: CLASSE DE PAGINA DE CONVERTER DE CSV PARA JSON E VICE VERSA
public class ConvertFile implements Initializable{

	@FXML
	private Button convertFileButton, cancelButton, chooseFileButton;

	@FXML
	private TextField filePathTF;

	@FXML
	private AnchorPane window;

	@FXML
	private void chooseFile() {
		FileChooser  fileChooser  = new FileChooser();
		fileChooser.getExtensionFilters().add(new ExtensionFilter("JSON", "*.json"));
		fileChooser.getExtensionFilters().add(new ExtensionFilter("CSV", "*.csv"));
		File file = fileChooser.showOpenDialog(new Stage());
		String filePath = file.getAbsolutePath();
		//String fileExtension = Schedule.getFileExtension(filePath);
		String fileExtension = getFileExtension(filePath);
		if(fileExtension.isBlank()) {
			JOptionPane.showMessageDialog(null, "O ficheiro selecionado não tem a extensão " + Schedule.FILE_FORMAT_JSON + " ou " + Schedule.FILE_FORMAT_CSV, "Alerta" , JOptionPane.ERROR_MESSAGE);
			filePathTF.setText("Nenhum ficheiro selecionado");
			return;
		}
		filePathTF.setText(filePath);
		if(fileExtension.equals(Schedule.FILE_FORMAT_CSV)) { // SUBSTITUIR POR CONSTANTE DO SALVADOR FILE_FORMAT_CSV
			convertFileButton.setText("Converter CSV para JSON");
			convertFileButton.setVisible(true);
		} else if(fileExtension.equals(Schedule.FILE_FORMAT_JSON)) { // SUBSTITUIR POR CONTANTE DO SALVADOR FILE_FORMAT_JSON
			convertFileButton.setText("Converter JSON para CSV");
			convertFileButton.setVisible(true);
		} else {
			convertFileButton.setText("Converter");
			convertFileButton.setVisible(false);
		}
	}
	
	// APAGAR APOS RECEBER FUNCAO DO SALVADOR
	static String getFileExtension(String fileName) {
		int dotIndex = fileName.lastIndexOf(".");
		if (dotIndex > 0) {
			return fileName.substring(dotIndex);
		}
		return "";
	}

	@FXML
	private void convertFile() {
		FileChooser  fileChooser  = new FileChooser();
		String fileToConvertPath = filePathTF.getText();
		if(convertFileButton.getText().equals("Converter CSV para JSON")) {
			fileChooser.getExtensionFilters().add(new ExtensionFilter("JSON", "*.json"));
			File fileToSave = fileChooser.showSaveDialog(new Stage());
			String fileToSavePath = fileToSave.getAbsolutePath();
			try {
				Schedule.convertCSV2JSON(fileToConvertPath, fileToSavePath, Schedule.DELIMITER.charAt(0));
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Erro ao converter ficheiro para JSON", "Alerta" , JOptionPane.ERROR_MESSAGE);
				return;
			}
			JOptionPane.showMessageDialog(null, "Ficheiro convertido para JSON com sucesso", "Sucesso" , JOptionPane.INFORMATION_MESSAGE);
		} else {
			fileChooser.getExtensionFilters().add(new ExtensionFilter("CSV", "*.csv"));
			File fileToSave = fileChooser.showSaveDialog(new Stage());
			String fileToSavePath = fileToSave.getAbsolutePath();
			try {
				Schedule.convertJSON2CSV(fileToConvertPath, fileToSavePath, Schedule.DELIMITER.charAt(0));
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Erro ao converter ficheiro para CSV", "Alerta" , JOptionPane.ERROR_MESSAGE);
				return;
			}
			JOptionPane.showMessageDialog(null, "Ficheiro convertido para JSON com sucesso", "Sucesso" , JOptionPane.INFORMATION_MESSAGE);
			returnHome();
		}
	}

	@FXML
	private void returnHome() {
		try {
			App.setRoot("/fxml/Main");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//  ######################### MAIN #################################################### //
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		App.setStageSize(window.getPrefWidth(),window.getPrefHeight());
	}
}
