package nu.ssis.a18mosu.datatransferobject;

import lombok.Data;

@Data
public class UpdateBookDTO {
	
	private String title;
	private String authors;
	private String image;
	private String description;
	private String language;

}