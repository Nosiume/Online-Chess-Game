package graphics.system;

import game.game.GameWindow;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjglx.util.vector.Vector3f;

public class Camera {

	private static final float SPEED = 0.01f;
	
	private Vector3f position = new Vector3f(0, 0, 0);
	private float pitch, yaw, roll;
	
	public Camera() {}

	public void move()
	{
		if(GLFW.glfwGetKey(GameWindow.window, GLFW.GLFW_KEY_UP) == GL11.GL_TRUE)
			position.z-=SPEED;
		if(GLFW.glfwGetKey(GameWindow.window, GLFW.GLFW_KEY_DOWN) == GL11.GL_TRUE)
			position.z+=SPEED;
		if(GLFW.glfwGetKey(GameWindow.window, GLFW.GLFW_KEY_LEFT) == GL11.GL_TRUE)
			position.x-=SPEED;
		if(GLFW.glfwGetKey(GameWindow.window, GLFW.GLFW_KEY_RIGHT) == GL11.GL_TRUE)
			position.x+=SPEED;
	}
	
	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}
	
}
