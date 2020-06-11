package game;

import game.gui.ConnectWindow;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Launcher {

	public static Font GLOBAL_FONT;
	public static BufferedImage ICON;
	
	public static void main(String[] args) throws Exception
	{
		GLOBAL_FONT = Font.createFont(Font.TRUETYPE_FONT, new File("res/font.ttf"));
		GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(GLOBAL_FONT);
		ICON = ImageIO.read(new File("res/images/icon.png"));
		
		ConnectWindow connectWindow = new ConnectWindow();
		connectWindow.defaultElements();
	}
	
}
