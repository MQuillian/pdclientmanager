package com.pdclientmanager.security;

import java.util.List;

import org.springframework.data.repository.Repository;

public interface UserRepository extends Repository<User, Long> {
    
    public User findByUsername(String username);
    
    public User findById(Long id);
    
    public List<User> findAll();
    
    public void save(User user);
    
    public void delete(User user);
}
