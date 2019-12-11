package beans;

import java.awt.image.BufferedImage;


public class Spritesheet {
 
	//attributtes
	final private int width_in_pixels, height_in_pixels;
	final private int width_in_sprites, height_in_sprites;
	final private int sprite_width, sprite_height;	
	final private Sprite[] sprites;
	
	//constructor
	public Spritesheet(final String path, final int spriteSize, boolean opaque) { //n*n
		final BufferedImage image;
		
		if(opaque)	image = ResourceLoader.loadCompatibleOpaqueImage(path);
		else image = ResourceLoader.loadCompatibleTransparentImage(path);
		
		width_in_pixels=image.getWidth();
		height_in_pixels=image.getHeight();
		
		width_in_sprites = width_in_pixels /spriteSize;
		height_in_sprites = height_in_pixels/spriteSize;
		
		sprite_width = spriteSize;
		sprite_height = spriteSize;
		
		sprites= new Sprite[width_in_sprites*height_in_sprites];		
		extractSprites(image);
	}
	
	public Spritesheet(final String path, final int spriteWidth, final int spriteHeight, boolean opaque) { //n*m
		final BufferedImage image;
		
		if(opaque)	image = ResourceLoader.loadCompatibleOpaqueImage(path);
		else image = ResourceLoader.loadCompatibleTransparentImage(path);
		
		width_in_pixels = image.getWidth();
		height_in_pixels = image.getHeight();
		
		width_in_sprites = width_in_pixels /spriteWidth;
		height_in_sprites = height_in_pixels/spriteHeight;
		
		sprite_width = spriteWidth;
		sprite_height = spriteHeight;
		
		sprites= new Sprite[width_in_sprites*height_in_sprites];	
		extractSprites(image);
	}
	
	
	private void extractSprites(BufferedImage image) {
		
		for (int y = 0; y < height_in_sprites; y++) {
			for (int x = 0; x < width_in_sprites; x++) {
				final int posX = x * sprite_width;
				final int posY = y * sprite_height;
				sprites[x + y*width_in_sprites]= new Sprite(image.getSubimage(posX, posY, sprite_width, sprite_height));
			}
		}
		
	}
	
	public Sprite getSprite(final int index) {
		return sprites[index];
	}
	
	public Sprite getSprite(final int x ,final int y) {
		return sprites[x +y *width_in_sprites];
	}

	public int getWidth_in_sprites() {
		return width_in_sprites;
	}

	public int getHeight_in_sprites() {
		return height_in_sprites;
	}
	
}
