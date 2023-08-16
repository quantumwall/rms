package org.quantum.rms.view;

import java.util.Objects;

import org.quantum.rms.model.Customer;
import org.quantum.rms.model.Driver;
import org.quantum.rms.model.User;
import org.quantum.rms.service.CustomerService;
import org.quantum.rms.service.DriverService;
import org.quantum.rms.service.UserService;
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

public class UserSettingsView extends Composite<Component> {

    private static final long serialVersionUID = 1L;
    private final User user;
    private final DriverService driverService;
    private final CustomerService customerService;
    private final UserService userService;
    private ComboBox<Driver> drivers = new ComboBox<>();
    private ComboBox<Customer> customers = new ComboBox<>();

    public UserSettingsView(User user, DriverService driverService, CustomerService customerService,
	    UserService userService) {
	this.user = user;
	this.driverService = driverService;
	this.customerService = customerService;
	this.userService = userService;
    }

    @Override
    protected Component initContent() {
	
	return new HorizontalLayout(getDriversSection());
    }

    private Component getDriversSection() {
	var driversSection = new VerticalLayout();
	drivers.setItems(user.getDrivers());
	drivers.setItemLabelGenerator(Driver::getName);
	var addButton = new Button(new Icon(VaadinIcon.PLUS));
	addButton.addClickListener(e -> showDriverForm(new Driver()));
	var removeButton = new Button(new Icon(VaadinIcon.CLOSE_SMALL));
	drivers.addValueChangeListener(e -> removeButton.setEnabled(Objects.nonNull(e.getValue())));
	removeButton.setEnabled(Objects.nonNull(drivers.getValue()));
//	removeButton.addClickListener(e -> deleteDriver(drivers.getValue()));
	driversSection.add(drivers, new HorizontalLayout(addButton, removeButton));
	return driversSection;
    }
    
//    private void deleteDriver(Driver driver) {
//	userService.findById(user.getId()).ifPresent(u -> {
//	    u.deleteDriver(driver);
//	    user.deleteDriver(driver);
//	    userService.save(u);
//	});
////	driverService.delete(driver);
//	updateDriversList();
//    }

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
	userService.findById(user.getId()).ifPresent(u -> {
	    u.addDriver(driver);
	    user.addDriver(driver);
	    userService.save(u);
	});
    }
    
    private void updateDriversList() {
	drivers.setItems(user.getDrivers());
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
	userService.findById(user.getId()).ifPresent(u -> {
	    u.addCustomer(customer);
	    user.addCustomer(customer);
	    userService.save(u);
	});
    }

    

    private void updateCustomersList() {
	customers.setItems(user.getCustomers());
    }

    private Component getCustomersSection(User user) {
	var customersSection = new VerticalLayout();
	var customers = new ComboBox<Customer>("Customers");
	customers.setItems(user.getCustomers());
	customers.setItemLabelGenerator(Customer::getName);
	customersSection.add(customers);
	return customersSection;
    }
}
