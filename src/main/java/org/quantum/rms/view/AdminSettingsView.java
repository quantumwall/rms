package org.quantum.rms.view;

import java.util.Objects;

import org.quantum.rms.model.User;
import org.quantum.rms.service.UserService;
import org.quantum.rms.view.form.UserForm;

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

    public AdminSettingsView(UserService userService) {
	this.userService = userService;
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
	var dialog = new Dialog();
	dialog.setModal(true);
	dialog.open();
	dialog.add(new UserForm(user, () -> {
	    dialog.close();
	    saveUser(user);
	    updateUsersList();
	}));
    }

    private void saveUser(User user) {
	// TODO: fix change password every user edition
	userService.save(user);
    }

}
