package org.quantum.rms.config;

import org.quantum.rms.services.JpaUserDetailsService;
import org.quantum.rms.views.LoginView;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.vaadin.flow.spring.security.VaadinWebSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends VaadinWebSecurity {

    private final JpaUserDetailsService userDetailsService;

    public SecurityConfig(JpaUserDetailsService userDetailsService) {
	this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
	http.userDetailsService(userDetailsService);
	super.configure(http);
	setLoginView(http, LoginView.class);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
	return new BCryptPasswordEncoder();
    }

//    @Bean
//    UserDetailsService userDetailsService(UserRepository userRepository) {
//	return username -> {
//	    return userRepository.findByEmail(username).map(SecurityUser::new)
//		    .orElseThrow(() -> new UsernameNotFoundException("%s is not found".formatted(username)));
//	};
//    }

}
