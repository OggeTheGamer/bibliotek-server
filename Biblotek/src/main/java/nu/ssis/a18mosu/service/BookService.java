package nu.ssis.a18mosu.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import nu.ssis.a18mosu.datatransferobject.CommentDTO;
import nu.ssis.a18mosu.exception.NotFoundException;
import nu.ssis.a18mosu.model.Book;
import nu.ssis.a18mosu.model.Comment;
import nu.ssis.a18mosu.model.GenericBook;
import nu.ssis.a18mosu.model.LibraryUser;
import nu.ssis.a18mosu.repository.BookRepository;
import nu.ssis.a18mosu.repository.CommentRepository;
import nu.ssis.a18mosu.repository.GenericBookRepository;

@Service
public class BookService {
	

	private static final GenericBook DEFAULT_BOOK;
	
	static {
		DEFAULT_BOOK = new GenericBook();
		DEFAULT_BOOK.setTitle("Okänd titel");
		DEFAULT_BOOK.setDescription("Vi kunde inte hitta boken i databasen men du kan låna den ändå!");
	}
	
	@Autowired
	private BookRepository bookRepo;
	@Autowired
	private BookApiClient bookApiClient;
	@Autowired
	private GenericBookRepository genericBookRepo;
	@Autowired
	private CommentRepository commentRepo;
	@Autowired
	private ModelMapper modelMapper;

	public Book getBook(final String id) {
		return bookRepo.findById(id).orElseThrow(()-> new NotFoundException());
	}
	
	public GenericBook getGenericBook(final String isbn) {
		return genericBookRepo.findById(isbn).orElseThrow(()-> new NotFoundException());
	}

	public void registerBook(final String isbn, final String bookId) {
		GenericBook genericBook = genericBookRepo.findById(isbn)
				.orElse(genericBookRepo.insert(getRemoteGenericBook(isbn)));
		Book book = new Book();
		book.setRegisteredDate(new Date());
		book.setBook(genericBook);
		book.setId(bookId);
		bookRepo.insert(book);
	}

	private GenericBook getRemoteGenericBook(String isbn) {
		try {
			return bookApiClient.getGenericBook(isbn);
		} catch (JSONException | IOException e) {
			GenericBook genericBook = new GenericBook();
			genericBook.setRegisteredDate(new Date());
			genericBook.setComments(new ArrayList<Comment>());
			genericBook.setIsbn(isbn);
			modelMapper.map(DEFAULT_BOOK, genericBook);
			return genericBook;
		}
	}

	public void updateGenericBook(GenericBook genericBook) {
		genericBookRepo.save(genericBook);		
	}
	
	public Page<GenericBook> getPage(final int page) {
		return genericBookRepo.findAll(PageRequest.of(page, 20));
	}

}