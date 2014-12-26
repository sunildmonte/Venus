package org.venus.infra.web.security.sso;

import java.util.Date;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SSOSession implements java.io.Serializable {
    public static final long EXPIRE_AFTER_IDLE_TIME_MILLIS = 3 * 60 * 1000; // 30 minutes
    public static final long EXPIRE_AFTER_TOTAL_TIME_MILLIS = 8 * 60 * 60 * 1000; // 8 hours
    public static final String SSO_COOKIE_NAME = "rds";
    private transient static final Logger LOG = LoggerFactory.getLogger(SSOSession.class);

    private String rdSessionID;
    private String username;
    private String ipAddress;
    private Date createdDate;
    private Date lastUpdatedDate;
    
    private SSOSession() {
    }
    
    public static SSOSession create(String username, String ipAddress) {
        SSOSession rdSession = new SSOSession();
        rdSession.rdSessionID = UUID.randomUUID().toString();
        rdSession.username = username;
        rdSession.ipAddress = ipAddress;
        Date now = new Date();
        rdSession.createdDate = now;
        rdSession.lastUpdatedDate = now;
        return rdSession;
    }

//    /**
//     * Returns the string which is used as the textual value of the cookie.
//     */
//    private String cookieValue() {
//        return rdSessionID.toString();
//    }

    /**
     * Generates a session cookie based on the data present. Note that the path of the cookie is "/" so that it gets
     * used by every web app.
     */
    public Cookie generateCookie() {
        Cookie ssoCookie = new Cookie(SSO_COOKIE_NAME, rdSessionID); //cookieValue());
        ssoCookie.setPath("/");
        ssoCookie.setHttpOnly(true); // prevents javascript attacks 
        ssoCookie.setMaxAge(-1); // session cookie, not persistent cookie
        return ssoCookie;
    }
    
    /**
     * Checks both the overall age of the cookie and the "idle" time i.e. how long ago it was last updated.
     * @return true if either of the age limits have been exceeded.
     */
    public boolean hasExpired() {
        boolean isExpired = false;
        long now = new Date().getTime();
        if (now - lastUpdatedDate.getTime() > EXPIRE_AFTER_IDLE_TIME_MILLIS
                ||
                now < lastUpdatedDate.getTime() // should never happen, but just an extra security measure
                ) {
            LOG.error("SSO cookie idle time has expired, last updated time: {}", lastUpdatedDate);
            isExpired = true;
        }
        if (now - createdDate.getTime() > EXPIRE_AFTER_TOTAL_TIME_MILLIS
                ||
                now < createdDate.getTime() // should never happen, but just an extra security measure
                ) {
            LOG.error("SSO cookie total time has expired, was created on: {}", createdDate);
            isExpired = true; // either of the 2 conditions should lead to expiry
        }
        return isExpired;
    }
    
    /**
     * Updates the last-updated timestamp to the current time.
     */
    public void updateTimeToNow() {
        lastUpdatedDate = new Date();
    }
    
    /**
     * Extracts the session ID from the cookie in an http request.
     * 
     * @param request
     * @return session ID if the cookie exists, null otherwise
     */
    public static String extractSessionIDFromCookie(HttpServletRequest request) {
        String sessionID = null;
        Cookie cookies[] = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                LOG.debug("Cookie in HTTP request: name = {}, value = {}", cookie.getName(), cookie.getValue());
                if (cookie.getName().equals(SSO_COOKIE_NAME)) {
                    sessionID = cookie.getValue();
                    break;
                }
            }
        }
        if (sessionID == null) {
            LOG.warn("SSO cookie not found in HTTP request, returning null");
        }
        return sessionID;
    }
    
    @Override
    public boolean equals(Object obj) {
        SSOSession other = (SSOSession) obj;
        return rdSessionID.equals(other.getRdSessionID());
    }
    
    @Override
    public int hashCode() {
        return rdSessionID.hashCode();
    }
    
    /**
     * Used for logging info only, NOT for the textual value of the cookie itself.
     */
    @Override
    public String toString() {
        return new StringBuilder()
        .append(rdSessionID).append(",")
        .append(username).append(",")
        .append(ipAddress).append(",")
        .append(createdDate).append(",")
        .append(lastUpdatedDate).append(",")
        .append(hasExpired())
        .toString();
    }
    
    public String getUsername() {
        return username;
    }

    public String getRdSessionID() {
        return rdSessionID;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }


}
