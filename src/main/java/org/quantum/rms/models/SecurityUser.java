package org.quantum.rms.models;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUser implements UserDetails {

    private static final long serialVersionUID = 1L;
    private final User user;

    public SecurityUser(User user) {
	Objects.requireNonNull(user);
	this.user = user;
    }
    
    public Long getId() {
	return user.getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
	return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
	return user.getPassword();
    }

    @Override
    public String getUsername() {
	return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
	return user.isActive();
    }

    @Override
    public boolean isAccountNonLocked() {
	return user.isActive();
    }

    @Override
    public boolean isCredentialsNonExpired() {
	return user.isActive();
    }

    @Override
    public boolean isEnabled() {
	return user.isActive();
    }
    
    
}
