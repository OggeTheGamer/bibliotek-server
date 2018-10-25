package nu.ssis.a18mosu.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import nu.ssis.a18mosu.datatransferobject.RemoteGenericBookDTO;
import nu.ssis.a18mosu.exception.NotFoundException;
import nu.ssis.a18mosu.model.Book;
import nu.ssis.a18mosu.model.Comment;
import nu.ssis.a18mosu.model.GenericBook;
import nu.ssis.a18mosu.repository.BookRepository;
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
	private GenericBookRepository genericBookRepo;
	@Autowired
	private ModelMapper modelMapper;
	private static Random random = new Random();
	
	private List<RemoteBookApiClient> bookApiClients;
	
	@Autowired
	public BookService(ApplicationContext applicationContext) {
		bookApiClients = new ArrayList<RemoteBookApiClient>(applicationContext.getBeansOfType(RemoteBookApiClient.class).values());
	}

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
		GenericBook genericBook = new GenericBook();
		genericBook.setRegisteredDate(new Date());
		genericBook.setComments(new ArrayList<Comment>());
		genericBook.setIsbn(isbn);

		for(RemoteBookApiClient client : bookApiClients) {
			Optional<RemoteGenericBookDTO> optionalBook = client.getRemoteGenericBook(isbn);
			if(optionalBook.isPresent()) {
				modelMapper.map(optionalBook.get(), genericBook);
				return genericBook;
			}
		}
		
		modelMapper.map(DEFAULT_BOOK, genericBook);
		return genericBook;
	}

	public void updateGenericBook(GenericBook genericBook) {
		genericBookRepo.save(genericBook);		
	}
	
	public Page<GenericBook> getPage(final int page) {
		return genericBookRepo.findAll(PageRequest.of(page, 20));
	}

}