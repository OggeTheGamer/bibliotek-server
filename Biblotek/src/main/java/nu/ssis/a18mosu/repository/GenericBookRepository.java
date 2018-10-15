package nu.ssis.a18mosu.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import nu.ssis.a18mosu.model.GenericBook;

public interface GenericBookRepository extends MongoRepository<GenericBook, String> {
	

}
