package org.hibernate.cache.jcache.access;

import org.hibernate.cache.CacheException;
import org.hibernate.cache.jcache.JCacheEntityRegion;
import org.hibernate.cache.spi.access.EntityRegionAccessStrategy;
import org.hibernate.cache.spi.access.SoftLock;

/**
 * @author Alex Snaps
 */
public class ReadWriteEntityRegionAccessStrategy extends JCacheRegionAccessStrategy<JCacheEntityRegion>
		implements EntityRegionAccessStrategy {

	public ReadWriteEntityRegionAccessStrategy(JCacheEntityRegion region) {
		super( region );
	}

	@Override
	public boolean insert(Object key, Object value, Object version) throws CacheException {
		throw new UnsupportedOperationException( "Implement me!" );
	}

	@Override
	public boolean afterInsert(Object key, Object value, Object version) throws CacheException {
		throw new UnsupportedOperationException( "Implement me!" );
	}

	@Override
	public boolean update(Object key, Object value, Object currentVersion, Object previousVersion)
			throws CacheException {
		throw new UnsupportedOperationException( "Implement me!" );
	}

	@Override
	public boolean afterUpdate(Object key, Object value, Object currentVersion, Object previousVersion, SoftLock lock)
			throws CacheException {
		throw new UnsupportedOperationException( "Implement me!" );
	}
}
