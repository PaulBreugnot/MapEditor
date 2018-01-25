package model.tile;

import model.sprite.TileSprite;

public class Tile {
	public static final int TILE_SIZE = 24;
	protected TileSprite tileSprite;
	protected String tileType;
	
	public Tile (String url, String tileType) {
		this.tileSprite = new TileSprite(url);
		this.tileType = tileType;
	}
	
	public Tile(TileSprite tileSprite, String tileType) {
		this.tileSprite = tileSprite;
		this.tileType = tileType;
	}
	
	public Tile copy() {
		return new Tile(tileSprite.copy(), tileType);
	}
	
	public TileSprite getTileSprite() {
		return tileSprite;
	}
	
	public String getTileType() {
		return tileType;
	}
}
