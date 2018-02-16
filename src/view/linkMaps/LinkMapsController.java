package view.linkMaps;

import java.io.IOException;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class LinkMapsController {

	@FXML
	private AnchorPane MapMenu1;
	@FXML
	private AnchorPane MapMenu2;

	private boolean currentlyLinking;

	@FXML
	public void initialize() {
	}

	public void setContent() {
		try {
			FXMLLoader loader1 = new FXMLLoader();
			loader1.setLocation(Main.class.getResource("/view/linkMaps/MapMenu.fxml"));

			AnchorPane root1 = (AnchorPane) loader1.load();
			MapMenuController controller1 = loader1.getController();
			controller1.setContent(this, "Map 1");
			MapMenu1.getChildren().add(root1);

			FXMLLoader loader2 = new FXMLLoader();
			loader2.setLocation(Main.class.getResource("/view/linkMaps/MapMenu.fxml"));

			AnchorPane root2 = (AnchorPane) loader2.load();
			MapMenuController controller2 = loader2.getController();
			controller2.setContent(this, "Map 2");
			MapMenu2.getChildren().add(root2);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setCurrentlyLinking(boolean currentlyLinking) {
		this.currentlyLinking = currentlyLinking;
	}

	public boolean isCurrentlyLinking() {
		return currentlyLinking;
	}
}