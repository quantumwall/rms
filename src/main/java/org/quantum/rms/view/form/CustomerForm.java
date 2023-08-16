package org.quantum.rms.view.form;

import org.quantum.rms.model.Customer;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.function.SerializableRunnable;

public class CustomerForm extends Composite<Component> {

    private static final long serialVersionUID = 1L;
    private TextField name = new TextField("Customer name");
    private final BeanValidationBinder<Customer> binder = new BeanValidationBinder<>(Customer.class);
    private final SerializableRunnable saveListener;

    public CustomerForm(Customer customer, SerializableRunnable saveListener) {
	this.saveListener = saveListener;
	binder.forField(name).bind("name");
	binder.setBean(customer);
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
