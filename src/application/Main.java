package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import model.map.Map;
import view.mainView.MainViewController;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	
	public static Map testMap;
	public static Scene scene;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root;
			FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/view/mainView/MainView.fxml"));
            root = (BorderPane) loader.load();
            MainViewController controller = loader.getController();
            controller.setToolBars();
			scene = new Scene(root,1000,550);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setMinHeight(600);
			primaryStage.setMinWidth(1050);
			primaryStage.setTitle("Map Editor 0.0.1");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
