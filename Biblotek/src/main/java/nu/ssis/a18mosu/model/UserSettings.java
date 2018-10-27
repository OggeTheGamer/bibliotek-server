package nu.ssis.a18mosu.model;

import javax.persistence.Embeddable;
import javax.persistence.Id;

import lombok.Data;

@Data @Embeddable 
public class UserSettings {
	
	@Id 
	private String id;
	private Boolean sendThanksMail;
	private Boolean sendReturnMail;
	
	public static UserSettings getDefault() {
		UserSettings userSettings = new UserSettings();
		userSettings.setSendReturnMail(true);
		userSettings.setSendThanksMail(true);
		return userSettings;
	}
	
}
