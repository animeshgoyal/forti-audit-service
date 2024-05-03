package com.fortitest.auditservice.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
    private static final String ADMIN_ROLE = "admin";

    public static boolean isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        for(GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
            if(grantedAuthority.getAuthority().equals(ADMIN_ROLE)) {
                return true;
            }
        }
        return false;
    }

    public static String getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getPrincipal().toString();
    }
}
