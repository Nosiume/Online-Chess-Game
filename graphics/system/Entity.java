package graphics.system;

import graphics.Shader;
import graphics.maths.MatrixMaths;
import graphics.memory.Model;

import org.lwjgl.opengl.GL20;
import org.lwjglx.util.vector.Matrix4f;
import org.lwjglx.util.vector.Vector3f;

public class Entity {

	private Model model;
	protected Vector3f rotation;
	protected Vector3f position;
	protected float scale;
	
	public Entity(Model model, Vector3f pos, Vector3f rotation, float scale)
	{
		this.model = model;
		this.rotation = rotation;
		this.position = pos;
		this.scale = scale;
	}
	
	//Move entity function
	public void move(float dx, float dy, float dz)
	{
		position.x += dx;
		position.y += dy;
		position.z += dz;
	}
	
	//Rotate entity function
	public void rotate(float dx, float dy, float dz)
	{
		rotation.x += dx;
		rotation.y += dy;
		rotation.z += dz;
	}
	
	//Renders an entity
	public void render(Shader shader, Camera camera)
	{
		Matrix4f transform = 
				MatrixMaths.createTransform(position, rotation, scale);
		
		shader.enable();
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		
		int transformLoc = shader.getUniformLocation("transform");
		shader.loadProjectionMatrix();
		shader.loadViewMatrix(camera);
		shader.loadMatrix(transformLoc, transform);
		shader.loadShineValues(model.getShineDamper(), model.getReflectivity());
		
		model.render();
		
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(0);
		shader.disable();
	}

	//========= GETTERS and SETTERS =========
	
	public Model getModel() {
		return model;
	}

	public Vector3f getRotation() {
		return rotation;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public void setRotation(Vector3f rotation) {
		this.rotation = rotation;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public Vector3f getPosition() {
		return position;
	}

	public float getScale() {
		return scale;
	}
	
}
