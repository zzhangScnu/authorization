package com.lihua.authorization.config;

import com.lihua.authorization.serviceimpl.MyUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final MyUserDetailService myUserDetailService;

    private final MyAccessDecisionManager myAccessDecisionManager;

    private final MySecurityMetaDataSource mySecurityMetaDataSource;

    private final MyAccessDeniedHandler myAccessDeniedHandler;

    private final BackdoorAuthenticateProvider backdoorAuthenticateProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(backdoorAuthenticateProvider);
        auth.userDetailsService(myUserDetailService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/login_p", "/index.html", "/error");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                        object.setAccessDecisionManager(myAccessDecisionManager);
                        object.setSecurityMetadataSource(mySecurityMetaDataSource);
                        return object;
                    }
                })
                .and()
                .formLogin().loginPage("/login_p").loginProcessingUrl("/login").permitAll()
                .usernameParameter("myusername").passwordParameter("mypassword")
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/login").permitAll()
                .and()
                .csrf().disable()
                .exceptionHandling().accessDeniedHandler(myAccessDeniedHandler);
    }
}
