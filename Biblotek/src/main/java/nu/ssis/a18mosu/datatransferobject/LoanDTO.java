package nu.ssis.a18mosu.datatransferobject;

import lombok.Data;
import nu.ssis.a18mosu.model.Book.BookStatus;
import nu.ssis.a18mosu.validator.BookConstraint;
import nu.ssis.a18mosu.validator.RFIDConstraint;

@Data
public class LoanDTO {
	
	@BookConstraint(status=BookStatus.AVALIABLE)
	private String bookId;
	@RFIDConstraint
	private String rfid;
}
