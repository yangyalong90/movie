package com.xxx.common.security;

import com.xxx.common.security.cache.DefaultTokenCache;
import com.xxx.common.security.cache.TokenCache;
import com.xxx.common.security.detail.UserDetail;
import com.xxx.common.security.handler.*;
import com.xxx.common.security.handler.url.UrlAccessDecision;
import com.xxx.common.security.handler.url.UrlAccessDeniedHandler;
import com.xxx.common.security.handler.url.UrlResourceDecider;
import com.xxx.common.security.jwt.JWTConfig;
import com.xxx.common.security.service.AbstractUserDetailService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private UrlResourceDecider urlResourceDecider;

    @Autowired(required = false)
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        if (authenticationProvider != null) {
            auth.authenticationProvider(authenticationProvider);
        }
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        UrlAccessDeniedHandler accessDeniedHandler = new UrlAccessDeniedHandler();
        http.authorizeRequests()
                // 所有请求都需要权限认证
                .anyRequest().authenticated()
                .and()
                .antMatcher("/**").authorizeRequests()
                // 设置访问请求决策控制
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                        object.setAccessDecisionManager(new UrlAccessDecision(urlResourceDecider));
                        return object;
                    }
                })
                .and()
                // 禁用session manage
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // 设置登录
                .formLogin()
                .loginProcessingUrl(securityProperties.loginUrl)
                // 设置登录成功handler
                .successHandler(authenticationHandler)
                // 设置登录失败handler
                .failureHandler(authenticationHandler)
                .and()
                // 登出 url
                .logout().logoutUrl(securityProperties.logoutUrl)
                .and()
                // 禁用 csrf
                .csrf().disable()
                // 添加token filter，主要根据request信息生成 authentication
                .addFilter(new AuthenticationTokenFilter(authenticationManager(), tokenHandler))
                // 设置异常处理器
                .exceptionHandling()
                // （未授权）访问拒绝处理
                .accessDeniedHandler(accessDeniedHandler)
                // 未认证处理
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
        return new AbstractUserDetailService() {
            @Override
            public UserDetail loadUserDetail(String username) throws UsernameNotFoundException {
                return new UserDetail(username, "1");
            }
        };
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

    @Bean
    public UrlResourceDecider urlResourceDecider(){
        return (auth, request) -> {};
    }

    @Bean
    @ConditionalOnMissingBean
    public PasswordEncoder defaultPasswordEncoder(){
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return rawPassword.toString();
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return encodedPassword.equals(rawPassword.toString());
            }
        };
    }

    @Configuration
    @ConfigurationProperties("spring.security")
    @Data
    public static class SecurityProperties {
        private static final String LOGIN_URL = "login";
        private static final String LOGOUT_URL = "logout";
        private String loginUrl = LOGIN_URL;
        private String logoutUrl = LOGOUT_URL;
        private boolean server;
    }

}
