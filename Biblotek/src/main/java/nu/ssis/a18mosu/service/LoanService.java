package nu.ssis.a18mosu.service;

import static nu.ssis.a18mosu.model.Book.BookStatus.AVALIABLE;
import static nu.ssis.a18mosu.model.Book.BookStatus.LOANED;
import static nu.ssis.a18mosu.model.Book.BookStatus.NOT_FOUND;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nu.ssis.a18mosu.model.Book;
import nu.ssis.a18mosu.model.Book.BookStatus;
import nu.ssis.a18mosu.model.Loan;
import nu.ssis.a18mosu.model.Student;
import nu.ssis.a18mosu.repository.BookRepository;
import nu.ssis.a18mosu.repository.GenericBookRepository;
import nu.ssis.a18mosu.repository.LoanRepository;
import nu.ssis.a18mosu.repository.StudentRepository;

@Service
public class LoanService {

	@Autowired
	private LoanRepository loanRepo;
	@Autowired
	private StudentRepository studentRepo;
	@Autowired
	private BookRepository bookRepo;
	
	public void loanBook(final String rfid, final String isbn) {
		Loan loan = new Loan();
		Student student = studentRepo.findById(rfid).get();
		loan.setBook(bookRepo.findById(isbn).get());
		loan.setLoanedDate(new Date());
		loan.setLoanedTo(student);
		loan.setReturned(false);
		loanRepo.save(loan);
	}
	
	public boolean returnBook(final String bookId) {
		List<Loan> loans = loanRepo.findActiveLoanById(bookId);
		if(status(bookId) != LOANED) {
			return false;
		}
		Loan loan = loans.get(0);
		loan.setReturnedDate(new Date());
		loan.setReturned(true);
		loanRepo.save(loan);
		return true;
	}
	
	public BookStatus status(final String bookId) {
		if(bookRepo.existsById(bookId)) {
			return loanRepo.findActiveLoanById(bookId).size() > 0 ? LOANED : AVALIABLE;
		} else {
			return NOT_FOUND;
		}
	}
	
	
}
