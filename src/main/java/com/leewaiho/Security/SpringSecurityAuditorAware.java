package com.leewaiho.Security;

import com.leewaiho.Bean.User;
import com.leewaiho.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SpringSecurityAuditorAware implements AuditorAware<User> {
    
    @Autowired
    private UserRepository userRepository;
    
    public User getCurrentAuditor() {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        
        User user = userRepository.findByUsername(((UserDetails) authentication.getPrincipal()).getUsername());
        
        return user;
    }
}