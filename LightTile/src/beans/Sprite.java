package beans;

import java.awt.image.BufferedImage;

public class Sprite {
	
	//attributes
	private final BufferedImage image;
	private final int width, height;
	
	//constructor
	public Sprite(final BufferedImage image) {
		this.image=image;
		width=image.getWidth();
		height=image.getHeight();
	}

	//getters && setters
	public BufferedImage getImage() {
		return image;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}	

	
	
	
}
