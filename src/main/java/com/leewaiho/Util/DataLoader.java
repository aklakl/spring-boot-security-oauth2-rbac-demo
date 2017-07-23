package com.leewaiho.Util;

import com.leewaiho.Bean.Permission;
import com.leewaiho.Bean.Role;
import com.leewaiho.Bean.User;
import com.leewaiho.Repository.PermissionRepository;
import com.leewaiho.Repository.RoleRepository;
import com.leewaiho.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Arrays;

/**
 * Created by leewaiho on 2017/7/20.
 */
@Component
@Transactional
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {
    
    private boolean isSetup = false;
    
    @Autowired
    private JdbcClientDetailsService jdbcClientDetailsService;
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    
    private void initClientDetails() {
        BaseClientDetails clientDetails = new BaseClientDetails();
        clientDetails.setClientId("jf-app");
        clientDetails.setClientSecret("secret");
        clientDetails.setAuthorizedGrantTypes(Arrays.asList("password", "authorization_code", "refresh_token", "implicit"));
        clientDetails.setScope(Arrays.asList("read", "write", "trust"));
        clientDetails.setAuthorities(Arrays.asList(new SimpleGrantedAuthority("TEST")));
        clientDetails.setAccessTokenValiditySeconds(120000);
        clientDetails.setRefreshTokenValiditySeconds(6000);
        jdbcClientDetailsService.addClientDetails(clientDetails);
    }
    
    private void initPermissions() {
        Permission permission = new Permission();
        permission.setName("查看自己的资料");
        permission.setUrl("/users/me");
        permission.setMethod(HttpMethod.GET.name());
        permission.setDescription("查看自己的资料");
        permissionRepository.save(permission);
    
        Permission get_all_users = new Permission();
        get_all_users.setName("GET_ALL_USERS");
        get_all_users.setUrl("/users");
    }
    
    private void initRoles() {
        Role role = new Role();
        role.setName("Tester");
        role.setDescription("Just Make a Demo");
        role.setPermissions(permissionRepository.findAll());
        roleRepository.save(role);
    }
    
    private void initUser() {
        User user = new User();
        user.setUsername("lee");
        user.setPassword("123456");
        user.setRoles(roleRepository.findAll());
        userRepository.save(user);
    }
    
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (!isSetup) {
            initClientDetails();
            initPermissions();
            initRoles();
            initUser();
            isSetup = true;
        }
    }
}
