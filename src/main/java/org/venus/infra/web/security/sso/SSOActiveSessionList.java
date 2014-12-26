package org.venus.infra.web.security.sso;


public interface SSOActiveSessionList {

    /**
     * Adds a session to the active session list.
     * 
     * @param session
     */
    public void addSession(SSOSession session);

    /**
     * Creates and adds a session (for the given user and ip address) to the active session list.
     * 
     * @param session
     */
    public SSOSession addSession(String username, String ipAddress);
    
    /**
     * Returns a matching SSOSession if it exists and is valid; null otherwise.
     * 
     * @param SessionId
     * @return
     */
    public SSOSession lookupValidSession(String rdSessionId);
    
    /**
     * Returns a matching SSOSession if it exists and is valid; null otherwise.
     * 
     * @param SessionId
     * @param ipAddress
     * @return
     */
    // public SSOSession lookupValidSession(String rdSessionId, String ipAddress);
    
    /**
     * Removes the session from the active session list. Clients should typically call this when they find that a
     * session has expired. If the input rdSessionId is null, or if the session does not exist, then this method has
     * no effect.
     * 
     * @param rdSessionId
     */
    public void removeSession(String rdSessionId);
}
