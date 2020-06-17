package graphics;

import static org.lwjgl.system.MemoryUtil.NULL;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;

public class DisplayManager {

	private static boolean glfwInitialized = false;
	
	//Creates a full screen window using LWJGL3
	public static long createWindow(String title) {
		
		//Initialize GLFW
		if (!GLFW.glfwInit())
			throw new IllegalStateException(
					"Can't initialize GLFW. (.jar is missing ?)");

		//Save to be sure that we do not end up initializing glfw multiple times
		if (glfwInitialized == false)
			glfwInitialized = true;

		//Setup glfw window settings
		GLFW.glfwDefaultWindowHints();
		GLFW.glfwWindowHint(GLFW.GLFW_DECORATED, GLFW.GLFW_FALSE);
		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_TRUE);
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE);

		//Gets the video mode of the user's primary monitor
		GLFWVidMode vidMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
		
		//Creates the window and stores it's id in a long type variable
		long window = GLFW.glfwCreateWindow(vidMode.width(), vidMode.height(), title, NULL, NULL);
		if(window == NULL)
			throw new RuntimeException("Failed to create GLFW window.");
		
		//Place the window on top left corner
		GLFW.glfwSetWindowPos(
				window,
				0, 0
		);
		
		//Binds the window context 
		GLFW.glfwMakeContextCurrent(window);
		GLFW.glfwSwapInterval(1); // 1 Update per swap
		GLFW.glfwShowWindow(window); // Shows the window
		
		return window; // Returns the created window's idea
	}

}
