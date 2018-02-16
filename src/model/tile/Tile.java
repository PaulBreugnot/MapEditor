package model.tile;

import java.io.Serializable;

import model.sprite.TileSprite;
import model.tile.warp.DestinationOf;
import model.tile.warp.GoTo;

public class Tile implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -652661369913332480L;
	public static final int TILE_SIZE = 24;
	protected TileSprite tileSprite;
	protected String tileType;
	
	protected DestinationOf destinationOf;
	protected GoTo goTo;
	
	public Tile() {
		this("", null);
	}
	
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
	
	public void setGoTo(GoTo goTo) {
		this.goTo = goTo;
	}
	
	public void setDestinationOf(DestinationOf destinationOf) {
		this.destinationOf = destinationOf;
	}
	
	public TileSprite getTileSprite() {
		return tileSprite;
	}
	
	public String getTileType() {
		return tileType;
	}
	
	public DestinationOf getDestinationOf() {
		return destinationOf;
	}
	
	public boolean isDestinationOfSomewhere() {
		return destinationOf != null;
	}
	
	public GoTo getGoTo() {
		return goTo;
	}
	
	public boolean goSomewhere() {
		return goTo != null;
	}

	@Override
	public String toString() {
		return "Tile [tileType=" + tileType + "]";
	}
	
	
}
