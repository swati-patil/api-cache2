/**
 * 
 */
package com.netflixgit.utils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author swati.p
 *
 */
public class HelperUtil {
	
	public static int checkServer (String hostname, int port) {
		int status = 0;
		StringBuilder url = new StringBuilder("http://").append(hostname)
				.append(":").append(port);
		
		try {
			URL serverUrl = new URL(url.toString());
			URLConnection conn = serverUrl.openConnection();
			conn.connect();
		} catch (MalformedURLException e) {
			status = 1;
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			status = 2;
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return status;
	}
	
	public static String generateGitAPI (String segment) {
		StringBuilder url = new StringBuilder("");
		url.append(Constants.GITAPI).append(segment).append("?access_token=")
			.append(System.getenv("GITHUB_API_TOKEN"));
		return url.toString();
	}

}
