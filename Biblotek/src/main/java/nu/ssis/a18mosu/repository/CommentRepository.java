package nu.ssis.a18mosu.repository;

import org.springframework.data.repository.CrudRepository;

import nu.ssis.a18mosu.model.Comment;

public interface CommentRepository extends CrudRepository<Comment, Integer> {

}
