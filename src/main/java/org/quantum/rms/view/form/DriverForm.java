package org.quantum.rms.view.form;

import org.quantum.rms.model.Driver;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.function.SerializableRunnable;

public class DriverForm extends FormLayout {

    private static final long serialVersionUID = 1L;
    private TextField name = new TextField(getTranslation("form.driver.field.name"));

    public DriverForm(Driver driver, SerializableRunnable saveListener) {
	BeanValidationBinder<Driver> binder = new BeanValidationBinder<>(Driver.class);
	binder.bindInstanceFields(this);
	binder.setBean(driver);
	add(name, new Button(getTranslation("form.driver.button.save"), e -> {
	    if (binder.validate().isOk()) {
		saveListener.run();
	    }
	}));
    }

}
