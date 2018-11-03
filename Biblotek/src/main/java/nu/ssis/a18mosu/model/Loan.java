package nu.ssis.a18mosu.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data @Entity
@Table(name="loans")
public class Loan {
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@ManyToOne
	private Book book;
	@ManyToOne(cascade=CascadeType.ALL)
	private LibraryUser loanTaker;
	private Date loanedDate;
	private Date returnedDate;
	
}
