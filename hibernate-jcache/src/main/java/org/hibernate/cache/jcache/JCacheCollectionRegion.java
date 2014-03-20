package org.hibernate.cache.jcache;

import javax.cache.Cache;

import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.CollectionRegion;
import org.hibernate.cache.spi.access.AccessType;
import org.hibernate.cache.spi.access.CollectionRegionAccessStrategy;

/**
 * @author Alex Snaps
 */
public class JCacheCollectionRegion extends JCacheTransactionalDataRegion implements CollectionRegion {

	public JCacheCollectionRegion(Cache<Object, Object> cache) {
		super( cache );
	}

	@Override
	public CollectionRegionAccessStrategy buildAccessStrategy(AccessType accessType) throws CacheException {
		throw new UnsupportedOperationException( "Implement me!" );
	}
}
