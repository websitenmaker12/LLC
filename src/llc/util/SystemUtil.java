package llc.util;

/**
 * Compare installed java version with required java version
 * @author erdlof
 */

public class SystemUtil {
	public static int compareVersions(String version1, String version2) {
		String[] temp1 = version1.split("\\.");
		String[] temp2 = version2.split("\\.");
		
		int i = 0;
		
		while (i < temp1.length && i < temp2.length && temp1[i].equals(temp2[i])) {
			i++;
		}
		
		if (i < temp1.length && i < temp2.length) {
			int difference = Integer.valueOf(temp1[i]).compareTo(Integer.valueOf(temp2[i]));
			return Integer.signum(difference);
		} else {
			return Integer.signum(temp1.length - temp2.length);
		}
	}
}
