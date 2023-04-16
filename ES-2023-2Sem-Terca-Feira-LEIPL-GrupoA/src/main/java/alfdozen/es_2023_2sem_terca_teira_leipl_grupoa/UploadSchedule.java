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
	private Button save, backButton, uploadSchedule, viewSchedule;

	@FXML
	private Label fileChosen;

	FileChooser fileChooser  = new FileChooser();
	File filePath;

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
		System.out.println("Path " + filePath);

		fileChosen.setText(filePath.getName());

		save.setVisible(true);
	}

	@FXML
	private void uploadFile() {

		if(filePath != null) {

			String filename = filePath.getAbsolutePath();
			System.out.println("absolute " + filename);

			String extension = filename.substring(filename.lastIndexOf(".")+1).toLowerCase();

			//			System.out.println(filename + " " + extension);

			Schedule abc = null;

			if(extension.equals("json")) {

				try {


					abc = Schedule.loadJSON(filename);

					System.out.println(abc.toString());

					viewSchedule.setVisible(true);

				}catch(Exception e1) {

					JOptionPane.showMessageDialog(null, "O ficheiro importado deu cócó JSON", "Alerta" , JOptionPane.INFORMATION_MESSAGE);
				}

				App.addSCHEDULE(abc);


			}else 
				
				if(extension.equals("csv")) {

			try {


				abc = Schedule.loadCSV(filename);

				System.out.println(abc.toString());

				viewSchedule.setVisible(true);

			}catch(Exception e1) {

				JOptionPane.showMessageDialog(null, "O ficheiro importado deu cócó CSV", "Alerta" , JOptionPane.INFORMATION_MESSAGE);
			}

			App.addSCHEDULE(abc);

		}else {

			JOptionPane.showMessageDialog(null, "O ficheiro importado tem extensão: "+ extension + "! Apenas são aceites extensões .json ou .csv", "Alerta" , JOptionPane.INFORMATION_MESSAGE);
		}
	}
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
