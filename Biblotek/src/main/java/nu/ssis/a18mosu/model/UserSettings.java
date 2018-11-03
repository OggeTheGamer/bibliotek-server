package nu.ssis.a18mosu.model;

import javax.persistence.Embeddable;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Embeddable 
public class UserSettings {
	
	@Id 
	private String id;
	private Boolean sendThanksMail;
	private Boolean sendReturnMail;
	
}
