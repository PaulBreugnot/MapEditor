package model.sprite;

import java.io.Serializable;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.map.Map;
import model.tile.Tile;

public class TileSprite extends Sprite implements Serializable{

	public static final TileSprite PairUnusedTile = new TileSprite("file:sprites/PairUnusedTile.png");
	public static final TileSprite UnpairUnusedTile = new TileSprite("file:sprites/UnpairUnusedTile.png");
	/**
	 * 
	 */
	private static final long serialVersionUID = -754550552184577425L;

	public TileSprite (String url) {
		super(url);
		//setFitHeight(Tile.TILE_SIZE * Map.scale);
		//setFitWidth(Tile.TILE_SIZE * Map.scale);
	}
	
	public ImageView imageView(double size) {
		ImageView imageView = new ImageView(ImageURL);
		imageView.setFitWidth(size);
		imageView.setFitHeight(size);
		return imageView;
	}
	
	public TileSprite copy(){
		return new TileSprite(ImageURL);
	}
}
