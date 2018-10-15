package nu.ssis.a18mosu.controller;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import nu.ssis.a18mosu.model.Book;
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

	@GetMapping("/book/{isbn}")
	public String specificBook(@PathVariable("isbn") String isbn, Model model) {
		GenericBook book = bookService.getGenericBook(isbn);
		model.addAttribute("book", book);
		model.addAttribute("status", loanService.genericBookStatus(isbn).toString()); //TODO
		
		return "book.html";
	}
	
	private Loan getExampleLoan(GenericBook book) {
		LibraryUser student = new LibraryUser();
		student.setEmailAdress("movitz.sunar@gmail.com");
		student.setName("Movitz Sunar");
		Loan loan = new Loan();
		loan.setLoanTaker(student);
		Book b = new Book();
		b.setBook(book);
		loan.setBook(b);
		return loan;
	}

	@GetMapping("/book/loan/{bookId}")
	public Object loanBook(@PathVariable("bookId") String bookId, Model model, DefaultOidcUser p) {
		Book book = bookService.getBook(bookId);
		try {
			emailService.sendThanksMail(getExampleLoan(book.getBook()));
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return "book.html";
	}

	@GetMapping("/")
	public String index(@RequestParam(defaultValue = "0") int page, Model model) {
		model.addAttribute("books", bookService.getPage(page));
		return "index.html";
	}

}
