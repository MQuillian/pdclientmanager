package com.pdclientmanager.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.pdclientmanager.security.UserService;

@EnableWebSecurity
@Order(Ordered.HIGHEST_PRECEDENCE)
@ComponentScan(basePackages = {"com.pdclientmanager.security", "com.pdclientmanager.calendar"})
public class SecurityConfigTest extends WebSecurityConfigurerAdapter {
    
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/admin/**", "/attorneys/add", "/attorneys/*/update",
                    "/attorneys/*/delete", "/investigators/add", "/investigators/*/update",
                    "/investigators/*/delete", "/charges/add",
                    "/charges/*/update", "/charges/*/delete",
                    "/judges/add", "/judges/*/update", "/judges/*/delete").hasRole("ADMIN")
            .antMatchers("/login*", "/resources/**", "/static/**").permitAll()
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