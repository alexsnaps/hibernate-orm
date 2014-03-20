package org.hibernate.cache.jcache.access;

import org.hibernate.cache.CacheException;
import org.hibernate.cache.jcache.JCacheEntityRegion;
import org.hibernate.cache.spi.access.EntityRegionAccessStrategy;
import org.hibernate.cache.spi.access.SoftLock;

/**
 * @author Alex Snaps
 */
public class ReadOnlyEntityRegionAccessStrategy extends JCacheRegionAccessStrategy<JCacheEntityRegion>
		implements EntityRegionAccessStrategy {

	public ReadOnlyEntityRegionAccessStrategy(JCacheEntityRegion jCacheEntityRegion) {
		super( jCacheEntityRegion );
	}

	@Override
	public boolean insert(Object key, Object value, Object version) throws CacheException {
		return false;
	}

	@Override
	public boolean afterInsert(Object key, Object value, Object version) throws CacheException {
		getRegion().put( key, value );
		return true;
	}

	@Override
	public boolean update(Object key, Object value, Object currentVersion, Object previousVersion)
			throws CacheException {
		throw new UnsupportedOperationException( "This is a ReadOnly strategy!" );
	}

	@Override
	public boolean afterUpdate(Object key, Object value, Object currentVersion, Object previousVersion, SoftLock lock)
			throws CacheException {
		throw new UnsupportedOperationException( "This is a ReadOnly strategy!" );
	}
}
