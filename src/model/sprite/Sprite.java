package model.sprite;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Sprite extends ImageView {

	public Sprite(Image image) {
		super(image);
	}
	
	public Sprite (String url) {
		super(url);
	}
}
