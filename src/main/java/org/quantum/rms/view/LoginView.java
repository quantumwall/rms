package org.quantum.rms.view;

import org.quantum.rms.util.Translator;
import org.quantum.rms.view.component.ChangeLocaleComponent;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("login")
@AnonymousAllowed
public class LoginView extends VerticalLayout implements BeforeEnterObserver, HasDynamicTitle {

    private static final long serialVersionUID = 1L;
    private LoginForm form = new LoginForm();

    public LoginView(Translator translator) {
	setSizeFull();
	setJustifyContentMode(JustifyContentMode.CENTER);
	setAlignItems(Alignment.CENTER);
	configureForm();
	var localeSwitcher = new ChangeLocaleComponent(translator);
	add(form, localeSwitcher);
	setAlignSelf(Alignment.END, localeSwitcher);
	expand(form);
    }

    private void configureForm() {
	form.setAction("login");
	form.setI18n(getI18n());
	form.addForgotPasswordListener(e -> UI.getCurrent().navigate(RegistrationView.class));
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

    @Override
    public String getPageTitle() {
	return getTranslation("view.login.page_title");
    }

}
