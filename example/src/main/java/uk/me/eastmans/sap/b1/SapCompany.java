package uk.me.eastmans.sap.b1;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SapCompany implements Serializable, Comparable {
    private String id;
    private String name;
    private String description;
    private Set<String> roles = new HashSet<String>();

    public SapCompany() {}

    public SapCompany( String id, String name, String description, String... roles ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.roles.addAll(Arrays.asList(roles));
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Set<String> getRoles() {
        return roles;
    }

    @Override
    public int compareTo(Object o) {

        return name.compareTo(((SapCompany)o).name);
    }
}
