package view;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import application.Main;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import model.map.Map;
import model.sprite.ItemSprite;
import model.sprite.TileSprite;
import model.tile.Tile;
import view.toolBars.ColumnToolBarController;
import view.toolBars.RowToolBarController;

public class MainViewController {

	private boolean unsaved;
	private boolean displayGrid;
	private StackPane contentStackPane;

	@FXML
	private BorderPane MainBorderPane;
	@FXML
	private BorderPane MapBorderPane;
	@FXML
	private ListView<Tile> TilesListView;
	@FXML
	private ListView<ItemSprite> ItemsListView;
	@FXML
	private ListView<Map> MapListView;
	@FXML
	private Slider zoomSlider;

	ArrayList<Tile> TileSpritesArrayList = new ArrayList<>();
	ObservableList<Tile> TileSpritesList = FXCollections.observableArrayList(TileSpritesArrayList);

	ArrayList<Map> MapsArrayList = new ArrayList<>();
	ObservableList<Map> MapsList = FXCollections.observableArrayList(MapsArrayList);

	@FXML
	private void initialize() {
		setListViews();
		//setToolBars();

	}

	public void setListViews() {
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
				newValue.setGraphics(zoomSlider.getValue() * Tile.TILE_SIZE);
				setMap(newValue);
			}
		});
	}

	public void setToolBars() {
		try {
			AnchorPane columnToolBar;
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/view/toolBars/ColumnToolBar.fxml"));
			columnToolBar = (AnchorPane) loader.load();
			ColumnToolBarController controller = loader.getController();
			controller.setMainViewController(this);
			MapBorderPane.setTop(columnToolBar);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			AnchorPane rowToolBar;
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/view/toolBars/RowToolBar.fxml"));
			rowToolBar = (AnchorPane) loader.load();
			RowToolBarController controller = loader.getController();
			controller.setMainViewController(this);
			MapBorderPane.setLeft(rowToolBar);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setMap(Map map) {
		map.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent me) {
				if (TilesListView.getSelectionModel().getSelectedItem() != null) {
					Main.scene.setCursor(new ImageCursor(TilesListView.getSelectionModel().getSelectedItem()
							.getTileSprite().imageView(24).getImage()));
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
				int x = (int) Math.floor(me.getX() / zoomSlider.getValue() / Tile.TILE_SIZE);
				int y = (int) Math.floor(me.getY() / zoomSlider.getValue() / Tile.TILE_SIZE);
				Tile tileToAdd = TilesListView.getSelectionModel().getSelectedItem().copy();
				map.addTile(x, y, tileToAdd);
				map.setGraphics(zoomSlider.getValue() * Tile.TILE_SIZE);
			}
		});

		zoomSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				for (Node child : contentStackPane.getChildren()) {
					((Map) child).setGraphics(new_val.doubleValue() * Tile.TILE_SIZE);
				}
			}
		});

		/*
		 * Stage testStage = new Stage(); AnchorPane testPane = new AnchorPane();
		 * testPane.getChildren().add(map.getTiles()[0][0].getTileSprite().imageView(24)
		 * ); Scene testScene = new Scene(testPane); testStage.setScene(testScene);
		 * testStage.show();
		 */
		contentStackPane = new StackPane();
		setGrid(map);
		contentStackPane.getChildren().add(map);
		((ScrollPane) MapBorderPane.getCenter()).setContent(contentStackPane);
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

	public void setUnsaved(boolean unsaved) {
		this.unsaved = unsaved;
	}

	private void setGrid(Map map) {
		Map grid = new Map(map.getMapWidth(), map.getMapHeight(), "grid");
		for (int i = 0; i < map.getMapHeight(); i++) {
			for (int j = 0; j < map.getMapWidth(); j++) {
				System.out.println((i + j) % 2);
				if ((i + j) % 2 == 0) {
					grid.addTile(i, j, new Tile(TileSprite.PairUnusedTile, "Unused"));
				} else {
					grid.addTile(i, j, new Tile(TileSprite.UnpairUnusedTile, "Unused"));
				}
			}
		}
		grid.setGraphics(zoomSlider.getValue() * Tile.TILE_SIZE);
		contentStackPane.getChildren().add(grid);
	}

	@FXML
	private void handleSaveMap() {
		MapListView.getSelectionModel().getSelectedItem().save();
		unsaved = false;
	}

	@FXML
	private void handleAddMap() {
		try {
			AnchorPane root;
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/view/NewMapView.fxml"));
			root = (AnchorPane) loader.load();
			NewMapController newMapController = loader.getController();
			newMapController.setMainViewController(this);
			Scene scene = new Scene(root);
			Stage newMapStage = new Stage();
			newMapController.setStage(newMapStage);
			newMapStage.setResizable(false);
			newMapStage.setTitle("New Map");
			newMapStage.setScene(scene);
			newMapStage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
