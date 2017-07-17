/**
 * 
 */
package com.netflixgit.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author swati.p
 *
 */
public class JsonToMap {
	
	public static SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

	static Map<String, Integer> generateMapFromResponse(String keyElement,
			String valueElement, String jsontxt) {	
		
		Map<String, Integer> viewMap = new HashMap<String, Integer>();
		JSONArray json = new JSONArray(jsontxt);
        for (int i = 0; i < json.length(); i++) {
        	JSONObject innerObject = json.getJSONObject(i);
        	String[] elementNames = JSONObject.getNames(innerObject);
            String key = ""; int value = 0;
            for (String elementName : elementNames){
            	if (elementName.equals(keyElement))
            		key = innerObject.getString(elementName);
            	
            	if (elementName.equals(valueElement)) 
            		value = innerObject.getInt(elementName);

            	if (!key.isEmpty()) {
            		viewMap.put(key, value);
            	}
            }
        }
        // sort map on value desc
        TreeMap<String, Integer> sortedMap = sortMapByValue(viewMap);
		return sortedMap;
	}
	
	static Map<String, String> generateUpdatedMapFromJSON(String keyElement, 
			String valueElement, String jsontxt) throws ParseException {
		Map<String, Long> viewMap = new HashMap<String, Long>();
		JSONArray json = new JSONArray(jsontxt);
        for (int i = 0; i < json.length(); i++) {
        	JSONObject innerObject = json.getJSONObject(i);
        	String[] elementNames = JSONObject.getNames(innerObject);
            String key = ""; long value = 0;
            for (String elementName : elementNames){
            	if (elementName.equals(keyElement))
            		key = innerObject.getString(elementName);
            	
            	if (elementName.equals(valueElement)) {           		
            		String datestamp = innerObject.getString(elementName);
            		Date d = f.parse(datestamp);
            		value = d.getTime();
            	}

            	if (!key.isEmpty()) {
            		viewMap.put(key, value);
            	}
            }
        }
        // sort map on value desc
        TreeMap<String, Long> sortedMap = sortMapByValueLong(viewMap);
        
        //convert long timestamp to formatted string
        Map<String, String> updated = new LinkedHashMap<String, String>();
		for (Map.Entry<String, Long> entry : sortedMap.entrySet())
		{
			Date date = new Date(entry.getValue().longValue());
			String formatted_date = f.format(date);
			updated.put(entry.getKey(), formatted_date);
		}
		return updated;
	}
	
	static TreeMap<String, Integer> sortMapByValue(Map<String, Integer> map){
		Comparator<String> comparator = new ValueComparator<String, Integer>(map); 
		TreeMap<String, Integer> result = new TreeMap<String, Integer>(comparator);
		result.putAll(map);
		return result;
	}
	
	public static TreeMap<String, Long> sortMapByValueLong(Map<String, Long> map){
		Comparator<String> comparator = new ValueComparator<String, Long>(map); 
		TreeMap<String, Long> result = new TreeMap<String, Long>(comparator);
		result.putAll(map);
		return result;
	}
	
	public static Map<String, Integer> generateReposByForkMap(String jsontxt) {
		return generateMapFromResponse(Constants.MAPKEY, Constants.FORKCOUNTMAPVALUE, jsontxt);
	}
	
	public static Map<String, Integer> generateReposByWatchersMap(String jsontxt) {
		return generateMapFromResponse(Constants.MAPKEY, Constants.WATCHERSMAPVALUE, jsontxt);
	}
	
	public static Map<String, Integer> generateReposByOpenIssuesMap(String jsontxt) {
		return generateMapFromResponse(Constants.MAPKEY, Constants.OPENISSUEVALUE, jsontxt);
	}
	
	static Map<String, String> sortedByTimeMap(Map<String,Long> input) {
		Map<String, String> updated = new LinkedHashMap<String, String>();
		for (Map.Entry<String, Long> entry : input.entrySet()) {
			Date date = new Date(entry.getValue().longValue());
			String formatted_date = f.format(date);
			updated.put(entry.getKey(), formatted_date);
		}
		return updated;
	}
	
	public static Map<String, Integer> generateReposByStarsMap(String jsontxt) {
		return generateMapFromResponse(Constants.MAPKEY, Constants.STARSMAPVALUE, jsontxt);
	}
	
	public static Map<String, Integer> generateReposByLastUpdatedMap(String jsontxt) {
		return generateMapFromResponse(Constants.MAPKEY, Constants.LASTUPVALUE, jsontxt);
	}
	
	public static <K, V> Map<K,V> sliceMap(Map<K,V> map, int index) {
		Map<K,V> sliced = new LinkedHashMap<K, V>();
		int counter = 0;
		for (Map.Entry<K, V> entry : map.entrySet()) {
			sliced.put(entry.getKey(), entry.getValue());
			counter++;
			if (counter == index) break;
		}
		return sliced;
	}
}
