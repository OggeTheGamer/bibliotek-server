package nu.ssis.a18mosu.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import nu.ssis.a18mosu.model.Comment;

public interface CommentRepository extends MongoRepository<Comment, String> {

}
