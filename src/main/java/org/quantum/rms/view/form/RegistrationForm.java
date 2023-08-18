package org.quantum.rms.view.form;

import org.quantum.rms.model.User;
import org.quantum.rms.service.UserService;
import org.quantum.rms.validator.UserExistsValidator;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.shared.Registration;

public class RegistrationForm extends Composite<Component> {

    private static final long serialVersionUID = 1L;
    private TextField name = new TextField(getTranslation("form.registration.name"));
    private EmailField email = new EmailField(getTranslation("form.registration.email"));
    private PasswordField password = new PasswordField(getTranslation("form.registration.password"));
    private Button submitButton = new Button(getTranslation("form.registration.button.registration"));
    private Binder<User> binder = new BeanValidationBinder<>(User.class);

    public RegistrationForm(UserService userService) {
	binder.bindInstanceFields(this);
	binder.forField(email).asRequired(getTranslation("form.registration.error.required_email"))
		.withValidator(new EmailValidator(getTranslation("form.registration.error.invalid_email")))
		.withValidator(
			new UserExistsValidator(getTranslation("form.registration.error.duplicate_email"), userService))
		.bind(User::getEmail, User::setEmail);
	binder.setBean(new User());
	submitButton.addClickListener(e -> validateAndRegister());
	submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    }

    private void validateAndRegister() {
	if (binder.validate().isOk()) {
	    fireEvent(new RegistrationEvent(this, binder.getBean()));
	}
    }

    @Override
    protected Component initContent() {
	var label = new H2(getTranslation("view.registration.label"));
	var form = new FormLayout(label, name, email, password, submitButton);
	form.setWidth(300, Unit.PIXELS);
	form.setResponsiveSteps(new ResponsiveStep("0", 1));
	var layout = new VerticalLayout(form);
	layout.setAlignSelf(Alignment.CENTER, form);
	return layout;
    }

    public static class RegistrationFormEvent extends ComponentEvent<RegistrationForm> {

	private static final long serialVersionUID = 1L;
	private User user;

	public RegistrationFormEvent(RegistrationForm source, User user) {
	    super(source, false);
	    this.user = user;
	}

	public User getUser() {
	    return user;
	}
    }

    public static class RegistrationEvent extends RegistrationFormEvent {

	private static final long serialVersionUID = 1L;

	public RegistrationEvent(RegistrationForm source, User user) {
	    super(source, user);
	}
    }

    public Registration addRegistrationListener(ComponentEventListener<RegistrationEvent> listener) {
	return addListener(RegistrationEvent.class, listener);
    }

}
