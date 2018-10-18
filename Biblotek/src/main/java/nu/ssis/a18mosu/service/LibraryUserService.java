package nu.ssis.a18mosu.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nu.ssis.a18mosu.model.LibraryUser;
import nu.ssis.a18mosu.repository.LibraryUserRepository;

@Service
public class LibraryUserService {

	@Autowired
	private LibraryUserRepository libraryUserRepo;

	public Optional<LibraryUser> getById(String id) {
		return libraryUserRepo.findById(id);
	}

	public LibraryUser register(LibraryUser user) {
		return libraryUserRepo.insert(user);
	}

}
