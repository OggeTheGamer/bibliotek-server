package nu.ssis.a18mosu.controller;

import java.security.Principal;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import nu.ssis.a18mosu.model.LibraryUser;
import nu.ssis.a18mosu.model.Loan;
import nu.ssis.a18mosu.service.BookService;
import nu.ssis.a18mosu.service.EmailService;
import nu.ssis.a18mosu.service.LoanService;

@Controller
public class LoanController {

	@Autowired
	private LoanService loanService;
	@Autowired
	private EmailService emailService;
	@Autowired
	private BookService bookService;
	
	@PostMapping("/loan/{bookId}")
	public Object makeLoan(@PathVariable("bookId") Integer bookId, Model model) {
		Loan loan = loanService.loanBook("userid", bookId);
		try {
			emailService.sendThanksMail(loan);
		} catch (MessagingException e) {
			e.printStackTrace(); //XXX
		}
		return "book.html";

	}
	
	@GetMapping("/loan/{isbn}")
	public Object loanBook(@PathVariable("isbn") String isbn, Model model) {
		model.addAttribute("book", bookService.getGenericBook(isbn));
		model.addAttribute("books", bookService.getBooksByIsbn(isbn));
		return "loanbook.html";
		
	}
	
	@ModelAttribute("user")
	public LibraryUser addUser(Principal principal) {
		return libraryUserDetailsService.loadUserByUsername(principal.getName());
	}
	
}