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

//PARA APAGAR: CLASSE DE PAGINA DE IMPORTAR O HORÁRIO DE UM WEBCAL
public class Webcal implements Initializable{


	@FXML
	private Button save, backButton, viewSchedule;

	@FXML
	private TextField webcal;

	@FXML
	private AnchorPane window;

	@FXML
	private void webcallLink() {
		if(webcal.getText().length() > 7) {
			String domain = webcal.getText().substring(0, 8);
			if(domain.equals("webcal://")) {
				//usar ficheiro para criar calendario
				viewSchedule.setVisible(true);
			}else {
				JOptionPane.showMessageDialog(null, "O link inserido: "+ domain + " não começa por webcal://", "Alerta" , JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	@FXML
	private void viewSchedule() throws IOException {
		App.setRoot("/fxml/ViewSchedule");
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
