package TasteTroveApplication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import TasteTroveApplication.models.User;
import TasteTroveApplication.service.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin("http://localhost:5173/")
public class UserController {

	UserService userServ;
	
	@Autowired
	public UserController(UserService userServ) {
		super();
		this.userServ = userServ;
	}
	
	// http://localhost:8088/TasteTroveApplication/user/id
	@GetMapping("{id}")
	public User getUserById(@PathVariable int id) {
		return userServ.findUserById(id);
	}

	// http://localhost:8088/TasteTroveApplication/user/username
	@GetMapping("/{username}")
    public User getUserByUsername(@PathVariable String username) {
        return userServ.findByUsername(username);
    }
	
	// http://localhost:8088/TasteTroveApplication/user
	@GetMapping
	public List<User> getAllUsers() {
		return userServ.findAllUsers();
	}
	
	@GetMapping("/me")
	public User getUserByBearerToken(Authentication auth) {
		return userServ.findByUsername(auth.getName());
	}
	
	// http://localhost:8088/TasteTroveApplication/user/register
	@PostMapping("register")
	public void addNewUser(@RequestBody User user) {
		userServ.registerUser(user);
	}
	
	// http://localhost:8088/TasteTroveApplication/user/update
	@PutMapping("update")
	public void updateUser(@RequestBody User user) {
		userServ.updateUser(user);
	}
	
	// http://localhost:8088/TasteTroveApplication/user/id
	@DeleteMapping("{id}")
	public void deleteUserById(@PathVariable int id) {
		userServ.deleteById(id);
	}
}
