package nu.ssis.a18mosu.model;

import lombok.Data;

@Data
public class UserSettings {
	
	private Boolean sendThanksMail;
	private Boolean sendReturnMail;
	
	public static UserSettings getDefault() {
		UserSettings userSettings = new UserSettings();
		userSettings.setSendReturnMail(true);
		userSettings.setSendThanksMail(true);
		return userSettings;
	}
	
}
