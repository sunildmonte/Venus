package org.venus.infra.web.security.sso;

import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("ehCacheImpl")
public class EhCacheSessionListImpl implements SSOActiveSessionList {

	private static final Logger LOG = LoggerFactory.getLogger(EhCacheSessionListImpl.class);
	
	private Cache cache;
	
	public EhCacheSessionListImpl() {
		CacheManager manager = CacheManager.create();
		cache = new Cache (new CacheConfiguration("sessionCache", 0)
//		.memoryStoreEvictionPolicy(MemoryStoreEvictionPolicy.LFU)
//		.eternal(false)
//		.timeToLiveSeconds(60)
//		.timeToIdleSeconds(30)
//		.diskExpiryThreadIntervalSeconds(0)
//		.persistence(new PersistenceConfiguration().strategy(Strategy.LOCALTEMPSWAP)))
		);
		manager.addCache(cache);
	}
	
	@Override
	public void addSession(SSOSession session) {
		Element element = new Element(session.getRdSessionID(), session); 
		cache.put(element);
		LOG.debug("Added session {} to cache.", session.getRdSessionID());
		logCache();
//		Element found = cache.get(session.getRdSessionID());
//		LOG.debug("Found in cache: {}", found);
	}

	@Override
	public SSOSession addSession(String username, String ipAddress) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SSOSession lookupValidSession(String rdSessionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeSession(String rdSessionId) {
		logCache();
//		Element found = cache.get(rdSessionId);
//		LOG.debug("Found in cache: {}", found);
		boolean result = cache.remove(rdSessionId);
		logCache();
		if (result) {
			LOG.debug("Removed session {} from cache.", rdSessionId);
		} else {
			LOG.warn("Session {} could not be found in cache.", rdSessionId);
		}
	}
	
	public void logCache() {
		List<String> keys = cache.getKeys();
    	LOG.debug("Existing keys in cache: {}", keys);
	}

}
