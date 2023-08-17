package org.quantum.rms.view.form;

import org.quantum.rms.model.Customer;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.function.SerializableRunnable;

public class CustomerForm extends FormLayout {

    private static final long serialVersionUID = 1L;
    private TextField name = new TextField("Customer name");

    public CustomerForm(Customer customer, SerializableRunnable saveListener) {
	BeanValidationBinder<Customer> binder = new BeanValidationBinder<>(Customer.class);
	binder.forField(name).bind("name");
	binder.setBean(customer);
	add(name, new Button("Save", e -> {
	    if (binder.validate().isOk()) {
		saveListener.run();
	    }
	}));
    }

}
