package nu.ssis.a18mosu.datatransferobject;


import javax.validation.constraints.Min;

import lombok.Data;
import nu.ssis.a18mosu.model.Book.BookStatus;
import nu.ssis.a18mosu.validator.BookConstraint;

@Data
public class BookRegisterDTO {
	
	private String isbn;
	@Min(value=0, message="Bok-id får inte vara negativt")
	@BookConstraint(status=BookStatus.NOT_FOUND, message="Detta bok-id finns redan i databasen.")
	private Integer bookId;

}
