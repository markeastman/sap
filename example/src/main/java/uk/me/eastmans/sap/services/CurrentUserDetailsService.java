package uk.me.eastmans.sap.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uk.me.eastmans.sap.b1.InvalidUserCredentials;
import uk.me.eastmans.sap.b1.SapFacade;
import uk.me.eastmans.sap.b1.SapUser;
import uk.me.eastmans.sap.b1.SapUserCredentials;

/**
 * Created by meastman on 23/12/15.
 */
@Service
public class CurrentUserDetailsService implements UserDetailsService {

    private final SapFacade b1;

    @Autowired
    public CurrentUserDetailsService(SapFacade b1) {
        this.b1 = b1;
    }

    @Override
    public CurrentUser loadUserByUsername(String id) throws UsernameNotFoundException {
        SapUserCredentials credentials = new SapUserCredentials();
        credentials.setId(id);
        credentials.setPassword(id);

        try {
            SapUser user = b1.authenticate(credentials);
            return new CurrentUser(user);
        } catch (InvalidUserCredentials iuc) {
            throw new UsernameNotFoundException(iuc.getMessage());
        }
    }
}
