package alfdozen.es_2023_2sem_terca_teira_leipl_grupoa;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.KeyStore.PrivateKeyEntry;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

//PARA APAGAR: CLASSE DE PAGINA DE IR BUSCAR HORÁRIO AO CSV, JSON OU FICHEIRO NA NET
public class UploadSchedule implements Initializable{


	@FXML
	private Button saveFileCSV, saveFileJSON, backButton, getFile, viewSchedule, uploadFile;
	private RadioButton optionLocal = new RadioButton(), optionOnline = new RadioButton();

	@FXML
	private Label fileChosen, labelOnline;
	@FXML
	private TextField inputOnline;
	
	@FXML
	private FileChooser fileChooser  = new FileChooser();
	private File filePath, filePathToSave;
	private String extension, filename, filenameToSave;
	private Schedule scheduleUploaded = null;

	@FXML
	private AnchorPane window; 

	@FXML
	private void viewSchedule() throws IOException {
		App.setRoot("/fxml/ViewSchedule");
	}

	@FXML
	private void getFile() {

		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("CSV Files", "*.csv", "JSON Files", "*.json"));
		fileChooser.setTitle("Open Resource File");
		filePath = fileChooser.showOpenDialog(new Stage());
		if (filePath == null) {
			JOptionPane.showMessageDialog(null, "Por favor, selecione um ficheiro.", "Alerta" , JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		fileChosen.setText(filePath.getName());
		filename = filePath.getAbsolutePath();
		extension = filename.substring(filename.lastIndexOf(".")+1).toLowerCase();
	
		
		if(extension.equals("csv") || extension.equals("json")) {
			uploadFile.setVisible(true);
			fileChosen.setVisible(true);
		}else {
			JOptionPane.showMessageDialog(null, "O ficheiro importado tem extensão: "+ extension + "! Apenas são aceites extensões .json ou .csv", "Alerta" , JOptionPane.INFORMATION_MESSAGE);
		}
	}

	@FXML
	private void setLocal() {
		optionLocal.setSelected(true);
		fileChosen.setVisible(true);
		getFile.setVisible(true);
		labelOnline.setVisible(false);
		inputOnline.setVisible(false);
		
		viewSchedule.setVisible(false);
		uploadFile.setVisible(false);
		saveFileCSV.setVisible(false);
		saveFileJSON.setVisible(false);
		
	}
	
	@FXML
	private void setRemote() {
		optionOnline.setSelected(true);
		fileChosen.setVisible(false);
		getFile.setVisible(false);
		inputOnline.setVisible(true);
		labelOnline.setVisible(true);
		
		viewSchedule.setVisible(false);
		uploadFile.setVisible(false);
		saveFileCSV.setVisible(false);
		saveFileJSON.setVisible(false);
	}

	@FXML
	private void saveFileCSV() {

		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("CSV", ".csv"));
		filePathToSave = fileChooser.showSaveDialog(new Stage());
		filenameToSave = filePathToSave.getAbsolutePath();

		try {
			Schedule.saveToCSV(scheduleUploaded, filenameToSave);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,"Erro ao gravar", "Alerta" , JOptionPane.ERROR_MESSAGE);
		}
	}

	@FXML
	private void saveFileJSON() {

		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("JSON", ".json"));
		filePathToSave = fileChooser.showSaveDialog(new Stage());
		filenameToSave = filePathToSave.getAbsolutePath();

		try {
			Schedule.saveToJSON(scheduleUploaded, filenameToSave);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,"Erro ao gravar", "Alerta" , JOptionPane.ERROR_MESSAGE);
		}
	}


	@FXML
	private void uploadFile() {
		try {
			if (optionOnline.isSelected()) {
				if (inputOnline.getText().isBlank() || (!Schedule.getFileExtension(inputOnline.getText()).equals(".csv") && !Schedule.getFileExtension(inputOnline.getText()).equals(".json"))) {
					JOptionPane.showMessageDialog(null, "URL do ficheiro remoto inválido", "Alerta" , JOptionPane.INFORMATION_MESSAGE);
					System.out.println(inputOnline.getText());
					System.out.println(Schedule.getFileExtension(inputOnline.getText()));
					return;
				}
				String tmpUrl= Schedule.downloadFileFromURL(inputOnline.getText());
				scheduleUploaded = Schedule.callLoad(tmpUrl);
			}else if(optionLocal.isSelected()){
				scheduleUploaded = Schedule.callLoad(filename);
			}
			viewSchedule.setVisible(true);
			saveFileJSON.setVisible(true);
			saveFileCSV.setVisible(true);
		}catch(Exception e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Erro ao importar ficheiro", "Alerta" , JOptionPane.INFORMATION_MESSAGE);
		}

		App.SCHEDULE = scheduleUploaded;
	}

	@FXML
	private void dealWithText() {
		uploadFile.setVisible(true);
	}


	@FXML
	private void returnHome() {
		try {
			App.setRoot("/fxml/Main");
		} catch (IOException e) {
			System.err.println("Erro ao tentar retornar");
		}
	}

	//  ######################### MAIN #################################################### //


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		App.setStageSize(window.getPrefWidth(),window.getPrefHeight());
	}
}
