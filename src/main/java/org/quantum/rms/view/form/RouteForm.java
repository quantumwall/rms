package org.quantum.rms.view.form;

import java.util.List;
import java.util.Objects;
import org.quantum.rms.model.Customer;
import org.quantum.rms.model.Driver;
import org.quantum.rms.model.Route;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;

public class RouteForm extends FormLayout {

    private static final long serialVersionUID = 1L;
    private DatePicker shipmentDate = new DatePicker(getTranslation("form.route.shipment_date"));
    private TextField departureCity = new TextField(getTranslation("form.route.departure_city"));
    private TextField destinationCity = new TextField(getTranslation("form.route.destination_city"));
    private IntegerField billNumber = new IntegerField(getTranslation("form.route.bill_number"));
    private BigDecimalField price = new BigDecimalField(getTranslation("form.route.price"));
    private TextField cargoName = new TextField(getTranslation("form.route.cargo"));
    private NumberField cargoWeight = new NumberField(getTranslation("form.route.weight"));
    private Checkbox paid = new Checkbox(getTranslation("form.route.paid"));
    private ComboBox<Customer> customer = new ComboBox<>(getTranslation("form.route.customer"));
    private ComboBox<Driver> driver = new ComboBox<>(getTranslation("form.route.driver"));
    private Binder<Route> binder = new BeanValidationBinder<>(Route.class);
    private Button saveButton = new Button(getTranslation("form.route.button.save"));
    private Button deleteButton = new Button(getTranslation("form.route.button.delete"));
    private Button cancelButton = new Button(getTranslation("form.route.button.cancel"));

    public RouteForm(List<Customer> customers, List<Driver> drivers) {
	binder.bindInstanceFields(this);
	customer.setItems(customers);
	customer.setItemLabelGenerator(Customer::getName);
	driver.setItems(drivers);
	driver.setItemLabelGenerator(Driver::getName);
	binder.forField(cargoName).bind("cargo.name");
	binder.forField(cargoWeight).bind("cargo.weight");
	add(shipmentDate, departureCity, destinationCity, billNumber, price, paid, cargoName, cargoWeight, customer,
		driver, createButtonLayout());
    }

    private HorizontalLayout createButtonLayout() {
	saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
	saveButton.addClickListener(e -> validateAndSave());

	deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
	deleteButton.addClickListener(e -> fireEvent(new DeleteEvent(this, binder.getBean())));
	deleteButton.setEnabled(false);

	cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
	cancelButton.addClickListener(e -> fireEvent(new CloseEvent(this)));

	var buttons = new HorizontalLayout(saveButton, deleteButton, cancelButton);
	return buttons;
    }

    private void validateAndSave() {
	if (binder.validate().isOk()) {
	    fireEvent(new SaveEvent(this, binder.getBean()));
	}
    }

    public void setRoute(Route route) {
	deleteButton.setEnabled(Objects.nonNull(route) && Objects.nonNull(route.getId()));
	binder.setBean(route);
    }

    public static class RouteFormEvent extends ComponentEvent<RouteForm> {

	private static final long serialVersionUID = 1L;
	private Route route;

	public RouteFormEvent(RouteForm source, Route route) {
	    super(source, false);
	    this.route = route;
	}

	public Route getRoute() {
	    return route;
	}
    }

    public static class SaveEvent extends RouteFormEvent {

	private static final long serialVersionUID = 1L;

	public SaveEvent(RouteForm source, Route route) {
	    super(source, route);
	}
    }

    public static class DeleteEvent extends RouteFormEvent {

	private static final long serialVersionUID = 1L;

	public DeleteEvent(RouteForm source, Route route) {
	    super(source, route);
	}
    }

    public static class CloseEvent extends RouteFormEvent {

	private static final long serialVersionUID = 1L;

	public CloseEvent(RouteForm source) {
	    super(source, null);
	}
    }

    public Registration addSaveListener(ComponentEventListener<SaveEvent> listener) {
	return addListener(SaveEvent.class, listener);
    }

    public Registration addDeleteListener(ComponentEventListener<DeleteEvent> listener) {
	return addListener(DeleteEvent.class, listener);
    }

    public Registration addCloseListener(ComponentEventListener<CloseEvent> listener) {
	return addListener(CloseEvent.class, listener);
    }
}
