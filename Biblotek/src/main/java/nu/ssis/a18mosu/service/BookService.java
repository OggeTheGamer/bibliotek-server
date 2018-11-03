package nu.ssis.a18mosu.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import nu.ssis.a18mosu.clients.RemoteBookApiClient;
import nu.ssis.a18mosu.datatransferobject.BookRegisterDTO;
import nu.ssis.a18mosu.datatransferobject.RemoteGenericBookDTO;
import nu.ssis.a18mosu.exception.NotFoundException;
import nu.ssis.a18mosu.model.Book;
import nu.ssis.a18mosu.model.Comment;
import nu.ssis.a18mosu.model.GenericBook;
import nu.ssis.a18mosu.model.Book.BookStatus;
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
	private LoanService loanService;
	@Autowired
	private ModelMapper modelMapper;
	private static Random random = new Random();

	private List<RemoteBookApiClient> bookApiClients;

	@Autowired
	public BookService(ApplicationContext applicationContext) {
		bookApiClients = new ArrayList<RemoteBookApiClient>(
				applicationContext.getBeansOfType(RemoteBookApiClient.class).values());
	}

	public Book getBook(Integer id) {
		return bookRepo.findById(id).orElseThrow(() -> new NotFoundException());
	}

	public GenericBook getGenericBook(final String isbn) {
		return genericBookRepo.findById(isbn).orElseThrow(() -> new NotFoundException());
	}

	public void registerBook(BookRegisterDTO bookDto) {
		GenericBook genericBook = genericBookRepo.findById(bookDto.getIsbn())
				.orElse(genericBookRepo.save(getRemoteGenericBook(bookDto.getIsbn())));
		Book book = modelMapper.map(bookDto, Book.class);
		book.setRegisteredDate(new Date());
		book.setBook(genericBook);
		bookRepo.save(book);
	}

	private GenericBook getRemoteGenericBook(String isbn) {
		GenericBook genericBook = new GenericBook();
		genericBook.setRegisteredDate(new Date());
		genericBook.setComments(new ArrayList<Comment>());
		genericBook.setIsbn(isbn);

		for (RemoteBookApiClient client : bookApiClients) {
			Optional<RemoteGenericBookDTO> optionalBook = client.getRemoteGenericBook(isbn);
			if (optionalBook.isPresent()) {
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

	public String getRandomIsbn() {
		int numBooks = (int) genericBookRepo.count();
		if(numBooks == 0) throw new NotFoundException(""); //FIXME så jävla fult
		return genericBookRepo.findAll(PageRequest.of(random.nextInt(numBooks), 1)).getContent().get(0).getIsbn();
	}

	public List<Book> getBooksByIsbn(String isbn) {
		return bookRepo.findByIsbn(isbn);
	}

	// FIXME Very inefficient,
	public List<Book> getAvaliableBooksByIsbn(String isbn) {
		return bookRepo.findByIsbn(isbn)
				.stream()
				.filter(b -> loanService.bookStatus(b.getId()) == BookStatus.AVALIABLE)
				.collect(Collectors.toList());
	}

}