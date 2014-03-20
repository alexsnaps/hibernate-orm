package org.hibernate.cache.jcache;

import java.util.EnumSet;
import java.util.Set;
import javax.cache.Cache;

import org.hibernate.cache.spi.CacheDataDescription;
import org.hibernate.cache.spi.TransactionalDataRegion;
import org.hibernate.cache.spi.access.AccessType;
import org.hibernate.cfg.Settings;

/**
 * @author Alex Snaps
 */
public class JCacheTransactionalDataRegion extends JCacheRegion implements TransactionalDataRegion {

	private static final Set<AccessType> SUPPORTED_ACCESS_TYPES
			= EnumSet.of( AccessType.READ_ONLY, AccessType.NONSTRICT_READ_WRITE, AccessType.READ_WRITE );

	private final CacheDataDescription metadata;
	private final Settings settings;

	public JCacheTransactionalDataRegion(Cache<Object, Object> cache, CacheDataDescription metadata, Settings settings) {
		super( cache );
		this.metadata = metadata;
		this.settings = settings;
	}

	public boolean isTransactionAware() {
		return false;
	}

	public CacheDataDescription getCacheDataDescription() {
		return metadata;
	}

	protected void throwIfAccessTypeUnsupported(AccessType accessType) {
		if ( supportedAccessTypes().contains( accessType ) ) {
			throw new UnsupportedOperationException( "This doesn't JCacheTransactionalDataRegion doesn't support " + accessType );
		}
	}

	protected Set<AccessType> supportedAccessTypes() {
		return SUPPORTED_ACCESS_TYPES;
	}

	public void clear() {
		cache.removeAll();
	}

	public Object get(Object key) {
		return cache.get( key );
	}

	public void remove(Object key) {
		cache.remove( key );
	}

	public void put(Object key, Object value) {
		cache.put( key, value );
	}

	public Settings getSettings() {
		return settings;
	}
}
