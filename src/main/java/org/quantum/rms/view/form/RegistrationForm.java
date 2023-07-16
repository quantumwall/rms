package org.quantum.rms.view.form;

import org.quantum.rms.model.User;
import org.quantum.rms.service.UserService;
import org.quantum.rms.validator.UserExistsValidator;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.shared.Registration;

public class RegistrationForm extends FormLayout {

    private static final long serialVersionUID = 1L;
    private final UserService userService;
    private TextField name = new TextField("Имя");
    private EmailField email = new EmailField("Электронная почта");
    private PasswordField password = new PasswordField("Пароль");
    private Button submitButton = new Button("Регистрация");
    private Binder<User> binder = new BeanValidationBinder<>(User.class);

    public RegistrationForm(UserService userService) {
	this.userService = userService;
	binder.bindInstanceFields(this);
	binder.forField(email).asRequired("Почта должна быть указана")
		.withValidator(new EmailValidator("Некорректный формат"))
		.withValidator(new UserExistsValidator("Пользователь с такой почтой уже существует", userService))
		.bind(User::getEmail, User::setEmail);
	binder.setBean(new User());
	submitButton.addClickListener(e -> validateAndRegister());
	setWidth(300, Unit.PIXELS);
	setResponsiveSteps(new ResponsiveStep("0", 1));
	add(name, email, password, submitButton);
    }

    private void validateAndRegister() {
	if (binder.validate().isOk()) {
	    fireEvent(new RegistrationEvent(this, binder.getBean()));
	} else {
	    Notification.show("Исправьте ошибки");
	}
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
