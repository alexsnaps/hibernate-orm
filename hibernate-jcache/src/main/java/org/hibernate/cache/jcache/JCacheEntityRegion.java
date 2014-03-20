package org.hibernate.cache.jcache;

import javax.cache.Cache;

import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.EntityRegion;
import org.hibernate.cache.spi.access.AccessType;
import org.hibernate.cache.spi.access.EntityRegionAccessStrategy;

/**
 * @author Alex Snaps
 */
public class JCacheEntityRegion extends JCacheTransactionalDataRegion implements EntityRegion {

	public JCacheEntityRegion(Cache<Object, Object> cache) {
		super( cache );
	}

	@Override
	public EntityRegionAccessStrategy buildAccessStrategy(AccessType accessType) throws CacheException {
		throw new UnsupportedOperationException( "Implement me!" );
	}

}
