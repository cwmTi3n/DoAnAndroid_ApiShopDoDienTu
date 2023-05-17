package com.kt.configs;

import com.kt.services.impl.CustomUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailService();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
    @Bean
    public SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authenticationProvider(authenticationProvider());
        httpSecurity.csrf().disable();
        httpSecurity.authorizeRequests(request -> request
                        .antMatchers("/admin/**", "/seller/**", "/account/**").authenticated());
        httpSecurity.authorizeRequests(request -> request
                .antMatchers("/admin/**").hasAuthority("ADMIN"));
        httpSecurity.authorizeRequests(request -> request
                .antMatchers("/seller/**").hasAnyAuthority("SELLER", "ADMIN"));
        httpSecurity.authorizeRequests(request -> request
                .anyRequest().permitAll())
                .httpBasic();
        return httpSecurity.build();
    }

}
