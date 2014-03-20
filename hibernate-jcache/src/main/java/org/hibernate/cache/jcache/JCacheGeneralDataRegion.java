package org.hibernate.cache.jcache;

import javax.cache.Cache;

import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.GeneralDataRegion;

/**
 * @author Alex Snaps
 */
public class JCacheGeneralDataRegion extends JCacheRegion implements GeneralDataRegion {

	public JCacheGeneralDataRegion(Cache<Object, Object> cache) {
		super( cache );
	}

	public Object get(Object key) throws CacheException {
		return cache.get( key );
	}

	public void put(Object key, Object value) throws CacheException {
		cache.put( key, value );
	}

	public void evict(Object key) throws CacheException {
		cache.remove( key );
	}

	public void evictAll() throws CacheException {
		cache.removeAll();
	}

}
