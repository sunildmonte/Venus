package org.venus.infra.util;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtils {
    
    private static final Logger LOG = LoggerFactory.getLogger(HttpUtils.class);
    
    /**
     * Fetches the "earliest" available IP address of the requestor, by following the X-Forwarded-For chain.
     */
    public static String originatingIPAddressFromRequest(HttpServletRequest request) {
        String ipAddress = request.getRemoteAddr();
        LOG.debug("IP address using request.getRemoteAddr(): {}", ipAddress);
        String proxiedIPAddress = request.getHeader("X-Forwarded-For");
        LOG.debug("IP address chain using X-Forwarded-For: {}", proxiedIPAddress);
        if (proxiedIPAddress != null) {
            String [] ipAddresses = proxiedIPAddress.split(",");
            ipAddress = ipAddresses[ipAddresses.length-1].trim();
        }
        LOG.debug("Going to return IP address: {}", ipAddress);
        return ipAddress;
    }

}
