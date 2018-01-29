package model.sprite;

import java.io.Serializable;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Sprite implements Serializable{

	protected String ImageURL;
	/**
	 * 
	 */
	private static final long serialVersionUID = 4517883076203861959L;
	
	public Sprite (String url) {
		this.ImageURL = url;
	}
}
