package org.quantum.rms.services;

import java.util.List;
import java.util.Objects;

import org.quantum.rms.models.User;
import org.quantum.rms.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
	this.userRepository = userRepository;
    }

    public List<User> findAll() {
	return userRepository.findAll();
    }

    public User findById(Long id) {
	return userRepository.findById(id).orElse(null);
    }

    @Transactional
    public User save(User user) {
	if (Objects.nonNull(user)) {
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
