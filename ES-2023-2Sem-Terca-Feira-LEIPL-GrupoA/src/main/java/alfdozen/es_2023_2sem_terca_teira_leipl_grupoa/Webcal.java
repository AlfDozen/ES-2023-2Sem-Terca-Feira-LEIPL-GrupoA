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


public class Webcal implements Initializable{


	@FXML
	private Button save, backButton;

	@FXML
	private TextField webcal;

	// Returns the number of days in the current month
	@FXML
	private void webcallLink() {

		if(webcal != null) {

			String domain = webcal.getText().substring(0, 8);

			if(domain.equals("webcal://")) {

				//usar ficheiro para criar calendario

			}else {

				JOptionPane.showMessageDialog(null, "O link inserido: "+ domain + " não começa por webcal://", "Alerta" , JOptionPane.INFORMATION_MESSAGE);
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
