package nu.ssis.a18mosu.controller;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import nu.ssis.a18mosu.model.Loan;
import nu.ssis.a18mosu.service.EmailService;
import nu.ssis.a18mosu.service.LoanService;

@Controller
public class LoanController {

	@Autowired
	private LoanService loanService;
	@Autowired
	private EmailService emailService;
	
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
	
}
