package com.leewaiho.Bean;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.Collection;

/**
 * Created by leewaiho on 2017/7/20.
 */
@Entity
public class Permission extends BaseBean {
    
    private String name;
    private String url;
    private String method;
    private String description;
    
    @ManyToMany(mappedBy = "permissions")
    @JsonIgnore
    private Collection<Role> roles;
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setMethod(String method) {
        this.method = method;
    }
    
    public String getMethod() {
        return method;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Collection<Role> getRoles() {
        return roles;
    }
    
    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }
}
