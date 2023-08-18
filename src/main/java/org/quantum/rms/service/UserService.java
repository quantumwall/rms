package org.quantum.rms.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.quantum.rms.model.User;
import org.quantum.rms.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
	this.userRepository = userRepository;
	this.passwordEncoder = passwordEncoder;
    }

    public List<User> findAll() {
	return userRepository.findAll();
    }

    public Optional<User>findById(Long id) {
	return userRepository.findById(id);
    }
    
    public Optional<User> findByEmail(String email) {
	return userRepository.findByEmail(email);
    }

    @Transactional
    public User save(User user) {
	if (Objects.nonNull(user)) {
	    user.setPassword(passwordEncoder.encode(user.getPassword()));
	    userRepository.save(user);
	}
	return user;
    }

    @Transactional
    public User delete(User user) {
	if (Objects.nonNull(user)) {
	    userRepository.delete(user);
	}
	return user;
    }

}
