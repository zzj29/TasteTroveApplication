package TasteTroveApplication.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import TasteTroveApplication.dal.UserRepository;
import TasteTroveApplication.exceptions.NotFoundException;
import TasteTroveApplication.exceptions.ValidationException;
import TasteTroveApplication.models.User;

@Service
public class UserService {

	UserRepository userRepo;
	PasswordEncoder encoder;
	
	@Autowired
	public UserService(UserRepository userRepo, PasswordEncoder encoder) {
		super();
		this.userRepo = userRepo;
		this.encoder = encoder;
	}

	public User findByUsername(String username) {
		return userRepo.findByUsername(username).orElseThrow(() -> new NotFoundException("User with username " + username + " not found"));
	}
	
	public User findUserById(int id) {
		return userRepo.findById(id).orElseThrow(() -> new NotFoundException("User with ID " + id + " not found"));
	}

	public boolean userExistsById(int id) {
		return userRepo.existsById(id);
	}

	public boolean userExistsByEmail(String email) {
		return userRepo.existsByEmail(email);
	}

	public List<User> findAllUsers() {
		return userRepo.findAll();
	}
	
	public void registerUser(User user) {
		if (userRepo.existsById(user.getId())) {
			throw new ValidationException(
					"User with name:" + user.getUsername() + " already exist.");
		} else {
			user.setPassword(encoder.encode(user.getPassword()));
			userRepo.save(user);
		}
	}

	public User updateUser(User user) {
		if (userRepo.existsById(user.getId())) {
			return userRepo.save(user);
		} else {
			throw new ValidationException("User " + user + "does not exist.");
		}
	}

	public void deleteById(int id) {
		userRepo.deleteById(id);
	}

}
