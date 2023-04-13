package alfdozen_es_2023_2sem_terca_feira_leipl_grupoa;


import javafx.application.Application;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
//import javafx.scene.web.WebEngine;
//import javafx.scene.web.WebView;


public class MainScreen extends Application implements Initializable{


	private String[] daysOfWeek = {"Segunda-feira", "Terça-feira", "Quarta-feira", "Quinta-feira", "Sexta-feira", "Sábado", "Domingo"};
	
	@FXML 
	private Button viewSchedule, createSchedule, uploadSchedule, webcal;
	
	@FXML
	private ImageView image;
	
	@FXML
	private Label header;
	
	
//	@FXML
//	private WebView webView = new WebView();
	
//	@FXML
//	private GridPane paneCalendar;

//	@FXML
//	private DatePicker datePicker;
	
    private static Scene scene;

	@Override
	public void start(Stage primaryStage) {
		try {

//			FXMLLoader loader = new FXMLLoader(Main.class.getResource("Controller.fxml"));
			scene = new Scene(loadFXML("/alfdozen_es_2023_2sem_terca_feira_leipl_grupoa/Controller"), 1000, 680);
//			Parent root = loader.load();
//			Scene scene = new Scene(root);

			//			daybutton.getScene().setRoot(mainViewRoot);

//			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch(Exception e) {
			System.err.println("Erro ao tentar correr o conteudo do controller");
		}
	}

	   static void setRoot(String fxml) throws IOException {
	        scene.setRoot(loadFXML(fxml));
	    }
	   
	   private static Parent loadFXML(String fxml) throws IOException {
	        FXMLLoader fxmlLoader = new FXMLLoader(MainScreen.class.getResource(fxml + ".fxml"));
	        return fxmlLoader.load();
	    }

//	@FXML
//	private void showButtons() {
//		// clear the GridPane
//		daybutton.setVisible(true);
//		weekbutton.setVisible(true);
//		monthbutton.setVisible(true);
//	}
//
//	@FXML
//	private void clearGridContent() {
//		// clear the GridPane
//		paneCalendar.getChildren().clear();
//		paneCalendar.getRowConstraints().clear();
//		paneCalendar.getColumnConstraints().clear();
//	}
//
//	@FXML
//	private void defineGridLayout() {
//		paneCalendar.setHgap(50);
//		paneCalendar.setVgap(50);
//
//		paneCalendar.prefWidthProperty().bind(monthbutton.getScene().widthProperty());
//		paneCalendar.prefHeightProperty().bind(monthbutton.getScene().heightProperty());
//	
//	}
//	
//	@FXML
//	private void allMonthLayout() {
//		paneCalendar.setHgap(80);
//		paneCalendar.setVgap(80);
//
//		paneCalendar.prefWidthProperty().bind(monthbutton.getScene().widthProperty());
//		paneCalendar.prefHeightProperty().bind(monthbutton.getScene().heightProperty());
//	
//	}


	//   ######################### MOSTRAR CALENDARIO #################################################### //

	@FXML
	public void showCalendar() {
		
	}
	//  ######################### MAIN #################################################### //

	public static void main(String[] args) {
		launch(args);
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		image.setImage(new Image("/alfdozen_es_2023_2sem_terca_feira_leipl_grupoa/default_avatar.jpg"));
//		showButtons();

	}
}
