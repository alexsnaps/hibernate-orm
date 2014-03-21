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

import java.util.HashMap;
import java.util.Map;
import javax.cache.Cache;

import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.Region;

/**
 * @author Alex Snaps
 */
public class JCacheRegion implements Region {

	protected final Cache<Object, Object> cache;

	public JCacheRegion(Cache<Object, Object> cache) {
		if(cache == null) {
			throw new NullPointerException("JCacheRegion requires a Cache!");
		}
		this.cache = cache;
	}

	public String getName() {
		return cache.getName();
	}

	public void destroy() throws CacheException {
		cache.getCacheManager().destroyCache( cache.getName() );
	}

	public boolean contains(Object key) {
		return cache.containsKey( key );
	}

	public long getSizeInMemory() {
		return -1;
	}

	public long getElementCountInMemory() {
		return -1;
	}

	public long getElementCountOnDisk() {
		return -1;
	}

	public Map toMap() {
		final Map<Object, Object> map = new HashMap<Object, Object>();
		for ( Cache.Entry<Object, Object> entry : cache ) {
			map.put( entry.getKey(), entry.getValue() );
		}
		return map;
	}

	public long nextTimestamp() {
		return JCacheRegionFactory.nextTS();
	}

	public int getTimeout() {
		return JCacheRegionFactory.timeOut();
	}

	Cache<Object, Object> getCache() {
		return cache;
	}
}
