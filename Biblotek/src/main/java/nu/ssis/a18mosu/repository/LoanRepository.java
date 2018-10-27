package nu.ssis.a18mosu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import nu.ssis.a18mosu.model.Loan;

public interface LoanRepository extends CrudRepository<Loan, String> {
	
	@Query("") //TODO
	List<Loan> findByIsbn(String isbn);

	@Query("SELECT * FROM loans WHERE book_id = :bookId")
	List<Loan> findByBookId(@Param("bookId") Integer bookId);
	
}