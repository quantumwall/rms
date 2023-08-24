package org.quantum.rms.validator;

import java.util.List;
import java.util.Objects;

import org.quantum.rms.model.Route;
import org.quantum.rms.service.RouteService;

import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.validator.AbstractValidator;

public class BillNumberExistsValidator extends AbstractValidator<String> {

    private static final long serialVersionUID = 1L;
    private final RouteService routeService;
    private final String errorMessage;
    private String previousValue;

    public BillNumberExistsValidator(String errorMessage, RouteService routeService) {
	super(errorMessage);
	this.errorMessage = errorMessage;
	this.routeService = routeService;
    }

    public void setPreviousValue(String previousValue) {
	this.previousValue = Objects.nonNull(previousValue) ? previousValue : "";
    }

    @Override
    public ValidationResult apply(String value, ValueContext context) {
	if (value.equals(previousValue)) {
	    return ValidationResult.ok();
	}
	List<Route> routes = routeService.findAll(value);
	return routes.stream().filter(r -> r.getBillNumber().equals(value)).toList().size() > 0
		? ValidationResult.error(errorMessage)
		: ValidationResult.ok();
    }

}
