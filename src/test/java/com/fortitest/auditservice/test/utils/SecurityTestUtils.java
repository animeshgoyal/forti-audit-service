package com.fortitest.auditservice.test.utils;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.util.ArrayList;
import java.util.List;

public class SecurityTestUtils {
    private static String USER_ID = "angoyal";
    private static String ADMIN_ROLE = "admin";

    private static String NON_ADMIN_ROLE = "nonadmin";
    public static void setupAdmin() {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(ADMIN_ROLE));
        PreAuthenticatedAuthenticationToken authToken = new PreAuthenticatedAuthenticationToken(USER_ID, null, grantedAuthorities);
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    public static void setupNonAdmin() {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(NON_ADMIN_ROLE));
        PreAuthenticatedAuthenticationToken authToken = new PreAuthenticatedAuthenticationToken(USER_ID, null, grantedAuthorities);
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }
}
