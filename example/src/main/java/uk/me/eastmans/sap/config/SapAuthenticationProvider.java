package uk.me.eastmans.sap.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import uk.me.eastmans.sap.b1.InvalidUserCredentials;
import uk.me.eastmans.sap.b1.SapFacade;
import uk.me.eastmans.sap.b1.SapUser;
import uk.me.eastmans.sap.b1.SapUserCredentials;
import uk.me.eastmans.sap.security.CurrentUser;

@Component
public class SapAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private SapFacade b1;

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {

        System.out.println( "Checking authentication " + authentication);
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
                return new SapAuthenticationToken( user, password );
                // Perhaps handle the attempt better, by flagging errors etc.
            } catch (InvalidUserCredentials iuc) {
                return null;
            }
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}