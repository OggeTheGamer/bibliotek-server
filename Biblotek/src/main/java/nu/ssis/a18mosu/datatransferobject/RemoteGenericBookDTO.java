package nu.ssis.a18mosu.datatransferobject;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class RemoteGenericBookDTO {
	
	private String isbn;
	private String title;
	private String authors;
	private String image;
	private String description;
	private String language;
	
}
