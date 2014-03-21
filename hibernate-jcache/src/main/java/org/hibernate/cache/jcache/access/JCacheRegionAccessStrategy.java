/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * Copyright (c) 2014, Red Hat Inc. or third-party contributors as
 * indicated by the @author tags or express copyright attribution
 * statements applied by the authors.  All third-party contributions are
 * distributed under license by Red Hat Inc.
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution; if not, write to:
 * Free Software Foundation, Inc.
 * 51 Franklin Street, Fifth Floor
 * Boston, MA  02110-1301  USA
 */

package org.hibernate.cache.jcache.access;

import org.hibernate.cache.CacheException;
import org.hibernate.cache.jcache.JCacheTransactionalDataRegion;
import org.hibernate.cache.spi.access.RegionAccessStrategy;
import org.hibernate.cache.spi.access.SoftLock;
import org.hibernate.cfg.Settings;

/**
 * @author Alex Snaps
 */
abstract class JCacheRegionAccessStrategy<R extends JCacheTransactionalDataRegion> implements RegionAccessStrategy {

	private final R region;

	public JCacheRegionAccessStrategy(R region) {
		if ( region == null ) {
			throw new NullPointerException( "Requires a non-null JCacheTransactionalDataRegion" );
		}
		this.region = region;
	}

	@Override
	public Object get(Object key, long txTimestamp) throws CacheException {
		return region.get( key );
	}

	@Override
	public boolean putFromLoad(Object key, Object value, long txTimestamp, Object version) throws CacheException {
		final Settings settings = region.getSettings();
		final boolean minimalPutOverride = settings != null && settings.isMinimalPutsEnabled();
		return putFromLoad( key, value, txTimestamp, version, minimalPutOverride );
	}

	@Override
	public boolean putFromLoad(Object key, Object value, long txTimestamp, Object version, boolean minimalPutOverride)
			throws CacheException {
		if ( minimalPutOverride && region.contains( key ) ) {
			return false;
		}
		else {
			region.put( key, value );
			return true;
		}
	}

	@Override
	public SoftLock lockItem(Object key, Object version) throws CacheException {
		return null;
	}

	@Override
	public SoftLock lockRegion() throws CacheException {
		return null;
	}

	@Override
	public void unlockItem(Object key, SoftLock lock) throws CacheException {
		evict( key );
	}

	@Override
	public void unlockRegion(SoftLock lock) throws CacheException {
		// no op
	}

	@Override
	public void remove(Object key) throws CacheException {
		region.remove( key );
	}

	@Override
	public void removeAll() throws CacheException {
		region.clear();
	}

	@Override
	public void evict(Object key) throws CacheException {
		region.remove( key );
	}

	@Override
	public void evictAll() throws CacheException {
		region.clear();
	}

	public R getRegion() {
		return region;
	}
}
