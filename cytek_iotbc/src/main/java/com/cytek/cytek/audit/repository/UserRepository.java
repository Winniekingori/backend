package com.cytek.cytek.audit.repository;

import com.cytek.cytek.audit.model.Role;
import com.cytek.cytek.audit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
//
//
//    List<User> findByRole(Role role);
}