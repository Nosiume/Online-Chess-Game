package graphics;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class ModelTexture {

	private static List<Integer> ids = new ArrayList<Integer>();
	
	private int id;
	
	public ModelTexture(String src)
	{
		Texture texture = null;
		try {
			texture = TextureLoader.getTexture("PNG", new FileInputStream(src));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.id = texture.getTextureID();
		ids.add(id);
	}
	
	public void bind()
	{
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
	}
	
	public void unbind()
	{
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}
	
	public static void cleanUp()
	{
		for(int id : ids)
		{
			GL11.glDeleteTextures(id);
		}
		ids.clear();
	}
	
}
