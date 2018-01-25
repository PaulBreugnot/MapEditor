package model.sprite;

import javafx.scene.image.Image;
import model.map.Map;
import model.tile.Tile;

public class TileSprite extends Sprite{

	public TileSprite(Image image) {
		super(image);
		setFitHeight(Tile.TILE_SIZE * Map.scale);
		setFitWidth(Tile.TILE_SIZE * Map.scale);
	}

	public TileSprite (String url) {
		super(url);
		setFitHeight(Tile.TILE_SIZE * Map.scale);
		setFitWidth(Tile.TILE_SIZE * Map.scale);
	}
}
