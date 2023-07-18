package org.quantum.rms.view.component;

import java.util.Locale;

import org.quantum.rms.util.Translator;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.server.VaadinSession;

//@org.springframework.stereotype.Component
public class ChangeLocaleComponent extends Composite<Component> {

    private static final long serialVersionUID = 1L;
    private Translator translator;
    
    public ChangeLocaleComponent(Translator transaltor) {
	this.translator = transaltor;
    }

    @Override
    protected Component initContent() {
	var locale = new ComboBox<Locale>();
	locale.setItems(translator.getProvidedLocales());
	locale.setItemLabelGenerator(Locale::getCountry);
	locale.setWidth(100, Unit.PIXELS);
	locale.setValue(getLocale());
	locale.addValueChangeListener(e -> changeLocale(e.getValue()));
	var layout = new HorizontalLayout(locale);
	return layout;
    }
    
    private void changeLocale(Locale locale) {
	VaadinSession.getCurrent().setLocale(locale);
    }

    
}
