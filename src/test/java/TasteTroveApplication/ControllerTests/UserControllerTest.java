package TasteTroveApplication.ControllerTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import TasteTroveApplication.controller.UserController;
import TasteTroveApplication.models.User;
import TasteTroveApplication.service.UserService;

@ExtendWith(MockitoExtension.class)	
public class UserControllerTest {

	private UserController userController;
	
	@Mock
	private UserService userServiceMock;
	
	@BeforeEach
	public void setup() {
		userController = new UserController(userServiceMock);
	}
	
	@Test
    public void testGetUserById() {
        int userId = 1;
        User user = new User();
        user.setId(userId);
        when(userServiceMock.findUserById(userId)).thenReturn(user);

        User result = userController.getUserById(userId);

        assertEquals(userId, result.getId());
        verify(userServiceMock, times(1)).findUserById(userId);
    }

    @Test
    public void testGetUserByUsername() {
        String username = "test1";
        User user = new User();
        user.setUsername(username);
        when(userServiceMock.findByUsername(username)).thenReturn(user);

        User result = userController.getUserByUsername(username);

        assertEquals(username, result.getUsername());
        verify(userServiceMock, times(1)).findByUsername(username);
    }
	
    @Test
    public void testGetAllUsers() {
        List<User> users = Arrays.asList(new User(), new User());
        when(userServiceMock.findAllUsers()).thenReturn(users);

        List<User> result = userController.getAllUsers();

        assertEquals(users.size(), result.size());
        verify(userServiceMock, times(1)).findAllUsers();
    }
    
    @Test
    public void testAddNewUser() {
        User user = new User();

        userController.addNewUser(user);

        verify(userServiceMock, times(1)).registerUser(user);
    }
    
    @Test
    public void testUpdateUser() {
        User user = new User();

        userController.updateUser(user);

        verify(userServiceMock, times(1)).updateUser(user);
    }
    
    @Test
    public void testDeleteUserById() {
        int userId = 1;

        userController.deleteUserById(userId);

        verify(userServiceMock, times(1)).deleteById(userId);
    }
}
