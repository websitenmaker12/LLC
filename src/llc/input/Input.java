package llc.input;

import java.util.ArrayList;
import java.util.List;

import llc.LLC;
import llc.engine.Camera;
import llc.engine.Triangle;
import llc.engine.gui.GUIElement;
import llc.engine.gui.screens.GUI;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class Input {
	private Camera Cam;

	public int scrollFrameBorder = 30;

	private Vector2f lastHoveredCell = null;
	private LLC LLC_ref;
	private Triangle[][][] triangles = null;

	private List<GUIElement> guiElements = new ArrayList<GUIElement>();

	public Input(LLC reference, Camera camera) {
		LLC_ref = reference;
		Cam = camera;
	}

	public void setGridGeometry(Triangle[][][] triangles) {
		this.triangles = triangles;
	}

	private Vector3f rayDirectionFromMousePos(int x, int y) {
		float windowWidth = LLC_ref.width;
		float windowHeight = LLC_ref.height;
		y = LLC_ref.height - y;
		float near = 0.1F;
		float fovy = 45;
		float aspect = windowWidth / windowHeight;

		float worldHeight = (float) (Math.tan(Math.toRadians(fovy / 2.0f)) * near) * 2.0f;
		float worldWidth = worldHeight * aspect;

		// float xWorld = (x / windowWidth) * worldWidth + xMin;
		// float yWorld = (y / windowHeight) * worldHeight + yMin;

		Vector3f xImagePlanePixelDeltaVector = Vector3f.cross(Cam.viewDir,
				Cam.up, null);
		Vector3f yImagePlanePixelDeltaVector = Vector3f.cross(
				xImagePlanePixelDeltaVector, Cam.viewDir, null);

		xImagePlanePixelDeltaVector.normalise().scale(
				worldWidth / (windowWidth - 1));
		yImagePlanePixelDeltaVector.normalise().scale(
				worldHeight / (windowHeight - 1));

		// System.out.println("x delta length: " +
		// xImagePlanePixelDeltaVector.length());
		// System.out.println("y delta length: " +
		// yImagePlanePixelDeltaVector.length());

		xImagePlanePixelDeltaVector.scale(x - windowWidth / 2.0f);
		yImagePlanePixelDeltaVector.scale(y - windowHeight / 2.0f);

		// System.out.println("x delta " + xImagePlanePixelDeltaVector);
		// System.out.println("y delta " + yImagePlanePixelDeltaVector);

		Vector3f nearVector = (Vector3f) new Vector3f(Cam.viewDir).normalise()
				.scale(near);
		Vector3f imagePlaneMousePos = new Vector3f(
				Cam.pos.x + nearVector.x + xImagePlanePixelDeltaVector.x
						+ yImagePlanePixelDeltaVector.x, Cam.pos.y
						+ nearVector.y + xImagePlanePixelDeltaVector.y
						+ yImagePlanePixelDeltaVector.y, Cam.pos.z
						+ nearVector.z + xImagePlanePixelDeltaVector.z
						+ yImagePlanePixelDeltaVector.z);

		// System.out.println("image plane: " + imagePlaneMousePos);

		Vector3f rayDirection = (Vector3f) Vector3f.sub(imagePlaneMousePos,
				Cam.pos, null).normalise();

		return rayDirection;
	}

	/**
	 * Raycasts a given mouse position into the scene and calculates the
	 * intersected cell on the XY layer (z == 0)
	 */
	@SuppressWarnings("unused")
	private Vector2f rayCastZ0(Vector3f rayDirection) {

		// System.out.println("ray: " + rayDirection);
		float t = (0 - Cam.pos.z) / rayDirection.z; // z of the grid is 0

		Vector3f intersection = Vector3f.add(Cam.pos,
				(Vector3f) rayDirection.scale(t), null);
		// System.out.println("Intersect: " + intersection);

		int cell_x = (int) intersection.x;
		int cell_y = (int) intersection.y;

		// System.out.println("clicked " + cell_x + " " + cell_y);

		Vector2f cellPos = new Vector2f(cell_x, cell_y);
		return cellPos;
	}

	// / From paper: "Fast Minimum Storage Ray/Triangle Intersection"
	private boolean intersectTriangle(Vector3f origin, Vector3f direction,
			Triangle t) {
		final float INTERSECTION_EPSILON = 0.0001f;

		Vector3f vertex0 = t.vertices[0].position;
		Vector3f edge1 = Vector3f.sub(t.vertices[1].position, vertex0, null);
		Vector3f edge2 = Vector3f.sub(t.vertices[2].position, vertex0, null);

		// begin calculating determinant - also used to calculate U parameter
		Vector3f pvec = Vector3f.cross(direction, edge2, null);

		// if determinant is near zero, ray lies in plane of triangle
		float det = Vector3f.dot(edge1, pvec);

		if (det > -INTERSECTION_EPSILON && det < INTERSECTION_EPSILON)
			return false;
		float invDet = 1.0f / det;

		// calculate distance from vert0 to ray origin
		Vector3f tvec = Vector3f.sub(origin, vertex0, null);

		// calculate U parameter and test bounds
		float u = Vector3f.dot(tvec, pvec) * invDet;
		if (u < 0.0f || u > 1.0f)
			return false;

		// prepare to test V parameter
		Vector3f qvec = Vector3f.cross(tvec, edge1, null);

		// calculate V parameter and test bounds
		float v = Vector3f.dot(direction, qvec) * invDet;
		if (v < 0.0f || u + v > 1.0f)
			return false;

		// calculate distance, ray intersects triangle
		//float distance = Vector3f.dot(edge2, qvec) * invDet;

		return true;
	}

	/**
	 * Raycasts a given mouse position into the scene and calculates the
	 * intersected cell (taking cell heights into account)
	 */
	private Vector2f rayCast(int x, int y) {
		Vector3f rayDirection = rayDirectionFromMousePos(x, y);

		int height = triangles.length;
		int width = triangles[0].length;
		for (int cellY = 0; cellY < height; cellY++)
			for (int cellX = 0; cellX < width; cellX++)
				for (Triangle t : triangles[cellY][cellX])
					if (intersectTriangle(Cam.pos, rayDirection, t))
						return new Vector2f(cellX, cellY);

		return new Vector2f(-1, -1);
	}

	public void mouseClick(int x, int y) {
		Vector2f Ray = rayCast(x, y);
		if(!isMouseOverGUI(x, y)) FireCellClickedEvent((int) Ray.x, (int) Ray.y);
	}

	public void mousePos(int x, int y) {

		int h = LLC_ref.height;
		int w = LLC_ref.width;

		if(!isMouseOverGUI(x, y)) {
			if (x < scrollFrameBorder) FireScrollEvent(Direction.left);
			if (x > w - scrollFrameBorder) FireScrollEvent(Direction.right);
	
			if (y < scrollFrameBorder) FireScrollEvent(Direction.up);
			if (y > h - scrollFrameBorder) FireScrollEvent(Direction.down);
	
			Vector2f hoveredCell = rayCast(x, y);
			if (hoveredCell != lastHoveredCell) {
				lastHoveredCell = hoveredCell;
				FireNewCellHoveredEvent((int) hoveredCell.x, (int) hoveredCell.y);
			}
		}
	}

	private boolean isMouseOverGUI(int x, int y) {
		for(GUIElement element : this.guiElements) if(element.isHovered(x, y)) return true;
		return false;
	}
	
	public void guiChange(GUI gui) {
		this.guiElements = gui.getElements();
	}

	// ------------- Interface for the Listener -------------
	public interface LogicListener {
		public void onScroll(Direction d);

		public void onCellClicked(int cell_x, int cell_y);

		public void onNewCellHovered(int cell_x, int cell_y);
	}

	public enum Direction {
		left, right, down, up;
	}

	// -------------------------------------------------------

	List<LogicListener> listeners = new ArrayList<LogicListener>();

	// ------------ Function to add yourself as Listener
	public void addFireListener(LogicListener toAdd) {
		listeners.add(toAdd);
	}

	public void FireScrollEvent(Direction d) {
		for (LogicListener hl : listeners)
			hl.onScroll(d);
	}

	public void FireCellClickedEvent(int cell_x, int cell_y) {
		for (LogicListener hl : listeners)
			hl.onCellClicked(cell_x, cell_y);
	}

	public void FireNewCellHoveredEvent(int cell_x, int cell_y) {
		for (LogicListener hl : listeners)
			hl.onNewCellHovered(cell_x, cell_y);
	}

}
