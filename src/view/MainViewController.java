package view;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import application.Main;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import model.map.Map;
import model.sprite.ItemSprite;
import model.tile.Tile;

public class MainViewController {

	@FXML
	private BorderPane MainBorderPane;
	@FXML
	private ListView<Tile> TilesListView;
	@FXML
	private ListView<ItemSprite> ItemsListView;
	@FXML
	private ListView<Map> MapListView;

	ArrayList<Tile> TileSpritesArrayList = new ArrayList<>();
	ObservableList<Tile> TileSpritesList = FXCollections.observableArrayList(TileSpritesArrayList);

	ArrayList<Map> MapsTileSpritesArrayList = new ArrayList<>();
	ObservableList<Map> MapsList = FXCollections.observableArrayList(MapsTileSpritesArrayList);

	@FXML
	private void initialize() {
		// TileSpritesList.add(new Tile("file:sprites/RedTile.png", "RedTile"));
		// TileSpritesList.add(new Tile("file:sprites/BlueTile.png", "BlueTile"));
		// TileSpritesList.add(new Tile("file:sprites/GreenTile.png", "GreenTile"));
		
		try {
			Files.list(Paths.get("sprites")).forEach(path -> {
				String fileName = path.getFileName().toString();
				TileSpritesList.add(new Tile("file:" + path, fileName.substring(0, fileName.length() - 4)));
			});
			Files.list(Paths.get("maps")).forEach(path -> {
				String fileName = path.getFileName().toString();
				Map map = new Map(fileName.substring(0, fileName.length() - 4));
				MapsList.add(map.load());
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TilesListView.setItems(TileSpritesList);

		TilesListView.setCellFactory(new Callback<ListView<Tile>, ListCell<Tile>>() {
			@Override
			public ListCell<Tile> call(ListView<Tile> TilesListView) {
				return new TileSpriteCell();
			}
		});

		MapListView.setItems(MapsList);

		MapListView.setCellFactory(new Callback<ListView<Map>, ListCell<Map>>() {
			@Override
			public ListCell<Map> call(ListView<Map> TilesListView) {
				return new MapCell();
			}
		});

		MapListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Map>() {
			public void changed(ObservableValue<? extends Map> observable, Map oldValue, Map newValue) {
				if (oldValue != null) {
					oldValue.save();
				}
				newValue.setGraphics(Map.scale * Tile.TILE_SIZE);
				setMap(newValue);
				System.out.println(newValue.getChildren());
				System.out.println(newValue.getHeight());
				System.out.println(newValue.getWidth());
				//System.out.println(newValue.getTiles()[1][1].getTileSprite().getFitWidth());
			}
		});
	}

	public void setMap(Map map) {
		map.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent me) {
				if (TilesListView.getSelectionModel().getSelectedItem() != null) {
					Main.scene.setCursor(new ImageCursor(
							TilesListView.getSelectionModel().getSelectedItem().getTileSprite().imageView(24).getImage()));
				}
			}
		});
		map.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent me) {
				Main.scene.setCursor(Cursor.DEFAULT);
			}
		});
		map.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent me) {
				int x = (int) Math.floor(me.getX() / Map.scale / Tile.TILE_SIZE);
				int y = (int) Math.floor(me.getY() / Map.scale / Tile.TILE_SIZE);
				Tile tileToAdd = TilesListView.getSelectionModel().getSelectedItem().copy();
				map.addTile(x, y, tileToAdd);
				map.setGraphics(Map.scale * Tile.TILE_SIZE);
			}
		});
		/*Stage testStage = new Stage();
		AnchorPane testPane = new AnchorPane();
		testPane.getChildren().add(map.getTiles()[0][0].getTileSprite().imageView(24));
		Scene testScene = new Scene(testPane);
		testStage.setScene(testScene);
		testStage.show();*/
		((ScrollPane) MainBorderPane.getCenter()).setContent(map);
	}

	static class TileSpriteCell extends ListCell<Tile> {
		@Override
		public void updateItem(Tile item, boolean empty) {
			super.updateItem(item, empty);
			if (item != null) {
				setGraphic(item.getTileSprite().imageView(24));
				setText(item.getTileType());
			}
		}
	}

	static class MapCell extends ListCell<Map> {
		@Override
		public void updateItem(Map item, boolean empty) {
			super.updateItem(item, empty);
			if (item != null) {
				setText(item.getMapName());
			}
		}
	}
}
