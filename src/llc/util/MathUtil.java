package llc.util;

import org.lwjgl.util.vector.Vector3f;

public class MathUtil {

	/**
	 * Returns a Vector which is closer to 'to' than 'from' by increasing all axis with time
	 */
	public static Vector3f lerpVector(Vector3f from, Vector3f to, float time) {
		time = clamp(time);
		return new Vector3f(from.x + (to.x - from.x) * time, from.y + (to.y - from.y) * time, from.z + (to.z - from.z) * time);
	}
	
	/**
	 * Returns a float which is clamped between 0 and 1
	 */
	public static float clamp(float f) {
		return f > 1 ? 1F : f < 0 ? 0F : f;
	}
	
	/**
	 * Returns true when the difference between the vectors is less than maxDistance
	 */
	public static boolean areEquals(Vector3f vec1, Vector3f vec2, float maxDistance) {
		return areEquals(vec1.x, vec2.x, maxDistance) && areEquals(vec1.y, vec2.y, maxDistance) && areEquals(vec1.z, vec2.z, maxDistance);
	}
	
	/**
	 * Returns true when the difference between the two floats is less than maxDistance
	 */
	public static boolean areEquals(float f1, float f2, float maxDistance) {
		return Math.abs(f2 - f1) <= Math.abs(maxDistance);
	}
	
	/**
	 * Returns the smaller Vector
	 */
	public static Vector3f minVector3f(Vector3f v1, Vector3f v2) {
		return v1.x < v2.x || v1.y < v2.y || v1.z < v2.z ? v1 : v2;
	}
	
}
