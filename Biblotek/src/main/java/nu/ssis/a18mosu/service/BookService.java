package nu.ssis.a18mosu.service;

import java.io.IOException;
import java.util.Date;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import nu.ssis.a18mosu.model.Book;
import nu.ssis.a18mosu.model.GenericBook;
import nu.ssis.a18mosu.repository.BookRepository;
import nu.ssis.a18mosu.repository.GenericBookRepository;

@Service
public class BookService {

	@Autowired
	private BookRepository bookRepo;
	@Autowired
	private BookApiClient bookApiClient;
	@Autowired
	private GenericBookRepository genericBookRepo;

	public Book getBook(final String id) {
		return bookRepo.findById(id).get();
	}
	
	public GenericBook getGenericBook(final String isbn) {
		return genericBookRepo.findById(isbn).get();
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

	public Page<GenericBook> getPage(final int page) {
		return genericBookRepo.findAll(PageRequest.of(page, 20));
	}
	
	public GenericBook getRemoteGenericBook(String isbn) {
		try {
			return bookApiClient.getGenericBook(isbn);
		} catch (JSONException | IOException e) {
			GenericBook gBook = new GenericBook();
			gBook.setIsbn(isbn);
			gBook.setDescription("Vi kunde inte hitta boken i databasen men du kan låna den ändå!");
			gBook.setTitle("Okänd title");
			return gBook;
		}
	}

	public void updateGenericBook(GenericBook genericBook) {
		genericBookRepo.save(genericBook);		
	}

}
