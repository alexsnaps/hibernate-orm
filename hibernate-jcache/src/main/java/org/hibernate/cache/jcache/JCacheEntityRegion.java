package org.hibernate.cache.jcache;

import javax.cache.Cache;

import org.hibernate.cache.CacheException;
import org.hibernate.cache.jcache.access.NonstrictEntityRegionAccessStrategy;
import org.hibernate.cache.jcache.access.ReadOnlyEntityRegionAccessStrategy;
import org.hibernate.cache.jcache.access.ReadWriteEntityRegionAccessStrategy;
import org.hibernate.cache.spi.CacheDataDescription;
import org.hibernate.cache.spi.EntityRegion;
import org.hibernate.cache.spi.access.AccessType;
import org.hibernate.cache.spi.access.EntityRegionAccessStrategy;
import org.hibernate.cfg.Settings;

/**
 * @author Alex Snaps
 */
public class JCacheEntityRegion extends JCacheTransactionalDataRegion implements EntityRegion {

	public JCacheEntityRegion(Cache<Object, Object> cache, CacheDataDescription metadata, Settings settings) {
		super( cache, metadata, settings );
	}

	@Override
	public EntityRegionAccessStrategy buildAccessStrategy(AccessType accessType) throws CacheException {
		throwIfAccessTypeUnsupported( accessType );
		switch ( accessType ) {
			case READ_ONLY:
				return new ReadOnlyEntityRegionAccessStrategy( this );
			case NONSTRICT_READ_WRITE:
				return new NonstrictEntityRegionAccessStrategy( this );
			case READ_WRITE:
				return new ReadWriteEntityRegionAccessStrategy( this );
			case TRANSACTIONAL:
				return createTransactionalEntityRegionAccessStrategy();
			default:
				throw new IllegalArgumentException( "Unknown AccessType: " + accessType );
		}
	}

	protected EntityRegionAccessStrategy createTransactionalEntityRegionAccessStrategy() {
		throw new UnsupportedOperationException("No org.hibernate.cache.spi.access.AccessType.TRANSACTIONAL support");
	}
}
