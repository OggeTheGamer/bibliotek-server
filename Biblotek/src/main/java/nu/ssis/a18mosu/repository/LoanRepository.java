package nu.ssis.a18mosu.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import nu.ssis.a18mosu.model.Loan;

public interface LoanRepository extends MongoRepository<Loan, String>{

	@Query("{\"book.$id\":?0, returned:false}")
	List<Loan> findActiveLoanById(String bookId);

}