package utils;

import javax.swing.JOptionPane;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class ShaderUtils {

	public static int loadShader(String vertPath, String fragPath)
	{
		String vertexSrc = GLUtils.loadShaderData(vertPath);
		String fragSrc = GLUtils.loadShaderData(fragPath);
		return createShader(vertexSrc, fragSrc);
	}

	private static int createShader(String vertexSrc, String fragSrc) {
		int programID = GL20.glCreateProgram();
		int vertID = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
		int fragID = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
		
		GL20.glShaderSource(vertID, vertexSrc);
		GL20.glShaderSource(fragID, fragSrc);
		
		GL20.glCompileShader(vertID);
		if(GL20.glGetShaderi(vertID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE)
		{
			JOptionPane.showMessageDialog(null, "Failed to compile VERTEX shader.", "Shader Error", JOptionPane.ERROR_MESSAGE);
			System.err.println(GL20.glGetShaderInfoLog(vertID));
			return -1;
		}
		
		GL20.glCompileShader(fragID);
		if(GL20.glGetShaderi(fragID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE)
		{
			JOptionPane.showMessageDialog(null, "Failed to compile FRAGMENT shader.", "Shader Error", JOptionPane.ERROR_MESSAGE);
			System.err.println(GL20.glGetShaderInfoLog(fragID));
			return -1;
		}
		
		GL20.glAttachShader(programID, vertID);
		GL20.glAttachShader(programID, fragID);
		GL20.glLinkProgram(programID);
		GL20.glValidateProgram(programID);
		
		GL20.glDeleteShader(vertID);
		GL20.glDeleteShader(fragID);
		
		return programID;
	}
	
}
