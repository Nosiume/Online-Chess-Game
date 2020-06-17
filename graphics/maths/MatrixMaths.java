package graphics.maths;

import game.game.GameWindow;
import game.settings.GameValues;
import graphics.system.Camera;

import java.nio.IntBuffer;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryStack;
import org.lwjglx.util.vector.Matrix4f;
import org.lwjglx.util.vector.Vector3f;

public class MatrixMaths 
{
	public static Matrix4f createViewMatrix(Camera camera)
	{
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		matrix.rotate((float) Math.toRadians(camera.getPitch()), new Vector3f(1, 0, 0));
		matrix.rotate((float) Math.toRadians(camera.getYaw()), new Vector3f(0, 1, 0));
		matrix.rotate((float) Math.toRadians(camera.getRoll()), new Vector3f(0, 0, 1));
		
		Vector3f cameraPos = camera.getPosition();
		Vector3f negativeCameraPos = new Vector3f(-cameraPos.x, -cameraPos.y, -cameraPos.z);
		matrix.translate(negativeCameraPos);
		return matrix;
	}
	
	public static Matrix4f createTransform(
			Vector3f translation,
			Vector3f rotation,
			float scale)
	{
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		matrix.translate(translation);
		matrix.rotate((float) Math.toRadians(rotation.x), new Vector3f(1, 0, 0));
		matrix.rotate((float) Math.toRadians(rotation.y), new Vector3f(0, 1, 0));
		matrix.rotate((float) Math.toRadians(rotation.z), new Vector3f(0, 0, 1));
		matrix.scale(new Vector3f(scale, scale, scale));
		return matrix;
	}
	
	public static Matrix4f getProjectionMatrix()
	{
		IntBuffer width = null;
		IntBuffer height = null;
		try (MemoryStack stack = MemoryStack.stackPush())
		{
			width = stack.mallocInt(1);
			height = stack.mallocInt(1);
			
			GLFW.glfwGetWindowSize(GameWindow.window, width, height);
		}
		
		float aspectRatio = (float) width.get() / (float) height.get();
		float y_scale = (float) ((1f / Math.tan(Math.toRadians(GameValues.FOV / 2))) * aspectRatio);
		float x_scale = y_scale / aspectRatio;
		float frustumLength = GameValues.FAR_PLANE - GameValues.NEAR_PLANE;
		
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		matrix.m00 = x_scale;
		matrix.m11 = y_scale;
		matrix.m22 = -((GameValues.FAR_PLANE + GameValues.NEAR_PLANE) / frustumLength);
		matrix.m23 = -1f;
		matrix.m32 = -((2 * GameValues.NEAR_PLANE * GameValues.FAR_PLANE) / frustumLength);
		matrix.m33 = 0f;
		
		return matrix;
	}
	
}
