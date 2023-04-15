package alfdozen.es_2023_2sem_terca_teira_leipl_grupoa;


import javafx.application.Application;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
//import javafx.scene.web.WebEngine;
//import javafx.scene.web.WebView;


public class MainScreen extends Application implements Initializable{


	@FXML
	private Button convertFile, createSchedule, uploadSchedule, webcal;
	
	@FXML
	private ImageView image;
	
	@FXML
	private Image photo = new Image("/fxml/iscte.gif");
	
	@FXML
	private Label header;
	
    private static Scene scene;

	public static Scene getScene() {
		return scene;
	}

	@Override
	public void start(Stage primaryStage) {
		try {

			scene = new Scene(loadFXML("/fxml/Main"));
			System.out.println("Scene created " + scene.toString());

			primaryStage.setScene(scene);
			primaryStage.show();

		} catch(Exception e) {
			System.err.println("Erro ao tentar correr o conteudo");
		}
	}

	   public static void setRoot(String fxml) throws IOException {
	        scene.setRoot(loadFXML(fxml));
	    }
	   
	   private static Parent loadFXML(String fxml) throws IOException {
	        FXMLLoader fxmlLoader = new FXMLLoader(MainScreen.class.getResource(fxml + ".fxml"));
	        System.out.println(fxmlLoader.toString());
	        return fxmlLoader.load();
	    }

	@FXML
	private void createSchedule() throws IOException {
		MainScreen.setRoot("/fxml/CreateSchedule");
	}
	
	@FXML
	private void uploadSchedule() throws IOException {
		MainScreen.setRoot("/fxml/UploadSchedule");
	}
	
	@FXML
	private void webcal() throws IOException {
		MainScreen.setRoot("/fxml/Webcal");
	}
	
	@FXML
	private void convertFile() throws IOException {
		MainScreen.setRoot("/fxml/ConvertFile");
	}
	
	public static void main(String[] args) {
		launch(args);
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		image.setImage(photo);
		header.setText("Bem vindo à plataforma de gestão de horários para alunos ISCTE");

	}
}
