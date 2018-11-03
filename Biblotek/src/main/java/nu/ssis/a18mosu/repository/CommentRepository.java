package nu.ssis.a18mosu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import nu.ssis.a18mosu.model.Comment;

public interface CommentRepository extends CrudRepository<Comment, Integer> {
	
	@Query("SELECT comment FROM Comment comment WHERE comment.book.isbn = ?1")
	List<Comment> findByIsbn(String isbn);

}
