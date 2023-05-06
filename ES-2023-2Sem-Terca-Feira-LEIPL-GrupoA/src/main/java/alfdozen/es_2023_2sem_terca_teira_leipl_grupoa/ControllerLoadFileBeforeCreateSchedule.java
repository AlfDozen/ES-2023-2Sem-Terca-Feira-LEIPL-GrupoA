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

public class ControllerLoadFileBeforeCreateSchedule implements Initializable{
	
	static final String TYPE_MESSAGE = "Alerta";

	@FXML
	private Button backButton;
	@FXML
	private Button createScheduleButton;
	@FXML
	private Button uploadFileButton;
	@FXML
	private Button getFileButton;
	@FXML
	private RadioButton optionLocal = new RadioButton();
	@FXML
	private RadioButton optionOnline = new RadioButton();
	@FXML
	private RadioButton optionFenix = new RadioButton();
	@FXML
	private RadioButton optionPrevious = new RadioButton();
	@FXML
	private Label fileChosen;
	@FXML
	private Label labelOnline;
	@FXML
	private Label labelFenix;
	@FXML
	private Label labelPreviousLoad;
	@FXML
	private Label labelPreviousNotLoad;
	@FXML
	private TextField urlChosen;
	@FXML
	private TextField webcalChosen;

	@FXML
	private AnchorPane window; 
	
	@FXML
	private void setLocal() {
		optionLocal.setSelected(true);
		fileChosen.setText("Nenhum ficheiro importado");
		fileChosen.setVisible(true);
		getFileButton.setVisible(true);
		
		labelOnline.setVisible(false);
		urlChosen.setVisible(false);
		labelFenix.setVisible(false);
		webcalChosen.setVisible(false);
		labelPreviousLoad.setVisible(false);
		labelPreviousNotLoad.setVisible(false);
		
		uploadFileButton.setVisible(false);
		createScheduleButton.setVisible(false);
	}
	
	@FXML
	private void setOnline() {
		optionOnline.setSelected(true);
		labelOnline.setVisible(true);
		urlChosen.setText("");
		urlChosen.setVisible(true);
		
		fileChosen.setVisible(false);
		getFileButton.setVisible(false);
		labelFenix.setVisible(false);
		webcalChosen.setVisible(false);
		labelPreviousLoad.setVisible(false);
		labelPreviousNotLoad.setVisible(false);
		
		uploadFileButton.setVisible(false);
		createScheduleButton.setVisible(false);
	}
	
	@FXML
	private void setFenix() {
		optionFenix.setSelected(true);
		labelFenix.setVisible(true);
		webcalChosen.setVisible(true);
		webcalChosen.setText("");
		
		fileChosen.setVisible(false);
		getFileButton.setVisible(false);
		labelOnline.setVisible(false);
		urlChosen.setVisible(false);
		labelPreviousLoad.setVisible(false);
		labelPreviousNotLoad.setVisible(false);
		
		uploadFileButton.setVisible(false);
		createScheduleButton.setVisible(false);
	}
	
	@FXML
	private void setPrevious() {
		optionPrevious.setSelected(true);
		
		fileChosen.setVisible(false);
		getFileButton.setVisible(false);
		labelOnline.setVisible(false);
		urlChosen.setVisible(false);
		labelFenix.setVisible(false);
		webcalChosen.setVisible(false);
		
		uploadFileButton.setVisible(false);
		
		if (scheduleIsEmpty(App.SCHEDULE)) {
			labelPreviousLoad.setVisible(false);
			labelPreviousNotLoad.setVisible(true);
			createScheduleButton.setVisible(false);
		} else {
			labelPreviousLoad.setVisible(true);
			labelPreviousNotLoad.setVisible(false);
			createScheduleButton.setVisible(true);
		}
	}
	
	@FXML
	private void getFile() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("CSV", "*.csv", "JSON", "*.json"));
		File file = fileChooser.showOpenDialog(new Stage());
		if (file == null) {
			JOptionPane.showMessageDialog(null, "Por favor, selecione um ficheiro.", TYPE_MESSAGE,
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		fileChosen.setText(file.getName());
		String filePath = file.getAbsolutePath();
		String fileExtension = Schedule.getFileExtension(filePath);	
		if(".csv".equals(fileExtension) || ".json".equals(fileExtension)) {
			fileChosen.setText(filePath);
			uploadFileButton.setVisible(true);
		}else {
			JOptionPane.showMessageDialog(null, "O ficheiro importado tem extensão: " + fileExtension 
					+ "! Apenas são aceites extensões .json ou .csv", TYPE_MESSAGE, JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	@FXML
	private void dealWithText() {
		uploadFileButton.setVisible(true);
	}
	
	@FXML
	private void uploadFile() {
		Schedule scheduleUploaded = null;
		try {
			if (optionLocal.isSelected()){
				scheduleUploaded = Schedule.callLoad(fileChosen.getText());
				JOptionPane.showMessageDialog(null, "Horário importado com sucesso", TYPE_MESSAGE, JOptionPane.INFORMATION_MESSAGE);
				createScheduleButton.setVisible(true);
				
			// NÃO FOI TESTADO - Se não for possível testar, na minha opinião, esta funcionalidade deve ser retirada
			// do trabalho. Prefiro entregar um trabalho incompleto do que com uma funcionalidade que não foi testada
			// para um teste básico
			} else if (optionOnline.isSelected()) {
				String filePath = urlChosen.getText();
				String fileExtension = Schedule.getFileExtension(filePath);	
				if (filePath.isBlank() || (!".csv".equals(fileExtension) && !".json".equals(fileExtension))) {
					JOptionPane.showMessageDialog(null, "URL do ficheiro remoto inválido", TYPE_MESSAGE, 
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				String tmpUrl= Schedule.downloadFileFromURL(filePath);
				scheduleUploaded = Schedule.callLoad(tmpUrl);
				createScheduleButton.setVisible(true);
			
			} else {
				String filePath = webcalChosen.getText();
				if (filePath.isBlank()) {
					JOptionPane.showMessageDialog(null, "Link do calendário pessoal inválido", TYPE_MESSAGE, 
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				scheduleUploaded = Schedule.loadWebcal(filePath);
				createScheduleButton.setVisible(true);
				
			}
			
		}catch(Exception e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Erro ao importar ficheiro", TYPE_MESSAGE, JOptionPane.INFORMATION_MESSAGE);
		}

		App.SCHEDULE = scheduleUploaded;
	}
	
	@FXML
	private void createSchedule() throws IOException {
		App.setRoot("/fxml/CreateSchedule");
	}

	@FXML
	private void returnHome() {
		try {
			App.setRoot("/fxml/Main");
		} catch (IOException e) {
			System.err.println("Erro ao tentar retornar");
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		App.setStageSize(window.getPrefWidth(),window.getPrefHeight());
	}
	
	// Método auxiliar
	private static boolean scheduleIsEmpty(Schedule sdl) {
		String empty = "Unknown Student Name\nUnknown Student Number\nSchedule is empty";
		if(empty.endsWith(sdl.toString())) {
			return true;
		}
		return false;
	}
	
	
}
