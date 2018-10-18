package nu.ssis.a18mosu.controller;

import javax.mail.MessagingException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import nu.ssis.a18mosu.datatransferobject.UpdateBookDTO;
import nu.ssis.a18mosu.model.GenericBook;
import nu.ssis.a18mosu.model.LibraryUser;
import nu.ssis.a18mosu.model.Loan;
import nu.ssis.a18mosu.service.BookService;
import nu.ssis.a18mosu.service.EmailService;
import nu.ssis.a18mosu.service.LoanService;

@Controller
public class BookController {

	@Autowired
	private BookService bookService;
	@Autowired
	private LoanService loanService;
	@Autowired
	private EmailService emailService;
	@Autowired
	private ModelMapper modelMapper;

	@GetMapping("/book/{isbn}")
	public String specificBook(@PathVariable("isbn") String isbn, Model model) {
		GenericBook book = bookService.getGenericBook(isbn);
		model.addAttribute("book", book);
		model.addAttribute("status", loanService.genericBookStatus(isbn).toString()); //TODO
		
		return "book.html";
	}
	
	@GetMapping("/book/{isbn}/edit")
	public String getEditPage(
			@PathVariable("isbn") String isbn, 
			Model model) {
		GenericBook genericBook = bookService.getGenericBook(isbn);
		UpdateBookDTO updateBookDto = new UpdateBookDTO();
		modelMapper.map(genericBook, updateBookDto);
		model.addAttribute("updateBookDto", updateBookDto);
		return "editbook.html";
	}
	
	@PostMapping("/book/{isbn}/edit")
	public String editGenericBook(
			@PathVariable("isbn") String isbn, 
			Model model, 
			@ModelAttribute UpdateBookDTO updateBookDto) {
		
		GenericBook genericBook = bookService.getGenericBook(isbn);
		modelMapper.map(updateBookDto, genericBook);
		bookService.updateGenericBook(genericBook);
		model.addAttribute("isbn", isbn);
		return "redirect:/book/" + isbn;
	}

	@GetMapping("/book/{bookId}/loan/")
	public Object loanBook(@PathVariable("bookId") String bookId, Model model) {
		Loan loan = loanService.loanBook("userid", bookId);
		try {
			emailService.sendThanksMail(loan);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return "book.html";
	}

	@GetMapping("/")
	@ResponseBody
	public String index(@RequestParam(defaultValue = "0") int page, Model model) {
		model.addAttribute("books", bookService.getPage(page));
		
		return ((LibraryUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getClass().getName();
//		return "index.html";
	}
	
}
