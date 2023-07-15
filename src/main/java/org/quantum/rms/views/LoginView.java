package org.quantum.rms.views;

import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("login")
@AnonymousAllowed
public class LoginView extends VerticalLayout {

    private static final long serialVersionUID = 1L;
    private LoginForm form = new LoginForm();

    public LoginView() {
	setSizeFull();
	setJustifyContentMode(JustifyContentMode.CENTER);
	setAlignItems(Alignment.CENTER);
	form.setAction("login");
	add(form);
    }

}
