package nu.ssis.a18mosu.controller;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import nu.ssis.a18mosu.clients.RecaptchaClient;
import nu.ssis.a18mosu.datatransferobject.LoanBookDTO;
import nu.ssis.a18mosu.datatransferobject.ReturnBookDTO;
import nu.ssis.a18mosu.model.LibraryUser;
import nu.ssis.a18mosu.model.Loan;
import nu.ssis.a18mosu.service.BookService;
import nu.ssis.a18mosu.service.EmailService;
import nu.ssis.a18mosu.service.LoanService;
import nu.ssis.a18mosu.validator.RecaptchaConstraint;

@Controller
public class LoanController {

	@Autowired
	private LoanService loanService;
	@Autowired
	private EmailService emailService;
	@Autowired
	private BookService bookService;
	@Autowired
	private RecaptchaClient recaptchaClient;
	
	@PostMapping("/loan")
	public String makeLoan(
			@Valid @ModelAttribute LoanBookDTO loanBookDto,
			@ModelAttribute("user") LibraryUser user,
			@RequestParam("g-recaptcha-response") String recaptchaResponse,
			BindingResult result
			) {
		if(!result.hasErrors() && recaptchaClient.verify(recaptchaResponse)) {
			Loan loan = loanService.loanBook(user, loanBookDto);
			try {
				emailService.sendThanksMail(loan);
			} catch (MessagingException e) {
				e.printStackTrace(); //XXX
			}
			return "redirect:/book/" + loan.getBook().getBook().getIsbn();
			
		} else {
			return "/"; //XXX error page?
		}
	}
	
	@PostMapping("/loan/return")
	public String returnBook(
			@Valid @ModelAttribute ReturnBookDTO returnBookDto,
			@ModelAttribute("user") LibraryUser user,
			BindingResult result
			) {
		if(!result.hasErrors()) {
			Loan loan = loanService.getActiveLoanByBookId(returnBookDto.getBookId());
			if(loan.getLoanTaker().getId().equals(user.getId())) {
				loanService.returnBook(returnBookDto.getBookId());
			} 
		}
		
		return "/";
	}
	
	@GetMapping("/loan/{isbn}")
	public String loanBook(@PathVariable("isbn") String isbn, Model model) {
		model.addAttribute("book", bookService.getGenericBook(isbn));
		model.addAttribute("books", bookService.getAvaliableBooksByIsbn(isbn));
		model.addAttribute("loanBookDto", new LoanBookDTO());
		return "loanbook.html";
		
	}
	
}