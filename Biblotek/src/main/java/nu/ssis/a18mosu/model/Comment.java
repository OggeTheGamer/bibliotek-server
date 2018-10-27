package nu.ssis.a18mosu.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data @Entity
@Table(name="comments")
public class Comment {
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@ManyToOne
	private LibraryUser author;
	private Date createdAt;
	private String content;
	@ManyToOne
	private GenericBook book;
	
	
}
