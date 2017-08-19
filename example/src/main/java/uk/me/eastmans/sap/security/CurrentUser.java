package uk.me.eastmans.sap.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import uk.me.eastmans.sap.b1.SapCompany;
import uk.me.eastmans.sap.b1.SapUser;

import java.util.Collection;

public class CurrentUser extends org.springframework.security.core.userdetails.User {

    private SapUser user;
    private SapCompany activeCompany;
    private Collection<GrantedAuthority> ourAuthorities;

    public CurrentUser(SapUser user) {
        super(user.getName(), user.getPasswordHash(), user.isEnabled(), user.isAccountNonExpired(), user.isCredentialsNonExpired(), user.isAccountNonLocked(),
                AuthorityUtils.createAuthorityList(((SapCompany)user.getAllowedCompanies().toArray()[0]).getRoles().toArray( new String[0])));
        this.user = user;
        this.ourAuthorities = super.getAuthorities();
        activeCompany = (SapCompany)user.getAllowedCompanies().toArray()[0];
    }

    public SapUser getSapUser() {
        return user;
    }

    public SapCompany getActiveCompany() {
        return activeCompany;
    }

    public void setActiveCompany(SapCompany company) {
        activeCompany = company;
        // We need to change the set of active roles for this authority.
        ourAuthorities = AuthorityUtils.createAuthorityList(company.getRoles().toArray( new String[0]));
    }

    /*
     * We cannot change the underlying list of authorities so we have to override them here
     */
    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return ourAuthorities;
    }
}