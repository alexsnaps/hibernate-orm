package org.hibernate.cache.jcache;

import javax.cache.Cache;

import org.hibernate.cache.spi.CacheDataDescription;
import org.hibernate.cache.spi.TransactionalDataRegion;

/**
 * @author Alex Snaps
 */
public class JCacheTransactionalDataRegion extends JCacheRegion implements TransactionalDataRegion {

	public JCacheTransactionalDataRegion(Cache<Object, Object> cache) {
		super( cache );
	}

	public boolean isTransactionAware() {
		throw new UnsupportedOperationException( "Implement me!" );
	}

	public CacheDataDescription getCacheDataDescription() {
		throw new UnsupportedOperationException( "Implement me!" );
	}

}
