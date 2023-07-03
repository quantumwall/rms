package org.quantum.rms.views;

import org.quantum.rms.models.Route;
import org.quantum.rms.services.CustomerService;
import org.quantum.rms.services.DriverService;
import org.quantum.rms.services.RouteService;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

@com.vaadin.flow.router.Route("routes")
public class RoutesView extends VerticalLayout {
    
    private static final long serialVersionUID = 1L;
	private final RouteService routeService;
    private final DriverService driverService;
    private final CustomerService customerService;

    public RoutesView(RouteService routeService, DriverService driverService, CustomerService customerService) {
        this.routeService = routeService;
        this.driverService = driverService;
        this.customerService = customerService;
        add(createGrid());
    }

    private Grid<Route> createGrid() {
        var grid = new Grid<Route>(Route.class, false);
        grid.addColumn(r -> r.getCargo().getGoods()).setHeader("Груз");
        grid.addColumn(Route::getDepartureCity).setHeader("Город загрузки");
        grid.addColumn(Route::getDestinationCity).setHeader("Город разгрузки");
        grid.addColumn(Route::getShipmentDate).setHeader("Дата погрузки");
        grid.addColumn(r -> r.getCustomer().getName()).setHeader("Заказчик").setSortable(true);
        grid.setItems(routeService.findAll());
        return grid;
    }
}
