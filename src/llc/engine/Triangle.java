package llc.engine;

public class Triangle {

	public Vertex[] vertices = new Vertex[3];
	
	public Triangle(Vertex vertex1, Vertex vertex2, Vertex vertex3) {
		vertices[0] = vertex1;
		vertices[1] = vertex2;
		vertices[2] = vertex3;
	}

}
