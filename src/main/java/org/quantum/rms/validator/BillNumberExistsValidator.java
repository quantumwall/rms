package org.quantum.rms.validator;

import java.util.List;

import org.quantum.rms.model.Route;
import org.quantum.rms.service.RouteService;

import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.validator.AbstractValidator;

public class BillNumberExistsValidator extends AbstractValidator<String> {

    private static final long serialVersionUID = 1L;
    private final RouteService routeService;
    private final String errorMessage;
    private String oldValue;

    public BillNumberExistsValidator(String errorMessage, RouteService routeService, String oldValue) {
	super(errorMessage);
	this.errorMessage = errorMessage;
	this.routeService = routeService;
	this.oldValue = oldValue;
    }

    @Override
    public ValidationResult apply(String value, ValueContext context) {
	if (oldValue.equals(value)) {
	    return ValidationResult.ok();
	}
	List<Route> routes = routeService.findAll(value);
	return routes.stream().filter(r -> r.getBillNumber().equals(value)).toList().size() > 0
		? ValidationResult.error(errorMessage)
		: ValidationResult.ok();
    }

}
