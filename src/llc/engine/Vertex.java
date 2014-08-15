package llc.engine;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class Vertex {
	public Vector3f position;
	public Vector3f normal;
	public Vector2f texCoord;


	public Vertex(Vector3f position, Vector3f normal, Vector2f texCoord) {
		this.position = position;
		this.normal = normal;
		this.texCoord = texCoord;
	}
}
