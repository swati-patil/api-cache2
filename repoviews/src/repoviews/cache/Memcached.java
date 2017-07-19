/**
 * 
 */
package repoviews.cache;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.MemcachedNode;
import repoviews.utils.Constants2;

/**
 * @author swati.p
 *
 */
public class Memcached {
	private static MemcachedClient cacheHandle = null;
	
	private Memcached() {}
	
	public static MemcachedClient getInstance() throws Exception {
		if (cacheHandle == null) {
			try {
				cacheHandle = new MemcachedClient(new InetSocketAddress("localhost", 11211));
				Logger.getLogger(Memcached.class.getName()).log(Level.INFO, "memcache client created");
			} catch (IOException ex) {
				Logger.getLogger(Memcached.class.getName()).log(Level.INFO, ex.getMessage(), ex);
				throw ex;
			}
		}
		return cacheHandle;
    }
	
	public static boolean checkMemcachedConnection() throws Exception {
		cacheHandle = getInstance();
		boolean connStatus = Boolean.TRUE;
		for (MemcachedNode node : cacheHandle.getNodeLocator().getAll()) {
			if (!node.isActive()) {
				cacheHandle.shutdown();
				connStatus = Boolean.FALSE;
				break;
			}
		}
		return connStatus;
	}
	
	public static String fetchValueFromCache (String key) throws Exception {
		String result = "";
		cacheHandle = getInstance();
		result = (String) cacheHandle.get(Constants2.CACHE_PREFIX+key);
		return result;
	}
	
	public static void setValueInCache (String key, String value) throws Exception {
		cacheHandle = getInstance();
		if (Constants2.urlsToBeCached.contains(key))
			cacheHandle.set(Constants2.CACHE_PREFIX+key, 3600, value);
		else {
			//ignore
			Logger.getLogger(Memcached.class.getName()).log(Level.INFO, "Key is ignored and not cached in cache");
		}
	}

}

