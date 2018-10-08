package nu.ssis.a18mosu.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import nu.ssis.a18mosu.datatransferobject.BookRegisterDTO;
import nu.ssis.a18mosu.service.BookService;

@Controller
public class AdminController {
	
	@Autowired
	private BookService bookService;

	@PostMapping("/admin/registerbook")
	public String registerBook(@Valid @ModelAttribute("book") BookRegisterDTO book, BindingResult result, Model model) {
		if(result.hasErrors()) {
			model.addAttribute("message", result.getAllErrors().stream().map(e -> e.getCode()).reduce("", (a, b) -> a + " " + b));
		} else {
			bookService.registerBook(book.getIsbn(), book.getId());
		}
		model.addAttribute("book", new BookRegisterDTO());
		return "registerbook.html";
	}
	
	@GetMapping("/admin/registerbook")
	public String getRegisterBook(Model model) {
		model.addAttribute("book", new BookRegisterDTO());
		return "registerbook.html";
	}
	
}
