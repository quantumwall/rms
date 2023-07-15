package org.quantum.rms.services;

import org.quantum.rms.models.SecurityUser;
import org.quantum.rms.repositories.UserRepository;
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
