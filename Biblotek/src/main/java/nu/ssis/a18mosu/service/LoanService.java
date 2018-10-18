package nu.ssis.a18mosu.service;

import static nu.ssis.a18mosu.model.Book.BookStatus.AVALIABLE;
import static nu.ssis.a18mosu.model.Book.BookStatus.LOANED;
import static nu.ssis.a18mosu.model.Book.BookStatus.NOT_FOUND;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nu.ssis.a18mosu.model.Book;
import nu.ssis.a18mosu.model.Book.BookStatus;
import nu.ssis.a18mosu.model.LibraryUser;
import nu.ssis.a18mosu.model.Loan;
import nu.ssis.a18mosu.repository.BookRepository;
import nu.ssis.a18mosu.repository.GenericBookRepository;
import nu.ssis.a18mosu.repository.LibraryUserRepository;
import nu.ssis.a18mosu.repository.LoanRepository;

@Service
public class LoanService {

	@Autowired
	private LoanRepository loanRepo;
	@Autowired
	private LibraryUserRepository libraryUserRepo;
	@Autowired
	private BookRepository bookRepo;
	@Autowired
	private GenericBookRepository genericBookRepo;
	
	public Loan loanBook(final String studentId, final String bookId) {
		Loan loan = new Loan();
		LibraryUser student = libraryUserRepo.findById(studentId).get();
		loan.setBook(bookRepo.findById(bookId).get());
		loan.setLoanedDate(new Date());
		loan.setLoanTaker(student);
		loan.setReturned(false);
		Book b = bookRepo.findById(bookId).get();
		b.setAvaliable(false);
		bookRepo.save(b);
		return loanRepo.insert(loan);
	}
	
	public boolean returnBook(final String bookId) {
		if(bookStatus(bookId) != LOANED) {
			return false;
		}
		List<Loan> loans = loanRepo.findActiveByBookId(bookId);
		Loan loan = loans.get(0);
		loan.setReturnedDate(new Date());
		loan.setReturned(true);
		loanRepo.save(loan);
		Book b = bookRepo.findById(bookId).get();
		b.setAvaliable(true);
		bookRepo.save(b);
		return true;
	}
	
	public BookStatus bookStatus(final String bookId) {
		if(bookRepo.existsById(bookId)) {
			return loanRepo.findActiveByBookId(bookId).size() > 0 ? LOANED : AVALIABLE;
		} else {
			return NOT_FOUND;
		}
	}
	
	public BookStatus genericBookStatus(final String isbn) {
		if(genericBookRepo.existsById(isbn)) {
			return bookRepo.findAvaliableBooksByIsbn(isbn).size() < 0 ? LOANED : AVALIABLE;
		} else {
			return NOT_FOUND;
		}
	}
	
	
}
