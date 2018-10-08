package nu.ssis.a18mosu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import nu.ssis.a18mosu.model.GenericBook;
import nu.ssis.a18mosu.service.BookService;

@Controller
public class WebController {
	
	@Autowired
	private BookService bookService;
	

	@GetMapping("/book/{isbn}")
	public String specificBook(@PathVariable("isbn") String isbn, Model model) {
		GenericBook book = bookService.getGenericBook(isbn);
		model.addAttribute("book", book);
		return "book.html";
	}
	
	@GetMapping("/")
	public String index(@RequestParam(defaultValue = "0") int page, Model model) {
		model.addAttribute("books", bookService.getPage(page));
		return "index.html";
	}
	
}