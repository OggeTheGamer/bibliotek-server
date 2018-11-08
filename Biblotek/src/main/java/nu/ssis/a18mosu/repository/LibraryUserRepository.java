package nu.ssis.a18mosu.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import nu.ssis.a18mosu.model.LibraryUser;
@Repository
public interface LibraryUserRepository extends CrudRepository<LibraryUser, String> {
	
}
