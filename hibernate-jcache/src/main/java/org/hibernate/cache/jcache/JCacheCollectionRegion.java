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

package org.hibernate.cache.jcache;

import javax.cache.Cache;

import org.hibernate.cache.CacheException;
import org.hibernate.cache.jcache.access.NonStrictCollectionRegionAccessStrategy;
import org.hibernate.cache.jcache.access.ReadOnlyCollectionRegionAccessStrategy;
import org.hibernate.cache.jcache.access.ReadWriteCollectionRegionAccessStrategy;
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
		switch ( accessType ) {
			case READ_ONLY:
				return new ReadOnlyCollectionRegionAccessStrategy( this );
			case NONSTRICT_READ_WRITE:
				return new NonStrictCollectionRegionAccessStrategy( this );
			case READ_WRITE:
				return new ReadWriteCollectionRegionAccessStrategy( this );
			case TRANSACTIONAL:
				throw new UnsupportedOperationException( "Implement me!" );
			default:
				throw new UnsupportedOperationException( "Unknown AccessType: " + accessType.name() );
		}
	}
}
