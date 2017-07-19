/**
 * 
 */
package repoviews.restapi;

import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import repoviews.cache.Memcached;
import repoviews.utils.JsonToMap;

/**
 * @author swati.p
 *
 */

@Path("/view")
public class RepoViewsService {

	@GET
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
	
	 
	@GET
	@Produces("application/json") @Path("/top/{n}/last_updated")
	public Response topReposByLastUpdated(@PathParam("n") int c) throws Exception {
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
		
		Map<String, String> outputMap = 
				JsonToMap.sliceMap(JsonToMap.generateReposByLastUpdatedMap(resultincache), c);
		return Response.status(200).entity(outputMap.toString()).build();
	}
	
	@GET
	@Produces("application/json") @Path("/top/{n}/open_issues")
	public Response topReposByOpenIssues(@PathParam("n") int c) {
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
				JsonToMap.sliceMap(JsonToMap.generateReposByOpenIssuesMap(resultincache), c);
		return Response.status(200).entity(outputMap.toString()).build();
	}
	
	@GET
	@Produces("application/json") @Path("/top/{n}/stars")
	public Response topReposByStars(@PathParam("n") int c) {
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
				JsonToMap.sliceMap(JsonToMap.generateReposByStarsMap(resultincache), c);
		return Response.status(200).entity(outputMap.toString()).build();
	}
	
	@GET
	@Produces("application/json") @Path("/top/{n}/watchers")
	public Response topReposByWatchers(@PathParam("n") int c) {
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
				JsonToMap.sliceMap(JsonToMap.generateReposByWatchersMap(resultincache), c);
		return Response.status(200).entity(outputMap.toString()).build();
	}
}
