package org.cognizant.usercitizenmanagement.dao;

import org.cognizant.usercitizenmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    // Used for authentication and checking uniqueness
    Optional<User> findByEmail(String email);
}