package com.lihua.authorization.config;

import com.lihua.authorization.persistent.pojo.Role;
import com.lihua.authorization.persistent.repository.ResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MySecurityMetaDataSource implements FilterInvocationSecurityMetadataSource {

    private final ResourceRepository resourceRepository;

    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        List<String> roleNameList = resourceRepository.findAll().stream()
                .filter(resource -> antPathMatcher.match(resource.getUrl(), requestUrl))
                .flatMap(resource -> resource.getRoleList().stream())
                .map(Role::getRole)
                .distinct()
                .collect(Collectors.toList());
        return roleNameList.isEmpty() ?
                SecurityConfig.createList("ROLE_NONE") : SecurityConfig.createList(roleNameList.toArray(new String[0]));
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return FilterInvocation.class.isAssignableFrom(aClass);
    }
}
