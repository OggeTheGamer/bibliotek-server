	package nu.ssis.a18mosu.service;

import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nu.ssis.a18mosu.datatransferobject.CommentDTO;
import nu.ssis.a18mosu.exception.NotFoundException;
import nu.ssis.a18mosu.model.Comment;
import nu.ssis.a18mosu.model.GenericBook;
import nu.ssis.a18mosu.model.LibraryUser;
import nu.ssis.a18mosu.repository.CommentRepository;
import nu.ssis.a18mosu.repository.GenericBookRepository;

@Service
public class CommentService {
	
	@Autowired
	private GenericBookRepository genericBookRepo;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private CommentRepository commentRepo;
	
	public void comment(CommentDTO commentDto, LibraryUser libraryUser, String isbn) {
		GenericBook genericBook = genericBookRepo.findById(isbn).orElseThrow(() -> new NotFoundException("hittade inte boken"));
		
		Comment comment = modelMapper.map(commentDto, Comment.class);
		comment.setAuthor(libraryUser);
		comment.setCreatedAt(new Date());
		comment.setBook(genericBookRepo.findById(isbn).get());
		
		commentRepo.save(comment);
		genericBook.getComments().add(comment);
		genericBookRepo.save(genericBook);
		
	}

	public List<Comment> findCommentsByIsbn(String isbn) {
		return commentRepo.findByIsbn(isbn);
	}

	
}
