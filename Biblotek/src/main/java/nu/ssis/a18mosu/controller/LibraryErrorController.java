package nu.ssis.a18mosu.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LibraryErrorController implements ErrorController {
	
	private static final String ERROR_PATH = "/error";
	
	@GetMapping(ERROR_PATH)
	public String error(HttpServletRequest httpRequest, Model model) {
		
		Integer code = (Integer) httpRequest.getAttribute("javax.servlet.error.status_code");
		model.addAttribute("code", code);
		return "error/error.html";
	}

	@Override
	public String getErrorPath() {
		return ERROR_PATH;
	}

}
