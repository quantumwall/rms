package org.quantum.rms.view;

import org.quantum.rms.model.User;
import org.quantum.rms.service.UserService;
import org.quantum.rms.view.form.RegistrationForm;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility;

@AnonymousAllowed
@Route("registration")
public class RegistrationView extends Composite<Component> implements HasDynamicTitle {

    private static final long serialVersionUID = 1L;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public RegistrationView(UserService userService, PasswordEncoder passwordEncoder) {
	this.userService = userService;
	this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected Component initContent() {
	return getForm();
    }

    private Component getForm() {
	var form = new RegistrationForm(userService);
	form.addRegistrationListener(e -> saveUser(e.getUser()));

	var label = new Div(new Text(getTranslation("view.registration.label")));
	label.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.FontWeight.BOLD);

	var content = new VerticalLayout();
	content.setSizeFull();
	content.setAlignItems(Alignment.CENTER);
	content.setJustifyContentMode(JustifyContentMode.CENTER);
	content.setAlignSelf(Alignment.CENTER, form);
	content.add(label, form);
	return content;
    }

    private void saveUser(User user) {
	user.setPassword(passwordEncoder.encode(user.getPassword()));
	userService.save(user);
	UI.getCurrent().navigate(RoutesView.class);
    }

    @Override
    public String getPageTitle() {
	return getTranslation("view.registration.page_title");
    }

}
