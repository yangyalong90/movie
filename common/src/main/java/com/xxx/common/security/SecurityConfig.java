package com.xxx.common.security;

import com.xxx.common.security.cache.DefaultTokenCache;
import com.xxx.common.security.cache.TokenCache;
import com.xxx.common.security.detail.UserDetail;
import com.xxx.common.security.handler.*;
import com.xxx.common.security.handler.url.UrlAccessDecision;
import com.xxx.common.security.handler.url.UrlAccessDeniedHandler;
import com.xxx.common.security.jwt.JWTConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

@Configuration
@EnableWebSecurity
@Import(JWTConfig.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationHandler authenticationHandler;

    @Autowired
    private TokenHandler tokenHandler;

    @Autowired(required = false)
    private AuthenticationProvider authenticationProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
        if (authenticationProvider != null) {
            auth.authenticationProvider(authenticationProvider);
        }
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        UrlAccessDeniedHandler accessDeniedHandler = new UrlAccessDeniedHandler();
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .antMatcher("/**").authorizeRequests()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                        object.setAccessDecisionManager(new UrlAccessDecision());
                        return object;
                    }
                })
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin()
                .loginProcessingUrl("/user/user/auth/login")
                .successHandler(authenticationHandler)
                .failureHandler(authenticationHandler)
                .and()
                .logout().logoutUrl("/logout")
                .and()
                .csrf().disable()
                .addFilter(new AuthenticationTokenFilter(authenticationManager(), tokenHandler))
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(accessDeniedHandler);
    }

    @Bean
    @ConditionalOnMissingBean
    public AuthenticationHandler authenticationHandler() {
        return new AuthenticationHandler();
    }

    @Bean
    @ConditionalOnMissingBean
    public UserDetailsService defaultUserDetailsService() {
        return username -> new UserDetail(username, "1");
    }

    @Bean
    @ConditionalOnMissingBean
    public TokenHandler tokenHandler() {
        return new DefaultTokenHandler();
    }

    @Bean
    @ConditionalOnMissingBean
    public TokenCache tokenCache() {
        return new DefaultTokenCache();
    }

}
