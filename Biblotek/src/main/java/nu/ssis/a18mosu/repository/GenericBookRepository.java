package nu.ssis.a18mosu.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import nu.ssis.a18mosu.model.GenericBook;

public interface GenericBookRepository extends PagingAndSortingRepository<GenericBook, String> {
	
	
}
