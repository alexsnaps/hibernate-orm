package org.hibernate.cache.jcache;

import javax.cache.Cache;

import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.CacheDataDescription;
import org.hibernate.cache.spi.CollectionRegion;
import org.hibernate.cache.spi.access.AccessType;
import org.hibernate.cache.spi.access.CollectionRegionAccessStrategy;
import org.hibernate.cfg.Settings;

/**
 * @author Alex Snaps
 */
public class JCacheCollectionRegion extends JCacheTransactionalDataRegion implements CollectionRegion {

	public JCacheCollectionRegion(Cache<Object, Object> cache, CacheDataDescription metadata, Settings settings) {
		super( cache, metadata, settings );
	}

	@Override
	public CollectionRegionAccessStrategy buildAccessStrategy(AccessType accessType) throws CacheException {
		throw new UnsupportedOperationException( "Implement me!" );
	}
}
