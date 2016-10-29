package sum.util;

import java.util.HashMap;

/**
 * a utility class that returns 0 if the map does not contain the key
 * @author jinfeng
 *
 */
public class MapUtil {
	public static double get(HashMap<String, Double> map, String key) {
		if(map.containsKey(key))
			return map.get(key);
		else
			return 0;
	}
}
