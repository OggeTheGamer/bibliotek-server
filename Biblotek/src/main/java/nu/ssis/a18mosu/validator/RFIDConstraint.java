package nu.ssis.a18mosu.validator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import org.springframework.beans.factory.annotation.Autowired;

import nu.ssis.a18mosu.service.StudentService;

@Documented
@Retention(RUNTIME)
@Target(FIELD)
@Constraint(validatedBy = RFIDConstraintValidator.class)
public @interface RFIDConstraint {

	public String message() default "Not a valid RFID";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {}; 
	
}

class RFIDConstraintValidator implements ConstraintValidator<RFIDConstraint, String> {

	@Autowired StudentService StudentService;
	
	@Override
	public boolean isValid(String rfid, ConstraintValidatorContext context) {
		return StudentService.exists(rfid);
	}
	
}
