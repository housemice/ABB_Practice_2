import org.example.User;
import org.example.UserRepository;
import org.example.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testIsUserActive_UserExistsAndIsActive() {
        // Arrange
        String username = "testUser";
        User activeUser = new User();
        activeUser.setUsername(username);
        activeUser.setActive(true);
        when(userRepository.findByUsername(username)).thenReturn(activeUser);

        // Act
        boolean isActive = userService.isUserActive(username);

        // Assert
        assertTrue(isActive);
    }

    @Test
    void testIsUserActive_UserExistsButIsNotActive() {
        // Arrange
        String username = "testUser";
        User inactiveUser = new User();
        inactiveUser.setUsername(username);
        inactiveUser.setActive(false);
        when(userRepository.findByUsername(username)).thenReturn(inactiveUser);

        // Act
        boolean isActive = userService.isUserActive(username);

        // Assert
        assertFalse(isActive);
    }

    @Test
    void testIsUserActive_UserDoesNotExist() {
        // Arrange
        String username = "nonExistentUser";
        when(userRepository.findByUsername(username)).thenReturn(null);

        // Act
        boolean isActive = userService.isUserActive(username);

        // Assert
        assertFalse(isActive);
    }

    @Test
    void testDeleteUser_UserExists() throws Exception {
        // Arrange
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        when(userRepository.findUserId(userId)).thenReturn(user);

        // Act & Assert
        assertDoesNotThrow(() -> userService.deleteUser(userId));
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void testDeleteUser_UserDoesNotExist() {
        // Arrange
        Long nonExistentUserId = 100L;
        when(userRepository.findUserId(nonExistentUserId)).thenReturn(null);

        // Act & Assert
        assertThrows(Exception.class, () -> userService.deleteUser(nonExistentUserId));
        verify(userRepository, never()).delete(any(User.class));
    }

    @Test
    void testGetUser_UserExists() throws Exception {
        // Arrange
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        when(userRepository.findUserId(userId)).thenReturn(user);

        // Act
        User retrievedUser = userService.getUser(userId);

        // Assert
        assertEquals(user, retrievedUser);
    }

    @Test
    void testGetUser_UserDoesNotExist() {
        // Arrange
        Long nonExistentUserId = 100L;
        when(userRepository.findUserId(nonExistentUserId)).thenReturn(null);

        // Act & Assert
        assertThrows(Exception.class, () -> userService.getUser(nonExistentUserId));
    }
}
