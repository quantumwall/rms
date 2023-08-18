package org.quantum.rms.view;

import org.quantum.rms.exception.UserNotFoundException;
import org.quantum.rms.model.SecurityUser;
import org.quantum.rms.model.User;
import org.quantum.rms.service.CustomerService;
import org.quantum.rms.service.DriverService;
import org.quantum.rms.service.SecurityService;
import org.quantum.rms.service.UserService;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.PermitAll;

@Route(value = "settings", layout = MainLayout.class)
@PermitAll
public class SettingsView extends Composite<Component> {

    private static final long serialVersionUID = 1L;
//    private final SecurityService securityService;
    private final DriverService driverService;
    private final CustomerService customerService;
    private final UserService userService;
    private final User user;

    public SettingsView(SecurityService securityService, CustomerService customerService, DriverService driverService,
	    UserService userService) {
	this.user = securityService.getAuthenticatedUser().map(SecurityUser::getUser).orElseThrow(() -> new UserNotFoundException(null));
	this.driverService = driverService;
	this.customerService = customerService;
	this.userService = userService;
    }

    @Override
    protected Component initContent() {
	var role = user.getRole();
	return switch (role) {
	case ADMIN -> new AdminSettingsView(userService);
	default -> new UserSettingsView(user, driverService, customerService);
	};
    }

}
