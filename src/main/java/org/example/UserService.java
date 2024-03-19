package org.example;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isUserActive(String username) {
        User user = userRepository.findByUsername(username);
        return user != null && user.isActive();
    }

    public void deleteUser(Long userId) throws Exception {
        User user = userRepository.findUserId(userId);
        if (user == null) {
            throw new Exception("User not found");
        }
        userRepository.delete(user);
    }

    public User getUser(Long userId) throws Exception {
        User user = userRepository.findUserId(userId);
        if (user == null) {
            throw new Exception("User not found");
        }
        return user;
    }
}
