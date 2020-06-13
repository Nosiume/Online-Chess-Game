package game.game;

import org.lwjgl.glfw.GLFW;

import graphics.GLFWKeyListener;

public class Keyboard extends GLFWKeyListener {

	@Override
	public void keyUpdate(long window, int key, int scancode, int action,
			int mods) {
		
		if(key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_RELEASE)
		{
			GLFW.glfwSetWindowShouldClose(window, true);
			System.exit(0);
		}
		
	}

}
