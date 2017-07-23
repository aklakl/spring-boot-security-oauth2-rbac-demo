package com.leewaiho.Security;

import com.leewaiho.ExceptionHandler.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collection;

/**
 * Created by leewaiho on 2017/7/23.
 */
@Service
public class MyAccessDecisionManager implements AccessDecisionManager {
    
    private static final Logger logger = LoggerFactory.getLogger(MyAccessDecisionManager.class);
    
    /**
     * @param authentication   发起请求的用户的验证信息
     * @param object           用户发起的请求,转换成Request获得请求的url和Method
     * @param configAttributes 访问资源所需要的权限
     * @throws AccessDeniedException
     * @throws InsufficientAuthenticationException
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        
        FilterInvocation filterInvocation = (FilterInvocation) object;
        
        for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
            if (grantedAuthority instanceof MyGrantedAuthority) {
                MyGrantedAuthority userGrantedAuthority = (MyGrantedAuthority) grantedAuthority;
                AntPathRequestMatcher antPathRequestMatcher = new AntPathRequestMatcher(userGrantedAuthority.getUrl());
                if (antPathRequestMatcher.matches(filterInvocation.getRequest())) {
                    String method = userGrantedAuthority.getMethod();
                    if (method == null || method.trim().equals("") || filterInvocation.getRequest().getMethod().trim().equals(method.trim()))
                        return;
                }
            } else {
                throw new ServiceException(HttpStatus.INTERNAL_SERVER_ERROR, "权限状态异常");
            }
        }
        
        try {
            filterInvocation.getResponse().sendError(HttpStatus.FORBIDDEN.value(), "由于权限不足,你的访问被拒绝");
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }
    
    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
