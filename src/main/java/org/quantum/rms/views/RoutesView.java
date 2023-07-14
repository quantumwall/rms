package org.quantum.rms.views;

import java.util.Objects;

import org.quantum.rms.models.Route;
import org.quantum.rms.services.CustomerService;
import org.quantum.rms.services.DriverService;
import org.quantum.rms.services.RouteService;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;

@com.vaadin.flow.router.Route(value = "routes", layout = MainLayout.class)
public class RoutesView extends VerticalLayout {

    private static final long serialVersionUID = 1L;
    private final RouteService routeService;
    private final CustomerService customerService;
    private final DriverService driverService;
    private Grid<Route> grid = new Grid<>();
    private RouteForm form;
    private TextField searchField;

    public RoutesView(RouteService routeService, CustomerService customerService, DriverService driverService) {
	this.routeService = routeService;
	this.customerService = customerService;
	this.driverService = driverService;
	setSizeFull();
	configureGrid();
	configureForm();
	add(getToolbar(), getContent());
	updateList();
	closeEditor();
    }

    private HorizontalLayout getToolbar() {
	searchField = new TextField();
	searchField.addValueChangeListener(e -> updateList());
	searchField.setValueChangeMode(ValueChangeMode.LAZY);
	searchField.setPlaceholder("Поиск...");
	var addRouteButton = new Button("Добавить маршрут");
	addRouteButton.addClickListener(e -> addRoute());
	return new HorizontalLayout(searchField, addRouteButton);
    }

    private void addRoute() {
	grid.asSingleSelect().clear();
	editRoute(new Route());
    }

    private HorizontalLayout getContent() {
	var content = new HorizontalLayout(grid, form);
	content.setFlexGrow(2, grid);
	content.setFlexGrow(1, form);
	content.setSizeFull();
	return content;
    }

    private void configureGrid() {
	grid.setSizeFull();
	grid.addColumn(r -> r.getCargo().getName()).setHeader("Груз");
	grid.addColumn(Route::getDepartureCity).setHeader("Город загрузки");
	grid.addColumn(Route::getDestinationCity).setHeader("Город разгрузки");
	grid.addColumn(Route::getShipmentDate).setHeader("Дата погрузки");
	grid.addColumn(r -> r.getCustomer().getName()).setHeader("Заказчик").setSortable(true);
	grid.getColumns().forEach(c -> c.setAutoWidth(true));
	grid.asSingleSelect().addValueChangeListener(e -> editRoute(e.getValue()));
    }

    private Component configureForm() {
	form = new RouteForm(customerService.findAll(), driverService.findAll());
	form.setWidth("30em");
	form.addSaveListener(e -> saveRoute(e.getRoute()));
	form.addDeleteListener(e -> deleteRoute(e.getRoute()));
	form.addCloseListener(e -> closeEditor());
	return form;
    }

    private void saveRoute(Route route) {
	if (Objects.isNull(route)) {
	    closeEditor();
	} else {
	    routeService.save(route);
	    closeEditor();
	    updateList();
	}
    }

    private void editRoute(Route route) {
	if (Objects.isNull(route)) {
	    closeEditor();
	} else {
	    form.setRoute(route);
	    form.setVisible(true);
	}
    }

    private void deleteRoute(Route route) {
	if (Objects.nonNull(route)) {
	    routeService.delete(route.getId());
	    closeEditor();
	    updateList();
	}
    }

    private void updateList() {
	grid.setItems(routeService.findAll(searchField.getValue()));
    }

    private void closeEditor() {
	form.setRoute(null);
	form.setVisible(false);
    }

}
