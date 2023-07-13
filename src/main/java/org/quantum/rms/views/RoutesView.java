package org.quantum.rms.views;

import org.quantum.rms.models.Route;
import org.quantum.rms.services.RouteService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

@com.vaadin.flow.router.Route("routes")
public class RoutesView extends VerticalLayout {

    private static final long serialVersionUID = 1L;
    private final RouteService routeService;

    public RoutesView(RouteService routeService) {
	this.routeService = routeService;
	add(createGrid());
    }

    private Grid<Route> createGrid() {
	var grid = new Grid<Route>(Route.class, false);
	grid.addColumn(r -> r.getCargo().getName()).setHeader("Груз");
	grid.addColumn(Route::getDepartureCity).setHeader("Город загрузки");
	grid.addColumn(Route::getDestinationCity).setHeader("Город разгрузки");
	grid.addColumn(Route::getShipmentDate).setHeader("Дата погрузки");
	grid.addColumn(r -> r.getCustomer().getName()).setHeader("Заказчик").setSortable(true);
	grid.setItems(routeService.findAll());
	return grid;
    }
}
