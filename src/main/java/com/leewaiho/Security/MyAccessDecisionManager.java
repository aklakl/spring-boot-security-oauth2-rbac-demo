package com.leewaiho.Security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Created by leewaiho on 2017/7/23.
 */
@Service
public class MyAccessDecisionManager implements AccessDecisionManager {
    
    private static final Logger logger = LoggerFactory.getLogger(MyAccessDecisionManager.class);
    
    /**
     * @param authentication   请求携带的验证信息
     * @param object           用户发起的请求,转换成Request获得请求的url和Method
     * @param configAttributes 访问资源所需要的权限
     * @throws AccessDeniedException
     * @throws InsufficientAuthenticationException
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        
        FilterInvocation filterInvocation = (FilterInvocation) object;
        
        //匿名用户
        if (authentication instanceof AnonymousAuthenticationToken) {
            logger.info("做一些匿名用户要执行的操作");
            throw new InsufficientAuthenticationException("你还未登录,请先登录");
        } else if (authentication instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) authentication;
            UserDetails userDetails = (UserDetails) usernamePasswordAuthenticationToken.getPrincipal();
            String client_id = userDetails.getUsername();
            logger.info("client_id: {} 正在访问", client_id);
        } else if (authentication instanceof OAuth2Authentication) {
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (GrantedAuthority grantedAuthority : authorities) {
                if (grantedAuthority instanceof MyGrantedAuthority) {
                    MyGrantedAuthority userGrantedAuthority = (MyGrantedAuthority) grantedAuthority;
                    AntPathRequestMatcher antPathRequestMatcher = new AntPathRequestMatcher(userGrantedAuthority.getUrl());
                    if (antPathRequestMatcher.matches(filterInvocation.getRequest())) {
                        String method = userGrantedAuthority.getMethod();
                        if (method == null || method.trim().equals("") || filterInvocation.getRequest().getMethod().trim().equals(method.trim()))
                            return;
                    }
                }
            }
        }
        throw new AccessDeniedException("由于权限不足,你的访问被拒绝");
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
