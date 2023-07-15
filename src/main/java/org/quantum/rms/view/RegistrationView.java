package org.quantum.rms.view;

import org.quantum.rms.model.User;
import org.quantum.rms.service.UserService;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("registration")
@AnonymousAllowed
public class RegistrationView extends Composite<Component> {

    private static final long serialVersionUID = 1L;
    private final UserService userService;
    private TextField name = new TextField("Имя");
    private EmailField email = new EmailField("Электронная почта");
    private PasswordField password = new PasswordField("Пароль");
    private PasswordField confirm = new PasswordField("Повторите пароль");
    private Binder<User> binder = new BeanValidationBinder<>(User.class);

    
    public RegistrationView(UserService userService) {
	this.userService = userService;
    }

    @Override
    protected Component initContent() {
	binder.bindInstanceFields(this);
	return getForm();
    }
    
    private Component getForm() {
	var registerButton = new Button("Регистрация", e -> validateAndRegister());
	registerButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
	var form = new FormLayout(name, email, password, confirm);
	form.setResponsiveSteps(new ResponsiveStep("0", 1));
	var label = new Div(new Text("Регистрация"));
	var content = new VerticalLayout(label, form, registerButton);
	content.setWidth(300, Unit.PIXELS);
	content.setAlignSelf(Alignment.CENTER, registerButton);
	content.setAlignSelf(Alignment.CENTER, label);
	content.setAlignItems(Alignment.CENTER);
	content.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
	var wrapper = new HorizontalLayout(content);
	wrapper.setSizeFull();
	wrapper.setAlignItems(Alignment.CENTER);
	wrapper.setJustifyContentMode(JustifyContentMode.CENTER);
	return wrapper;
    }

    private void validateAndRegister() {

    }

    private boolean isUserExists(String email) {
	return userService.findByEmail(email).isPresent();
    }

}
