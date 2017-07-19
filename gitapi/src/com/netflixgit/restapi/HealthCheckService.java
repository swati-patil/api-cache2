/**
 * 
 */
package com.netflixgit.restapi;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import com.netflixgit.cache.Memcached;
import com.netflixgit.utils.HelperUtil;
//import com.sun.jersey.api.core.ResourceConfig;

import net.spy.memcached.MemcachedClient;

/**
 * @author swati.p
 *
 */
@Path("/healthcheck")
public class HealthCheckService { 
	
	@GET
	@Produces("application/json")
	public Response checkDependencies() {
		JSONObject jsonObject = new JSONObject();
		String memcacheRunStatus = "";
		boolean memcacheProcess = Boolean.TRUE;
		// check if memcached server is up.
		try {
			MemcachedClient client = Memcached.getInstance();
			boolean mstatus = Memcached.checkMemcachedConnection();
			client.add("test:key", 3600, "test value");
			memcacheRunStatus = "Memcached service is running";
		} catch (Exception e) {
			memcacheRunStatus = "Memcached service is not running";
			memcacheProcess = Boolean.FALSE;
		}
		
		//check if webserver is running
		
		int status = HelperUtil.checkServer("localhost", 
				8080);
		String serverStatusVerbose = "";
		if (status == 0) serverStatusVerbose = "Server is running";
		else if (status == 1) serverStatusVerbose = "Server URL isn't right";
		else if (status == 2) serverStatusVerbose = "Server isn't responding";
		int statusCode = 200;
		
		if (!memcacheProcess || (status == 1 || status == 2))
			statusCode = 500;
		
		jsonObject.put("Memcached Status", memcacheRunStatus);
		jsonObject.put("Webserver Status", serverStatusVerbose);
		
		StringBuilder result = new StringBuilder("Health Check Status - ")
				.append(System.getProperty("line.separator"))
				.append(jsonObject);
		
		return Response.status(statusCode).entity(result.toString()).build();
	}

}
