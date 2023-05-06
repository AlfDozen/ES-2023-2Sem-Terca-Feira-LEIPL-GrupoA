package alfdozen.es_2023_2sem_terca_teira_leipl_grupoa;


import javafx.application.Application;

//import java.awt.Button;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.layout.AnchorPane;


public class App extends Application implements Initializable{


	@FXML
	private Button convertFile, createSchedule, uploadSchedule, webcal;

	@FXML
	private ImageView image;

	@FXML
	private Image photo = new Image("/fxml/iscte.gif");

	@FXML
	private Label header;

	@FXML
	private AnchorPane window; 

	private static Scene SCENE;
	private static Stage STAGE;

	public static Stage getSTAGE() {
		return STAGE;
	}

	public static Schedule SCHEDULE = new Schedule(); 

	public static void setStageSize(double width, double height) {
		STAGE.setWidth(width);
		STAGE.setHeight(height);
	}

	@Override
	public void start(Stage primaryStage) {
		try {

			STAGE = primaryStage;

			SCENE = new Scene(loadFXML("/fxml/Main"));
			System.out.println("Scene created " + SCENE.toString());

			STAGE.setScene(SCENE);
			STAGE.show();

		} catch(Exception e) {
			System.err.println("Erro ao tentar correr o conteudo");
		}
	}

	public static void setRoot(String fxml) throws IOException {
		SCENE.setRoot(loadFXML(fxml));
	}

	private static Parent loadFXML(String fxml) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
		System.out.println(fxmlLoader.toString());
		return fxmlLoader.load();
	}

	@FXML
	private void createSchedule() throws IOException {
		App.setRoot("/fxml/CreateSchedule");
	}

	@FXML
	private void uploadSchedule() throws IOException {
		App.setRoot("/fxml/UploadSchedule");
	}

	@FXML
	private void webcal() throws IOException {
		App.setRoot("/fxml/Webcal");
	}

	@FXML
	private void convertFile() throws IOException {
		App.setRoot("/fxml/ConvertFile");
	}

	@FXML
	private void closeWindow() throws IOException {
		STAGE.close();
	}

	public static void main(String[] args) {
		launch(args);
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		App.setStageSize(window.getPrefWidth(),window.getPrefHeight());
		image.setImage(photo);
		header.setText("Bem vindo à plataforma de gestão de horários para alunos ISCTE");
	}
}
