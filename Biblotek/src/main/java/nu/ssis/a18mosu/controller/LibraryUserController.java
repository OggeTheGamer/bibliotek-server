package nu.ssis.a18mosu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import nu.ssis.a18mosu.service.LoanService;

@Controller	
public class LibraryUserController {

	@Autowired
	private LoanService loanService;
	
	@GetMapping("/user")
	public String getUser(Model model) {
		return "user/user.html";
	}
	
	@GetMapping("/user/settings")
	public String getUserSettings(Model model) {
		return "user/settings.html";
	}

	@PostMapping("/user/settings")
	public String updateUserSettings(Model model) {
		return "user/settings.html";
	}
}
