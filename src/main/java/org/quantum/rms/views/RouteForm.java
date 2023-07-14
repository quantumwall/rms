package org.quantum.rms.views;

import java.util.List;

import org.quantum.rms.models.Customer;
import org.quantum.rms.models.Driver;
import org.quantum.rms.models.Route;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
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
    private DatePicker shipmentDate = new DatePicker("Дата погрузки");
    private TextField departureCity = new TextField("Город погрузки");
    private TextField destinationCity = new TextField("Город разгрузки");
    private IntegerField billNumber = new IntegerField("Номер акта/счета");
    private BigDecimalField price = new BigDecimalField("Стоимость рейса");
    private TextField cargoName = new TextField("Груз");
    private NumberField cargoWeight = new NumberField("Вес");
    private Checkbox paid = new Checkbox("Оплачен");
    private ComboBox<Customer> customer = new ComboBox<>("Заказчик");
    private ComboBox<Driver> driver = new ComboBox<>("Водитель");
    private Binder<Route> binder = new BeanValidationBinder<>(Route.class);
    private Button saveButton = new Button("Сохранить");
    private Button deleteButton = new Button("Удалить");
    private Button closeButton = new Button("Отменить");

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

	closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
	closeButton.addClickListener(e -> fireEvent(new CloseEvent(this)));

	var buttons = new HorizontalLayout(saveButton, deleteButton, closeButton);
	return buttons;
    }

    private void validateAndSave() {
	if (binder.validate().isOk()) {
	    fireEvent(new SaveEvent(this, binder.getBean()));
	} else {
	    Notification.show("Допущены ошибки при заполнении полей формы");
	}
    }

    public void setRoute(Route route) {
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
