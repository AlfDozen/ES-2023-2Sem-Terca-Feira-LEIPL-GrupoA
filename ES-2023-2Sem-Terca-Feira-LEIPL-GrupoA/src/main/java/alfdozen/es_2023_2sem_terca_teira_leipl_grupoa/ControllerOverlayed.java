package alfdozen.es_2023_2sem_terca_teira_leipl_grupoa;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

/**
 * The Overlayed class is a JavaFX controller that manages the GUI components and events in 
 * a scene that displays information about overlapping and overcrowded lectures in a schedule. 
 * It provides two ListViews to display overlapping and overcrowded lectures and has methods 
 * to change scenes and initialize the contents of the ListViews. Implements the Initializable 
 * interface to initialize the contents of the ListViews when the corresponding FXML file is loaded.
 * 
 * @author alfdozen
 * @version 1.0.0
 */
public class ControllerOverlayed implements Initializable {

	@FXML
	private AnchorPane window = new AnchorPane();
	@FXML
	private ListView<String> overlayedList = new ListView<>();
	@FXML
	private ListView<String> overlappedList = new ListView<>();

	/**
	 * Initializes the controller class. This method is automatically called after
	 * the FXML file has been loaded. It clears the contents of both ListView
	 * objects and checks if there are any overlapping lectures or overcrowded
	 * lectures in the current schedule. If there are overlapping lectures, it adds
	 * them to the "overlayedList" ListView. If there are overcrowded lectures, it
	 * adds them to the "overlappedList" ListView. If either ListView is empty, it
	 * adds a message indicating that there are no overlapping or overcrowded
	 * lectures.
	 *
	 * @param arg0 The location used to resolve relative paths for the root object
	 *             or null if the location is not known.
	 * @param arg1 The resources used to localize the root object or null if the
	 *             root object was not localized.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		overlayedList.getItems().clear();
		overlappedList.getItems().clear();
		if (!App.schedule.hasOverlappingLectures()) {
			String noOver = "Não há sobreposições de aulas";
			overlayedList.getItems().add(noOver);
		} else {
			int n = App.schedule.getLectures().size();
			for (int i = 0; i < n; i++) {
				for (int j = i + 1; j < n; j++) {
					Lecture lec1 = App.schedule.getLectures().get(i);
					Lecture lec2 = App.schedule.getLectures().get(j);
					addLectureToOverlayedAndOverlapped(lec1, lec2);
				}
			}
		}
		if (overlappedList.getItems().isEmpty()) {
			String noOver = "Não há aulas sobrelotadas";
			overlappedList.getItems().add(noOver);
		}
	}

	/**
	 * Adds the given lectures to the overlayed and overlapped lists if they overlap
	 * or if one of them is overcrowded. If the lectures overlap, their string
	 * representation is added to the overlayed list if it is not already there. If
	 * one of the lectures is overcrowded, its string representation with the suffix
	 * " - Sobrelotada" is added to the overlapped list if it is not already there.
	 * 
	 * @param lec1 the first lecture
	 * @param lec2 the second lecture
	 */
	private void addLectureToOverlayedAndOverlapped(Lecture lec1, Lecture lec2) {
		if (lec1.getTimeSlot().overlaps(lec2.getTimeSlot())) {
			String over = lec1.toString();
			if (!overlayedList.getItems().contains(over)) {
				overlayedList.getItems().add(over);
			}
			String over2 = lec2.toString();
			if (!overlayedList.getItems().contains(over2)) {
				overlayedList.getItems().add(over2);
			}
		}
		if (lec1.isOvercrowded()) {
			String over = lec1.toString() + " - Sobrelotada";
			if (!overlappedList.getItems().contains(over)) {
				overlappedList.getItems().add(over);
			}
		}
	}
}
