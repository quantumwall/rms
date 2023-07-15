package org.quantum.rms.service;

import java.util.Optional;

import org.quantum.rms.model.SecurityUser;
import org.springframework.stereotype.Service;

import com.vaadin.flow.spring.security.AuthenticationContext;

@Service
public class SecurityService {

    private final AuthenticationContext authContext;

    public SecurityService(AuthenticationContext authContext) {
	this.authContext = authContext;
    }
    
    public Optional<SecurityUser> getAuthenticatedUser() {
	return authContext.getAuthenticatedUser(SecurityUser.class);
    }
    
    public void logout() {
	authContext.logout();
    }
    
}
