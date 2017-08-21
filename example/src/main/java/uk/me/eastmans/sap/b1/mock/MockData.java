package uk.me.eastmans.sap.b1.mock;

import uk.me.eastmans.sap.b1.SapCompany;
import uk.me.eastmans.sap.b1.SapUser;
import static uk.me.eastmans.sap.b1.SapRoles.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by markeastman on 26/07/2017.
 */
public class MockData {
    private Map<String,SapUser> users = new HashMap<String,SapUser>();

    public MockData() {
        // Create some users
        SapUser u1 = new SapUser("admin", true, true, true, true);
        users.put( u1.getName(), u1 );
        SapUser u2 = new SapUser("steve", true, true, false, true);
        users.put( u2.getName(), u2 );
        SapUser u3 = new SapUser("disabled", false, true, true, true);
        users.put( u3.getName(), u3 );
        SapUser u4 = new SapUser("expired", true, false, true, true);
        users.put( u4.getName(), u4 );
        SapUser u5 = new SapUser("locked", true, true, true, false);
        users.put( u5.getName(), u5 );

        u1.addAllowedCompany( new SapCompany( "C1","Company 1", "Long name for company 1", "ROLE_USERS", "ROLE_ROLE_USERS", ADMIN, NEW_ORDERS, USERS, FEATURE_4, FEATURE_5, FEATURE_6 ) );
        u1.addAllowedCompany( new SapCompany( "C2","Company 2", "Long name for company 2",ADMIN, USERS) );
        u1.addAllowedCompany( new SapCompany( "C3","Company 3", "Long name for company 3",ADMIN, NEW_ORDERS, FEATURE_6 ) );
        u1.addAllowedCompany( new SapCompany( "C4","Company 4", "Long name for company 4",ADMIN, NEW_ORDERS, FEATURE_4, FEATURE_5 ) );
        u2.addAllowedCompany( new SapCompany( "C1","Company 1", "Long name for company 1",NEW_ORDERS, USERS) );
        u2.addAllowedCompany( new SapCompany( "C3","Company 3", "Long name for company 3",FEATURE_4, FEATURE_5, FEATURE_6 ) );

        SapCompany placeHolder = new SapCompany("--","Invalid", "No companies available");
        u3.addAllowedCompany(placeHolder);
        u4.addAllowedCompany(placeHolder);
        u5.addAllowedCompany(placeHolder);
    }

    public Map<String, SapUser> getUsers() {
        return users;
    }
}
