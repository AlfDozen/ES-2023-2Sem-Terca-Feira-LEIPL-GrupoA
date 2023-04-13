package alfdozen_es_2023_2sem_terca_feira_leipl_grupoa;


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


//	private String[] daysOfWeek = {"Segunda-feira", "Terça-feira", "Quarta-feira", "Quinta-feira", "Sexta-feira", "Sábado", "Domingo"};
	
	@FXML
	private Button viewSchedule, createSchedule, uploadSchedule, webcal;
	
	@FXML
	private ImageView image;
	
	@FXML
	private Image photo = new Image("/alfdozen_es_2023_2sem_terca_feira_leipl_grupoa/default_avatar.jpg");
	
	@FXML
	private Label header;
	
    private static Scene scene;

	@Override
	public void start(Stage primaryStage) {
		try {

			scene = new Scene(loadFXML("/alfdozen_es_2023_2sem_terca_feira_leipl_grupoa/Main"));
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
	private void viewSchedule() throws IOException {
		MainScreen.setRoot("/alfdozen_es_2023_2sem_terca_feira_leipl_grupoa/Schedule");
	}
	
	@FXML
	private void createSchedule() throws IOException {
		MainScreen.setRoot("/alfdozen_es_2023_2sem_terca_feira_leipl_grupoa/Schedule2");
	}
	
	@FXML
	private void uploadSchedule() throws IOException {
		MainScreen.setRoot("/alfdozen_es_2023_2sem_terca_feira_leipl_grupoa/Schedule");
	}
	
	@FXML
	private void webcal() throws IOException {
		MainScreen.setRoot("/alfdozen_es_2023_2sem_terca_feira_leipl_grupoa/Schedule");
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		image.setImage(photo);

	}
}
