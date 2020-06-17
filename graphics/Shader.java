package graphics;

import graphics.maths.MatrixMaths;
import graphics.system.Camera;
import graphics.system.Light;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;
import org.lwjglx.util.vector.Matrix4f;
import org.lwjglx.util.vector.Vector3f;

import utils.ShaderUtils;

public class Shader {

	public static final int VERTEX_ATTRIB = 0;
	public static final int TEXTURE_ATTRIB = 1;
	public static final int NORMAL_ATTRIB = 2;
	
	//Buffer to load matrices
	private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
	
	@SuppressWarnings("unused")
	private boolean enabled = false;
	private final int ID;
	
	//TODO: Uniform stuff
	
	public Shader(String vertex, String fragment)
	{
		this.ID = ShaderUtils.loadShader(vertex, fragment);
	}
	
	//Returns the uniform id in the shader code
	public int getUniformLocation(String uniform)
	{
		return GL20.glGetUniformLocation(ID, uniform);
	}
	
	//Loads a float into an existing uniform variable in the shader code
	public void loadFloat(int location, float value)
	{
		GL20.glUniform1f(location, value);
	}
	
	//Loads a vector into an existing uniform variable in the shader code
	public void loadVector(int location, Vector3f vector)
	{
		GL20.glUniform3f(location, vector.x, vector.y, vector.z);
	}
	
	//Loads a boolean as an integer in the shader code
	public void loadBoolean(int location, boolean value)
	{
		GL20.glUniform1i(location, value ? 1 : 0);
	}
	
	//Loads a matrix in shader code for transform / rotation / world matrices
	public void loadMatrix(int location, Matrix4f matrix)
	{
		matrix.store(matrixBuffer);
		matrixBuffer.flip();
		GL20.glUniformMatrix4fv(location, false, matrixBuffer);
	}
	
	//Pushes the projection matrix into the glsl shader code
	public void loadProjectionMatrix()
	{
		int location = getUniformLocation("projection");
		Matrix4f proj = MatrixMaths.getProjectionMatrix();
		loadMatrix(location, proj);
	}
	
	//Pushes the view matrix from camera object into glsl shader code
	public void loadViewMatrix(Camera camera) {
		int location = getUniformLocation("view");
		Matrix4f view = MatrixMaths.createViewMatrix(camera);
		loadMatrix(location, view);
	}
	
	//Loads a light source into the shader code for rendering w/ lighting system
	public void loadLight(Light light)
	{
		int locationPos = getUniformLocation("lightPos");
		int locationColor = getUniformLocation("lightColor");
		loadVector(locationPos, light.getPosition());
		loadVector(locationColor, light.getColor());
	}
	
	//Loads the shining values for lighting system
	public void loadShineValues(float damper, float reflectivity)
	{
		int damperLoc = getUniformLocation("shineDamper");
		int reflectLoc = getUniformLocation("reflectivity");
		loadFloat(damperLoc, damper);
		loadFloat(reflectLoc, reflectivity);
	}
	
	//Enable shader
	public void enable()
	{
		GL20.glUseProgram(ID);
		enabled = true;
	}
	
	//Disable shader
	public void disable()
	{
		GL20.glUseProgram(0);
		enabled = false;
	}

}
