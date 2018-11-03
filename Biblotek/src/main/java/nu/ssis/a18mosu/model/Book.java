package nu.ssis.a18mosu.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter  @Entity
@Table(name="books")
public class Book {
	
	@Id
	private Integer id;
	@ManyToOne
	private GenericBook book;
	private Date registeredDate;
	@OneToMany(mappedBy="book")
	private List<Loan> loans;
	
	public enum BookStatus {
		LOANED, AVALIABLE, NOT_FOUND
	}
	
}
