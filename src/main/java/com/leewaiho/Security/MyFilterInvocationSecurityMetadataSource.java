package com.leewaiho.Security;

import com.leewaiho.Bean.Permission;
import com.leewaiho.Repository.PermissionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by leewaiho on 2017/7/23.
 */
@Component
public class MyFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    
    private static final Logger logger = LoggerFactory.getLogger(MyFilterInvocationSecurityMetadataSource.class);
    
    private static final AntPathRequestMatcher OAUTH_ENDPOINT = new AntPathRequestMatcher("/oauth/**");
    
    private static final Collection<ConfigAttribute> toDecide = new ArrayList<ConfigAttribute>(){{
        add(new SecurityConfig("toDecide"));}};
    /**
     * 检查请求的资源是否被保护,如果被保护则返回访问需要的权限;
     * 返回null则跳过检测
     *
     * @param object 转换成httpRequest提取url 和 method
     * @return 访问需要的权限，否则返回null
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        FilterInvocation filterInvocation = (FilterInvocation) object;
        logger.info("请求的url: {}, method: {}", filterInvocation.getFullRequestUrl(), filterInvocation.getHttpRequest().getMethod());
        
        if (!OAUTH_ENDPOINT.matches(filterInvocation.getRequest())) {
            return toDecide;
        }
        return null;
    }
    
    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }
    
    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
