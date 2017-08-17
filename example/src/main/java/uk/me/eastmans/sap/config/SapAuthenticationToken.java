package uk.me.eastmans.sap.config;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import uk.me.eastmans.sap.security.CurrentUser;

import java.util.Collection;

public class SapAuthenticationToken extends UsernamePasswordAuthenticationToken {
    public SapAuthenticationToken(CurrentUser principal, String credentials )
    {
        super( principal, credentials, principal.getAuthorities() );
    }

    /*
     * We cannot change the underlying list of authorities so we have to override them here
     */
    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return ((CurrentUser)getPrincipal()).getAuthorities();
    }
}

