package org.venus.infra.web.security.sso;

import java.util.List;
import java.util.Map;

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
		//TODO get rid of old sessions from cache
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
		throw new UnsupportedOperationException();
	}

	@Override
	public SSOSession lookupValidSession(String rdSessionId) {
		logCache();
		SSOSession session = null;
		Element found = cache.get(rdSessionId);
		if (found != null) {
			session = (SSOSession) found.getObjectValue();
            if (session.hasExpired()) {
            	LOG.warn("Session {} found in cache BUT has expired.", rdSessionId);
            	removeSession(rdSessionId);
                session = null;
            } else {
    			LOG.debug("Session {} found in cache and is valid.", rdSessionId);
            	session.updateTimeToNow();
        		Element element = new Element(rdSessionId, session); 
        		cache.put(element); // updates the cache
            }
		} else {
			LOG.warn("Session {} NOT found in cache.", rdSessionId);
		}
		logCache();
		return session;
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
//		List<String> keys = cache.getKeys();
//    	LOG.debug("Existing keys in cache: {}", keys);
		Map<Object, Element> elements = cache.getAll(cache.getKeys());
    	LOG.debug("Existing elements in cache: {}", elements);
	}

}


