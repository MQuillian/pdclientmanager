package com.pdclientmanager.repository.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;

import org.springframework.security.core.userdetails.UserDetails;

import com.pdclientmanager.util.validator.EmailConstraint;
import com.pdclientmanager.util.validator.PasswordConstraint;

@Entity
public class User implements UserDetails {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotEmpty(message = "Name")
    private String fullName;
    
    @NotEmpty(message = "Username")
    private String username;
    
    @EmailConstraint
    @NotEmpty(message = "Email")
    private String email;
    
    @PasswordConstraint
    @NotEmpty(message = "Password")
    private String password;
    
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL,
            fetch = FetchType.EAGER, mappedBy = "user")
    @NotEmpty
    private Set<Authority> authorities;
    
    private boolean enabled;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    
    public User() {
        
    }
    
    public User(Long id, String fullName, String username, String email, String password,
            Set<Authority> authorities, boolean enabled, boolean accountNonExpired,
            boolean accountNonLocked, boolean credentialsNonExpired) {
        this.id = id;
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.enabled = enabled;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
    }
    
    public User(Long id, String fullName, String username, String email, String password,
            Set<Authority> authorities, String calendarUserId, boolean enabled) {
        this(id, fullName, username, email, password, authorities, enabled,
                true, true, true);
    }
    
    public Long getId() {
        return id;
    }
    
    public String getFullName() {
        return fullName;
    }
    
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    @Override
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    @Override
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    @Override
    public Set<Authority> getAuthorities() {
        return authorities;
    }
    
    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }
    
    @Override
    public boolean isEnabled() {
        return enabled;
    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }
    
    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }
    
    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }
    
    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }
    
    public static class UserBuilder {
        
        private Long id = 1L;
        private String fullName = "Full Name";
        private String username = "user";
        private String email = "user@defualt.com";
        private String password = "userpass";
        private Set<Authority> authorities = new HashSet<>();
        private boolean enabled = true;
        private boolean accountNonExpired = true;
        private boolean accountNonLocked = true;
        private boolean credentialsNonExpired = true;
        
        public UserBuilder withId(Long id) {
            this.id = id;
            return this;
        }
        
        public UserBuilder withFullName(String fullName) {
            this.fullName = fullName;
            return this;
        }
        
        public UserBuilder withUsername(String username) {
            this.username = username;
            return this;
        }
        
        public UserBuilder withEmail(String email) {
            this.email = email;
            return this;
        }
        
        public UserBuilder withPassword(String password) {
            this.password = password;
            return this;
        }
        
        public UserBuilder withAuthorities(Set<Authority> authorities) {
            this.authorities = authorities;
            return this;
        }
        
        public UserBuilder withEnabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }
        
        public UserBuilder withAccountNonExpired(boolean accountNonExpired) {
            this.accountNonExpired = accountNonExpired;
            return this;
        }
        
        public UserBuilder withAccountNonLocked(boolean accountNonLocked) {
            this.accountNonLocked = accountNonLocked;
            return this;
        }
        
        public UserBuilder withCredentialsNonExpired(boolean credentialsNonExpired) {
            this.credentialsNonExpired = credentialsNonExpired;
            return this;
        }
        
        public User build() {
            return new User(id, fullName, username, email, password, authorities,
                    enabled, accountNonExpired, accountNonLocked, credentialsNonExpired);
        }
    }
}
