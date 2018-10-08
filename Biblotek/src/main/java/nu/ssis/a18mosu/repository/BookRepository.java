package nu.ssis.a18mosu.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import nu.ssis.a18mosu.model.Book;

public interface BookRepository extends MongoRepository<Book, String> {
	
}
