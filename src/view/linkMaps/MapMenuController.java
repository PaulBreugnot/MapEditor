package view.linkMaps;

import application.Main;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.map.Map;
import model.tile.Tile;

public class MapMenuController {
	
	@FXML
	private Label MapMenuLabel;
	
	@FXML
	private ListView<Map> MapsListView;

	@FXML
	private ScrollPane ScrollPane;

	@FXML
	private Slider ZoomSlider;
	
	private StackPane contentStackPane;
	private AnchorPane StartTilesAnchorPane = new AnchorPane();
	private AnchorPane DestinationTilesAnchorPane = new AnchorPane();
	private LinkMapsController linkMapsController;
	
	Rectangle potentialStartTileGraphic;
	
	@FXML
	public void initialize() {
		
	}
	
	public Slider getZoomSlider() {
		return ZoomSlider;
	}
	
	public void setContent(LinkMapsController linkMapsController, String MenuLabel) {
		this.linkMapsController = linkMapsController;
		MapMenuLabel.setText(MenuLabel);
		MapsListViewBuilder.setContent(this, MapsListView);
	}
	
	public void setMap(Map map) {
		map.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent me) {

			}
		});
		map.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent me) {
				Main.scene.setCursor(Cursor.DEFAULT);
			}
		});

		ZoomSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				map.setGraphics(new_val.doubleValue() * Tile.TILE_SIZE);
				setWrapTilesGraphics(map);
			}
		});
		
		contentStackPane = new StackPane();
		contentStackPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent me) {
				System.out.println("Select Start Tile");
				int x = (int) Math.floor(me.getX() / ZoomSlider.getValue() / Tile.TILE_SIZE);
				int y = (int) Math.floor(me.getY() / ZoomSlider.getValue() / Tile.TILE_SIZE);
				if (map.getTile(x ,y) != null) {
					StartTilesAnchorPane.getChildren().remove(potentialStartTileGraphic);
					double displayTileSize = ZoomSlider.getValue() * Tile.TILE_SIZE;
					potentialStartTileGraphic = new Rectangle(displayTileSize, displayTileSize, Color.BLUE);
					potentialStartTileGraphic.setOpacity(0.3);
					AnchorPane.setTopAnchor(potentialStartTileGraphic, y * displayTileSize);
					AnchorPane.setLeftAnchor(potentialStartTileGraphic, x * displayTileSize);
					System.out.println("Display rectangle");
					StartTilesAnchorPane.getChildren().add(potentialStartTileGraphic);
					
					linkMapsController.setCurrentlyLinking(true);
				}
			}
		});
		
		contentStackPane.getChildren().add(map);
		//startContentStackPane.getChildren().add(StartMapStartTilesAnchorPane);
		//startContentStackPane.getChildren().add(StartMapEndTilesAnchorPane);
		setWrapTilesGraphics(map);
		ScrollPane.setContent(contentStackPane);
		//startScrollPane.setContent(map);
	}
	
	public void setWrapTilesGraphics(Map map) {
		contentStackPane.getChildren().remove(StartTilesAnchorPane);
		contentStackPane.getChildren().remove(DestinationTilesAnchorPane);
		
		StartTilesAnchorPane.getChildren().clear();
		DestinationTilesAnchorPane.getChildren().clear();
		
		double displayTileSize = ZoomSlider.getValue() * Tile.TILE_SIZE;
		StartTilesAnchorPane.setPrefHeight(map.getMapHeight() * displayTileSize);
		DestinationTilesAnchorPane.setPrefHeight(map.getMapWidth() * displayTileSize);

		for (int x = 0; x < map.getMapHeight(); x++) {
			for (int y = 0; y < map.getMapWidth(); y++) {
				if (map.getTiles()[x][y] != null) {
					if (map.getTiles()[x][y].goSomewhere()) {
						Rectangle startTileGraphic = new Rectangle(displayTileSize, displayTileSize, Color.BLUE);
						startTileGraphic.setOpacity(0.2);
						AnchorPane.setTopAnchor(startTileGraphic, x * displayTileSize);
						AnchorPane.setLeftAnchor(startTileGraphic, y * displayTileSize);
						StartTilesAnchorPane.getChildren().add(startTileGraphic);
					}
					if (map.getTiles()[x][y].isDestinationOfSomewhere()) {
						Rectangle endTileGraphic = new Rectangle(displayTileSize, displayTileSize, Color.RED);
						endTileGraphic.setOpacity(0.2);
						AnchorPane.setTopAnchor(endTileGraphic, x * displayTileSize);
						AnchorPane.setLeftAnchor(endTileGraphic, y * displayTileSize);
						DestinationTilesAnchorPane.getChildren().add(endTileGraphic);
					}
				}
			}
		}
		contentStackPane.getChildren().add(StartTilesAnchorPane);
		contentStackPane.getChildren().add(DestinationTilesAnchorPane);
	}
}
