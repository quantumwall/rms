package org.quantum.rms.validator;

import org.quantum.rms.service.UserService;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.validator.AbstractValidator;

public class UserExistsValidator extends AbstractValidator<String> {

    private static final long serialVersionUID = 1L;
    private final UserService userService;
    private final String errorMessage;

    public UserExistsValidator(String errorMessage, UserService userService) {
	super(errorMessage);
	this.userService = userService;
	this.errorMessage = errorMessage;
    }

    @Override
    public ValidationResult apply(String value, ValueContext context) {
	return userService.findByEmail(value).map(u -> ValidationResult.error(errorMessage))
		.orElse(ValidationResult.ok());
    }

}
