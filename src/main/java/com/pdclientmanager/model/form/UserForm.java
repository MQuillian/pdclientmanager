package com.pdclientmanager.model.form;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import com.pdclientmanager.util.validator.EmailConstraint;
import com.pdclientmanager.util.validator.MatchingPasswordConstraint;
import com.pdclientmanager.util.validator.PasswordConstraint;

@MatchingPasswordConstraint
public class UserForm {

    private Long id;
    
    @NotEmpty
    private String username;
    
    @EmailConstraint
    @NotEmpty
    private String email;
    
    @PasswordConstraint
    @NotEmpty
    private String password;
    
    @PasswordConstraint
    @NotEmpty
    private String matchingPassword;
    
    @NotEmpty
    private List<String> roles;
    
    public UserForm() {
        
    }
    
    public UserForm(Long id, String username, String email, String password,
            String matchingPassword, List<String> roles) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.matchingPassword = matchingPassword;
        this.roles = roles;
    }
    
    public boolean isNew() {
        return id == null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getMatchingPassword() {
        return matchingPassword;
    }
    
    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }
    
    public List<String> getRoles() {
        return roles;
    }
    
    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
    
    public static class UserFormBuilder {
        
        private Long id = 1L;
        private String username = "Default UserForm";
        private String email = "default@email.com";
        private String password = "defaultpass";
        private String matchingPassword = "defaultpass";
        private List<String> roles = new ArrayList<String>();
        
        public UserFormBuilder() {}
        
        public UserFormBuilder withId(Long id) {
            this.id = id;
            return this;
        }
        
        public UserFormBuilder withUsername(String username) {
            this.username = username;
            return this;
        }
        
        public UserFormBuilder withEmail(String email) {
            this.email = email;
            return this;
        }
        
        public UserFormBuilder withPassword(String password) {
            this.password = password;
            return this;
        }
        
        public UserFormBuilder withMatchingPassword(String matchingPassword) {
            this.matchingPassword = matchingPassword;
            return this;
        }
        
        public UserFormBuilder withRoles(List<String> roles) {
            this.roles = roles;
            return this;
        }
        
        public UserForm build() {
            return new UserForm(id, username, email, password, matchingPassword, roles);
        }
    }
}
