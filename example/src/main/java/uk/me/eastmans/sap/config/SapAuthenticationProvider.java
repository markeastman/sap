package uk.me.eastmans.sap.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import uk.me.eastmans.sap.b1.InvalidUserCredentials;
import uk.me.eastmans.sap.b1.SapFacade;
import uk.me.eastmans.sap.b1.SapUser;
import uk.me.eastmans.sap.b1.SapUserCredentials;
import uk.me.eastmans.sap.security.CurrentUser;

import javax.security.auth.login.CredentialExpiredException;

@Component
public class SapAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private SapFacade b1;

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {

        if (authentication.isAuthenticated())
        {
            // We do not need to do anything except check if he has been expired etc.
            return authentication;
        }
        else
        {
            // We need to authenticate
            String name = authentication.getName();
            String password = authentication.getCredentials().toString();

            SapUserCredentials credentials = new SapUserCredentials();
            credentials.setId(name);
            credentials.setPassword(password);

            // We should now authenticate against the SAP B1 system
            try {
                SapUser sapUser = b1.authenticate(credentials);
                CurrentUser user = new CurrentUser(sapUser);
                if (!user.isEnabled())
                    throw new DisabledException("Account is disabled");
                if (!user.isCredentialsNonExpired())
                    throw new CredentialsExpiredException("Credentials have expired");
                if (!user.isAccountNonExpired())
                    throw new AccountExpiredException("Account has expired");
                if (!user.isAccountNonLocked())
                    throw new LockedException("Account is Locked");
                return new SapAuthenticationToken( user, password );
            } catch (InvalidUserCredentials iuc) {
                throw new BadCredentialsException("The id or password you have entered is invalid, try again.");
            }
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}