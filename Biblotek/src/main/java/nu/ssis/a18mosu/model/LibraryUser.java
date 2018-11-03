package nu.ssis.a18mosu.model;

import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Entity
@Table(name="library_users")
public class LibraryUser implements UserDetails {

	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	private static final long serialVersionUID = 3913729226683610419L;
	
	@Id
	private String id;
	private String givenName;	
	private String familyName;
	private UserSettings userSettings;
//	private List<TEMPROLE> roles;
	private String email;
	private Boolean enabled;
	@OneToMany(mappedBy="loanTaker")
	private List<Loan> loans;
	@OneToMany(mappedBy="book")
	private List<Comment> comments;
	
	@Deprecated
	public enum TEMPROLE {
		ROLE_USER
	}
	
	public static LibraryUser getDefault() {
		LibraryUser libraryUser = new LibraryUser();
		libraryUser.setEnabled(true);
		UserSettings userSettings = UserSettings.getDefault();
//		libraryUser.setUserSettings(userSettings);
		return libraryUser;
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