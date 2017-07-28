package uk.me.eastmans.sap.b1;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by markeastman on 26/07/2017.
 */
public class SapUser implements Serializable{
    private String name;
    private String passwordHash;
    private Set<SapCompany> allowedCompanies = new HashSet<SapCompany>();

    public SapUser() {}

    public SapUser( String name )
    {
        this.name = name;
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
