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
