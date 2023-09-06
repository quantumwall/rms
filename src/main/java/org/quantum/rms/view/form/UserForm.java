package org.quantum.rms.view.form;

import org.quantum.rms.model.Role;
import org.quantum.rms.model.User;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.function.SerializableRunnable;

public class UserForm extends FormLayout {

    private static final long serialVersionUID = 1L;
    private TextField name = new TextField(getTranslation("form.user.field.name"));
    private EmailField email = new EmailField(getTranslation("form.user.field.email"));
    private PasswordField password = new PasswordField(getTranslation("form.user.field.password"));
    private Checkbox active = new Checkbox(getTranslation("form.user.field.active"));
    private ComboBox<Role> role = new ComboBox<>(getTranslation("form.user.field.role"));

    public UserForm(User user, SerializableRunnable saveListener) {
	configureRoles();
	var binder = new BeanValidationBinder<User>(User.class);
	binder.bindInstanceFields(this);
	binder.setBean(user);
	var addButton = new Button(getTranslation("form.user.button.save"), e -> {
	    if (binder.validate().isOk()) {
		saveListener.run();
	    }
	});
	setResponsiveSteps(new ResponsiveStep("0", 1));
	setWidth(300, Unit.PIXELS);
	add(name, email, password, active, role, addButton);
    }
    
    private void configureRoles() {
	role.setItems(Role.values());
	role.setItemLabelGenerator(Role::name);
    }
  
}
