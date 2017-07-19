/**
 * 
 */
package com.netflixgit.utils;

/**
 * @author swati.p
 *
 */
public class ConnectionUtil {
	
	public static int TotalPages(String link) {
		if (link.isEmpty()) return 0;
		
		String[] linkTokens = link.split(",");
		for (String token : linkTokens) {
			if (token.contains("last")) {
				String totalPages = token.substring(token.lastIndexOf("&")+1, 
						token.indexOf(">"));
				if (totalPages.contains("="))
					return Integer.parseInt(totalPages.split("=")[1]);
			}
		}
		return 1;
	}
	
}
