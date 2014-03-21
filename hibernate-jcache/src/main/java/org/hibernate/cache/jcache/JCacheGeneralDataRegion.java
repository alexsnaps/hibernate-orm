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
import org.hibernate.cache.spi.GeneralDataRegion;

/**
 * @author Alex Snaps
 */
public class JCacheGeneralDataRegion extends JCacheRegion implements GeneralDataRegion {

	public JCacheGeneralDataRegion(Cache<Object, Object> cache) {
		super( cache );
	}

	public Object get(Object key) throws CacheException {
		return cache.get( key );
	}

	public void put(Object key, Object value) throws CacheException {
		cache.put( key, value );
	}

	public void evict(Object key) throws CacheException {
		cache.remove( key );
	}

	public void evictAll() throws CacheException {
		cache.removeAll();
	}

}
