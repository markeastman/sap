package uk.me.eastmans.sap.services;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import uk.me.eastmans.sap.b1.SapUser;

public class CurrentUser extends org.springframework.security.core.userdetails.User {

    private SapUser user;

    public CurrentUser(SapUser user) {
        super(user.getName(), user.getPasswordHash(), AuthorityUtils.createAuthorityList("B1_USER"));
        this.user = user;
    }

    public SapUser getSapUser() {
        return user;
    }
}