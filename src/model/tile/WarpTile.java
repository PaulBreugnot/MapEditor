package model.tile;

import model.sprite.TileSprite;

public class WarpTile extends Tile {
	
	private String mapDestinationPath;
	private int xDestinationCoordinates;
	private int yDestinationCoordinates;

	public WarpTile(TileSprite tileSprite, String tileType) {
		super(tileSprite, tileType);
	}
	
	public WarpTile(TileSprite tileSprite, String tileType, String mapDestinationPath, int xDestinationCoordinates, int yDestinationCoordinates) {
		super(tileSprite, tileType);
		this.mapDestinationPath = mapDestinationPath;
		this.xDestinationCoordinates = xDestinationCoordinates;
		this.yDestinationCoordinates = yDestinationCoordinates;
	}
	

}
