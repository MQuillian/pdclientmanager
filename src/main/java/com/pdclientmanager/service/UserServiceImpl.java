package com.pdclientmanager.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import com.pdclientmanager.model.form.UserForm;
import com.pdclientmanager.repository.entity.User;

@Service
public class UserServiceImpl implements UserService {

//    private UserRepository repository;
    private UserDetailsManager inMemoryUserDetailsManager;
    private BCryptPasswordEncoder encoder;
    
    @Autowired
    public UserServiceImpl(UserDetailsManager inMemoryUserDetailsManager, BCryptPasswordEncoder encoder) 
        throws IllegalStateException {
//        this.repository = repository;
        this.inMemoryUserDetailsManager = inMemoryUserDetailsManager;
        this.encoder = encoder;
    }
    
    @Override
    public String saveUser(UserForm userForm) {
        if(userExists(userForm)) {
            throw new IllegalStateException("User already exists");
        } else {
            String hashedPassword = encoder.encode(userForm.getPassword());
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            for(String role : userForm.getRoles()) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
            }
            User user = new User(userForm.getUsername(), userForm.getEmail(),
                    hashedPassword, authorities, true);
//            repository.save(user);
            inMemoryUserDetailsManager.createUser(user);
            return user.getUsername();
        }
    }
    
    private boolean userExists(UserForm userForm) {
//        return repository.findByUsername(userForm.getUsername()) != null;
        return inMemoryUserDetailsManager.userExists(userForm.getUsername());
    }

}
