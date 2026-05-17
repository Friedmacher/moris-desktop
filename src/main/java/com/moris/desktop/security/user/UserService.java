package com.moris.desktop.security.user;

import com.moris.desktop.security.login.PasswordGenerator;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public Optional<User> findByUserName(String username) {
		return userRepository.findByUserName(username);
	}

	public Optional<User> findByUserNameAndPassword(String username, String password) {
		return userRepository.findByUserNameAndPassword(username, password);
	}

	public void saveUser(String username) {
		User newUser = new User();
		newUser.setUserName(username);
		userRepository.save(newUser);
	}

	public void resetPassword(String username, String password) {
		Optional<User> user = userRepository.findByUserName(username);
		if (user.isPresent()) {
			User changedUser = user.get();
			changedUser.setPassword(password);
			changedUser.setPasswordInitial(true);
			userRepository.save(changedUser);
		}
	}

	public Optional<User> registerNewUser(String username) {
		User newUser = new User();
		newUser.setUserName(username);
		newUser.setPassword(PasswordGenerator.generate(32));
		newUser.setPasswordInitial(true);
		newUser.setUserType(UserType.USER);
		userRepository.save(newUser);
		return Optional.of(newUser);
	}
}
