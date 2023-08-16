package org.quantum.rms.view;

import org.quantum.rms.model.Role;
import org.quantum.rms.model.SecurityUser;
import org.quantum.rms.service.SecurityService;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.PermitAll;

@Route(value = "settings", layout = MainLayout.class)
@PermitAll
public class SettingsView extends Composite<Component> {

    private static final long serialVersionUID = 1L;
    private final SecurityService securityService;

    public SettingsView(SecurityService securityService) {
	this.securityService = securityService;
    }

    @Override
    protected Component initContent() {
	var role = securityService.getAuthenticatedUser().map(SecurityUser::getRole).orElse(Role.USER);
	return switch (role) {
	case ADMIN -> new AdminSettingsView();
	default -> new UserSettingsView();
	};
    }

}
