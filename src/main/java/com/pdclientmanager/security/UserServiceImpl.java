package com.pdclientmanager.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.pdclientmanager.calendar.CalendarService;
import com.pdclientmanager.model.form.UserForm;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository repository;
    private BCryptPasswordEncoder encoder;
    
    @Autowired
    public UserServiceImpl(UserRepository repository, BCryptPasswordEncoder encoder) 
        throws IllegalStateException {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = repository.findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException(username + " could not be found");
        } else {
            return user;
        }
    }

    @Override
    public Long saveUser(UserForm userForm) throws IOException {
        User user = mapFormToUser(userForm);
        repository.save(user);
        return user.getId();
    }
    
    @Override
    public UserForm findFormById(Long id) {
        User user = repository.findById(id);
        UserForm form = mapUserToForm(user);
        return form;
    }
    
    @Override
    public UserForm findFormByFullName(String fullName) {
        User user = repository.findByFullName(fullName);
        return mapUserToForm(user);
    }
    
    @Override
    public List<User> findAll() {
        return repository.findAll();
    }
    
    @Override
    public boolean deleteById(Long id) {
        User user = repository.findById(id);
        if(user != null) {
            repository.delete(user);
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public UserForm getCurrentUserAsForm() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null) {
            User currentUser = repository.findByUsername(auth.getName());
            return mapUserToForm(currentUser);
        }
        return null;
    }
    
    @Override
    public List<String> getCurrentUserRoles() {
    	List<String> roles = new ArrayList<>();
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	if(auth != null) {
    		for(GrantedAuthority authority : auth.getAuthorities()) {
    			roles.add(authority.getAuthority());
    		}
    	}
    	return roles;
    }
    
    @Override
    public String getCurrentUserFullName() {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	if(auth != null) {
    		User user = (User) auth.getDetails();
    		return user.getFullName();
    	} else {
    		return null;
    	}
    }
    
    // Mapping methods
    private User mapFormToUser(UserForm userForm) throws IOException {
        User user = null;
        if(userForm.getId() == null) {
            user = new User(userForm.getId(), userForm.getFullName(), userForm.getUsername(), userForm.getEmail(),
                    encoder.encode(userForm.getPassword()), new HashSet<Authority>(), "", true);
            for(String role : userForm.getRoles()) {
                Authority auth = new Authority(null, "ROLE_" + role, user);
                user.getAuthorities().add(auth);
            }
            if(!userForm.getRoles().contains("ADMIN")) {
                Authority auth = new Authority(null, "ROLE_USER", user);
                user.getAuthorities().add(auth);
            }
        } else {
            user = repository.findById(userForm.getId());
            user.setUsername(userForm.getUsername());
            user.setEmail(userForm.getEmail());
            user.getAuthorities().clear();
            for(String role : userForm.getRoles()) {
                Authority auth = new Authority(null, "ROLE_" + role, user);
                user.getAuthorities().add(auth);
            }
            if(!userForm.getRoles().contains("ADMIN")) {
                Authority auth = new Authority(null, "ROLE_USER", user);
                user.getAuthorities().add(auth);
            }
        }
        return user;
    }
    
    private UserForm mapUserToForm(User user) {
        UserForm userForm = new UserForm(user.getId(), user.getFullName(), user.getUsername(), user.getEmail(),
                user.getPassword(), user.getPassword(), new ArrayList<String>());
        for(Authority auth : user.getAuthorities()) {
            String role = auth.getAuthority();
            userForm.getRoles().add(role.substring(5, role.length()));
        }
        return userForm;
    }
}
