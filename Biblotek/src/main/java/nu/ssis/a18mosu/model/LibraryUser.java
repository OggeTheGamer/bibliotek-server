package nu.ssis.a18mosu.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Document(collection = "users")
public class LibraryUser implements UserDetails {

	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	private static final long serialVersionUID = 3913729226683610419L;
	
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	private static final List<LibraryUserRole> STARTER_ROLES = Arrays.asList(LibraryUser.LibraryUserRole.ROLE_USER);
	

	@Id
	private String id;
	private String givenName;
	private String familyName;
	private UserSettings userSettings;
	private List<LibraryUserRole> roles;
	private String email;
	private Boolean enabled;
	
	public static LibraryUser getDefault() {
		LibraryUser libraryUser = new LibraryUser();
		libraryUser.setRoles(STARTER_ROLES);
		libraryUser.setEnabled(true);
		UserSettings userSettings = UserSettings.getDefault();
		libraryUser.setUserSettings(userSettings);
		return libraryUser;
	}

	public enum LibraryUserRole {
		ROLE_ADMIN, ROLE_TEACHER, ROLE_STUDENT, ROLE_USER
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

}