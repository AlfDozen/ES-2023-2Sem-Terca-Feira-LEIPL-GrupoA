package alfdozen.es_2023_2sem_terca_teira_leipl_grupoa;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

public class Overlayed implements Initializable{

	@FXML
	private AnchorPane window;


	@FXML
	private ListView<Label> overlayedList;

	@FXML
	private ListView<Label> overlappedList;
	
	@FXML
	private void createSchedule() {
		try {
			App.setRoot("/fxml/ViewSchedule");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		overlayedList.getItems().clear();
		overlappedList.getItems().clear();

		if(!App.getSchedule().hasOverlappingLectures()) {
			Label noOver = new Label("Não há sobreposições de aulas");
			overlayedList.getItems().add(noOver);
		}else {
			int n = App.getSchedule().getLectures().size();
			for (int i = 0; i < n; i++) {
				for (int j = i + 1; j < n; j++) {
					if (App.getSchedule().getLectures().get(i).getTimeSlot().overlaps(App.getSchedule().getLectures().get(j).getTimeSlot())) {
						Label over = new Label(App.getSchedule().getLectures().get(i).toString());
						if(!overlayedList.getItems().contains(over)) {
							overlayedList.getItems().add(over);
						}
					}
					
					if(App.getSchedule().getLectures().get(i).isOvercrowded()) {
						Label over = new Label(App.getSchedule().getLectures().get(i).toString() + " - Sobrelotada");
						if(!overlappedList.getItems().contains(over)) {
							overlappedList.getItems().add(over);
						}
					}
				}
			}
		}
		
		if(overlappedList.getItems().isEmpty()) {
			Label noOver = new Label("Não há aulas sobrelotadas");
			overlappedList.getItems().add(noOver);
		}
	}
}
