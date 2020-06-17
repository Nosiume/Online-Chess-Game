package game.game;

import graphics.DisplayManager;
import graphics.ModelTexture;
import graphics.OBJLoader;
import graphics.Shader;
import graphics.memory.Model;
import graphics.memory.VertexArray;
import graphics.system.Camera;
import graphics.system.Entity;
import graphics.system.Light;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjglx.util.vector.Vector3f;

public class GameWindow {

	//Game window id (static because we only want 1 game window)
	public static long window;
	
	//Elements or Debugging
	Shader shader;
	Entity entity;
	Camera camera;
	Light light;
	
	public GameWindow()
	{
		window = DisplayManager.createWindow("Online Chess");
	}
	
	private void init()
	{
		GL.createCapabilities();
		
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClearColor(0.168f, 0.678f, 0.772f, 1f);
		
		camera = new Camera();
		
		shader = new Shader("res/shaders/shader.vert", "res/shaders/shader.frag");
		shader.enable();
		
		light = new Light(new Vector3f(0, 0, 20f), new Vector3f(1f, 1f, 1f));
		VertexArray vao = OBJLoader.loadOBJ("res/models/debug.obj");
		ModelTexture texture = new ModelTexture("res/models/debug.png");
		Model model = new Model(vao, texture);
		model.setShineDamper(10f);
		model.setReflectivity(1f);
		
		entity = new Entity(model, new Vector3f(0, 0, -20f), new Vector3f(0, 0, 0), 1f);
	}
	
	//Game update
	private void update()
	{
		entity.rotate(0, 0.02f, 0);
		camera.move();
	}
	
	//Game rendering
	private void render()
	{
		//Clears screen
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		
		//Rendering stuff
		shader.loadLight(light);
		entity.render(shader, camera);
		
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
			if(GLFW.glfwGetKey(window, GLFW.GLFW_KEY_ESCAPE) == GL11.GL_TRUE)
				break;
			
			update();
			render();
		}
		
		shader.disable();
		GL20.glDisableVertexAttribArray(Shader.VERTEX_ATTRIB);
		GL20.glDisableVertexAttribArray(Shader.TEXTURE_ATTRIB);
		GL20.glDisableVertexAttribArray(Shader.NORMAL_ATTRIB);
		
		ModelTexture.cleanUp();
		GLFW.glfwDestroyWindow(window);
		System.exit(0);
	}
	
}
