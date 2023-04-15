package alfdozen.es_2023_2sem_terca_teira_leipl_grupoa;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class UploadSchedule implements Initializable{


	@FXML
	private Button save, backButton, uploadSchedule;

	@FXML
	private Label fileChosen;
	
	FileChooser fileChooser  = new FileChooser();
	File filePath;

	// Returns the number of days in the current month


	@FXML
	private void viewSchedule() throws IOException {
		MainScreen.setRoot("/fxml/ViewSchedule");
	}
	
	@FXML
	private void getFile() {

		fileChooser.setTitle("Open Resource File");

		filePath = fileChooser.showOpenDialog(new Stage());
		
		fileChosen.setText(filePath.getName());
		
	}

	@FXML
	private void uploadFile() {

		if(filePath != null) {

			String filename = filePath.getName();
			String extension = filename.substring(filename.lastIndexOf(".")+1).toLowerCase();

			System.out.println(filename + " " + extension);

			if(extension.equals("json") || extension.equals("csv")) {

				//usar ficheiro para criar calendario

			}else {

				JOptionPane.showMessageDialog(null, "O ficheiro importado tem extensão: "+ extension + "! Apenas são aceites extensões .json ou .csv", "Alerta" , JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}


	@FXML
	private void returnHome() {
		try {
			MainScreen.setRoot("/fxml/Main");
		} catch (IOException e) {
			System.err.println("Erro ao tentar retornar");
		}

	}

	//  ######################### MAIN #################################################### //


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {


	}
}
