package nu.ssis.a18mosu.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter  @Entity
@Table(name="generic_books")
public class GenericBook {

	@Id
	private String isbn;
	private String title;
	private String authors;
	@Column(length=2083)
	private String image;
	@Column(columnDefinition="TEXT")
	private String description;
	private String language;
	private Date registeredDate;
	@OneToMany(mappedBy="book")
	private List<Comment> comments;
	@OneToMany(mappedBy="book")
	private List<Book> books;

}
