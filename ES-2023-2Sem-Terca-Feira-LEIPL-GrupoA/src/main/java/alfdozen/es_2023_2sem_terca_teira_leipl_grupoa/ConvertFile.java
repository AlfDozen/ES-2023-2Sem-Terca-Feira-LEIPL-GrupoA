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


public class ConvertFile implements Initializable{


	@FXML
	private Button convertJSON2CSV, convertCSV2JSON, backButton, getFileJSON, getFileCSV ;

	@FXML
	private TextField delimiter;

	@FXML
	private Label label;

	@FXML
	private AnchorPane window;

	@FXML
	private Label fileChosen;

	private FileChooser fileChooser  = new FileChooser();
	private FileChooser fileChooserToSave  = new FileChooser();
	private File filePath, filePathToSave;
	private String filename, filenameToSave;

	@FXML
	private void getFileJSON() {

		//PARA ABRIR
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("JSON", "*.json"));
		fileChooser.setTitle("Open JSON File");

		filePath = fileChooser.showOpenDialog(new Stage());
		filename = filePath.getAbsolutePath();

		//TEXTO DA LABEL
		fileChosen.setText(filePath.getName());

		delimiter.setVisible(true);
		label.setVisible(true);
	}

	@FXML
	private void getFileCSV() {

		//PARA ABRIR
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("CSV", "*.csv"));
		fileChooser.setTitle("Open CSV File");

		filePath = fileChooser.showOpenDialog(new Stage());
		filename = filePath.getAbsolutePath();

		//TEXTO DA LABEL
		fileChosen.setText(filePath.getName());

		delimiter.setVisible(true);
		label.setVisible(true);
	}

	@FXML
	private void delimiter() {
		if(filename.endsWith(".json")) {
			convertJSON2CSV.setVisible(true);
		}else {
			convertCSV2JSON.setVisible(true);
		}
	}

	@FXML
	private void convertJSON2CSV() {
		//PARA GRAVAR
		fileChooserToSave.getExtensionFilters().addAll(new ExtensionFilter("CSV", ".csv"));
		filePathToSave = fileChooserToSave.showSaveDialog(new Stage());
		filenameToSave = filePathToSave.getAbsolutePath();

		try {
			Schedule.convertJSON2CSV(filename, filenameToSave, delimiter.getText().charAt(0));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Erro ao converter para ficheiro CSV", "Alerta" , JOptionPane.INFORMATION_MESSAGE);
		}
		JOptionPane.showMessageDialog(null, "Ficheiro convertido com sucesso!", "Sucesso" , JOptionPane.INFORMATION_MESSAGE);
	}

	@FXML
	private void convertCSV2JSON() {
		//PARA GRAVAR
		fileChooserToSave.getExtensionFilters().addAll(new ExtensionFilter("JSON", ".json"));
		filePathToSave = fileChooserToSave.showSaveDialog(new Stage());
		filenameToSave = filePathToSave.getAbsolutePath();
		
		try {
			Schedule.convertCSV2JSON(filename, filenameToSave, delimiter.getText().charAt(0));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Erro ao converter para ficheiro JSON", "Alerta" , JOptionPane.INFORMATION_MESSAGE);
		}
		JOptionPane.showMessageDialog(null, "Ficheiro convertido com sucesso!", "Sucesso" , JOptionPane.INFORMATION_MESSAGE);
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
