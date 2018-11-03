package nu.ssis.a18mosu.datatransferobject;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class CommentDTO {
	
	@NotEmpty(message="Kommentaren får inte vara tom")
	private String content;

}
