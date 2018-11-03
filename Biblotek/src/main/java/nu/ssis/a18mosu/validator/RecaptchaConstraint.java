package nu.ssis.a18mosu.validator;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import org.springframework.beans.factory.annotation.Autowired;

import nu.ssis.a18mosu.clients.RecaptchaClient;

@Documented
@Retention(RUNTIME)
@Target(ElementType.PARAMETER)
@Constraint(validatedBy = RecaptchaConstraintValidator.class)
public @interface RecaptchaConstraint {

	public String message() default "Ogiltig recaptcha";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
	
}

class RecaptchaConstraintValidator implements ConstraintValidator<RecaptchaConstraint, String> {
	
	@Autowired
	private RecaptchaClient recaptchaClient;
	
	@Override
	public boolean isValid(String response, ConstraintValidatorContext context) {
		return recaptchaClient.verify(response);
	}
	
}