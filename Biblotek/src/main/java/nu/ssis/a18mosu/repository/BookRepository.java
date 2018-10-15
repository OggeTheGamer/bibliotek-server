package nu.ssis.a18mosu.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import nu.ssis.a18mosu.model.Book;

public interface BookRepository extends MongoRepository<Book, String> {
	
	@Query("{\"book.$id\":?0, avaliable:true}")
	List<Book> findAvaliableBooksByIsbn(String isbn);
	
}
