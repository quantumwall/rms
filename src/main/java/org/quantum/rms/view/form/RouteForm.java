package org.quantum.rms.view.form;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import org.quantum.rms.model.Customer;
import org.quantum.rms.model.Driver;
import org.quantum.rms.model.Route;
import org.quantum.rms.service.RouteService;
import org.quantum.rms.validator.BillNumberExistsValidator;

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
    private TextField billNumber = new TextField(getTranslation("form.route.bill_number"));
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
    private BillNumberExistsValidator billValidator;

    public RouteForm(List<Customer> customers, List<Driver> drivers, RouteService routeService) {
	billValidator = new BillNumberExistsValidator(getTranslation("form.route.error.bill_number_exists"),
		routeService);
	configureBinder(routeService);
	configureCustomer(customers);
	configureDriver(drivers);
	configureShipmentDate();
	add(shipmentDate, departureCity, destinationCity, billNumber, price, paid, cargoName, cargoWeight, customer,
		driver, createButtonLayout());
    }

    private void configureCustomer(List<Customer> customers) {
	customer.setItems(customers);
	customer.setItemLabelGenerator(Customer::getName);
    }

    private void configureDriver(List<Driver> drivers) {
	driver.setItems(drivers);
	driver.setItemLabelGenerator(Driver::getName);
    }

    private void configureBinder(RouteService routeService) {
	binder.bindInstanceFields(this);
	binder.forField(cargoName).bind("cargo.name");
	binder.forField(cargoWeight).bind("cargo.weight");
	binder.forField(billNumber).withValidator(billValidator).bind(Route::getBillNumber, Route::setBillNumber);
	binder.forField(customer).asRequired(getTranslation("forms.required_field")).bind(Route::getCustomer,
		Route::setCustomer);
	binder.forField(driver).asRequired(getTranslation("forms.required_field")).bind(Route::getDriver,
		Route::setDriver);
    }

    private void configureShipmentDate() {
	var i18n = new DatePicker.DatePickerI18n();
	i18n.setCancel(getTranslation("datepicker.button.cancel"));
	i18n.setToday(getTranslation("datepicker.button.today"));
	i18n.setMonthNames(
		List.of(getTranslation("datepicker.month.january"), getTranslation("datepicker.month.february"),
			getTranslation("datepicker.month.march"), getTranslation("datepicker.month.april"),
			getTranslation("datepicker.month.may"), getTranslation("datepicker.month.june"),
			getTranslation("datepicker.month.july"), getTranslation("datepicker.month.august"),
			getTranslation("datepicker.month.september"), getTranslation("datepicker.month.october"),
			getTranslation("datepicker.month.november"), getTranslation("datepicker.month.december")));
	i18n.setWeekdaysShort(List.of(getTranslation("datepicker.shortday.sun"),
		getTranslation("datepicker.shortday.mon"), getTranslation("datepicker.shortday.tue"),
		getTranslation("datepicker.shortday.wed"), getTranslation("datepicker.shortday.thu"),
		getTranslation("datepicker.shortday.fri"), getTranslation("datepicker.shortday.sat")));
	i18n.setWeekdays(List.of(getTranslation("datepicker.day.sun"), getTranslation("datepicker.day.mon"),
		getTranslation("datepicker.day.tue"), getTranslation("datepicker.day.wed"),
		getTranslation("datepicker.day.thu"), getTranslation("datepicker.day.fri"),
		getTranslation("datepicker.day.sat")));
	i18n.setFirstDayOfWeek(1);
	shipmentDate.setI18n(i18n);
	shipmentDate.setMax(LocalDate.now());
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
	if (Objects.nonNull(route)) {
	    billValidator.setPreviousValue(route.getBillNumber());
	    deleteButton.setEnabled(Objects.nonNull(route.getId()));
	}
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
