package nu.ssis.a18mosu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller	
public class LibraryUserController {

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
