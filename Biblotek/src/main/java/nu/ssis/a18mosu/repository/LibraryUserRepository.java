package nu.ssis.a18mosu.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import nu.ssis.a18mosu.model.LibraryUser;

public interface LibraryUserRepository extends MongoRepository<LibraryUser, String> {
	
}
