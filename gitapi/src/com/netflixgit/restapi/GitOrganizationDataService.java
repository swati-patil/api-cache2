/**
 * 
 */
package com.netflixgit.restapi;


import java.net.HttpURLConnection;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.netflixgit.cache.Memcached;
import com.netflixgit.invokers.HttpURLConnector;
import com.netflixgit.utils.ConnectionUtil;
import com.netflixgit.utils.Constants;
import com.netflixgit.utils.HelperUtil;
import com.netflixgit.utils.JsonToMap;


/**
 * @author swati.p
 *
 */

@Path("/")
public class GitOrganizationDataService {
	
	@GET
	@Path("{any: .*}")
	@Produces("application/json") 
	public Response invokeGitAPI(@Context HttpServletRequest request) {
		String url = request.getRequestURL().toString();
		Logger.getLogger(this.getClass().getName()).log(Level.INFO, url);
		
		String redirect_segment = url.substring(Constants.BASEURI.length(), url.length());
		StringBuilder response = new StringBuilder();
		HttpURLConnector executor = new HttpURLConnector();
		HttpURLConnection connection = null; 
		
		try {
			String resultincache = Memcached.fetchValueFromCache(redirect_segment);
			String executeUrl = HelperUtil.generateGitAPI(redirect_segment);
			if (resultincache == null || resultincache.isEmpty()) {
				Logger.getLogger(this.getClass().getName()).log(Level.INFO, 
						HelperUtil.generateGitAPI(redirect_segment));
				connection = executor.executeGetRequest(executeUrl);
				if (connection != null) {
					String jsonString = parseOutputData(connection, executor, executeUrl);
					response.append(jsonString);
					Memcached.setValueInCache(redirect_segment, response.toString());
				}
			} else {
				Logger.getLogger(this.getClass().getName()).log(Level.INFO, 
						"Reading value from cache");
				response.append(resultincache);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO Auto-generated catch block
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, e.toString());
		}
		
		return Response.status(200).entity(response.toString()).build();
	}
	
	@Produces("application/json") @Path("/top/{n}/forks")
	public Response topReposByFork(@PathParam("n") int c) {
		String resultincache = "";
		try {
			resultincache = Memcached.fetchValueFromCache("/orgs/Netflix/repos");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.status(500).entity("Cache could not be recoved. Please check if memcached is running").build();
		}
		
		if (resultincache == null || resultincache.isEmpty()) {
			return Response.status(500).entity("Data is not cached").build();
		}
		
		Map<String, Integer> outputMap = 
				JsonToMap.sliceMap(JsonToMap.generateReposByForkMap(resultincache), c);
		return Response.status(200).entity(outputMap.toString()).build();
	} 
	
	public static String parseOutputData(HttpURLConnection conn, 
			HttpURLConnector exe, String url) throws Exception {
		String returnValue = "";
		int pages = 0;
		JSONArray resJson  = new JSONArray();
		String paginationHeader = exe.getPaginationHeader();
		String res = exe.getResponse();
		Object json = new JSONTokener(res).nextValue();
		if (json instanceof JSONArray) {
			resJson = new JSONArray(res); // json
			if (paginationHeader != null) {
				pages = ConnectionUtil.TotalPages(paginationHeader);
			}
			if (pages > 1) {
				for (int i = 2; i < pages + 1; i++) {
					url = url + "&page=" + i;
					conn = exe.executeGetRequest(url);
					res = exe.getResponse();
					JSONArray pagedRes = new JSONArray(res);
					for (int ind = 0; ind < pagedRes.length(); ind++) {
						resJson.put(pagedRes.getJSONObject(ind));
					}
				}
			}
			returnValue = resJson.toString(4);
		} else {
			JSONObject data = new JSONObject(res);
			returnValue = data.toString(4);
		}
		return returnValue;
	}
}
