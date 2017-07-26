package uk.me.eastmans.sap.b1;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.Serializable;

/**
 * Created by markeastman on 26/07/2017.
 */
public class SapUser implements Serializable{
    private String name;
    private String passwordHash;

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
}
