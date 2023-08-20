package org.quantum.rms.view;

import java.util.Objects;

import org.quantum.rms.model.User;
import org.quantum.rms.service.UserService;
import org.quantum.rms.view.form.UserForm;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class AdminSettingsView extends Composite<Component> {

    private static final long serialVersionUID = 1L;
    private final UserService userService;
    private final ComboBox<User> users = new ComboBox<>("Users");
    private final PasswordEncoder passwordEncoder;

    public AdminSettingsView(UserService userService, PasswordEncoder passwordEncoder) {
	this.userService = userService;
	this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected Component initContent() {
	return new VerticalLayout(getUsersSection());
    }

    private Component getUsersSection() {
	updateUsersList();
	users.setItemLabelGenerator(User::getName);
	var addButton = new Button(VaadinIcon.PLUS.create(), e -> showUserForm(new User()));
	var editButton = new Button(VaadinIcon.PENCIL.create(), e -> showUserForm(users.getValue()));
	editButton.setEnabled(Objects.nonNull(users.getValue()));
	users.addValueChangeListener(e -> editButton.setEnabled(Objects.nonNull(e.getValue())));
	var layout = new HorizontalLayout(users, addButton, editButton);
	layout.setAlignItems(Alignment.BASELINE);
	return layout;
    }

    private void updateUsersList() {
	users.setItems(userService.findAll());
    }

    private void showUserForm(User user) {
	var oldPassword = user.getPassword();
	var dialog = new Dialog();
	dialog.setModal(true);
	dialog.open();
	dialog.add(new UserForm(user, () -> {
	    dialog.close();
	    if (passwordChanged(user, oldPassword)) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
	    }
	    saveUser(user);
	    updateUsersList();
	}));
    }

    private void saveUser(User user) {
	userService.save(user);
    }

    private boolean passwordChanged(User user, String oldPassword) {
	return !user.getPassword().equals(oldPassword);
    }

}
