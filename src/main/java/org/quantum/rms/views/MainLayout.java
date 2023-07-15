package org.quantum.rms.views;

import org.quantum.rms.services.SecurityService;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.Tabs.Orientation;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class MainLayout extends AppLayout {

    private static final long serialVersionUID = 1L;
    private static final String APP_TITLE = "Система управления маршрутами";
    private final SecurityService securityService;

    public MainLayout(SecurityService securityService) {
	this.securityService = securityService;
	addToNavbar(getHeader());
	addToDrawer(getMenu());
    }

    private Component getMenu() {
	var tabs = new Tabs(new Tab(new RouterLink("Маршруты", RoutesView.class)),
		new Tab(new RouterLink("Настройки", SettingsView.class)));
	tabs.setOrientation(Orientation.VERTICAL);
	return tabs;
    }

    private Component getHeader() {
	var label = new H2(APP_TITLE);
	var logoutButton = new Button("Выйти", e -> securityService.logout());
	logoutButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
	var header = new HorizontalLayout(new DrawerToggle(), label, logoutButton);
	header.setWidthFull();
	header.addClassNames(LumoUtility.Padding.Vertical.NONE, LumoUtility.Padding.Horizontal.MEDIUM);
	header.expand(label);
	return header;
    }
}
