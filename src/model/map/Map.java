package model.map;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import application.Main;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.sprite.TileSprite;
import model.tile.Tile;

public class Map extends AnchorPane implements Serializable{

	private int mapWidth;
	private int mapHeight;
	private String mapName;
	private Tile tileArray[][];
	public static double scale = 20;
	
	public Map(int mapWidth, int mapHeight, String mapName) {
		this.mapWidth = mapWidth;
		this.mapHeight = mapHeight;
		this.mapName = mapName;
		tileArray = new Tile[mapHeight][mapWidth];
		//setMinWidth(scale * mapWidth * Tile.TILE_SIZE);
		//setMinHeight(scale * mapHeight * Tile.TILE_SIZE);
		setPrefWidth(scale * mapWidth * Tile.TILE_SIZE);
		setPrefHeight(scale * mapHeight * Tile.TILE_SIZE);
	}
	
	public void addTile(int x, int y, Tile tile) {
		if(x < mapWidth && y < mapHeight) {
			tileArray[y][x] = tile;
		}
	}
	
	public static Map load(String mapName) {
	    ObjectInputStream ois = null;
	    Map map = null;
	    try {
	      final FileInputStream fichier = new FileInputStream("maps/" + mapName + ".map");
	      ois = new ObjectInputStream(fichier);
	      map = (Map) ois.readObject();
	    } catch (final java.io.IOException e) {
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
		  // ...
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
	
	public void setGraphics() {
		for (int i = 0; i < mapHeight; i++) {
			for (int j = 0; j< mapWidth; j++) {
				System.out.println(i + " " + j);
				TileSprite tileSprite = tileArray[i][j].getTileSprite();
				AnchorPane.setTopAnchor(tileSprite, i * scale * Tile.TILE_SIZE);
				AnchorPane.setLeftAnchor(tileSprite, j * scale * Tile.TILE_SIZE);
				getChildren().add(tileSprite);
			}
		}
	}
}
