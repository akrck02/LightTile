package beans;

import java.util.ArrayList;

public class LightMap {

	//attributes
	private int width,height;
	private ArrayList<String> codes;
	private ArrayList<Spritesheet> spritesheets;
	private ArrayList<Sprite> sprites;
	private int[] collisions;
	private String posActual;
	private String salida;
	
	//constructor
	public LightMap(int width, int height) {
		super();
		this.width = width;
		this.height = height;
		this.codes = new ArrayList<String>();
		this.spritesheets = new ArrayList<Spritesheet>();
		this.sprites = new ArrayList<Sprite>();
		this.collisions = new int[height*width];
	}
	
	//getters && setters
	public int getWidth() {return width;}
	public void setWidth(int width) {	this.width = width;}
	public int getHeight() {return height;}
	public void setHeight(int height) {this.height = height;}
	public ArrayList<Spritesheet> getSpritesheets() {return spritesheets;}
	public void setSpritesheets(ArrayList<Spritesheet> spritesheets) {this.spritesheets = spritesheets;}
	public ArrayList<Sprite> getSprites() {return sprites;}
	public void setSprites(ArrayList<Sprite> sprites) {this.sprites = sprites;}
	public int[] getCollisions() {	return collisions;}
	public void setCollisions(int[] collisions) {this.collisions = collisions;}
	public String getPosActual() {	return posActual;}
	public void setPosActual(String posActual) {this.posActual = posActual;}
	public String getSalida() {return salida;}
	public void setSalida(String salida) {this.salida = salida;}	
	
	
}
