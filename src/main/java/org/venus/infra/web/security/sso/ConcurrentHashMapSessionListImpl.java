package org.venus.infra.web.security.sso;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("hashMapImpl")
public class ConcurrentHashMapSessionListImpl implements SSOActiveSessionList {
    private static final Logger LOG = LoggerFactory.getLogger(ConcurrentHashMapSessionListImpl.class);
    
    private static final int MAX_EXPECTED_NUMBER_OF_SESSIONS = 500; // so initial capacity = 500 x 0.75 = 375
    private static final int MAX_EXPECTED_NO_OF_CONCURRENT_LOGINS_AND_LOGOUTS = 10; 

    /** Default to 30 mins **/
    private Integer sessionTimeOutinMins = 30; 
    
    private ConcurrentMap<String, SSOSession> sessionMap =
            new ConcurrentHashMap<String, SSOSession>(375, 0.75f, MAX_EXPECTED_NO_OF_CONCURRENT_LOGINS_AND_LOGOUTS);

    @Override
    public void addSession(SSOSession session) {
        LOG.debug("Adding session to list: {}", session);
        sessionMap.put(session.getRdSessionID().toString(), session);
    }

    @Override
    public SSOSession addSession(String username, String ipAddress) {
        SSOSession session = SSOSession.create(username, ipAddress);
        LOG.debug("Adding session to list: {}", session);
        sessionMap.put(session.getRdSessionID().toString(), session);
        return session;
    }

    @Override
    public SSOSession lookupValidSession(String rdSessionId) {
        SSOSession session = null;
        if (rdSessionId != null) { // needed cos will get NPE if null
            session = sessionMap.get(rdSessionId);
            if (session != null) {
                if (session.hasExpired()) {
                    session = null;
                } else {
                	session.updateTimeToNow();
                }
            }
        }
        LOG.debug("Looked up rdSessionId {}: {}", rdSessionId, session);
        return session;
    }

//    @Override
//    public SSOSession lookupValidSession(String rdSessionId, String ipAddress) {
//        SSOSession session = lookupValidSession(rdSessionId);
//        if (session != null) {
//            if (!session.getIpAddress().equals(ipAddress)) {
//                session = null;
//            }
//        }
//        LOG.debug("Looked up SessionId {} with IP address {}: {}", rdSessionId, ipAddress, session);
//        return session;
//    }

    @Override
    public void removeSession(String SessionId) {
        LOG.debug("Removing session ID from list: {}", SessionId);
        if (SessionId != null) { // needed cos will get NPE if null
            sessionMap.remove(SessionId);
        }
    }

    @Override
    public String toString() {
        return sessionMap.toString();
    }
}
