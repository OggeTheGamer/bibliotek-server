package nu.ssis.a18mosu.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import nu.ssis.a18mosu.model.Student;

public interface StudentRepository extends MongoRepository<Student, String> {
	
}
