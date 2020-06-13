package game.game;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import graphics.DisplayManager;

public class GameWindow {

	//Game window id
	private long window;
	
	public GameWindow()
	{
		window = DisplayManager.createWindow("Online Chess");
		DisplayManager.addKeyListener(window, new Keyboard());
	}
	
	private void init()
	{
		GL.createCapabilities();
		
		GL11.glClearColor(0.8f, 0.8f, 0.8f, 1f);
	}
	
	//Game update
	private void update()
	{
		
	}
	
	//Game rendering
	private void render()
	{
		//Clears screen
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		
		//Rendering stuff
		GL11.glBegin(GL11.GL_TRIANGLES);
		GL11.glColor3f(1.0f, 0.0f, 0.0f);
		GL11.glVertex2f(0.0f, 0.5f);
		GL11.glColor3f(0.0f, 1.0f, 0.0f);
		GL11.glVertex2f(-0.5f, -0.5f);
		GL11.glColor3f(0.0f, 0.0f, 1.0f);
		GL11.glVertex2f(0.5f, -0.5f);
		GL11.glEnd();
		
		//Swap buffers and update events
		GLFW.glfwSwapBuffers(window);
		GLFW.glfwPollEvents();
	}
	
	//Main game loop
	public void loop()
	{
		init();
		
		while(!GLFW.glfwWindowShouldClose(window))
		{
			update();
			render();
		}
		
		GLFW.glfwDestroyWindow(window);
	}
	
}
