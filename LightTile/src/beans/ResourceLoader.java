package beans;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class ResourceLoader {

	//read images
	public static BufferedImage loadCompatibleOpaqueImage(final String path) {
		
		Image image=null;
		try {
			image= ImageIO.read(new File(path));
		} catch (IOException e) {e.printStackTrace();}
		
		GraphicsConfiguration gc =GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		
		BufferedImage acceleratedImage = gc.createCompatibleImage(image.getWidth(null),image.getHeight(null),Transparency.OPAQUE);
		Graphics g = acceleratedImage.getGraphics();
		g.drawImage(image, 0, 0, null);
		g.dispose();
		return acceleratedImage;
	
	}
	
	public static BufferedImage loadCompatibleTransparentImage(final String path) {
		Image image=null;
		try {
			image= ImageIO.read(new File(path));
		} catch (IOException e) {e.printStackTrace();}
		
		GraphicsConfiguration gc =GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		
		BufferedImage acceleratedImage = gc.createCompatibleImage(image.getWidth(null),image.getHeight(null),Transparency.TRANSLUCENT);
		Graphics g = acceleratedImage.getGraphics();
		g.drawImage(image, 0, 0, null);
		g.dispose();
		return acceleratedImage;
	
	}
	
	//read text files
	public static String readTextFile(final String path) {
		
		String content="";
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
			String line;
			while((line=reader.readLine())!=null)  	content+=line;
			reader.close();
		} catch (IOException e) {e.printStackTrace();}
		
		return content;
	}
	
	//read fonts
	public static Font loadFont(final String path) {
		
		Font f =null;
		
		try {
			InputStream bytes= new FileInputStream(new File(path));
			f=Font.createFont(Font.TRUETYPE_FONT, bytes);
		} catch (FontFormatException | IOException e) {e.printStackTrace();	}
		
		f=f.deriveFont(12f);
		return f;
	}
	
	
}
