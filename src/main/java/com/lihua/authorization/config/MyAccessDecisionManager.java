package com.lihua.authorization.config;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MyAccessDecisionManager implements AccessDecisionManager {

    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
            throws AccessDeniedException, InsufficientAuthenticationException {
        if (authentication instanceof AnonymousAuthenticationToken) {
            throw new BadCredentialsException("用户未登录");
        }
        List<String> validAuthorityList = configAttributes.stream()
                .map(ConfigAttribute::getAttribute)
                .collect(Collectors.toList());
        if (validAuthorityList.contains("ROLE_NONE") || validAuthorityList.contains("ROLE_ADMIN")) {
            return;
        }
        boolean flag = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(validAuthorityList::contains);
        if (!flag) {
            throw new AccessDeniedException("你没有访问" + object + "的权限!");
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
