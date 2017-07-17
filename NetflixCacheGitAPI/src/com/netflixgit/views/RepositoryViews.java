/**
 * 
 */
package com.netflixgit.views;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import com.netflixgit.utils.JsonToMap;
import com.netflixgit.utils.ValueComparator;

/**
 * @author swati.p
 *
 */
public class RepositoryViews {
	
	public static Map<String, Integer> topReposByFork (String data, int number) {
		Map<String, Integer> outputMap = new LinkedHashMap<String, Integer>();
		if (data == null || data.isEmpty()) {
			outputMap.putAll(
				JsonToMap.sliceMap(JsonToMap.generateReposByForkMap(data), number));
		}
		return outputMap;
		
	}
	
	public static TreeMap<String, Integer> sortMapByValue(Map<String, Integer> map){
		Comparator<String> comparator = new ValueComparator<String, Integer>(map); 
		TreeMap<String, Integer> result = new TreeMap<String, Integer>(comparator);
		result.putAll(map);
		return result;
	}

}
