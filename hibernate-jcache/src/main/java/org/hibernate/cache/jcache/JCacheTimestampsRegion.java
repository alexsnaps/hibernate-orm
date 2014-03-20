package org.hibernate.cache.jcache;

import javax.cache.Cache;

import org.hibernate.cache.spi.TimestampsRegion;

/**
 * @author Alex Snaps
 */
public class JCacheTimestampsRegion extends JCacheGeneralDataRegion implements TimestampsRegion {

	public JCacheTimestampsRegion(Cache<Object, Object> cache) {
		super( cache );
	}

}
