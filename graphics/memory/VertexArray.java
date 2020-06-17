package graphics.memory;

import graphics.Shader;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import utils.GLUtils;

public class VertexArray {

	//Draw type (by default triangles)
	private int type = GL11.GL_TRIANGLES;
	
	private int vao, ibo;
	private int count;
	
	public VertexArray(int count)
	{
		this.count = count;
		vao = GL30.glGenVertexArrays();
	}
	
	//Creates a textured model's VAO
	public VertexArray(float[] vertices, int[] indices, float[] textureCoords, float[] normals)
	{
		count = indices.length;
		
		vao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vao);
		
		storeDataInAttributeList(Shader.VERTEX_ATTRIB, 3, vertices);
		storeDataInAttributeList(Shader.NORMAL_ATTRIB, 3, normals);
		storeDataInAttributeList(Shader.TEXTURE_ATTRIB, 2, textureCoords);
		
		ibo = GL30.glGenBuffers();
		GL30.glBindBuffer(GL30.GL_ELEMENT_ARRAY_BUFFER, ibo);
		GL30.glBufferData(GL30.GL_ELEMENT_ARRAY_BUFFER, GLUtils.storeDataInIntBuffer(indices), GL30.GL_STATIC_DRAW);
	
		//Unbind all
		GL30.glBindBuffer(GL30.GL_ELEMENT_ARRAY_BUFFER, 0);
		GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, 0);
		GL30.glBindVertexArray(0);
	}
	
	private void storeDataInAttributeList(int index, int size, float[] data)
	{
		int vbo = GL30.glGenBuffers();
		GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, vbo);
		GL30.glBufferData(GL30.GL_ARRAY_BUFFER, GLUtils.storeDataInFloatBuffer(data), GL30.GL_STATIC_DRAW);
		GL30.glVertexAttribPointer(index, size, GL11.GL_FLOAT, false, 0, 0);
		GL30.glEnableVertexAttribArray(index);
	}
	
	//Changes the draw type of the vertex array
	public void setType(int gl_type)
	{
		this.type = gl_type;
	}
	
	//Binds the vertex array and indices for GL_STATIC_DRAW purpose
	public void bind()
	{
		GL30.glBindVertexArray(vao);
		if(ibo > 0)
			GL30.glBindBuffer(GL30.GL_ELEMENT_ARRAY_BUFFER, ibo);
	}
	
	//Unbinds the vertex array and indices
	public void unbind()
	{
		GL30.glBindVertexArray(0);
		if(ibo > 0)
			GL30.glBindBuffer(GL30.GL_ELEMENT_ARRAY_BUFFER, 0);
	}
	
	//Executes draw call of current vao
	private void draw()
	{
		if(ibo > 0 )
			GL11.glDrawElements(type, count, GL11.GL_UNSIGNED_INT, 0);
		else
			GL11.glDrawArrays(type, 0, count);
	}
	
	//Renders the vao to the screen
	public void render()
	{
		bind();
		draw();
		unbind();
	}
	
}
