package nu.ssis.a18mosu.datatransferobject;

import javax.validation.constraints.NotNull;

import lombok.Data;
import nu.ssis.a18mosu.model.Book.BookStatus;
import nu.ssis.a18mosu.validator.BookConstraint;

@Data
public class LoanBookDTO {
	
	@NotNull
	@BookConstraint(status = BookStatus.AVALIABLE)
	private Integer bookId;
	
}
