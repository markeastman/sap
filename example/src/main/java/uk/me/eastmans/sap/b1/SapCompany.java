package uk.me.eastmans.sap.b1;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SapCompany implements Serializable {
    private String name;
    private Set<String> roles = new HashSet<String>();

    public SapCompany() {}

    public SapCompany( String name, String... roles ) {
        this.name = name;
        this.roles.addAll(Arrays.asList(roles));
    }

    public String getName() {
        return name;
    }

    public Set<String> getRoles() {
        return roles;
    }
}
