/**
 * 
 */
package com.netflixgit.invokers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

import com.netflixgit.utils.Constants;

/**
 * @author swati.p
 *
 */
public class HttpURLConnector {
	
	HttpURLConnection conn = null;

	public HttpURLConnection executeGetRequest(String url) throws Exception{
		try {
			URL serverUrl = new URL(url);
			conn = (HttpURLConnection) serverUrl.openConnection();
			//set request method
			conn.setRequestMethod("GET");
			conn.setRequestProperty("User-Agent", "NetFlix git automation");
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}

	
	public String getPaginationHeader() throws IOException {
		if (conn != null) {
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				return conn.getHeaderField(Constants.LINK_HEADER);
			}
		}
		return null;		
	}
	
	public String getResponse() throws IOException {
		StringBuffer response = new StringBuffer();
		if (conn != null) {
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(conn.getInputStream()));
			
			String inputLine = "";
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			//print result
			//JSONArray resJson  = new JSONArray(response.toString().trim());
		}
		return response.toString();
	}
}
