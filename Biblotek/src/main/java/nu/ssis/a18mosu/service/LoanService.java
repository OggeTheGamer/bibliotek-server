package nu.ssis.a18mosu.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

	public Loan getActiveLoanByBookId(Integer bookId) {
		return loanRepo.findActiveByBookId(bookId).get(0);
	}
	
	public Loan loanBook(LibraryUser user, LoanBookDTO loanBookDto) {
		Loan loan = new Loan();
		loan.setBook(bookRepo.findById(loanBookDto.getBookId()).get());
		loan.setLoanedDate(new Date());
		loan.setLoanTaker(user);
		return loanRepo.save(loan);
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

}
