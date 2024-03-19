package org.example;

public interface UserRepository {
    User findByUsername(String username);
    User findUserId(Long userId);
    void delete(User user);
}