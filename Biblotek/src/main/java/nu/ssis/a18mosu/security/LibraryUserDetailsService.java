package nu.ssis.a18mosu.security;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import nu.ssis.a18mosu.model.LibraryUser;
import nu.ssis.a18mosu.model.LibraryUser.LibraryUserRole;
import nu.ssis.a18mosu.model.UserSettings;
import nu.ssis.a18mosu.repository.LibraryUserRepository;

@Service
public class LibraryUserDetailsService extends OidcUserService implements UserDetailsService {
	
	private static final List<LibraryUserRole> STARTER_ROLES = Arrays.asList(LibraryUser.LibraryUserRole.ROLE_USER);
	
	@Autowired
	private LibraryUserRepository libraryUserRepo;

	@Override
	public OidcUser loadUser(OidcUserRequest userRequest) {
		OidcUser user = super.loadUser(userRequest);
		String sub = userRequest.getIdToken().getSubject();
		try {
			loadUserByUsername(sub);
		} catch (UsernameNotFoundException e) {
			LibraryUser libraryUser = new LibraryUser();
			libraryUser.setId(sub);
			libraryUser.setEmail(user.getEmail());
			libraryUser.setUserSettings(new UserSettings());
			libraryUser.setRoles(STARTER_ROLES);
			libraryUserRepo.insert(libraryUser);
		}
		return user;
	}

	@Override
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
		return libraryUserRepo.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
	}

}
