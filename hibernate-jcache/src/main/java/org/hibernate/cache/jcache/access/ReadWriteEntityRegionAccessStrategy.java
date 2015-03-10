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

import javax.cache.processor.EntryProcessor;
import javax.cache.processor.EntryProcessorException;
import javax.cache.processor.MutableEntry;

import org.hibernate.cache.CacheException;
import org.hibernate.cache.jcache.JCacheEntityRegion;
import org.hibernate.cache.spi.access.EntityRegionAccessStrategy;
import org.hibernate.cache.spi.access.SoftLock;

/**
 * @author Alex Snaps
 */
public class ReadWriteEntityRegionAccessStrategy
		extends AbstractReadWriteRegionAccessStrategy<JCacheEntityRegion>
		implements EntityRegionAccessStrategy {


	public ReadWriteEntityRegionAccessStrategy(JCacheEntityRegion region) {
		super( region );
	}

	@Override
	public boolean insert(Object key, Object value, Object version) throws CacheException {
		return false;
	}

	@Override
	public boolean afterInsert(Object key, Object value, Object version) throws CacheException {
		return region.invoke(
				key, new EntryProcessor<Object, Object, Boolean>() {
					@Override
					public Boolean process(MutableEntry<Object, Object> entry, Object... args)
							throws EntryProcessorException {
						if ( !entry.exists() ) {
							entry.setValue( new Item( args[0], args[1], (Long) args[2] ) );
							return true;
						}
						else {
							return false;
						}
					}
				}, value, version, region.nextTimestamp()
		);
	}

	@Override
	public boolean update(Object key, Object value, Object currentVersion, Object previousVersion)
			throws CacheException {
		return false;
	}

	@Override
	public boolean afterUpdate(Object key, Object value, Object currentVersion, Object previousVersion, SoftLock lock)
			throws CacheException {
		return region.invoke(
				key, new EntryProcessor<Object, Object, Boolean>() {
					@Override
					public Boolean process(MutableEntry<Object, Object> entry, Object... args)
							throws EntryProcessorException {
						final Lockable item = (Lockable) entry.getValue();

						if ( item != null && item.isUnlockable( (SoftLock) args[3] ) ) {
							final Lock lockItem = (Lock) item;
							if ( lockItem.wasLockedConcurrently() ) {
								lockItem.unlock( (Long) args[1] );
								entry.setValue( lockItem );
								return false;
							}
							else {
								entry.setValue( new Item( args[0], args[1], (Long) args[4] ) );
								return true;
							}
						}
						else {
							entry.setValue( null );
							return false;
						}

					}
				}, value, currentVersion, previousVersion, lock, region.nextTimestamp()
		);
	}
}
