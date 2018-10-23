package nu.ssis.a18mosu.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data @Document(collection="comments")
public class Comment {
	
	@Id
	private String id;
	@DBRef
	private LibraryUser author;
	@DBRef
	private List<Comment> replies;
	private Date createdAt;
	private String content;
	
}
