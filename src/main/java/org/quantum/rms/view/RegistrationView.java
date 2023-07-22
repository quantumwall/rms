package org.quantum.rms.view;

import org.quantum.rms.model.User;
import org.quantum.rms.service.UserService;
import org.quantum.rms.util.Translator;
import org.quantum.rms.view.component.ChangeLocaleComponent;
import org.quantum.rms.view.form.RegistrationForm;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility;

@AnonymousAllowed
@Route("registration")
public class RegistrationView extends VerticalLayout implements HasDynamicTitle {

    private static final long serialVersionUID = 1L;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public RegistrationView(UserService userService, PasswordEncoder passwordEncoder, Translator translator) {
	this.userService = userService;
	this.passwordEncoder = passwordEncoder;

	var form = new RegistrationForm(userService);
	form.addRegistrationListener(e -> saveUser(e.getUser()));

	var label = new Div(new Text(getTranslation("view.registration.label")));
	label.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.FontWeight.BOLD);

	var localeSwitcher = new ChangeLocaleComponent(translator);

	add(label, form, localeSwitcher);
	setSizeFull();
	setAlignItems(Alignment.CENTER);
	setJustifyContentMode(JustifyContentMode.CENTER);
	setAlignSelf(Alignment.END, localeSwitcher);
	expand(label, form);
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
