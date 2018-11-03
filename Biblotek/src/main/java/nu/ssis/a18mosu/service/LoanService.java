package nu.ssis.a18mosu.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nu.ssis.a18mosu.model.Book;
import nu.ssis.a18mosu.model.Book.BookStatus;
import nu.ssis.a18mosu.model.LibraryUser;
import nu.ssis.a18mosu.model.Loan;
import nu.ssis.a18mosu.repository.BookRepository;
import nu.ssis.a18mosu.repository.LibraryUserRepository;
import nu.ssis.a18mosu.repository.LoanRepository;

@Service
public class LoanService {

	@Autowired
	private LoanRepository loanRepo;
	@Autowired
	private BookRepository bookRepo;
	
	public Loan loanBook(LibraryUser user, Integer bookId) {
		Loan loan = new Loan();
		loan.setBook(bookRepo.findById(bookId).get());
		loan.setLoanedDate(new Date());
		loan.setLoanTaker(user);
		Book book = bookRepo.findById(bookId).get(); 
		bookRepo.save(book);
		return loanRepo.save(loan);
	}

	public BookStatus bookStatus(Integer bookId) {
		if(!bookRepo.findById(bookId).isPresent()) {
			return BookStatus.NOT_FOUND;
		} else {
			List<Loan> loans = loanRepo.findByBookId(bookId);
			for(Loan loan : loans) {
				if(loan.getReturnedDate() == null) {
					return BookStatus.LOANED;
				}
			}
			return BookStatus.AVALIABLE;
		}
	}
	
}
