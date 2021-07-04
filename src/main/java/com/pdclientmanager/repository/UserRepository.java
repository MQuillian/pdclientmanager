package com.pdclientmanager.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.pdclientmanager.repository.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {
    
    public User findByUsername(String username);
    
    public Optional<User> findById(Long id);
    
    public User findByFullName(String fullName);
    
    public List<User> findAll();
    
    @SuppressWarnings("unchecked")
	public User save(User user);
    
    public void delete(User user);
}
