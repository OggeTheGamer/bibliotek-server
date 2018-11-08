package nu.ssis.a18mosu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import nu.ssis.a18mosu.model.Book;

@Repository
public interface BookRepository extends CrudRepository<Book, Integer> {

	@Query("select book from Book book where book.book.isbn = ?1")
	List<Book> findByIsbn(String isbn);

}
