package alfdozen.es_2023_2sem_terca_teira_leipl_grupoa;

import javafx.application.Application;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * The App class launches the stage, sets the main menu scene and manages the
 * GUI components and events in the scene. The user can click on different
 * buttons to go to the the corresponding scenes (importSchedule, importWebcal,
 * createSchedule and convertFile). The exit button is used to close the
 * application. If the user already imported a schedule file, the viewSchedule
 * button is visible and can be clicked to go to the viewSchedule scene.
 * 
 * @author alfdozen
 * @version 1.0.0
 */
public class App extends Application implements Initializable {

	static final String ALERT_MESSAGE = "Alerta";
	static final String ERROR_MESSAGE = "Erro";
	static final String SUCCESS_MESSAGE = "Sucesso";
	static final String ERROR_SELECT_FILE_MESSAGE = "Por favor, selecione um ficheiro";
	static final String SUCCESS_DESCRIPTION_MESSAGE = "Horário importado com sucesso";
	static final String ERROR_SAVING = "Erro ao gravar";
	public static final int WIDTH = 700;
	public static final int HEIGHT = 750;
	
	public static Schedule schedule;
	private static Scene scene;
	private static Stage stage;

	@FXML
	private Button importScheduleButton = new Button();
	@FXML
	private Button importWebcalButton = new Button();
	@FXML
	private Button createScheduleButton = new Button();
	@FXML
	private Button convertFileButton = new Button();
	@FXML
	private Button viewScheduleButton = new Button();
	@FXML
	private ImageView logoImage = new ImageView();
	@FXML
	private AnchorPane window = new AnchorPane();
	
	
	/**
	 * This method is called after the init method has returned, and after the
	 * system is ready for the application to begin running. It receives the primary
	 * stage for the application and sets the main scene. If an error occurs while
	 * doing this, an error dialog is shown and the application shuts down.
	 * 
	 * @param primaryStage the primary stage for this application, onto which the
	 *                     application scene can be set.
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			schedule = new Schedule();
			stage = primaryStage;
			scene = new Scene(loadFXML("/fxml/Main"));
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Erro ao iniciar o programa", ERROR_MESSAGE,
					JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
	}
	
	/**
	 * Sets a new value of schedule to the global instance of schedule.
	 * 
	 * @param newSchedule the new schedule to update the global schedule
	 */
	public static void setSchedule(Schedule newSchedule) {
		schedule = newSchedule;
	}
	
	/**
	 * Defines if the the application's main stage can be resizable or not.
	 * 
	 * @param bool option a boolean value that if true allow resizing or false to
	 *               disable resizing)
	 */
	public static void setStageResize(boolean bool) {
		stage.setResizable(bool);
	}

	/**
	 * This method sets the stage size according to the width and heigh received.
	 * 
	 * @param width  the width to set the stage to.
	 * @param height the height to set the stage to.
	 */
	public static void setStageSize(double width, double height) {
		stage.setWidth(width);
		stage.setHeight(height);
	}

	/**
	 * This methods sets the root of the scene, effectively changing scenes
	 * according to the received fxml file.
	 * 
	 * @param fxml the relative path to the fxml file to set the scene to.
	 * @throws IOException if an error occurs while changing the scene.
	 */
	public static void setRoot(String fxml) throws IOException {
		scene.setRoot(loadFXML(fxml));
	}

	/**
	 * This method loads a parent scene from the received fxml file.
	 * 
	 * @param fxml the relative path to the fxml file to load.
	 * @return the parent scene loaded from the fxml file.
	 * @throws IOException if an error occurs while loading the fxml file.
	 */
	private static Parent loadFXML(String fxml) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
		return fxmlLoader.load();
	}

	/**
	 * This method is called by the event of clicking on the importScheduleButton.
	 * It changes to the importSchedule scene. If an error occurs while doing this,
	 * an error dialog is shown and the application shuts down.
	 */
	@FXML
	private void importSchedule() {
		try {
			App.setRoot("/fxml/ImportSchedule");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Erro ao abrir o menu de carregar horário de ficheiro",
					ERROR_MESSAGE, JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
	}

	/**
	 * This method is called by the event of clicking on the importWebcalButton. It
	 * changes to the importWebcal scene. If an error occurs while doing this, an
	 * error dialog is shown and the application shuts down.
	 */
	@FXML
	private void importWebcal() {
		try {
			App.setRoot("/fxml/Webcal");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Erro ao abrir o menu de importar horário do Fénix", ERROR_MESSAGE,
					JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
	}

	/**
	 * This method is called by the event of clicking on the createScheduleButton.
	 * It changes to the createSchedule scene. If an error occurs while doing this,
	 * an error dialog is shown and the application shuts down.
	 */
	@FXML
	private void createSchedule() {
		try {
			App.setRoot("/fxml/LoadFileBeforeCreateSchedule");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Erro ao abrir o menu de escolher horário", ERROR_MESSAGE,
					JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
	}

	/**
	 * This method is called by the event of clicking on the convertFileButton. It
	 * changes to the convertSchedule scene. If an error occurs while doing this, an
	 * error dialog is shown and the application shuts down.
	 */
	@FXML
	private void convertFile() {
		try {
			App.setRoot("/fxml/ConvertFile");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Erro ao abrir o menu de converter ficheiros", ERROR_MESSAGE,
					JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
	}

	/**
	 * This method is called by the event of clicking on the viewScheduleButton. It
	 * changes to the viewSchedule scene. If an error occurs while doing this, an
	 * error dialog is shown and the application shuts down.
	 */
	@FXML
	private void viewSchedule() {
		try {
			App.setRoot("/fxml/ViewSchedule");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Erro ao abrir o menu de consultar horário", ERROR_MESSAGE,
					JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
	}

	/**
	 * This method is called by the event of clicking on the closeWindowButton. It
	 * closes the stage, effectively shutting down the application.
	 */
	@FXML
	private void closeWindow() {
		stage.close();
	}

	/**
	 * Shows a new stage with a list of all the lecture conflicts in the schedule.
	 * The conflicts are displayed using the "Overlayed.fxml" file. If there is an
	 * IO exception while loading the FXML file, no action is taken.
	 */
	@FXML
	static void showLectureConflicts() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/fxml/Overlayed.fxml"));
			Parent root = fxmlLoader.load();
			Scene scene = new Scene(root, WIDTH, HEIGHT);
			Stage newStage = new Stage();
			newStage.setScene(scene);
			newStage.setTitle("Conflitos no Horário");
			newStage.setResizable(false);
			newStage.centerOnScreen();
			newStage.show();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Erro ao abrir o menu de mostrar conflitos do horário", ERROR_MESSAGE,
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * This method is called after its root element has been completely processed
	 * and initializes the GUI components and stage in the controller needed for the
	 * main scene. If the schedule object is not null and has lectures, the
	 * viewScheduleButton is shown. Otherwise, it is hidden.
	 *
	 * @param arg0 the location used to resolve relative paths for the root object,
	 *             or null if the location is not known.
	 * @param arg1 the resources used to localize the root object, or null if the
	 *             root object was not localized.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		App.setStageSize(window.getPrefWidth(), window.getPrefHeight());
		logoImage.setImage(new Image("/fxml/iscte.gif"));
		viewScheduleButton.setVisible(!Schedule.scheduleIsEmpty(schedule));
	}

	/**
	 * The main method of this application. It launches the application in the main
	 * menu.
	 * 
	 * @param args the command line arguments.
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
