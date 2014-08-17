package llc.util;

import org.lwjgl.util.vector.Vector3f;

public class MathUtil {

	public static Vector3f lerpVector(Vector3f from, Vector3f to, float time) {
		time = clamp(time);
		return new Vector3f(from.x + (to.x - from.x) * time, from.y + (to.y - from.y) * time, from.z + (to.z - from.z) * time);
	}
	
	public static float clamp(float f) {
		return f > 1 ? 1F : f < 0 ? 0F : f;
	}
	
	public static boolean areEquals(Vector3f vec1, Vector3f vec2, float maxDistance) {
		return areEquals(vec1.x, vec2.x, maxDistance) && areEquals(vec1.y, vec2.y, maxDistance) && areEquals(vec1.z, vec2.z, maxDistance);
	}
	
	public static boolean areEquals(float f1, float f2, float maxDistance) {
		return Math.abs(f2 - f1) <= Math.abs(maxDistance);
	}
	
}
