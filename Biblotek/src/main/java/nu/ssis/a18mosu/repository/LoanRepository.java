package nu.ssis.a18mosu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import nu.ssis.a18mosu.model.Loan;
@Repository
public interface LoanRepository extends JpaRepository<Loan, Integer> {

	@Query("select loan from Loan loan where loan.book.id = ?1")
	List<Loan> findByBookId(@Param("bookId") Integer bookId);
	
	@Query("select loan from Loan loan where loan.book.book.isbn = ?1")
	List<Loan> findByIsbn(@Param("isbn") String isbn);

	@Query("select loan from Loan loan where loan.book.id = ?1 and loan.returnedDate = null")
	List<Loan> findActiveByBookId(Integer bookId);

	@Query("select loan from Loan loan where loan.returnedDate = null")
	List<Loan> findAllActive();
}