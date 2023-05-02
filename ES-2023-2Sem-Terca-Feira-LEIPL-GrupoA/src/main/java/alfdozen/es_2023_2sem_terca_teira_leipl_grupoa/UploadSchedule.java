package alfdozen.es_2023_2sem_terca_teira_leipl_grupoa;


import java.io.File;
import java.io.IOException;
import java.net.URL;
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
	private Button saveFileCSV, saveFileJSON, backButton, getFile, viewSchedule, uploadFileCSV, uploadFileJSON;

	@FXML
	private Label fileChosen;
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

		fileChooser.setTitle("Open Resource File");
		filePath = fileChooser.showOpenDialog(new Stage());
		fileChosen.setText(filePath.getName());
		filename = filePath.getAbsolutePath();
		extension = filename.substring(filename.lastIndexOf(".")+1).toLowerCase();

		if(extension.equals("csv")) {
			uploadFileCSV.setVisible(true);

		}else if(extension.equals("json")){
			uploadFileJSON.setVisible(true);

		}else {
			JOptionPane.showMessageDialog(null, "O ficheiro importado tem extensão: "+ extension + "! Apenas são aceites extensões .json ou .csv", "Alerta" , JOptionPane.INFORMATION_MESSAGE);
		}
	}


	@FXML
	private void saveFileCSV() {

		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("CSV", ".csv"));
		filePathToSave = fileChooser.showSaveDialog(new Stage());

		try {
			if(filePathToSave != null) {
				filenameToSave = filePathToSave.getAbsolutePath();
			Schedule.saveToCSV(scheduleUploaded, filenameToSave);
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,"Deu cócó ao gravar", "Alerta" , JOptionPane.ERROR_MESSAGE);
		}
	}

	@FXML
	private void saveFileJSON() {

		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("JSON", ".json"));
		filePathToSave = fileChooser.showSaveDialog(new Stage());

		try {
			if(filePathToSave != null) {
				filenameToSave = filePathToSave.getAbsolutePath();
			Schedule.saveToJSON(scheduleUploaded, filenameToSave);
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,"Deu cócó ao gravar", "Alerta" , JOptionPane.ERROR_MESSAGE);
		}
	}


	@FXML
	private void uploadFileCSV() {

		try {
			scheduleUploaded = Schedule.loadCSV(filename);
			viewSchedule.setVisible(true);
			saveFileJSON.setVisible(true);

		}catch(Exception e1) {

			JOptionPane.showMessageDialog(null, "Erro ao importar ficheiro CSV", "Alerta" , JOptionPane.INFORMATION_MESSAGE);
		}

		App.setSchedule(scheduleUploaded);
	}


	@FXML
	private void uploadFileJSON() {

		try {
			scheduleUploaded = Schedule.loadJSON(filename);
			viewSchedule.setVisible(true);
			saveFileCSV.setVisible(true);
		}catch(Exception e1) {
			JOptionPane.showMessageDialog(null, "Erro ao importar ficheiro JSON", "Alerta" , JOptionPane.INFORMATION_MESSAGE);
		}
		App.setSchedule(scheduleUploaded);
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
