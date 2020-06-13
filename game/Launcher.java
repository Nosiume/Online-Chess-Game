package game;

import game.waitingroom.ConnectWindow;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import network.Client;
import network.Server;

public class Launcher {

	public static String USERNAME = null;
	public static Server SERVER = null;
	public static Client CLIENT = null;
	
	public static Font GLOBAL_FONT;
	public static BufferedImage ICON;
	
	public static ConnectWindow connectWindow;
	
	public static void main(String[] args) throws Exception
	{		
		GLOBAL_FONT = Font.createFont(Font.TRUETYPE_FONT, new File("res/font.ttf"));
		GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(GLOBAL_FONT);
		ICON = ImageIO.read(new File("res/images/icon.png"));
		
		connectWindow = new ConnectWindow(true);
		connectWindow.defaultElements();
	}
	
}
