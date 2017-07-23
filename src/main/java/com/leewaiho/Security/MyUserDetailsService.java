package com.leewaiho.Security;

import com.leewaiho.Bean.Permission;
import com.leewaiho.Bean.Role;
import com.leewaiho.Bean.User;
import com.leewaiho.Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by leewaiho on 2017/7/22.
 */
@Service
public class MyUserDetailsService implements UserDetailsService {
    
    private final static Logger logger = LoggerFactory.getLogger(MyUserDetailsService.class);
    
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        User user = userRepository.findByUsername(username);
        if (user == null) {
            logger.info("{} not found", username);
            throw new UsernameNotFoundException("User Not Found!");
        }
        
        
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        if (user.getRoles() != null && !user.getRoles().isEmpty()) {
            Set<Permission> permissions = new LinkedHashSet<>();
            for (Role role : user.getRoles())
                permissions.addAll(role.getPermissions());
            
            if (permissions != null && !permissions.isEmpty())
                for (Permission permission : permissions)
                    grantedAuthorities.add(new MyGrantedAuthority(permission.getUrl(), permission.getMethod()));
            
        }
        
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
        
    }
    
    
}
