package graphics;

public abstract class GLFWKeyListener {
	
	//Will be called when the GLFWKeyCallbackI's invoke func will be called.
	public abstract void keyUpdate(long window, int key, int scancode, int action, int 	mods);
	
}
