package nu.ssis.a18mosu.datatransferobject;

import lombok.Data;
import nu.ssis.a18mosu.model.Book.BookStatus;
import nu.ssis.a18mosu.validator.BookConstraint;

@Data
public class BookRegisterDTO {
	
	private String isbn;
	@BookConstraint(status=BookStatus.NOT_FOUND)
	private Integer bookIdd;

}
