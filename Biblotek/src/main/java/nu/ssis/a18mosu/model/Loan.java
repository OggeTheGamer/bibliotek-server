package nu.ssis.a18mosu.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data @Entity
@Table(name="loans")
public class Loan {
	
	@Id
	private String id;
	@ManyToOne
	private Book book;
	@ManyToOne
	private LibraryUser loanTaker;
	private Date loanedDate;
	private Date returnedDate;
	
}
