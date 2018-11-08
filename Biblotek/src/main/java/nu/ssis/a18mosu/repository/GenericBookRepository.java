package nu.ssis.a18mosu.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import nu.ssis.a18mosu.model.GenericBook;

@Repository
public interface GenericBookRepository extends PagingAndSortingRepository<GenericBook, String> {
	
	
}
