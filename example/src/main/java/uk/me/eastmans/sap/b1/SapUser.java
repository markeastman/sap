package uk.me.eastmans.sap.b1;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Created by markeastman on 26/07/2017.
 */
public class SapUser implements Serializable{
    private String name;
    private String passwordHash;
    private SortedSet<SapCompany> allowedCompanies = new ConcurrentSkipListSet<>();
    private boolean enabled;
    private boolean accountNonExpired;
    private boolean credentialsNonExpired;
    private boolean accountNonLocked;

    public SapUser() {}

    public SapUser( String name, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked )
    {
        this.name = name;
        this.enabled =  enabled;
        this.accountNonExpired = accountNonExpired;
        this.credentialsNonExpired = credentialsNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.passwordHash = new BCryptPasswordEncoder().encode(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Set<SapCompany> getAllowedCompanies() {
        return allowedCompanies;
    }

    public void addAllowedCompany(SapCompany company) {
        allowedCompanies.add(company);
    }

}
