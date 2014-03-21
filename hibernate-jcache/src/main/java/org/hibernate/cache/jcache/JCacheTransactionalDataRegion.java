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
