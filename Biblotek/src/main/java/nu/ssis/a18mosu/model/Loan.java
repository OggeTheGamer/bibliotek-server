package nu.ssis.a18mosu.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data @Document(collection="loans")
public class Loan {
	
	@Id
	private String id;
	@DBRef
	private Book book;
	@DBRef
	private Student loanTaker;
	private Date loanedDate;
	private Date returnedDate;
	private Boolean returned;
	
}
