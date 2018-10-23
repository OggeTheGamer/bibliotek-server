package nu.ssis.a18mosu.security;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import nu.ssis.a18mosu.model.LibraryUser;
import nu.ssis.a18mosu.repository.LibraryUserRepository;

@Service
public class LibraryUserDetailsService extends OidcUserService implements UserDetailsService {
	
	
	@Autowired
	private LibraryUserRepository libraryUserRepo;
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public OidcUser loadUser(OidcUserRequest userRequest) {
		OidcUser user = super.loadUser(userRequest);
		String sub = userRequest.getIdToken().getSubject();
		try {
			loadUserByUsername(sub);
		} catch (UsernameNotFoundException e) {
			LibraryUser libraryUser = LibraryUser.getDefault();
			libraryUser.setEmail(user.getEmail());
			libraryUser.setGivenName(user.getGivenName());
			libraryUser.setFamilyName(user.getFamilyName());
			libraryUser.setId(sub);
			libraryUserRepo.insert(libraryUser);
		}
		return user;
	}

	@Override
	public LibraryUser loadUserByUsername(String id) throws UsernameNotFoundException {
		return libraryUserRepo.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
	}

}
