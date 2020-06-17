package graphics.memory;

import graphics.ModelTexture;

public class Model {

	private VertexArray vao;
	private ModelTexture texture;
	
	private float shineDamper = 1f;
	private float reflectivity = 0f;
	
	public Model(VertexArray vao, ModelTexture texture)
	{
		this.vao = vao;
		this.texture = texture;
	}

	public void render()
	{
		texture.bind();
		vao.render();
		texture.unbind();
	}
	
	public float getShineDamper() {
		return shineDamper;
	}

	public void setShineDamper(float shineDamper) {
		this.shineDamper = shineDamper;
	}

	public float getReflectivity() {
		return reflectivity;
	}

	public void setReflectivity(float reflectivity) {
		this.reflectivity = reflectivity;
	}

	public VertexArray getVao() {
		return vao;
	}

	public ModelTexture getTexture() {
		return texture;
	}
	
}
