package org.quantum.rms.view;

import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("login")
@AnonymousAllowed
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    private static final long serialVersionUID = 1L;
    private LoginForm form = new LoginForm();

    public LoginView() {
	setSizeFull();
	setJustifyContentMode(JustifyContentMode.CENTER);
	setAlignItems(Alignment.CENTER);
	form.setAction("login");
	form.setI18n(getI18n());
	add(form);
    }

    private LoginI18n getI18n() {
	var i18n = LoginI18n.createDefault();
	var i18nForm = i18n.getForm();
	i18nForm.setTitle(getTranslation("view.login.title"));
	i18nForm.setUsername(getTranslation("view.login.username"));
	i18nForm.setPassword(getTranslation("view.login.password"));
	i18nForm.setSubmit(getTranslation("view.login.button.submit"));
	i18nForm.setForgotPassword(getTranslation("view.login.button.forgot"));
	i18n.setForm(i18nForm);

	var i18nError = i18n.getErrorMessage();
	i18nError.setTitle(getTranslation("view.login.error.credentials.title"));
	i18nError.setMessage(getTranslation("view.login.error.credentials.message"));
	i18n.setErrorMessage(i18nError);

	return i18n;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
	form.setError(event.getLocation().getQueryParameters().getParameters().containsKey("error"));
    }

}
