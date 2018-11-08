package nu.ssis.a18mosu.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import nu.ssis.a18mosu.datatransferobject.LoanBookDTO;
import nu.ssis.a18mosu.model.Book;
import nu.ssis.a18mosu.model.Book.BookStatus;
import nu.ssis.a18mosu.model.LibraryUser;
import nu.ssis.a18mosu.model.Loan;
import nu.ssis.a18mosu.repository.BookRepository;
import nu.ssis.a18mosu.repository.LoanRepository;

@Service
public class LoanService {

	@Autowired
	private LoanRepository loanRepo;
	@Autowired
	private BookRepository bookRepo;
	@Autowired
	private EmailService emailService;

	public Loan getActiveLoanByBookId(Integer bookId) {
		return loanRepo.findActiveByBookId(bookId).get(0);
	}
	
	public Loan loanBook(LibraryUser user, LoanBookDTO loanBookDto) {
		Loan loan = new Loan();
		loan.setBook(bookRepo.findById(loanBookDto.getBookId()).get());
		loan.setLoanedDate(new Date());
		loan.setLoanTaker(user);
		loan = loanRepo.save(loan);
		try {
			emailService.sendThanksMail(loan);
		} catch (MessagingException e ) {
			e.printStackTrace(); //XXX
		}
		
		return loan;
	}
	
	public void returnBook(Integer bookId) {
		Loan loan = loanRepo.findActiveByBookId(bookId).get(0);
		loan.setReturnedDate(new Date());
		loanRepo.save(loan);
	}

	public BookStatus bookStatus(Integer bookId) {
		if (!bookRepo.existsById(bookId)) {
			return BookStatus.NOT_FOUND;
		} else {
			List<Loan> loans = loanRepo.findByBookId(bookId);
			for (Loan loan : loans) {
				if (loan.getReturnedDate() == null) {
					return BookStatus.LOANED;
				}
			}
			return BookStatus.AVALIABLE;
		}
	}

	public BookStatus genericBookStatus(String isbn) {
		List<Book> books = bookRepo.findByIsbn(isbn);
		if(books.isEmpty()) {
			return BookStatus.NOT_FOUND;
		} else {
			for(Book book : books) {
				if(bookStatus(book.getId()) == BookStatus.AVALIABLE) {
					return BookStatus.AVALIABLE;
				}
			}
			return BookStatus.LOANED;
		}
	}
	
	/***
	 * Runs every day except weekends at five in the morning and notifies users with overdue loans
	 */
	@Scheduled(cron="0 0 5 * * MON-FRI")
	private void emailUsersOverdueLoans() {
		for(Loan loan : getOverdueLoans()) {
			try {
				emailService.sendReturnMail(loan);
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}
	}
	
	/***
	 * Returns loans that are overdue by one month.
	 * 
	 * @return overdue loans
	 */
	public List<Loan> getOverdueLoans() {
		return loanRepo.findAllActive() //TODO This can be done with SQL
				.stream()
				.filter(loan -> {
					Calendar c = Calendar.getInstance();
					c.add(Calendar.MONTH, -1);
					return loan.getLoanedDate().before(c.getTime());
				})
				.collect(Collectors.toList());
	}

}
