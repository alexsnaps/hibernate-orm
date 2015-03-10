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
import org.hibernate.cache.jcache.access.NonStrictNaturalIdRegionAccessStrategy;
import org.hibernate.cache.jcache.access.ReadOnlyNaturalIdRegionAccessStrategy;
import org.hibernate.cache.jcache.access.ReadWriteNaturalIdRegionAccessStrategy;
import org.hibernate.cache.spi.CacheDataDescription;
import org.hibernate.cache.spi.NaturalIdRegion;
import org.hibernate.cache.spi.access.AccessType;
import org.hibernate.cache.spi.access.NaturalIdRegionAccessStrategy;
import org.hibernate.cfg.Settings;

/**
 * @author Alex Snaps
 */
public class JCacheNaturalIdRegion extends JCacheTransactionalDataRegion implements NaturalIdRegion {

	public JCacheNaturalIdRegion(Cache<Object, Object> cache, CacheDataDescription metadata, Settings settings) {
		super( cache, metadata, settings );
	}

	@Override
	public NaturalIdRegionAccessStrategy buildAccessStrategy(AccessType accessType) throws CacheException {
		switch ( accessType ) {
			case READ_ONLY:
				return new ReadOnlyNaturalIdRegionAccessStrategy( this );
			case NONSTRICT_READ_WRITE:
				return new NonStrictNaturalIdRegionAccessStrategy( this );
			case READ_WRITE:
				return new ReadWriteNaturalIdRegionAccessStrategy( this );
			case TRANSACTIONAL:
				throw new UnsupportedOperationException( "Implement me!" );
			default:
				throw new UnsupportedOperationException( "Unknown AccessType: " + accessType.name() );
		}
	}
}
