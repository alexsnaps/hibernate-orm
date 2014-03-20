package org.hibernate.cache.jcache;

import javax.cache.Cache;

import org.hibernate.cache.spi.QueryResultsRegion;

/**
 * @author Alex Snaps
 */
public class JCacheQueryResultsRegion extends JCacheGeneralDataRegion implements QueryResultsRegion {

	public JCacheQueryResultsRegion(Cache<Object, Object> cache) {
		super( cache );
	}

}
