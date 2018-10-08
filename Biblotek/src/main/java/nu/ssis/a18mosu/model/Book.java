package nu.ssis.a18mosu.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data @Document(collection="books")
public class Book {
	
	@Id
	private String id;
	@DBRef
	private GenericBook book;
	private Date registeredDate;
	
	public enum BookStatus {
		LOANED, AVALIABLE, NOT_FOUND
	}
	
}
