package com.pdclientmanager.repository.entity;

import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

//@Entity
public class User implements UserDetails {
    
    private static final long serialVersionUID = 1L;
    
//    @Id
    private String username;
    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private boolean enabled;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    
    public User(String username, String email, String password,
            Collection<? extends GrantedAuthority> authorities, boolean enabled,
            boolean accountNonExpired, boolean accountNonLocked, boolean credentialsNonExpired) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.enabled = enabled;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
    }
    
    public User(String username, String email, String password,
            Collection<? extends GrantedAuthority> authorities, boolean enabled) {
        this(username, email, password, authorities, enabled,
                true, true, true);
    }
    
    @Override
    public String getUsername() {
        return username;
    }
    
    public String getEmail() {
        return email;
    }
    
    @Override
    public String getPassword() {
        return password;
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    
    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }
}
