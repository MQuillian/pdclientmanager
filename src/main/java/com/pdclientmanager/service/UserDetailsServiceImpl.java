//package com.pdclientmanager.service;
//
//import javax.transaction.Transactional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
//import com.pdclientmanager.repository.UserRepository;
//import com.pdclientmanager.repository.entity.User;
//
//public class UserDetailsServiceImpl implements UserDetailsService {
//    
//    private UserRepository repository;
//
//    @Autowired
//    public UserDetailsServiceImpl(UserRepository repository) {
//        this.repository = repository;
//    }
//
//    @Override
//    @Transactional
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = repository.findByUsername(username);
//        return user;
//    }
//}
