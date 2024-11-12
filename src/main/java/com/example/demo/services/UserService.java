package com.example.demo.services;

import com.example.demo.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    public User createUser(User user);
    public List<User> getAllUsers();
    public Optional<User> getUserById(Long id);
    public User updateUser(Long id, User updatedUser);
    public void deleteUser(Long id);
}
