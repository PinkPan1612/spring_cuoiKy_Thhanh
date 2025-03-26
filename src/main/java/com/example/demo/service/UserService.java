package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    // get user by id
    public User getUserByID(long id) {
        return this.userRepository.findOneById(id);
    }

    // save
    public void saveUser(User user) {
        this.userRepository.save(user);
    }

    // get role by role_name
    public Role getRole(String role_name) {
        return this.roleRepository.findByRoleName(role_name);
    }

    // delete by id
    public void deleteUser(long id) {
        this.userRepository.deleteById(id);

    }

    // delete by id
    public User getUserByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }
}
