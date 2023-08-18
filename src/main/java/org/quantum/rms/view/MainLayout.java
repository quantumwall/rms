package org.quantum.rms.view;

import org.quantum.rms.service.SecurityService;
import org.quantum.rms.util.Translator;
import org.quantum.rms.view.component.ChangeLocaleComponent;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.Tabs.Orientation;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class MainLayout extends AppLayout {

    private static final long serialVersionUID = 1L;
    private final SecurityService securityService;
    private final Translator translator;

    public MainLayout(SecurityService securityService, Translator translator) {
	this.securityService = securityService;
	this.translator = translator;
	addToNavbar(getHeader());
	addToDrawer(getMenu());
    }

    private Component getMenu() {
	var tabs = new Tabs(getMenuItems());
	tabs.setOrientation(Orientation.VERTICAL);
	return tabs;
    }

    private Tab[] getMenuItems() {
	return new Tab[] { new Tab(VaadinIcon.TRUCK.create(), new RouterLink(getTranslation("layout.main.link.routes"), RoutesView.class)),
		new Tab(VaadinIcon.COG.create(), new RouterLink(getTranslation("layout.main.link.settings"), SettingsView.class)) };
    }

    private Component getHeader() {
	var label = new H3(getTranslation("layout.main.label"));
	var logoutButton = new Button(getTranslation("layout.main.button.logout"), e -> securityService.logout());
	logoutButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
	var localeSwitcher = new ChangeLocaleComponent(translator);
	var header = new HorizontalLayout(new DrawerToggle(), label, localeSwitcher, logoutButton);
	header.setWidthFull();
	header.addClassNames(LumoUtility.Padding.Horizontal.MEDIUM);
	header.expand(label);
	header.setAlignItems(Alignment.CENTER);
	return header;
    }
}
