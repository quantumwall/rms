package org.quantum.rms.view.form;


import org.quantum.rms.model.Customer;
import org.quantum.rms.model.Driver;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.function.SerializableRunnable;

public class DriverForm extends Composite<Component> {

    private static final long serialVersionUID = 1L;
    private TextField name = new TextField("Customer name");
    private final BeanValidationBinder<Driver> binder = new BeanValidationBinder<>(Driver.class);
    private final SerializableRunnable saveListener;

    public DriverForm(Driver driver, SerializableRunnable saveListener) {
	this.saveListener = saveListener;
	binder.forField(name).bind("name");
	binder.setBean(driver);
    }

    @Override
    protected Component initContent() {
	return new VerticalLayout(name, new Button("Save", e -> {
	    if (binder.isValid()) {
		saveListener.run();
	    }
	}));
    }


}
