package nu.ssis.a18mosu.repository;

import org.springframework.data.repository.CrudRepository;

import nu.ssis.a18mosu.model.LibraryUser;

public interface LibraryUserRepository extends CrudRepository<LibraryUser, String> {
	
}
