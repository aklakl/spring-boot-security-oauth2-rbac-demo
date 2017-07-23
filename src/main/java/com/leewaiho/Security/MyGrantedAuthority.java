package com.leewaiho.Security;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

/**
 * Created by leewaiho on 2017/7/22.
 */
public class MyGrantedAuthority implements GrantedAuthority, ConfigAttribute, Serializable {
    
    private String url;
    private String method;
    
    public MyGrantedAuthority(String url, String method) {
        this.url = url;
        this.method = method;
    }
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public String getMethod() {
        return method;
    }
    
    public void setMethod(String method) {
        this.method = method;
    }
    
    @Override
    public String getAuthority() {
        return this.method + " " + this.url;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        MyGrantedAuthority that = (MyGrantedAuthority) o;
        
        if (url != null ? !url.equals(that.url) : that.url != null) return false;
        return method != null ? method.equals(that.method) : that.method == null;
    }
    
    @Override
    public int hashCode() {
        int result = url != null ? url.hashCode() : 0;
        result = 31 * result + (method != null ? method.hashCode() : 0);
        return result;
    }
    
    @Override
    public String getAttribute() {
        return this.method + " " + this.url;
    }
}
