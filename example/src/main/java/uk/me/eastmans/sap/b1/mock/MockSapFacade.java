package uk.me.eastmans.sap.b1.mock;

import org.springframework.stereotype.Service;
import uk.me.eastmans.sap.b1.InvalidUserCredentials;
import uk.me.eastmans.sap.b1.SapFacade;
import uk.me.eastmans.sap.b1.SapUser;
import uk.me.eastmans.sap.b1.SapUserCredentials;
import uk.me.eastmans.sap.b1.mock.MockData;

/**
 * Created by markeastman on 26/07/2017.
 */
@Service
public class MockSapFacade implements SapFacade {
    private static MockData data = new MockData();

    @Override
    public SapUser authenticate(SapUserCredentials credentials) throws InvalidUserCredentials {
        // Check to see if we have any users with this name
        if (data.getUsers().containsKey(credentials.getId()))
        {
            // Yes we have one, now check the password (which is the same as the name for us
            SapUser user = data.getUsers().get(credentials.getId());
            if (user.getName().equals(credentials.getPassword()))
            {
                // All good
                return user;
            }
            throw new InvalidUserCredentials();
        }
        throw new InvalidUserCredentials();
    }
}
