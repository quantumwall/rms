package org.quantum.rms.view;

import org.quantum.rms.model.User;
import org.quantum.rms.service.UserService;
import org.quantum.rms.util.Translator;
import org.quantum.rms.view.component.ChangeLocaleComponent;
import org.quantum.rms.view.form.RegistrationForm;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@AnonymousAllowed
@Route("registration")
public class RegistrationView extends VerticalLayout implements HasDynamicTitle {

    private static final long serialVersionUID = 1L;
    private final UserService userService;

    public RegistrationView(UserService userService, Translator translator) {
	this.userService = userService;

	var form = new RegistrationForm(userService);
	form.addRegistrationListener(e -> saveUser(e.getUser()));

	var localeSwitcher = new ChangeLocaleComponent(translator);

	add(form, localeSwitcher);
	setSizeFull();
	setAlignSelf(Alignment.END, form);
	setAlignSelf(Alignment.END, localeSwitcher);
	expand(form);
    }

    private void saveUser(User user) {
	userService.save(user);
	UI.getCurrent().navigate(RoutesView.class);
    }

    @Override
    public String getPageTitle() {
	return getTranslation("view.registration.page_title");
    }

}
