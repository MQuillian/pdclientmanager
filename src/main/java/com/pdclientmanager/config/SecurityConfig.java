package com.pdclientmanager.config;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import com.pdclientmanager.repository.entity.User;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth)
        throws Exception {
        
        auth.userDetailsService(inMemoryUserDetailsManager()).passwordEncoder(passwordEncoder());
    }
    
    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        Collection<UserDetails> users = new ArrayList<>();
        Collection<SimpleGrantedAuthority> userRoles = new ArrayList<>();
        userRoles.add(new SimpleGrantedAuthority("ROLE_USER"));
        users.add(new User("user", "user@test.com", passwordEncoder().encode("userpass"), userRoles, true));
        
        Collection<SimpleGrantedAuthority> adminRoles = new ArrayList<>();
        adminRoles.add(new SimpleGrantedAuthority("ROLE_USER"));
        adminRoles.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        users.add(new User("admin", "admin@test.com", passwordEncoder().encode("adminpass"), adminRoles, true));

        return new InMemoryUserDetailsManager(users);
    }
    
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/admin/**", "/attorneys/add", "/attorneys/*/update",
                    "/attorneys/*/delete", "investigators/add", "/investigators/*/update",
                    "/investigators/*/delete", "/charges/add",
                    "/charges/*/update", "/charges/*/delete",
                    "/judges/add", "/judges/*/update", "/judges/*/delete").hasRole("ADMIN")
            .antMatchers("/login*", "/resources/**", "/static/**").permitAll()
            .antMatchers("/**").hasAnyRole("ADMIN", "USER")
            .and().formLogin()
            .loginPage("/login.html")
            .loginProcessingUrl("/handle_login")
            .defaultSuccessUrl("/", true)
            .failureUrl("/login.html?error=true")
            .and().logout().logoutSuccessUrl("/login.html?logout=true").permitAll()
            .and().csrf().disable();
    }
}
