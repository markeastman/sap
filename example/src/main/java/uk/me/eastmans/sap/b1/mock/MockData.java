package uk.me.eastmans.sap.b1.mock;

import uk.me.eastmans.sap.b1.SapUser;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by markeastman on 26/07/2017.
 */
public class MockData {
    private Map<String,SapUser> users = new HashMap<String,SapUser>();

    public MockData() {
        // Create some users
        SapUser u1 = new SapUser("admin");
        users.put( u1.getName(), u1 );
        SapUser u2 = new SapUser("steve");
        users.put( u2.getName(), u2 );
    }

    public Map<String, SapUser> getUsers() {
        return users;
    }
}
