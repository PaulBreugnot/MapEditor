package model.map;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import application.Main;
import javafx.beans.property.DoubleProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.sprite.TileSprite;
import model.tile.Tile;

public class Map extends AnchorPane implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7554534579885271464L;
	private int mapWidth;
	private int mapHeight;
	private String mapName;
	private Tile tileArray[][];
	//public static DoubleProperty scaleProperty;
	//public static double scale = 4;

	public Map(int mapWidth, int mapHeight, String mapName) {
		this.mapWidth = mapWidth;
		this.mapHeight = mapHeight;
		this.mapName = mapName;
		tileArray = new Tile[mapHeight][mapWidth];
		// setMinWidth(scale * mapWidth * Tile.TILE_SIZE);
		// setMinHeight(scale * mapHeight * Tile.TILE_SIZE);
	}

	public Map(String mapName) {
		this.mapName = mapName;
	}

	public String getMapName() {
		return mapName;
	}

	public int getMapWidth() {
		return mapWidth;
	}

	public int getMapHeight() {
		return mapHeight;
	}

	public Tile[][] getTiles() {
		return tileArray;
	}

	public void addTile(int x, int y, Tile tile) {
		if (x < mapWidth && y < mapHeight) {
			tileArray[y][x] = tile;
		}
	}

	public Map load() {
		ObjectInputStream ois = null;
		Map map = null;
		try {
			final FileInputStream fichier = new FileInputStream("maps/" + mapName + ".map");
			System.out.println("maps/" + mapName + ".map");
			ois = new ObjectInputStream(fichier);
			map = (Map) ois.readObject();
		} catch (final java.io.IOException e) {
			System.out.println("error");
			e.printStackTrace();
		} catch (final ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ois != null) {
					ois.close();
				}
			} catch (final IOException ex) {
				ex.printStackTrace();
			}
		}
		return map;
	}

	public void save() {
		ObjectOutputStream oos = null;
		try {
			final FileOutputStream file = new FileOutputStream("maps/" + mapName + ".map");
			oos = new ObjectOutputStream(file);
			oos.writeObject(this);
		} catch (final java.io.IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (oos != null) {
					oos.flush();
					oos.close();
				}
			} catch (final IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	public void setGraphics(double diplayTileSize) {
		getChildren().clear();
		for (int i = 0; i < mapHeight; i++) {
			for (int j = 0; j < mapWidth; j++) {
				if (tileArray[i][j] != null) {
					TileSprite tileSprite = tileArray[i][j].getTileSprite();
					ImageView tileImage = tileSprite.imageView(diplayTileSize);
					AnchorPane.setTopAnchor(tileImage, i * diplayTileSize);
					AnchorPane.setLeftAnchor(tileImage, j * diplayTileSize);
					getChildren().add(tileImage);
				}
			}
		}
		setPrefWidth(mapWidth * diplayTileSize);
		setPrefHeight(mapHeight * diplayTileSize);
	}
}
