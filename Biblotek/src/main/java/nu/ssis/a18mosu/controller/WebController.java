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
import nu.ssis.a18mosu.model.Loan;
import nu.ssis.a18mosu.model.Student;
import nu.ssis.a18mosu.service.BookService;
import nu.ssis.a18mosu.service.EmailServiceImp;
import nu.ssis.a18mosu.service.LoanService;

@Controller
public class WebController {

	@Autowired
	private BookService bookService;
	@Autowired
	private LoanService loanService;
	@Autowired
	private EmailServiceImp emailService;

	@GetMapping("/book/{isbn}")
	public String specificBook(@PathVariable("isbn") String isbn, Model model) {
		GenericBook book = bookService.getGenericBook(isbn);
		model.addAttribute("book", book);
		model.addAttribute("status", loanService.genericBookStatus(isbn).toString()); //TODO
		Student student = new Student();
		student.setEmailAdress("movitz.sunar@gmail.com");
		student.setName("Movitz Sunar");
		Loan loan = new Loan();
		loan.setLoanTaker(student);
		Book b = new Book();
		b.setBook(book);
		loan.setBook(b);
		try {
			emailService.sendThanksMail(loan);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return "book.html";
	}

	@GetMapping("/book/{bookId}/loan")
	public Object loanBook(@PathVariable("bookId") String bookId, Model model, DefaultOidcUser p) {
		model.addAttribute("userAttributes", p.getAttributes());
		return "user.html";
	}

	@GetMapping("/")
	public String index(@RequestParam(defaultValue = "0") int page, Model model) {
		model.addAttribute("books", bookService.getPage(page));
		return "index.html";
	}

}
