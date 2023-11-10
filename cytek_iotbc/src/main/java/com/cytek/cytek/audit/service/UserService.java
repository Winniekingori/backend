package com.cytek.cytek.audit.service;

import com.cytek.cytek.audit.model.User;
import com.cytek.cytek.audit.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private  final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllClients() {
        return userRepository.findAll();
    }
}
