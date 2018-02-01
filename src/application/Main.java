package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import model.map.Map;
import model.sprite.TileSprite;
import model.tile.Tile;
import view.MainViewController;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	
	public static Map testMap;
	public static Scene scene;
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root;
			FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/view/MainView.fxml"));
            root = (BorderPane) loader.load();
            MainViewController controller = loader.getController();
			scene = new Scene(root,1000,550);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			/*testMap = new Map(2,2,"testMap");
			testMap.addTile(0,0,new Tile("file:sprites/RedTile.png", "RedTile"));
			testMap.addTile(0,1,new Tile("file:sprites/BlueTile.png", "BlueTile"));
			testMap.addTile(1,0,new Tile("file:sprites/GreenTile.png", "GreenTile"));
			testMap.addTile(1,1,new Tile("file:sprites/RedTile.png", "RedTile"));
			testMap.save();*/
			//testMap.setGraphics(Map.scale * Tile.TILE_SIZE);
			//controller.setMap(testMap);
			//Tile exampleTile = new Tile("file:sprites/RedTile.png", "RedTile");
			//root.getChildren().add(exampleTile.getTileSprite());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
