package nu.ssis.a18mosu.repository;

import org.springframework.data.repository.CrudRepository;

import nu.ssis.a18mosu.model.Book;

public interface BookRepository extends CrudRepository<Book, Integer> {
	
}
