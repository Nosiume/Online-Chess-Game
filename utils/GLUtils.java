package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjglx.util.vector.Matrix4f;

public class GLUtils {

	public static String loadShaderData(String src)
	{
		try {
			StringBuilder sb = new StringBuilder();
			BufferedReader reader = new BufferedReader(new FileReader(new File(src)));
			
			String buffer = "";
			while((buffer = reader.readLine()) != null)
			{
				sb.append(buffer + '\n');
			}
			
			reader.close();
			return sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void printMatrix(Matrix4f matrix)
	{
		System.out.println("(" + matrix.m00 + ", " + matrix.m01 + ", " + matrix.m02 + ", " + matrix.m03 + ")");
		System.out.println("(" + matrix.m10 + ", " + matrix.m11 + ", " + matrix.m12 + ", " + matrix.m13 + ")");
		System.out.println("(" + matrix.m20 + ", " + matrix.m21 + ", " + matrix.m22 + ", " + matrix.m23 + ")");
		System.out.println("(" + matrix.m30 + ", " + matrix.m31 + ", " + matrix.m32 + ", " + matrix.m33 + ")");
	}
	
	public static FloatBuffer storeDataInFloatBuffer(float[] data)
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data).flip();
		return buffer;
	}
	
	public static IntBuffer storeDataInIntBuffer(int[] data)
	{
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data).flip();
		return buffer;
	}
	
}
