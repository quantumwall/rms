package org.quantum.rms.service;

import org.quantum.rms.model.SecurityUser;
import org.quantum.rms.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public JpaUserDetailsService(UserRepository userRepository) {
	this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	return userRepository.findByEmail(username).map(SecurityUser::new)
		.orElseThrow(() -> new UsernameNotFoundException("%s not found".formatted(username)));
    }

}
