package nu.ssis.a18mosu.exception;

import java.util.NoSuchElementException;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class NotFoundHandler {
	
	@ExceptionHandler(NoSuchElementException.class)
	public String handleException(NoSuchElementException e, Model model) {
		model.addAttribute("code", 404);
	    return "/error/error.html";
	}

}
