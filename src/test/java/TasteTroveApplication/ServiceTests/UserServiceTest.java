package TasteTroveApplication.ServiceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import TasteTroveApplication.dal.UserRepository;
import TasteTroveApplication.exceptions.NotFoundException;
import TasteTroveApplication.models.User;
import TasteTroveApplication.service.UserService;

@ExtendWith(MockitoExtension.class)	
public class UserServiceTest {

	private UserService userService;
	
	@Mock
    private UserRepository userRepositoryMock;
	
	@Mock
	private PasswordEncoder encoderMock;

	@BeforeEach
	public void setup() {
		userService = new UserService(userRepositoryMock, encoderMock);
	}
	
	@Test
	public void testFindByUsername() {
		String username = "testUser";
        User user = new User();
        user.setUsername(username);
        when(userRepositoryMock.findByUsername(username)).thenReturn(Optional.of(user));
        
        User foundUser = userService.findByUsername(username);

        assertEquals(username, foundUser.getUsername());
        verify(userRepositoryMock, times(1)).findByUsername(username);
	}
	
	@Test
	public void testFindUserById() {
		int userId = 1;
        User user = new User();
        user.setId(userId);
        when(userRepositoryMock.findById(userId)).thenReturn(Optional.of(user));

        User foundUser = userService.findUserById(userId);

        assertEquals(userId, foundUser.getId());
        verify(userRepositoryMock, times(1)).findById(userId);
	}
	
	@Test
    public void testFindUserById_NotFound() {
        int userId = 1;
        when(userRepositoryMock.findById(userId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            userService.findUserById(userId);
        });
        
        assertEquals("User with ID " + userId + " not found", exception.getMessage());
        verify(userRepositoryMock, times(1)).findById(userId);
    }

	@Test
    public void testUserExistsById() {
        int userId = 1;
        when(userRepositoryMock.existsById(userId)).thenReturn(true);

        boolean result = userService.userExistsById(userId);

        assertTrue(result);
        verify(userRepositoryMock, times(1)).existsById(userId);
    }
	
	@Test
    public void testUserExistsByEmail() {
        String email = "test@example.com";
        when(userRepositoryMock.existsByEmail(email)).thenReturn(true);

        boolean result = userService.userExistsByEmail(email);

        assertTrue(result);
        verify(userRepositoryMock, times(1)).existsByEmail(email);
    }
	
	@Test
    public void testFindAllUsers() {
        List<User> users = Arrays.asList(new User(), new User());
        when(userRepositoryMock.findAll()).thenReturn(users);

        List<User> result = userService.findAllUsers();

        assertEquals(users.size(), result.size());
        verify(userRepositoryMock, times(1)).findAll();
    }
	
	@Test
	public void testSaveUser() {
        User user = new User();
        when(userRepositoryMock.save(user)).thenReturn(user);

        userService.registerUser(user);
        
        verify(userRepositoryMock, times(1)).existsById(user.getId());
        verify(userRepositoryMock, times(1)).save(user);
	}
	
	@Test
    public void testUpdateUser() {
		User existingUser = new User();
        existingUser.setId(1);
        existingUser.setUsername("oldUsername");
        User updatedUser = new User();
        updatedUser.setId(1);
        updatedUser.setUsername("newUsername");
        when(userRepositoryMock.existsById(existingUser.getId())).thenReturn(true);
        when(userRepositoryMock.save(updatedUser)).thenReturn(updatedUser);

        User result = userService.updateUser(updatedUser);

        assertEquals(existingUser.getId(), result.getId());
        assertEquals(updatedUser.getUsername(), result.getUsername());
        verify(userRepositoryMock, times(1)).existsById(existingUser.getId());
        verify(userRepositoryMock, times(1)).save(updatedUser);
    }
	
	@Test
    public void testDeleteById() {
        int userId = 1;
        User user = new User();
        user.setId(userId);

        userService.deleteById(userId);

        verify(userRepositoryMock, times(1)).deleteById(userId);
    }
}
