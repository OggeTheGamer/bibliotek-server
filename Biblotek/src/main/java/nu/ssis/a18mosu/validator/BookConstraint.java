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

import nu.ssis.a18mosu.model.Book.BookStatus;
import nu.ssis.a18mosu.service.LoanService;

@Documented
@Retention(RUNTIME)
@Target(FIELD)
@Constraint(validatedBy = LoanConstraintValidator.class)
public @interface BookConstraint {

	public String message() default "The id you provided was not... TODO errormessages"; //TODO
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
	public BookStatus status(); 
	
}

class LoanConstraintValidator implements ConstraintValidator<BookConstraint, String> {

	@Autowired 
	private LoanService loanService;
	private BookConstraint bc;
	
	@Override
	public void initialize(BookConstraint constraint) {
		bc = constraint;
	}
	
	@Override
	public boolean isValid(String bookId, ConstraintValidatorContext context) {
		return loanService.bookStatus(bookId) == bc.status();
	}
	
}