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


public class ConvertFile implements Initializable{


	@FXML
	private Button convert, backButton, getFile ;
	
	@FXML
	private AnchorPane window;
	
	@FXML
	private Label fileChosen;

	FileChooser fileChooser  = new FileChooser();
	File filePath;

	@FXML
	private void getFile() {

		fileChooser.setTitle("Open Resource File");

		filePath = fileChooser.showOpenDialog(new Stage());

		fileChosen.setText(filePath.getName());

		convert.setVisible(true);
	}

	@FXML
	private void convert() {

		if(filePath != null) {

			String filename = filePath.getName();
			String extension = filename.substring(filename.lastIndexOf(".")+1).toLowerCase();

			System.out.println(filename + " " + extension);

			if(extension.equals("json")) {

				try {

					Schedule.convertJSON2CSV(filename, filename, ',');
					JOptionPane.showMessageDialog(null, "Ficheiro convertido com sucesso!", "Sucesso" , JOptionPane.INFORMATION_MESSAGE);
					
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "O ficheiro importado tem extensão: "+ extension + "! Apenas são aceites extensões .json ou .csv", "Alerta" , JOptionPane.INFORMATION_MESSAGE);
				}
			}else 
				if(extension.equals("csv")) {

					System.out.println("o path é csv");
					
					try {
						Schedule.convertCSV2JSON(filename, filename, ';');
						JOptionPane.showMessageDialog(null, "Ficheiro convertido com sucesso!", "Sucesso" , JOptionPane.INFORMATION_MESSAGE);
						
					} catch (IOException e) {
						JOptionPane.showMessageDialog(null, "O ficheiro importado tem extensão: "+ extension + "! Apenas são aceites extensões .json ou .csv", "Alerta" , JOptionPane.INFORMATION_MESSAGE);
					}


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
