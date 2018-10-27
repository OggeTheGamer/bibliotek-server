package nu.ssis.a18mosu.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import nu.ssis.a18mosu.model.LibraryUser;
import nu.ssis.a18mosu.security.LibraryUserDetailsService;

public class UserControllerAdvice {
	
	@Autowired
	private LibraryUserDetailsService libraryUserDetailsService;
	
	@ModelAttribute("user")
	public LibraryUser addUser(Principal principal) {
		return libraryUserDetailsService.loadUserByUsername(principal.getName());
	}

}
