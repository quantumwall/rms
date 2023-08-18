package org.quantum.rms.view;

import org.quantum.rms.service.UserService;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;

public class AdminSettingsView extends Composite<Component> {

    private static final long serialVersionUID = 1L;
    private final UserService userService;

    public AdminSettingsView(UserService userService) {
	this.userService = userService;
    }

    @Override
    protected Component initContent() {
	return new Div(new Text("admin settings"));
    }

}
