package org.quantum.rms.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.Tabs.Orientation;
import com.vaadin.flow.router.RouterLink;

public class MainLayout extends AppLayout {

    private static final long serialVersionUID = 1L;
    private static final String APP_TITLE = "Система управления маршрутами";

    public MainLayout() {
	var label = new H2(APP_TITLE);
	addToNavbar(new DrawerToggle(), label);
	addToDrawer(getMenu());
    }

    private Component getMenu() {
	var tabs = new Tabs(new Tab(new RouterLink("Маршруты", RoutesView.class)),
		new Tab(new RouterLink("Настройки", SettingsView.class)));
	tabs.setOrientation(Orientation.VERTICAL);
	return tabs;
    }
}
