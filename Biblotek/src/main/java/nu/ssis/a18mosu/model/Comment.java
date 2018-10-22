package nu.ssis.a18mosu.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;

import lombok.Data;

@Data
public class Comment {
	
	@DBRef
	private LibraryUser author;
	private Date createdAt;
	private String content;
	private String id;
	private List<Comment> replies;

}
