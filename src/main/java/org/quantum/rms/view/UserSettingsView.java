package org.quantum.rms.view;

import java.util.Objects;

import org.quantum.rms.model.Customer;
import org.quantum.rms.model.Driver;
import org.quantum.rms.model.User;
import org.quantum.rms.service.CustomerService;
import org.quantum.rms.service.DriverService;
import org.quantum.rms.view.form.CustomerForm;
import org.quantum.rms.view.form.DriverForm;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;

public class UserSettingsView extends Composite<Component> {

    private static final long serialVersionUID = 1L;
    private final User user;
    private final DriverService driverService;
    private final CustomerService customerService;
    private ComboBox<Driver> drivers = new ComboBox<>(getTranslation("view.settings.user.drivers"));
    private ComboBox<Customer> customers = new ComboBox<>(getTranslation("view.settings.user.customers"));

    public UserSettingsView(User user, DriverService driverService, CustomerService customerService) {
	this.user = user;
	this.driverService = driverService;
	this.customerService = customerService;
    }

    @Override
    protected Component initContent() {

	return new VerticalLayout(getDriversSection(), getCustomersSection());
    }

    private Component getDriversSection() {
	var driversSection = new HorizontalLayout();
	updateDriversList();
	drivers.setItemLabelGenerator(Driver::getName);
	var addButton = new Button(new Icon(VaadinIcon.PLUS), e -> showDriverForm(new Driver()));
	addButton.setTooltipText(getTranslation("view.settings.user.button.add.driver"));
	var editButton = new Button(VaadinIcon.PENCIL.create(), e -> showDriverForm(drivers.getValue()));
	editButton.setEnabled(Objects.nonNull(drivers.getValue()));
	editButton.setTooltipText(getTranslation("view.settings.user.button.edit.driver"));
	drivers.addValueChangeListener(e -> editButton.setEnabled(Objects.nonNull(e.getValue())));
	driversSection.add(drivers, addButton, editButton);
	driversSection.setAlignItems(Alignment.BASELINE);
	return driversSection;
    }

    private void showDriverForm(Driver driver) {
	var dialog = new Dialog();
	dialog.open();
	dialog.setModal(true);
	dialog.add(new DriverForm(driver, () -> {
	    saveDriver(driver);
	    dialog.close();
	    updateDriversList();
	}));
    }

    private void saveDriver(Driver driver) {
	driver.setUser(user);
	user.addDriver(driver);
	driverService.save(driver);
    }

    private void updateDriversList() {
	drivers.setItems(user.getDrivers());
    }

    private Component getCustomersSection() {
	var customersSection = new HorizontalLayout();
	updateCustomersList();
	customers.setItemLabelGenerator(Customer::getName);
	var addButton = new Button(VaadinIcon.PLUS.create(), e -> showCustomerForm(new Customer()));
	addButton.setTooltipText(getTranslation("view.settings.user.button.add.customer"));
	var editButton = new Button(VaadinIcon.PENCIL.create(), e -> showCustomerForm(customers.getValue()));
	editButton.setEnabled(Objects.nonNull(customers.getValue()));
	editButton.setTooltipText(getTranslation("view.settings.user.button.edit.customer"));
	customers.addValueChangeListener(e -> editButton.setEnabled(Objects.nonNull(e.getValue())));
	customersSection.add(customers, addButton, editButton);
	customersSection.setAlignItems(Alignment.BASELINE);
	return customersSection;
    }

    private void showCustomerForm(Customer customer) {
	var dialog = new Dialog();
	dialog.open();
	dialog.setModal(true);
	dialog.add(new CustomerForm(customer, () -> {
	    saveCustomer(customer);
	    dialog.close();
	    updateCustomersList();
	}));
    }

    private void saveCustomer(Customer customer) {
	customer.setUser(user);
	user.addCustomer(customer);
	customerService.save(customer);
    }

    private void updateCustomersList() {
	customers.setItems(user.getCustomers());
    }

}
