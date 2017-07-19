/**
 * 
 */
package repoviews.utils;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * @author swati.p
 *
 */
public class ValueComparator<K, V extends Comparable<V>> implements Comparator<K> {
	
	Map<K,V> map = new HashMap<K,V>();

	
	public ValueComparator(Map<K, V> map) {
		super();
		this.map.putAll(map);
	}


	@Override
	public int compare(K o1, K o2) {
		// TODO Auto-generated method stub
		return -map.get(o1).compareTo(map.get(o2));
	}

}
