package nu.ssis.a18mosu.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import nu.ssis.a18mosu.model.LibraryUser;
import nu.ssis.a18mosu.security.LibraryUserDetailsService;

@Controller	
public class LibraryUserController {

	@Autowired
	private LibraryUserDetailsService libraryUserDetailsService;
	
	@GetMapping("/user")
	public String getUser(Model model) {
		return "user/user.html";
	}
	
	@GetMapping("/user/settings")
	public String getUserSettings(Model model) {
		return "user/usersettings.html";
	}

	@PostMapping("/user/settings")
	public String updateUserSettings(Model model) {
		return "user/usersettings.html";
	}
	
	@ModelAttribute("user")
	public LibraryUser addUser(Principal principal) {
		return libraryUserDetailsService.loadUserByUsername(principal.getName());
	}
}
