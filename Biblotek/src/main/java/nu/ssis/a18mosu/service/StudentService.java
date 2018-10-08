package nu.ssis.a18mosu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nu.ssis.a18mosu.model.Student;
import nu.ssis.a18mosu.repository.StudentRepository;

@Service
public class StudentService {

	@Autowired
	private StudentRepository studentRepo;
	
	public boolean exists(String rfid) {
		return studentRepo.existsById(rfid);
	}
	
	public void registerStudent(String rfid) {
		Student s = new Student();
		s.setRfid(rfid);
		studentRepo.save(s);
	}
}
