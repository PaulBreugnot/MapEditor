package view.toolBars;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import view.mainView.MainViewController;

public class RowToolBarController {
	
	@FXML
	private Button addTopRow;
	@FXML
	private Button addBottomRow;
	@FXML
	private Button delTopRow;
	@FXML
	private Button delBottomRow;
	
	private MainViewController mainViewController;
	
	public void setMainViewController(MainViewController mainViewController) {
		this.mainViewController = mainViewController;
	}
}
