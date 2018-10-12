package nu.ssis.a18mosu.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data @Document(collection="generic_books")
public class GenericBook {

	@Id
	private String isbn;
	private String title;
	private String authors;
	private String image;
	private String description;
	private String language;
	private Date registeredDate;

}
