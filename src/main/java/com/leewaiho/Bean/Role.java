package com.leewaiho.Bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by leewaiho on 2017/7/20.
 */
@Entity
public class Role extends BaseBean {
    
    private String name;
    private String description;
    
    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;
    
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "roles_permissions",
            joinColumns= @JoinColumn(name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "id")
    )
    private Collection<Permission> permissions;
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Collection<User> getUsers() {
        return users;
    }
    
    public void setUsers(Collection<User> users) {
        this.users = users;
    }
    
    public Collection<Permission> getPermissions() {
        return permissions;
    }
    
    public void setPermissions(Collection<Permission> permissions) {
        this.permissions = permissions;
    }
    
}
