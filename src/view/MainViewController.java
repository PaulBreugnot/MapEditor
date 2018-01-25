package view;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Callback;
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
	
	@FXML
	private void initialize() {
		//TileSpritesList.add(new Tile("file:sprites/RedTile.png", "RedTile"));
		//TileSpritesList.add(new Tile("file:sprites/BlueTile.png", "BlueTile"));
		//TileSpritesList.add(new Tile("file:sprites/GreenTile.png", "GreenTile"));
		try {
			Files.list(Paths.get("sprites")).forEach(path -> {
			String fileName = path.getFileName().toString();
			TileSpritesList.add(new Tile("file:" + path, fileName.substring(0, fileName.length()-4)));});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TilesListView.setItems(TileSpritesList);
		 
		TilesListView.setCellFactory(new Callback<ListView<Tile>,
            ListCell<Tile>>() {
                @Override 
                public ListCell<Tile> call(ListView<Tile> TilesListView) {
                    return new TileSpriteCell();
                }
            }
        );
	}
	
	public void setMap(Map map) {
		((ScrollPane) MainBorderPane.getCenter()).setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
		AnchorPane test = new AnchorPane();
		test.setPrefWidth(1000);
		test.setPrefHeight(1000);
		((ScrollPane) MainBorderPane.getCenter()).setContent(map);
	}
	
    static class TileSpriteCell extends ListCell<Tile> {
        @Override
        public void updateItem(Tile item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {
            	item.getTileSprite().setFitWidth(Tile.TILE_SIZE);
            	item.getTileSprite().setFitHeight(Tile.TILE_SIZE);
            	setGraphic(item.getTileSprite());
            	setText(item.getTileType());
            }
        }
    }
}
