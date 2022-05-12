package com.pdclientmanager.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.pdclientmanager.service.UserService;

@EnableWebSecurity
@ComponentScan(basePackages = {"com.pdclientmanager"})
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Autowired
    UserService customUserService;
    
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth)
        throws Exception {
        auth.userDetailsService(customUserService);
    }
    
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/admin/**", "/attorneys/add", "/attorneys/*/update",
                    "/attorneys/*/delete", "/investigators/add", "/investigators/*/update",
                    "/investigators/*/delete", "/charges/add",
                    "/charges/*/update", "/charges/*/delete",
                    "/judges/add", "/judges/*/update", "/judges/*/delete").hasRole("ADMIN")
            .antMatchers("/login*", "/resources/**", "/static/**", "/healthcheck", "/favicon.ico").permitAll()
            .antMatchers(HttpMethod.POST, "/attorneys/**", "/investigators/**", "/charges/**",
                    "/judges/**").hasRole("ADMIN")
            .antMatchers("/**").hasAnyRole("ADMIN", "USER")
            .and().formLogin()
            .loginPage("/login.html")
            .loginProcessingUrl("/handle_login")
            .defaultSuccessUrl("/", true)
            .failureUrl("/login.html?error=true")
            .and().exceptionHandling().accessDeniedPage("/accessDenied.jsp")
            .and().logout().logoutSuccessUrl("/login.html?logout=true").permitAll()
            .and().csrf().disable();
    }
}
