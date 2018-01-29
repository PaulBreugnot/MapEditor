package view;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.map.Map;

public class NewMapController {

	@FXML
	private TextField nameField;
	@FXML
	private TextField widthField;
	@FXML
	private TextField heightField;

	private Stage stage;
	private MainViewController mainViewController;

	public void setMainViewController(MainViewController mainViewController) {
		this.mainViewController = mainViewController;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	@FXML
	private void addMap() {
		mainViewController.MapsList.add(new Map(Integer.parseInt(widthField.getText()),
				Integer.parseInt(heightField.getText()), nameField.getText()));
		mainViewController.setUnsaved(false);
		stage.close();
	}

}
